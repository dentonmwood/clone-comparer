/* Sources used:
    https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
    https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
    https://stackoverflow.com/questions/29454663/using-java-regex-read-a-text-file-to-match-multiple-patterns
 */

package com.csi.czech.reader;

import com.csi.czech.clone.Clone;
import com.csi.czech.source.Source;
import com.csi.czech.clone.PyCloneClone;
import com.csi.czech.source.PyCloneSource;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the PyClone clones. We use Google's SimpleJSON library
 * to parse the files and return the results.
 */
public class PyCloneCloneReader implements CloneReader {
    /** The SimpleJSON parser to use */
    private final JSONParser jsonParser;

    /**
     * Constructor for the reader
     * @param jsonParser the SimpleJSON parser to use
     */
    public PyCloneCloneReader(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public String extractFromFile(String inputFilename)
            throws IOException {
        File file = new File(inputFilename);
        try (BufferedReader jsonReader = new BufferedReader(
                new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            jsonReader.lines().forEach(builder::append);
            return builder.toString();
        }
    }

    public List<Clone> processJson(String json) throws IOException {
        try {
            JSONArray array = (JSONArray) this.jsonParser.parse(json);
            List<Clone> clones = new ArrayList<>();
            for (Object o : array) {
                JSONObject object = (JSONObject) o;
                String value = (String) object.get("value");
                Long matchWeight = (Long) object.get("match_weight");
                JSONObject sourceObject = (JSONObject) object.get("origins");

                List<Source> origins = this.getSources(sourceObject);
                addClones(origins, clones, value, matchWeight);
            }
            return clones;
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<Clone> readClones(String inputFilename) throws IOException {
        // Check for empty array
        try (Scanner s = new Scanner(new File(inputFilename))) {
            String t = s.next();
            if (t.equals("[]")) {
                return new ArrayList<>();
            }
            return processJson(extractFromFile(inputFilename));
        }
    }

    /**
     * Individual reader method which parses file sources and returns the results.
     * @param sourceObject the data for the sources
     * @return the parsed sources
     * @throws IOException if the sources can't be parsed
     */
    private List<Source> getSources(JSONObject sourceObject)
            throws IOException {
        List<Source> origins = new ArrayList<>();
        Set<String> keys = new HashSet<>(sourceObject.keySet());
        for (String originKey: keys) {
            Pattern p = Pattern.compile("([^ ]+) \\(([0-9]+), ([0-9]+)\\)");
            Matcher m = p.matcher(originKey);
            if (m.matches()) {
                String filename = m.group(1);
                Long startLine = Long.parseLong(m.group(2));
                Long endLine = Long.parseLong(m.group(3));
                Double weight = (Double) sourceObject.get(originKey);
                origins.add(new PyCloneSource(filename, startLine, endLine,
                        weight));
            } else {
                throw new IOException("Invalid PyClone source");
            }
        }
        return origins;
    }

    /**
     * Adds clones to the returned result. This is intensive because if PyClone detects three
     * or more sources which are all clones of each other, it returns them as a cluster instead
     * of individual pairings. This function breaks the cluster into pairs of 2 to make them
     * easier to compare.
     * @param sources the file sources to break up
     * @param clones the list of clones (to add the clones to)
     * @param value the Python AST class name of the clones
     * @param matchWeight the weight of the similarity between the clones
     */
    private void addClones(List<Source> sources, List<Clone> clones, String value, Long matchWeight) {
        // For comparison to other tools, create a clone for each two origins
        for (int i = 0; i < sources.size(); i++) {
            for (int j = i + 1; j < sources.size(); j++) {
                Source source1 = sources.get(i);
                Source source2 = sources.get(j);
                Clone clone = new PyCloneClone(value, matchWeight);
                clone.addSource(source1);
                clone.addSource(source2);

                clones.add(clone);
            }
        }
    }
}
