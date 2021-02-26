package com.company.controls.main.window.utils.workers;

import com.company.model.search.SearchResultsDataModel;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegexProcessThreadRunnable implements Runnable{

    private String regex;
    private SearchResultsDataModel model;

    private boolean killThread = false;
    private boolean isSuccessful = false;
    private boolean isComplete = false;


    public StringRegexProcessThreadRunnable(String regex, SearchResultsDataModel model) {
        this.regex = regex;
        this.model = model;
    };

    public void killSearch () {
        this.killThread = true;
    }

    @Override
    public void run() {
        boolean regexEmpty = regex.isEmpty();
        Pattern pattern = Pattern.compile(regex);
        while (!killThread) {
            String fileToProcess = model.grabFileToProcess();
            if (fileToProcess == null) {
                setProcessedAllFiles();
                return;
            }

            if (regexEmpty) {
                model.addResults(fileToProcess, new HashMap<>());
                continue;
            }


            processFileContents(fileToProcess, pattern);
        }

        setThreadKilled();
    }

    private boolean processFileContents(String fileToProcess, Pattern pattern) {
        try {
            HashMap<Integer, String> matchingFileLines = new HashMap<>();
            Iterator<String> lines = Files.lines(Path.of(fileToProcess)).iterator();
            int lineNumber = 0;
            while (lines.hasNext()) {
                if (killThread) {
                    setThreadKilled();
                    return false;
                }
                lineNumber += 1;
                String line = lines.next();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    matchingFileLines.put(lineNumber, line);
                }
            }
            if (matchingFileLines.size() > 0) {
                model.addResults(fileToProcess, matchingFileLines);
                return true;
            }
        } catch (UncheckedIOException | IOException e) {
            System.out.println("Error trying to read: " + fileToProcess);
        }
        return true;
    }

    private void setProcessedAllFiles() {
        isComplete = true;
        isSuccessful = true;
        System.out.println("Content Thread Exiting");
    }

    private void setThreadKilled() {
        isSuccessful = false;
        isComplete = true;
    }
}
