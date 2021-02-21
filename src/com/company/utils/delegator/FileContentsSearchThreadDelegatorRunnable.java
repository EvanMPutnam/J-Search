package com.company.utils.delegator;

import com.company.model.search.SearchResultsDataModel;
import com.company.utils.workers.StringRegexProcessThreadRunnable;

import java.util.ArrayList;

public class FileContentsSearchThreadDelegatorRunnable implements Runnable{

    private static final int DEFAULT_WAIT_TIME = 500;

    private SearchResultsDataModel model;
    private String regex;

    private ArrayList<Runnable> runnables = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();

    private boolean killThreads;

    private int maxCores = 1;

    private boolean complete = false;
    private boolean isSuccessful = false;

    public FileContentsSearchThreadDelegatorRunnable(SearchResultsDataModel model, String regex, int maxCores) {
        this.model = model;
        this.regex = regex;
        this.maxCores = maxCores;
    }


    public void killSearch() {
        for (Runnable r: runnables) {
            ((StringRegexProcessThreadRunnable)r).killSearch();
        }
        this.killThreads = true;
    }


    @Override
    public void run() {
        while (!this.model.isFinishedFileRegexSearch()) {
            System.out.println("Waiting on main file search thread.");
            if (this.killThreads) {
                complete = true;
                System.out.println("Killing threads FileContent Delegator");
                return;
            }
            wait(DEFAULT_WAIT_TIME);
        }

        if (this.killThreads) {
            complete = true;
            System.out.println("Killing threads FileContent Delegator");
            return;
        }


        // Finished searching!  Go to town!
        System.out.println("Delegating regex's to start searching files.");
        startContentSearchThreads();
        joinThreads();

        // Status
        complete = true;
        isSuccessful = !killThreads;
        System.out.println("File Content Delegator: Exit");
    }

    private void startContentSearchThreads() {
        runnables.clear();
        threads.clear();
        for (int i = 0; i < maxCores; i++) {
            //
            Runnable runnable = new StringRegexProcessThreadRunnable(regex, model);
            runnables.add(runnable);
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
    }

    private void joinThreads() {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public boolean didComplete() {
        return complete;
    }




}
