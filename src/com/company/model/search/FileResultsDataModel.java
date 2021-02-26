package com.company.model.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FileResultsDataModel {

    // Path ->
    private HashMap<String, HashMap<Integer, String>> map = new HashMap<>();

    public FileResultsDataModel() {

    }

    public void addLineToFile (String path, String line, int lineNumber) {
        if (!map.containsKey(path)) {
            map.put(path, new HashMap<>());
        }
        map.get(path).put(lineNumber, line);
    }

    public void addLinesToFile (String path, HashMap<Integer, String> lineMap) {
        map.put(path, lineMap);
    }

    public boolean hasDestination(String path) {
        return map.containsKey(path);
    }

    public String getResultsString(String path) {
        String rString = "";
        if (map.containsKey(path)) {
            List<Integer> keys = new ArrayList<>(map.get(path).keySet());
            Collections.sort(keys);
            for (Integer key : keys) {
                rString += key.toString() + ": " + map.get(path).get(key) + '\n';
            }
            return rString;
        }
        return rString;
    }

    public void clear() {
        map.clear();
    }


}
