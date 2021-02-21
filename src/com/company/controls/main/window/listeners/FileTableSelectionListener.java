package com.company.controls.main.window.listeners;

import com.company.model.search.SearchResultsDataModel;
import com.company.views.main.window.MainWindowView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileTableSelectionListener implements ListSelectionListener {
    private MainWindowView view;
    private SearchResultsDataModel model;

    public FileTableSelectionListener(MainWindowView view, SearchResultsDataModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !(view.getMainWindowForm().getTableModel().getRowCount() < 1)) {
            String path = view.getMainWindowForm().getSelectedTableRow();
            if (path == "") {
                return;
            }
            String resultsText = model.getResultsData(path);
            view.getMainWindowForm().getResultsText().setText(resultsText);
        } else {
            System.out.println("Something happening here!");
        }
    }
}
