package com.company.controls.main.window.utils.delegator;

import com.company.model.search.SearchResultsDataModel;
import com.company.controls.main.window.utils.workers.FileRegexProcessThreadRunnable;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileSearchThreadDelegatorRunnable implements Runnable{

    private int numOfThreads = 1;
    private List<Thread> threads = new ArrayList<>();
    private List<Runnable> attachedRunnables = new ArrayList<>();
    private SearchResultsDataModel searchResultsDataModel;
    private Path startingPath;
    private String regexPattern;

    private boolean killThreads = false;

    private boolean isSuccessful = false;

    private boolean didComplete = false;


    public FileSearchThreadDelegatorRunnable(Path startingPath, String regexPattern, int numOfThreads, SearchResultsDataModel searchResultsDataModel) {
        this.numOfThreads = numOfThreads;
        this.searchResultsDataModel = searchResultsDataModel;

        this.startingPath = startingPath;
        this.regexPattern = regexPattern;
    }

    public void StartFileSearch() throws IOException {

        // Reset the data in our controller.
        searchResultsDataModel.clearData();
        attachedRunnables.clear();
        threads.clear();

        List<String> resultsPaths = new ArrayList<>();
        Files.walkFileTree(startingPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                resultsPaths.add(file.toString());
                if (killThreads) {
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }
        });

        // If the command to kill the thread was sent then terminate the rest of the search.
        if (killThreads) {
            System.out.println("Thread killed during file search.");
            return;
        }

        // Here we want to calculate the indexes for each thread to look at.
        int totalItems = resultsPaths.size();
        int itemsDifference = totalItems / numOfThreads;

        // If we have less items then number of threads just use a single thread.
        if (totalItems <= numOfThreads) {
            FileRegexProcessThreadRunnable runnable = new FileRegexProcessThreadRunnable(regexPattern, resultsPaths, searchResultsDataModel,
                    0, totalItems);
            Thread thread = new Thread(runnable);
            thread.start();
            threads.add(thread);
            attachedRunnables.add(runnable);
        } else {
            int startIndex = 0;
            for (int i = 0; i < numOfThreads; i++) {
                if (startIndex + itemsDifference >= totalItems) {
                    FileRegexProcessThreadRunnable runnable = new FileRegexProcessThreadRunnable(regexPattern, resultsPaths, searchResultsDataModel, startIndex, totalItems);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    threads.add(thread);
                    attachedRunnables.add(runnable);
                    break;
                }
                FileRegexProcessThreadRunnable runnable = new FileRegexProcessThreadRunnable(regexPattern, resultsPaths, searchResultsDataModel,
                        startIndex, startIndex + itemsDifference);
                Thread thread = new Thread(runnable);
                thread.start();
                threads.add(thread);
                attachedRunnables.add(runnable);
                startIndex += itemsDifference;
            }
        }
    }

    public void killSearch() {
        for (Runnable runnable : attachedRunnables) {
            ((FileRegexProcessThreadRunnable)runnable).setAlive(false);
        }
        killThreads = true;
        System.out.println("Trying to kill search.");
    }



    public boolean isSearching() {
        for (Thread t : threads) {
            if (t.isAlive()) return true;
        }
        return false;
    }

    public boolean didComplete() {
        return didComplete;
    }


    @Override
    public void run() {
        try {
            System.out.println("Starting search! Number of threads");
            StartFileSearch();
            for (Thread t: threads) {
                t.join();
            }
            isSuccessful = killThreads ? false : true;
            if (isSuccessful) {
                searchResultsDataModel.finalizeFileSearch();
                System.out.println("File Delegator: Done searching for file names.");
            }
            didComplete = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
