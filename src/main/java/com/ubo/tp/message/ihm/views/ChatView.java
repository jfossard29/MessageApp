package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.ihm.controllers.ISessionController;

import javax.swing.*;
import java.awt.*;

public class ChatView extends JPanel {

    protected ISessionController mController;

    // Couleurs Discord-like
    private final Color COLOR_MAIN = new Color(54, 57, 63);
    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_HEADER = new Color(47, 49, 54);

    public ChatView(ISessionController controller) {
        this.mController = controller;
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new GridBagLayout());
        this.setBackground(COLOR_MAIN);
        GridBagConstraints gbc = new GridBagConstraints();

        // Header Chat
        JPanel chatHeader = new JPanel(new GridBagLayout());
        chatHeader.setBackground(COLOR_HEADER);
        chatHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(30, 30, 30)));

        //TODO : A changer
        JLabel channelTitle = new JLabel("# général");
        channelTitle.setForeground(COLOR_TEXT);
        channelTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        channelTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbcChatHeader = new GridBagConstraints();
        gbcChatHeader.gridx = 0;
        gbcChatHeader.gridy = 0;
        gbcChatHeader.weightx = 1.0;
        gbcChatHeader.anchor = GridBagConstraints.WEST;
        chatHeader.add(channelTitle, gbcChatHeader);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(chatHeader, gbc);

        // Zone de messages
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(COLOR_MAIN);
        chatArea.setForeground(COLOR_TEXT);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(new JScrollPane(chatArea), gbc);

        // Zone de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(COLOR_MAIN);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField inputField = new JTextField();
        inputField.setBackground(new Color(64, 68, 75));
        inputField.setForeground(COLOR_TEXT);
        inputField.setCaretColor(COLOR_TEXT);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.gridx = 0;
        gbcInput.gridy = 0;
        gbcInput.weightx = 1.0;
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(inputField, gbcInput);

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(inputPanel, gbc);
    }
}
