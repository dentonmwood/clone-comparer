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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the PyClone clones. We use Google's SimpleJSON library
 * to parse the files and return the results.
 */
public class PyCloneCloneReader implements CloneReader {
    /** The SimpleJSON parser to use */
    private JSONParser jsonParser;

    /**
     * Constructor for the reader
     * @param jsonParser the SimpleJSON parser to use
     */
    public PyCloneCloneReader(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    /* Sources used:
        https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
        https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
        https://stackoverflow.com/questions/29454663/using-java-regex-read-a-text-file-to-match-multiple-patterns
     */
    @Override
    public List<Clone> readClones(String inputFilename) throws IOException {
        try {
            File file = new File(inputFilename);

            // Check for empty array
            Scanner s = new Scanner(file);
            String t = s.next();
            if (t.equals("[]")) {
                return new ArrayList<>();
            }

            JSONArray array = (JSONArray) this.jsonParser.parse(new FileReader(file));
            List<Clone> clones = new ArrayList<>();
            for (Object o: array) {
                JSONObject object = (JSONObject) o;
                String value = (String) object.get("value");
                Long matchWeight = (Long) object.get("match_weight");
                JSONObject sourceObject = (JSONObject) object.get("origins");

                List<Source> origins = this.getSources(sourceObject.keySet());
                addClones(origins, clones, value, matchWeight);
            }
            return clones;
        } catch (ParseException e) {
            throw new IOException("Unable to parse JSON file: " + e.getMessage());
        }
    }

    /**
     * Individual reader method which parses file sources and returns the results.
     * @param sourceKeys the data for the sources
     * @return the parsed sources
     * @throws IOException if the sources can't be parsed
     */
    private List<Source> getSources(Set<String> sourceKeys) throws IOException {
        List<Source> origins = new ArrayList<>();
        for (String originKey: sourceKeys) {
            Pattern p = Pattern.compile("([^ ]+) \\(([0-9]+), ([0-9]+)\\)");
            Matcher m = p.matcher(originKey);
            if (m.matches()) {
                String filepath = m.group(1);
                String filename = FilenameUtils.getName(filepath);
                Long startLine = Long.parseLong(m.group(2));
                Long endLine = Long.parseLong(m.group(3));
                origins.add(new PyCloneSource(filename, startLine, endLine));
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
        for (Source origin1: sources) {
            for (Source origin2: sources) {
                if (!origin1.equals(origin2)) {
                    // Implemented as a set to make sure no duplicates
                    Clone clone = new PyCloneClone(value, matchWeight);
                    clone.addSource(origin1);
                    clone.addSource(origin2);

                    if (!clones.contains(clone)) {
                        clones.add(clone);
                    }
                }
            }
        }
    }
}
