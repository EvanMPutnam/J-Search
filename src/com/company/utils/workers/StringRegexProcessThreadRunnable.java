package com.company.utils.workers;

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
        Pattern pattern = Pattern.compile(regex);
        while (!killThread) {
            String fileToProcess = model.grabFileToProcess();
            if (fileToProcess == null) {
                isComplete = true;
                isSuccessful = true;
                System.out.println("Content Thread Exiting");
                return;
            }
            try {
                HashMap<Integer, String> matchingFileLines = new HashMap<>();
                int lineNumber = 0;
                Iterator<String> lines = Files.lines(Path.of(fileToProcess)).iterator();
                try {
                    while (lines.hasNext()) {
                        if (killThread) {
                            isSuccessful = false;
                            isComplete = true;
                            return;
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
                    }
                } catch (UncheckedIOException e) {
                    System.out.println("Error trying to read: " + fileToProcess);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        isSuccessful = false;
        isComplete = true;
    }
}
