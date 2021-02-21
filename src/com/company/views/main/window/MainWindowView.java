package com.company.views.main.window;

import com.company.model.ui.FileTableModel;
import com.company.views.main.window.forms.MainWindowForm;

import javax.swing.*;

public class MainWindowView {

    private static final String TITLE = "J Fast Search";

    private MainWindowForm mainWindowForm;
    private JFrame masterFrame;

    public MainWindowView(FileTableModel model) {
        mainWindowForm = new MainWindowForm(model);
        masterFrame = new JFrame();

        masterFrame.setTitle(TITLE);
        masterFrame.add(mainWindowForm.getTopLevelPanel());
        masterFrame.setVisible(true);
        masterFrame.setSize(1600, 800);
    }

    public JFrame getMasterFrame() {
        return masterFrame;
    }

    public MainWindowForm getMainWindowForm() {
        return mainWindowForm;
    }
}
