package com.company.controls.main.window.listeners;

import com.company.views.main.window.MainWindowView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowseFileButtonListener implements ActionListener {

    private MainWindowView mainWindowView;

    public BrowseFileButtonListener(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int retVal = fileChooser.showOpenDialog(mainWindowView.getMasterFrame());
        if (retVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Found folder " + fileChooser.getSelectedFile());
            mainWindowView.getMainWindowForm().getFolderDestinationTextField().setText(fileChooser.getSelectedFile().toString());
        }
    }
}
