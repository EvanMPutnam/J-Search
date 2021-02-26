package com.company.controls.main.window;


import com.company.controls.main.window.listeners.BrowseFileButtonListener;
import com.company.controls.main.window.listeners.FileTableSelectionListener;
import com.company.controls.main.window.listeners.HelpButtonMessagesListener;
import com.company.controls.main.window.listeners.SearchButtonListener;
import com.company.controls.main.window.listeners.menu.ClipboardCopyMenuListener;
import com.company.controls.main.window.listeners.menu.FileBrowserMenuListener;
import com.company.model.search.SearchResultsDataModel;
import com.company.views.main.window.MainWindowView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// https://stackoverflow.com/questions/5217611/the-mvc-pattern-and-swing
public class MainWindowController implements PropertyChangeListener {

    private MainWindowView windowView;
    private SearchResultsDataModel searchResultsDataModel;


    public MainWindowController(MainWindowView view, SearchResultsDataModel searchResultsDataModel) {
        this.searchResultsDataModel = searchResultsDataModel;
        this.windowView = view;

        // Link listener controllers to components.
        setupHelpButtons();
        setupBrowseButton();
        setupSearchButton();
        setupTableSelect();
        setupMenuSelect();

        // Handle model data changes and update components.
        searchResultsDataModel.addListener(this);
    }

    private void setupMenuSelect() {
        windowView.getFileBrowserMenuItem().addActionListener(new FileBrowserMenuListener(windowView));
        windowView.getCopyPathToClipboardMenuItem().addActionListener(new ClipboardCopyMenuListener(windowView));
    }

    private void setupTableSelect () {
        windowView.getMainWindowForm().getFileTable().getSelectionModel().addListSelectionListener(
                new FileTableSelectionListener(windowView, searchResultsDataModel));
    }

    private void setupHelpButtons () {
        windowView.getMainWindowForm().getHelpSearchStringButton().addActionListener(
                new HelpButtonMessagesListener(HelpButtonMessagesListener.REGEX_STRING_HELPER, windowView.getMasterFrame()));

        windowView.getMainWindowForm().getHelpFileButton().addActionListener(
                new HelpButtonMessagesListener(HelpButtonMessagesListener.REGEX_FILE_HELPER, windowView.getMasterFrame()));

        windowView.getMainWindowForm().getHelpFolderButton().addActionListener(
                new HelpButtonMessagesListener(HelpButtonMessagesListener.DESTINATION_HELPER, windowView.getMasterFrame()));
    }

    private void setupBrowseButton () {
        windowView.getMainWindowForm().getBrowseButton().addActionListener(new BrowseFileButtonListener(windowView));
    }

    private void setupSearchButton () {
        windowView.getMainWindowForm().getSearchButton().addActionListener(new SearchButtonListener(searchResultsDataModel, windowView));
    }

    // Handle updates...
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (SearchResultsDataModel.DATA_UPDATE_EVENT.equalsIgnoreCase(propName)) {
            String newValue = (String) evt.getNewValue();
            windowView.getMainWindowForm().getTableModel().addRow(new Object[]{newValue});

        } else if (SearchResultsDataModel.DATA_CLEAR_EVENT.equalsIgnoreCase(propName)) {
            // Hey we have a clear of the data!
            windowView.getMainWindowForm().getTableModel().setRowCount(0);

        } else if (SearchResultsDataModel.DATA_FINISH_SEARCH_EVENT.equalsIgnoreCase(propName)) {
            // Nothing here atm.  Might add something later.

        } else if (SearchResultsDataModel.DATA_CONTENT_SEARCH_FINISH_EVENT.equalsIgnoreCase(propName)) {
            windowView.getMainWindowForm().setSearchStatusText("Finished");
        }
    }
}
