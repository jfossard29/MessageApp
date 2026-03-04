package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IChannelController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddChannelDialog extends JDialog {

    private final IChannelController mController;
    private final Set<User> mAvailableUsers;
    private JTextField mNameField;
    private JCheckBox mPrivateCheckBox;
    private JList<User> mUsersList;
    private JScrollPane mUsersScrollPane;

    public AddChannelDialog(Frame owner, IChannelController controller, Set<User> availableUsers) {
        super(owner, "Créer un canal", true);
        this.mController = controller;
        this.mAvailableUsers = availableUsers;
        this.initGui();
    }

    private void initGui() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(getOwner());
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(54, 57, 63));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Label Nom
        JLabel label = new JLabel("NOM DU CANAL");
        label.setForeground(new Color(185, 187, 190));
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 0;
        this.add(label, gbc);

        // Input Nom
        mNameField = new JTextField();
        mNameField.setBackground(new Color(64, 68, 75));
        mNameField.setForeground(Color.WHITE);
        mNameField.setCaretColor(Color.WHITE);
        mNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 1;
        this.add(mNameField, gbc);

        // Checkbox Privé
        mPrivateCheckBox = new JCheckBox("Canal privé");
        mPrivateCheckBox.setForeground(Color.WHITE);
        mPrivateCheckBox.setOpaque(false);
        mPrivateCheckBox.setFocusPainted(false);
        mPrivateCheckBox.addActionListener(e -> toggleUserList(mPrivateCheckBox.isSelected()));
        gbc.gridy = 2;
        this.add(mPrivateCheckBox, gbc);

        // Liste des utilisateurs
        DefaultListModel<User> listModel = new DefaultListModel<>();
        if (mAvailableUsers != null) {
            for (User user : mAvailableUsers) {
                listModel.addElement(user);
            }
        }
        mUsersList = new JList<>(listModel);
        mUsersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        mUsersList.setBackground(new Color(47, 49, 54));
        mUsersList.setForeground(new Color(185, 187, 190));
        mUsersList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof User) {
                    setText(((User) value).getUserTag());
                }
                if (isSelected) {
                    setBackground(new Color(88, 101, 242));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(47, 49, 54));
                    setForeground(new Color(185, 187, 190));
                }
                return this;
            }
        });

        mUsersScrollPane = new JScrollPane(mUsersList);
        mUsersScrollPane.setBorder(BorderFactory.createEmptyBorder());
        mUsersScrollPane.setVisible(false); // Caché par défaut
        gbc.gridy = 3;
        gbc.weighty = 1.0; // Prend de la place verticalement
        gbc.fill = GridBagConstraints.BOTH;
        this.add(mUsersScrollPane, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton cancelBtn = new JButton("Annuler");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.addActionListener(e -> dispose());

        JButton createBtn = new JButton("Créer") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(getBackground().darker());
                } else {
                    g.setColor(getBackground());
                }
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        createBtn.setBackground(new Color(88, 101, 242));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createBtn.setContentAreaFilled(false);
        createBtn.setOpaque(false);
        createBtn.setBorderPainted(false);
        createBtn.addActionListener(e -> createChannel());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(createBtn);

        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(buttonPanel, gbc);
    }

    private void toggleUserList(boolean show) {
        mUsersScrollPane.setVisible(show);
        this.revalidate();
        this.repaint();
    }

    private void createChannel() {
        String name = mNameField.getText();
        boolean isPrivate = mPrivateCheckBox.isSelected();
        boolean success;

        if (isPrivate) {
            List<User> selectedUsers = mUsersList.getSelectedValuesList();
            success = mController.createChannel(name, selectedUsers);
        } else {
            success = mController.createChannel(name);
        }

        if (success) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création (nom vide ou existant).", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
