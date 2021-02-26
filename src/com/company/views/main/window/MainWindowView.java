package com.company.views.main.window;

import com.company.model.ui.FileTableModel;
import com.company.views.main.window.forms.MainWindowForm;

import javax.swing.*;

public class MainWindowView {

    private static final String TITLE = "J Fast Search";
    private static final int STARTING_WIDTH = 1600;
    private static final int STARTING_HEIGHT = 800;

    private MainWindowForm mainWindowForm;
    private JFrame masterFrame;

    private JMenuItem fileBrowserMenuItem;
    private JMenuItem copyPathToClipboardMenuItem;

    public MainWindowView(FileTableModel model) {
        mainWindowForm = new MainWindowForm(model);
        masterFrame = new JFrame();

        setupMenu(masterFrame);

        masterFrame.setTitle(TITLE);
        masterFrame.add(mainWindowForm.getTopLevelPanel());
        masterFrame.setVisible(true);
        masterFrame.setSize(STARTING_WIDTH, STARTING_HEIGHT);
    }

    private void setupMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File Options");
        fileBrowserMenuItem = new JMenuItem("Open File Browser at File Location");
        copyPathToClipboardMenuItem = new JMenuItem("Copy Path to Clipboard");
        menu.add(fileBrowserMenuItem);
        menu.add(copyPathToClipboardMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    public JFrame getMasterFrame() {
        return masterFrame;
    }

    public MainWindowForm getMainWindowForm() {
        return mainWindowForm;
    }

    public JMenuItem getFileBrowserMenuItem() {
        return fileBrowserMenuItem;
    }

    public JMenuItem getCopyPathToClipboardMenuItem() {
        return copyPathToClipboardMenuItem;
    }


}
