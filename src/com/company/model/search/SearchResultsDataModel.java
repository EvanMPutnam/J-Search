package com.company.model.search;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.*;

public class SearchResultsDataModel {

    public static final String DATA_UPDATE_EVENT = "data_update";
    public static final String DATA_CLEAR_EVENT = "data_clear";
    public static final String DATA_FINISH_SEARCH_EVENT = "data_files_finished";
    public static final String DATA_CONTENT_SEARCH_FINISH_EVENT = "data_files_content_finished";

    private Queue<String> validFilePaths;
    private SwingPropertyChangeSupport propChangeFirer;

    private volatile boolean finishedFileRegexSearch = false;

    private FileResultsDataModel results;

    public SearchResultsDataModel() {
        propChangeFirer = new SwingPropertyChangeSupport(this);
        validFilePaths = new LinkedList<>();
        results = new FileResultsDataModel();
    }

    public void finalizeFileSearch() {
        synchronized (validFilePaths) {
            finishedFileRegexSearch = true;
            propChangeFirer.firePropertyChange(DATA_FINISH_SEARCH_EVENT, false, finishedFileRegexSearch);
        }
    }

    public void finalizeFileContentSearch() {
        synchronized (validFilePaths) {
            finishedFileRegexSearch = true;
            propChangeFirer.firePropertyChange(DATA_CONTENT_SEARCH_FINISH_EVENT, false, finishedFileRegexSearch);
        }
    }

    public String grabFileToProcess() {
        synchronized (validFilePaths) {
            String front = null;
            if (!validFilePaths.isEmpty()) {
                front = validFilePaths.remove();
            }
            return front;
        }
    }

    public void addPath(String path) {
        synchronized (validFilePaths) {
            validFilePaths.add(path);
        }
    }

    public void clearData() {
        synchronized (validFilePaths) {
            finishedFileRegexSearch = false;
            validFilePaths.clear();
            results.clear();
            results = new FileResultsDataModel();
            propChangeFirer.firePropertyChange(DATA_CLEAR_EVENT, false, true);
        }
    }

    public boolean isFinishedFileRegexSearch() {
        return finishedFileRegexSearch;
    }

    public void addListener (PropertyChangeListener prop) {
        propChangeFirer.addPropertyChangeListener(prop);
    }

    public void addResults (String filePath, HashMap<Integer, String> lines) {
        synchronized (results) {
            results.addLinesToFile(filePath, lines);
            propChangeFirer.firePropertyChange(DATA_UPDATE_EVENT, null, filePath);
        }
    }

    public String getResultsData(String path) {
        return results.getResultsString(path);
    }

}
