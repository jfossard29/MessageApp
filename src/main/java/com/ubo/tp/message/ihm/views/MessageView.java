package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Vue représentant un message unique dans le chat.
 */
public class MessageView extends JPanel {

    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_TEXT_MUTED = new Color(142, 146, 151);
    private final Color COLOR_BACKGROUND = new Color(54, 57, 63);
    private final Color COLOR_BACKGROUND_SELF = new Color(64, 68, 75); // Légèrement plus clair pour soi-même

    public MessageView(Message message, boolean isCurrentUser) {
        this.initGui(message, isCurrentUser);
    }
    
    // Constructeur pour compatibilité (par défaut false)
    public MessageView(Message message) {
        this(message, false);
    }

    private void initGui(Message message, boolean isCurrentUser) {
        this.setLayout(new GridBagLayout());
        
        Color bgColor = isCurrentUser ? COLOR_BACKGROUND_SELF : COLOR_BACKGROUND;
        this.setBackground(bgColor);
        this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        GridBagConstraints gbc = new GridBagConstraints();

        // Avatar (Placeholder)
        JLabel avatar = new JLabel(message.getSender().getName().substring(0, 1).toUpperCase());
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setVerticalAlignment(SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        avatar.setForeground(Color.WHITE);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(88, 101, 242)); // "Blurple"
        avatar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2; // S'étend sur 2 lignes
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(avatar, gbc);

        // Header (Nom + Date)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        headerPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(message.getSender().getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(COLOR_TEXT);
        headerPanel.add(nameLabel);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        JLabel dateLabel = new JLabel(sdf.format(new Date(message.getEmissionDate())));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        dateLabel.setForeground(COLOR_TEXT_MUTED);
        headerPanel.add(dateLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(5, 0, 2, 5);
        this.add(headerPanel, gbc);

        // Contenu du message
        JTextArea messageContent = new JTextArea(message.getText());
        messageContent.setEditable(false);
        messageContent.setLineWrap(true);
        messageContent.setWrapStyleWord(true);
        messageContent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageContent.setForeground(COLOR_TEXT);
        messageContent.setBackground(bgColor); // Même fond que le panel
        messageContent.setBorder(null);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        this.add(messageContent, gbc);
    }
}
