package com.company.utils.workers;

import com.company.model.search.SearchResultsDataModel;

import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRegexProcessThreadRunnable implements Runnable {

    private Pattern pattern;
    private int currentIndex;
    private int finishIndex;
    private boolean isAlive = true;
    List<String> paths;
    SearchResultsDataModel dataOut;

    public FileRegexProcessThreadRunnable(String regexPattern, List<String> paths, SearchResultsDataModel dataOut, int startIndex, int finishIndex) {
        this.paths = paths;
        this.dataOut = dataOut;
        this.pattern = Pattern.compile(regexPattern);
        this.currentIndex = startIndex;
        this.finishIndex = finishIndex;
    }

    public void setAlive(boolean alive) {
        this.isAlive = false;
    }

    @Override
    public void run() {
        for (int i = currentIndex; i < finishIndex; i++) {
            if (this.isAlive) {
                String pathURL = paths.get(i);
                String fileNameType = Paths.get(pathURL).getFileName().toString();
                Matcher matcher = pattern.matcher(fileNameType);
                if (matcher.find()) {
                    dataOut.addPath(pathURL);
                }
            } else {
                break;
            }
        }
    }
}
