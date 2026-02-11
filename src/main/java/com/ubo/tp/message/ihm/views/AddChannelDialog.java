package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.ihm.controllers.IChannelController;

import javax.swing.*;
import java.awt.*;

public class AddChannelDialog extends JDialog {

    private final IChannelController mController;
    private JTextField mNameField;

    public AddChannelDialog(Frame owner, IChannelController controller) {
        super(owner, "Créer un canal", true);
        this.mController = controller;
        this.initGui();
    }

    private void initGui() {
        this.setSize(400, 200);
        this.setLocationRelativeTo(getOwner());
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(54, 57, 63));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel label = new JLabel("NOM DU CANAL");
        label.setForeground(new Color(185, 187, 190));
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 0;
        this.add(label, gbc);

        mNameField = new JTextField();
        mNameField.setBackground(new Color(64, 68, 75));
        mNameField.setForeground(Color.WHITE);
        mNameField.setCaretColor(Color.WHITE);
        mNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 1;
        this.add(mNameField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton cancelBtn = new JButton("Annuler");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.addActionListener(e -> dispose());

        JButton createBtn = new JButton("Créer");
        createBtn.setBackground(new Color(88, 101, 242));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createBtn.addActionListener(e -> createChannel());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(createBtn);

        gbc.gridy = 2;
        this.add(buttonPanel, gbc);
    }

    private void createChannel() {
        String name = mNameField.getText();
        if (mController.createChannel(name)) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création (nom vide ou existant).", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
