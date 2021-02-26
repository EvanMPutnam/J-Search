package com.company.controls.main.window.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpButtonMessagesListener implements ActionListener {

    public final static String REGEX_STRING_HELPER = "The search regex that appears in your given file.\n" +
            "You can test your 'find' regex's at this website:\n" +
            "https://www.freeformatter.com/java-regex-tester.html";

    public final static String REGEX_FILE_HELPER = "The search regex of the file name.\n" +
            "You can test your 'find' regex's at this website:\n" +
            "https://www.freeformatter.com/java-regex-tester.html";

    public final static String DESTINATION_HELPER = "Type in the root folder path\nyou would like to recursively descend.";

    private String messageToDisplay;
    private JFrame frame;

    public HelpButtonMessagesListener(String messageToDisplay, JFrame frame) {
        this.messageToDisplay = messageToDisplay;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, messageToDisplay);
    }
}
