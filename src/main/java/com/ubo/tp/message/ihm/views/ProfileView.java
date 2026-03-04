package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IProfileController;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JDialog {

    private final IProfileController mController;
    private JTextField mNameField;

    public ProfileView(Frame owner, IProfileController controller) {
        super(owner, "Mon Profil", true);
        this.mController = controller;
        this.initGui();
    }

    private void initGui() {
        this.setSize(400, 300);
        this.setLocationRelativeTo(getOwner());
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(54, 57, 63));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Titre
        JLabel titleLabel = new JLabel("MON PROFIL");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        this.add(titleLabel, gbc);

        // Label Nom
        JLabel nameLabel = new JLabel("NOM D'AFFICHAGE");
        nameLabel.setForeground(new Color(185, 187, 190));
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 1;
        this.add(nameLabel, gbc);

        // Input Nom
        mNameField = new JTextField();
        mNameField.setBackground(new Color(64, 68, 75));
        mNameField.setForeground(Color.WHITE);
        mNameField.setCaretColor(Color.WHITE);
        mNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        User currentUser = mController.getCurrentUser();
        if (currentUser != null) {
            mNameField.setText(currentUser.getName());
        }
        
        gbc.gridy = 2;
        this.add(mNameField, gbc);

        // Bouton Sauvegarder
        JButton saveBtn = new JButton("Sauvegarder");
        saveBtn.setBackground(new Color(88, 101, 242));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveBtn.addActionListener(e -> {
            mController.updateName(mNameField.getText());
            dispose();
        });
        
        gbc.gridy = 3;
        this.add(saveBtn, gbc);

        // Séparateur
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(79, 84, 92));
        gbc.gridy = 4;
        this.add(separator, gbc);

        // Section Danger Zone
        JLabel dangerLabel = new JLabel("ZONE DANGEREUSE");
        dangerLabel.setForeground(new Color(237, 66, 69));
        dangerLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 5;
        this.add(dangerLabel, gbc);

        // Bouton Supprimer Compte
        JButton deleteBtn = new JButton("Supprimer mon compte");
        deleteBtn.setBackground(new Color(237, 66, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.", 
                "Supprimer le compte", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                mController.deleteAccount();
                dispose();
            }
        });
        
        gbc.gridy = 6;
        this.add(deleteBtn, gbc);
    }
}
