package com.company.controls.main.window.listeners;

import com.company.model.search.SearchResultsDataModel;
import com.company.controls.main.window.utils.delegator.FileContentsSearchThreadDelegatorRunnable;
import com.company.controls.main.window.utils.delegator.FileSearchThreadDelegatorRunnable;
import com.company.views.main.window.MainWindowView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class SearchButtonListener implements ActionListener {


    private FileSearchThreadDelegatorRunnable fileSearchThreadDelegatorRunnable;
    private FileContentsSearchThreadDelegatorRunnable fileContentsSearchThreadDelegatorRunnable;
    private Thread searchThread = null;
    private Thread fileContentsSearchThread = null;

    private SearchResultsDataModel searchResultsDataModel;
    private MainWindowView mainWindowView;
    private int numberOfCores = Runtime.getRuntime().availableProcessors();

    public SearchButtonListener(SearchResultsDataModel searchResultsDataModel, MainWindowView mainWindowView) {
        this.searchResultsDataModel = searchResultsDataModel;
        this.mainWindowView = mainWindowView;
    }

    public SearchButtonListener(SearchResultsDataModel searchResultsDataModel, MainWindowView mainWindowView, int numberOfCores) {
        this.numberOfCores = numberOfCores;
        this.searchResultsDataModel = searchResultsDataModel;
        this.mainWindowView = mainWindowView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStillActive()) {
            fileSearchThreadDelegatorRunnable.killSearch();
            fileContentsSearchThreadDelegatorRunnable.killSearch();
            JOptionPane.showMessageDialog(mainWindowView.getMasterFrame(), "Please wait.  Killing search.\n");
        } else {
            // Clear the model data.
            searchResultsDataModel.clearData();

            // Pull relevant fields to check for validity.
            String searchRegex = mainWindowView.getMainWindowForm().getSearchStringTextField().getText();
            String searchFileRegex = mainWindowView.getMainWindowForm().getFileStringTextField().getText();
            String destination = mainWindowView.getMainWindowForm().getFolderDestinationTextField().getText();
            if (!verifyInput(searchRegex, searchFileRegex, destination)) {
                return;
            }

            // These are the search threads for files.
            Path path = Path.of(destination);
            fileSearchThreadDelegatorRunnable = new FileSearchThreadDelegatorRunnable(path, searchFileRegex, numberOfCores, searchResultsDataModel);
            searchThread = new Thread(fileSearchThreadDelegatorRunnable);
            searchThread.start();

            // This is the delegator thread for searching within those files.
            fileContentsSearchThreadDelegatorRunnable = new FileContentsSearchThreadDelegatorRunnable(searchResultsDataModel, searchRegex, numberOfCores);
            fileContentsSearchThread = new Thread(fileContentsSearchThreadDelegatorRunnable);
            fileContentsSearchThread.start();
        }
    }

    private boolean isStillActive() {
        // Determine if a search is ongoing and needs to kill off other items.
        boolean conditionFileSearch = searchThread != null && (!fileSearchThreadDelegatorRunnable.didComplete() || searchThread.isAlive());
        boolean conditionFileContentsSearch = fileContentsSearchThread != null && (!fileContentsSearchThreadDelegatorRunnable.didComplete() || fileContentsSearchThread.isAlive());
        return conditionFileSearch && conditionFileContentsSearch;
    }

    private boolean verifyInput (String searchRegex, String searchFileRegex, String destination) {
        boolean doesPathExist = false;
        boolean doesSearchRegexResolve = false;
        boolean doesSearchFileRegexResolve = false;

        // Check if path is valid.
        Path path = Path.of(destination);
        doesPathExist = Files.exists(path);
        if (!doesPathExist || destination.strip().equals("")) {
            JOptionPane.showMessageDialog(mainWindowView.getMasterFrame(), "Error: Path does not exist.\n");
            return false;
        }

        // Check if the search string regex is valid.
        try {
            Pattern.compile(searchRegex);
            doesSearchRegexResolve = true;
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(mainWindowView.getMasterFrame(), "Error: Search string regex invalid.\n");
        }

        // Check if the file name regex is valid.
        try {
            Pattern.compile(searchFileRegex);
            doesSearchFileRegexResolve = true;
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(mainWindowView.getMasterFrame(), "Error: File name regex invalid.\n");
        }


        return doesPathExist && doesSearchFileRegexResolve && doesSearchRegexResolve;
    }
}
