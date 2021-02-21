package com.company.views.main.window.forms;

import com.company.model.ui.FileTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindowForm {
    // View components here.
    private JTextField searchStringTextField;
    private JButton helpSearchStringButton;
    private JTextField folderDestinationTextField;
    private JButton browseButton;
    private JButton helpFolderButton;
    private JPanel topLevelPanel;
    private JPanel searchStringPanel;
    private JPanel searchFolderPanel;
    private JPanel searchFolderButtonsPanel;
    private JPanel searchFilePanel;
    private JButton helpFileButton;
    private JPanel searchPanel;
    private JButton searchButton;
    private JTextField fileStringTextField;
    private JScrollPane fileScrollPane;
    private JScrollPane resultsScrollPane;

    private JTable fileTable;
    private JList listOfPaths;

    private JTextArea resultsText;

    // Models here.
    private DefaultTableModel tableModel;


    public MainWindowForm(FileTableModel fileTableModel) {
        this.tableModel = fileTableModel;
        createUIComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tableModel.addColumn("File Name");

        fileTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fileTable.setModel(tableModel);
        fileTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
        fileTable.setFillsViewportHeight(true);
        fileTable.setAutoCreateRowSorter(false);

        resultsText = new JTextArea();
        resultsText.setEditable(false);

        resultsScrollPane.setViewportView(resultsText);
        fileScrollPane.setViewportView(fileTable);

    }

    public String getSelectedTableRow() {
        if (fileTable.getRowCount() < 1) {
            return "";
        }
        return fileTable.getValueAt(fileTable.getSelectedRow(), 0).toString();
    }


    public JTable getFileTable() {
        return fileTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextArea getResultsText() {
        return resultsText;
    }

    public void setListOfPaths(JList listOfPaths) {
        this.listOfPaths = listOfPaths;
    }

    public JList getListOfPaths() {
        return listOfPaths;
    }

    public JTextField getSearchStringTextField() {
        return searchStringTextField;
    }

    public JTextField getFolderDestinationTextField() {
        return folderDestinationTextField;
    }

    public JTextField getFileStringTextField() {
        return fileStringTextField;
    }

    public JButton getHelpSearchStringButton() {
        return helpSearchStringButton;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getHelpFolderButton() {
        return helpFolderButton;
    }

    public JPanel getTopLevelPanel() {
        return topLevelPanel;
    }

    public JButton getHelpFileButton() {
        return helpFileButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        topLevelPanel = new JPanel();
        topLevelPanel.setLayout(new BorderLayout(0, 0));
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        topLevelPanel.add(searchPanel, BorderLayout.NORTH);
        searchStringPanel = new JPanel();
        searchStringPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanel.add(searchStringPanel, gbc);
        searchStringTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchStringPanel.add(searchStringTextField, gbc);
        helpSearchStringButton = new JButton();
        helpSearchStringButton.setText("Help");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchStringPanel.add(helpSearchStringButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchStringPanel.add(spacer1, gbc);
        searchFolderPanel = new JPanel();
        searchFolderPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanel.add(searchFolderPanel, gbc);
        folderDestinationTextField = new JTextField();
        folderDestinationTextField.setHorizontalAlignment(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchFolderPanel.add(folderDestinationTextField, gbc);
        searchFolderButtonsPanel = new JPanel();
        searchFolderButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        searchFolderPanel.add(searchFolderButtonsPanel, gbc);
        browseButton = new JButton();
        browseButton.setHorizontalAlignment(0);
        browseButton.setText("Browse");
        searchFolderButtonsPanel.add(browseButton);
        helpFolderButton = new JButton();
        helpFolderButton.setText("Help");
        searchFolderButtonsPanel.add(helpFolderButton);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchFolderPanel.add(spacer2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Folder Destination");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        searchPanel.add(label1, gbc);
        searchFilePanel = new JPanel();
        searchFilePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanel.add(searchFilePanel, gbc);
        fileStringTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchFilePanel.add(fileStringTextField, gbc);
        helpFileButton = new JButton();
        helpFileButton.setText("Help");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        searchFilePanel.add(helpFileButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchFilePanel.add(spacer3, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("File String");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        searchPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Search String");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        searchPanel.add(label3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(spacer4, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        topLevelPanel.add(panel1, BorderLayout.CENTER);
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setContinuousLayout(false);
        splitPane1.setDividerLocation(600);
        panel1.add(splitPane1, BorderLayout.CENTER);
        fileScrollPane = new JScrollPane();
        splitPane1.setLeftComponent(fileScrollPane);
        resultsScrollPane = new JScrollPane();
        splitPane1.setRightComponent(resultsScrollPane);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        topLevelPanel.add(panel2, BorderLayout.SOUTH);
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(10);
        label4.setText("Search Status: None       ");
        panel2.add(label4);
        searchButton = new JButton();
        searchButton.setHorizontalAlignment(0);
        searchButton.setText("Search");
        panel2.add(searchButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return topLevelPanel;
    }

}
