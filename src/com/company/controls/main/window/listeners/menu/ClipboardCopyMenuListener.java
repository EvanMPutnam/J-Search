package com.company.controls.main.window.listeners.menu;

import com.company.views.main.window.MainWindowView;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClipboardCopyMenuListener implements ActionListener {

    private MainWindowView windowView;

    public ClipboardCopyMenuListener(MainWindowView windowView) {
        this.windowView = windowView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedFile = windowView.getMainWindowForm().getSelectedTableRow();
        if (!selectedFile.isEmpty()) {
            Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .setContents(new StringSelection(selectedFile), null);
        }
    }
}
