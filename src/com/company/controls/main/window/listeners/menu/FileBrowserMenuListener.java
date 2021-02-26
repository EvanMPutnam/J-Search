package com.company.controls.main.window.listeners.menu;

import com.company.views.main.window.MainWindowView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileBrowserMenuListener implements ActionListener {

    private MainWindowView windowView;

    public FileBrowserMenuListener(MainWindowView windowView) {
        this.windowView = windowView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedFile = windowView.getMainWindowForm().getSelectedTableRow();
        if (!selectedFile.isEmpty()) {
            try {
                Desktop.getDesktop().browse(new File(selectedFile).getParentFile().toURI());
            } catch (IOException ioException) {

            }
        }
    }
}
