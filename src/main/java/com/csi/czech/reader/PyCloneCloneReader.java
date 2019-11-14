package com.csi.czech.reader;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;
import com.csi.czech.pyclone.PyCloneClone;
import com.csi.czech.pyclone.PyCloneSource;
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

public class PyCloneCloneReader implements CloneReader {
    private JSONParser jsonParser;

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
            JSONArray array = (JSONArray) this.jsonParser.parse(new FileReader(file));

            List<Clone> clones = new ArrayList<>();
            for (Object o: array) {
                JSONObject object = (JSONObject) o;
                String value = (String) object.get("value");
                Long matchWeight = (Long) object.get("match_weight");
                JSONObject originObject = (JSONObject) object.get("origins");

                List<Source> origins = this.getOrigins(originObject.keySet(), value);
                addClones(origins, clones, value, matchWeight);
            }
            return clones;
        } catch (ParseException e) {
            throw new IOException("Unable to parse JSON file: " + e.getMessage());
        }
    }

    private List<Source> getOrigins(Set<String> originKeys, String value) throws IOException {
        List<Source> origins = new ArrayList<>();
        for (String originKey: originKeys) {
            Pattern p = Pattern.compile("([^ ]*) \\(L: ([0-9]*) C: ([0-9]*)\\)");
            Matcher m = p.matcher(originKey);
            if (m.matches()) {
                String filepath = m.group(1);
                String filename = FilenameUtils.getName(filepath);
                Long lineNumber = Long.parseLong(m.group(2));
                Long columnNumber = Long.parseLong(m.group(3));
                origins.add(new PyCloneSource(filename, lineNumber, columnNumber));
            } else if (value.equals("FunctionDef")) {
                // The clone identifies a function. Not much we can do here for now
                // TODO: add lines to function node
                origins.add(new PyCloneSource(originKey, 0L, 0L));
            } else {
                throw new IOException("Improper filename given: " + originKey);
            }
        }
        return origins;
    }

    private void addClones(List<Source> origins, List<Clone> clones, String value, Long matchWeight) {
        // For comparison to other tools, create a clone for each two origins
        for (Source origin1: origins) {
            for (Source origin2: origins) {
                if (!origin1.equals(origin2)) {
                    // Implemented as a set to make sure no duplicates
                    Set<Source> sources = new HashSet<>(2);
                    sources.add(origin1);
                    sources.add(origin2);
                    Clone clone = new PyCloneClone(sources, value, matchWeight);

                    if (!clones.contains(clone)) {
                        clones.add(clone);
                    }
                }
            }
        }
    }
}
