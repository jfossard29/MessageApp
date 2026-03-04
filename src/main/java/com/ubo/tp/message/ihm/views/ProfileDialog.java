package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IProfileController;

import javax.swing.*;
import java.awt.*;

public class ProfileDialog extends JDialog {

    private final IProfileController mController;
    private final User mCurrentUser;
    private JTextField mNameField;

    public ProfileDialog(Frame owner, IProfileController controller, User currentUser) {
        super(owner, "Mon Profil", true);
        this.mController = controller;
        this.mCurrentUser = currentUser;
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

        // Label Nom
        JLabel nameLabel = new JLabel("NOM D'AFFICHAGE");
        nameLabel.setForeground(new Color(185, 187, 190));
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 0;
        this.add(nameLabel, gbc);

        // Input Nom
        mNameField = new JTextField(mCurrentUser.getName());
        mNameField.setBackground(new Color(64, 68, 75));
        mNameField.setForeground(Color.WHITE);
        mNameField.setCaretColor(Color.WHITE);
        mNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 1;
        this.add(mNameField, gbc);

        // Bouton Enregistrer
        JButton saveBtn = new JButton("Enregistrer");
        saveBtn.setBackground(new Color(88, 101, 242));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveBtn.addActionListener(e -> {
            mController.updateDisplayName(mNameField.getText());
            dispose();
        });
        gbc.gridy = 2;
        this.add(saveBtn, gbc);

        // Zone de danger
        JPanel dangerZone = new JPanel(new BorderLayout());
        dangerZone.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(237, 66, 69)), "Zone de danger", 0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(237, 66, 69)));
        dangerZone.setOpaque(false);
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        this.add(dangerZone, gbc);

        JButton deleteBtn = new JButton("Supprimer mon compte");
        deleteBtn.setBackground(new Color(237, 66, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer votre compte ?\nTous vos messages seront anonymisés. Cette action est irréversible.",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                mController.deleteAccount();
                dispose();
            }
        });
        dangerZone.add(deleteBtn, BorderLayout.CENTER);
    }
}
