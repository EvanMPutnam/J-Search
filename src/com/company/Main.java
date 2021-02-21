package com.company;

///////// NOTES: Possible TODO items.
// TODO: Add tabbed panes for multiple searches?
// TODO: Add chaining of folder paths???  So maybe...  "C://vars/temp AND C://users or something stupid like that.
/////////

/**
 * File Search Design:
 *      Features
 *      - Only search actual files if the regex search is not ""
 *      - Provide option to copy path to clipboard or open current file.
 *      - Display metadata like extensions, file size in lines, etc.
 *      -
*/

// Tabbed panes: https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html

import com.company.controls.main.window.MainWindowController;
import com.company.model.search.SearchResultsDataModel;
import com.company.model.ui.FileTableModel;
import com.company.views.main.window.MainWindowView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Warning: Can not obtain system UI style.  Defaulting to normal theme.");
        }

        // Create the models
        FileTableModel fileTableModel = new FileTableModel();
        SearchResultsDataModel searchResultsDataModel = new SearchResultsDataModel();

        // Create the views
        MainWindowView mainWindowView = new MainWindowView(fileTableModel);

        // Create the controllers
        MainWindowController mainWindowController = new MainWindowController(mainWindowView, searchResultsDataModel);

    }
}
