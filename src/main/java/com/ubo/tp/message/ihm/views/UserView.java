package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;

/**
 * Vue représentant le profil d'un utilisateur (avatar, nom, tag).
 */
public class UserView extends JPanel {

    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_TEXT_MUTED = new Color(142, 146, 151);

    public UserView(User user) {
        this.initGui(user);
    }

    private void initGui(User user) {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false); // Fond transparent

        GridBagConstraints gbc = new GridBagConstraints();

        // Placeholder pour l'avatar
        JLabel avatar = new JLabel(user.getName().substring(0, 1).toUpperCase());
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setVerticalAlignment(SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        avatar.setForeground(Color.WHITE);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(88, 101, 242)); // "Blurple"
        // Pour faire un cercle, il faudrait surcharger paintComponent, restons simple.
        avatar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2; // S'étend sur 2 lignes
        gbc.insets = new Insets(5, 5, 5, 10);
        this.add(avatar, gbc);

        // Nom de l'utilisateur
        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(COLOR_TEXT);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(5, 0, 0, 5);
        this.add(nameLabel, gbc);

        // Tag de l'utilisateur
        JLabel tagLabel = new JLabel("@" + user.getUserTag());
        tagLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tagLabel.setForeground(COLOR_TEXT_MUTED);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        this.add(tagLabel, gbc);
    }
}
