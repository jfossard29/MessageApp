package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IChannelController;
import main.java.com.ubo.tp.message.ihm.controllers.ISessionController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SidebarView extends JPanel {
    // Couleurs Discord-like
    private final Color COLOR_SIDEBAR = new Color(32, 34, 37);
    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_INPUT = new Color(64, 68, 75);

    protected ISessionController mSessionController;
    protected IChannelController mChannelController;
    protected User mCurrentUser;
    
    private Set<User> listAllUsers;
    private Set<User> listAllUsersFiltered;
    private Set<Channel> listAllChannels;
    private Set<Channel> listAllChannelsFiltered;
    
    private JTextField mSearchField;

    public SidebarView(ISessionController sessionController, IChannelController channelController) {
        this.mSessionController = sessionController;
        this.mChannelController = channelController;
        this.listAllUsers = new HashSet<>();
        this.listAllUsersFiltered = new HashSet<>();
        this.listAllChannels = new HashSet<>();
        this.listAllChannelsFiltered = new HashSet<>();
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(240, 0));
        this.setBackground(COLOR_SIDEBAR);

        // Header Sidebar (Nom de l'app ou du serveur)
        JPanel sidebarHeader = new JPanel(new GridBagLayout());
        sidebarHeader.setBackground(COLOR_SIDEBAR);
        sidebarHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(20, 20, 20)));

        JLabel appName = new JLabel("MessageApp");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        appName.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbcSidebarHeader = new GridBagConstraints();
        gbcSidebarHeader.gridx = 0;
        gbcSidebarHeader.gridy = 0;
        gbcSidebarHeader.weightx = 1.0;
        gbcSidebarHeader.anchor = GridBagConstraints.WEST;
        sidebarHeader.add(appName, gbcSidebarHeader);
        
        // Bouton "+" pour ajouter un canal
        JButton addChannelBtn = new JButton("+");
        addChannelBtn.setForeground(Color.WHITE);
        addChannelBtn.setBackground(COLOR_SIDEBAR);
        addChannelBtn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        addChannelBtn.setFocusPainted(false);
        addChannelBtn.setContentAreaFilled(false);
        addChannelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addChannelBtn.setToolTipText("Créer un canal");
        addChannelBtn.addActionListener(e -> {
            AddChannelDialog dialog = new AddChannelDialog((Frame) SwingUtilities.getWindowAncestor(this), mChannelController);
            dialog.setVisible(true);
            // Rafraichir après fermeture du dialogue
            refreshLists();
        });
        
        gbcSidebarHeader.gridx = 1;
        gbcSidebarHeader.weightx = 0;
        gbcSidebarHeader.anchor = GridBagConstraints.EAST;
        sidebarHeader.add(addChannelBtn, gbcSidebarHeader);

        GridBagConstraints gbcSidebarItem = new GridBagConstraints();
        gbcSidebarItem.gridx = 0;
        gbcSidebarItem.gridy = 0;
        gbcSidebarItem.weightx = 1.0;
        gbcSidebarItem.fill = GridBagConstraints.HORIZONTAL;
        this.add(sidebarHeader, gbcSidebarItem);

        // Liste principale (Canaux + Utilisateurs)
        JPanel mainListPanel = new JPanel(new GridBagLayout());
        mainListPanel.setBackground(COLOR_SIDEBAR);

        // On met le panel dans un ScrollPane
        JScrollPane scrollPane = new JScrollPane(mainListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_SIDEBAR);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        gbcSidebarItem.gridy = 1;
        gbcSidebarItem.weighty = 1.0;
        gbcSidebarItem.fill = GridBagConstraints.BOTH;
        this.add(scrollPane, gbcSidebarItem);

        // Input de recherche
        mSearchField = new JTextField(20);
        mSearchField.setBackground(COLOR_INPUT);
        mSearchField.setForeground(COLOR_TEXT);
        mSearchField.setCaretColor(COLOR_TEXT);
        mSearchField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterLists(mSearchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterLists(mSearchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterLists(mSearchField.getText());
            }
        });

        gbcSidebarItem.gridy = 2;
        gbcSidebarItem.weighty = 0;
        gbcSidebarItem.fill = GridBagConstraints.HORIZONTAL;
        gbcSidebarItem.insets = new java.awt.Insets(5, 10, 5, 10);
        this.add(mSearchField, gbcSidebarItem);

        // User Info (Bas de la sidebar)
        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(new Color(41, 43, 47));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Utilisateur");
        userLabel.setForeground(COLOR_TEXT);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton logoutBtn = new JButton("Déconnexion") {
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
        logoutBtn.setBackground(new Color(237, 66, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(false);
        logoutBtn.addActionListener(e -> {
            if (mSessionController != null) mSessionController.logout();
        });

        GridBagConstraints gbcUserPanel = new GridBagConstraints();
        gbcUserPanel.gridx = 0;
        gbcUserPanel.gridy = 0;
        gbcUserPanel.weightx = 1.0;
        gbcUserPanel.anchor = GridBagConstraints.WEST;
        userPanel.add(userLabel, gbcUserPanel);

        gbcUserPanel.gridx = 1;
        gbcUserPanel.weightx = 0;
        gbcUserPanel.anchor = GridBagConstraints.EAST;
        userPanel.add(logoutBtn, gbcUserPanel);

        gbcSidebarItem.gridy = 3;
        gbcSidebarItem.weighty = 0;
        gbcSidebarItem.fill = GridBagConstraints.HORIZONTAL;
        gbcSidebarItem.insets = new java.awt.Insets(0, 0, 0, 0);
        this.add(userPanel, gbcSidebarItem);

        // Stockage des références
        this.putClientProperty("userLabel", userLabel);
        this.putClientProperty("mainListPanel", mainListPanel);
    }

    public void setUser(User user) {
        this.mCurrentUser = user;
        JLabel userLabel = (JLabel) this.getClientProperty("userLabel");
        if (userLabel != null && user != null) {
            userLabel.setText(user.getUserTag());
        }
        this.refreshLists();
    }

    public void refreshLists() {
        this.listAllUsers = mSessionController.getAllUsers();
        this.listAllChannels = mChannelController.getAllChannels();
        filterLists(mSearchField.getText());
    }
    
    private void filterLists(String query) {
        if (this.listAllUsers == null) this.listAllUsers = new HashSet<>();
        if (this.listAllChannels == null) this.listAllChannels = new HashSet<>();

        if (query == null || query.trim().isEmpty()) {
            this.listAllUsersFiltered = new HashSet<>(this.listAllUsers);
            this.listAllChannelsFiltered = new HashSet<>(this.listAllChannels);
        } else {
            String lowerQuery = query.toLowerCase();
            this.listAllUsersFiltered = this.listAllUsers.stream()
                .filter(u -> u.getUserTag().toLowerCase().contains(lowerQuery) || 
                             (u.getName() != null && u.getName().toLowerCase().contains(lowerQuery)))
                .collect(Collectors.toSet());
            
            this.listAllChannelsFiltered = this.listAllChannels.stream()
                .filter(c -> c.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toSet());
        }
        
        updateDisplay();
    }

    private void updateDisplay() {
        JPanel mainListPanel = (JPanel) this.getClientProperty("mainListPanel");
        if (mainListPanel == null) return;

        mainListPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        // Section Canaux
        if (!listAllChannelsFiltered.isEmpty()) {
            JLabel channelsLabel = new JLabel("CANAUX");
            channelsLabel.setForeground(new Color(142, 146, 151));
            channelsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            channelsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            mainListPanel.add(channelsLabel, gbc);
            gbc.gridy++;

            for (Channel c : listAllChannelsFiltered) {
                JButton channelBtn = new JButton("# " + c.getName());
                channelBtn.setHorizontalAlignment(SwingConstants.LEFT);
                channelBtn.setForeground(new Color(142, 146, 151));
                channelBtn.setBackground(COLOR_SIDEBAR);
                channelBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                channelBtn.setFocusPainted(false);
                channelBtn.setContentAreaFilled(false);
                channelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                mainListPanel.add(channelBtn, gbc);
                gbc.gridy++;
            }
        }

        // Section Utilisateurs
        if (!listAllUsersFiltered.isEmpty()) {
            JLabel usersLabel = new JLabel("UTILISATEURS");
            usersLabel.setForeground(new Color(142, 146, 151));
            usersLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            usersLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
            mainListPanel.add(usersLabel, gbc);
            gbc.gridy++;

            for (User u : listAllUsersFiltered) {
                if (mCurrentUser != null && u.getUserTag().equals(mCurrentUser.getUserTag())) continue;

                JButton userBtn = new JButton(u.getUserTag());
                userBtn.setHorizontalAlignment(SwingConstants.LEFT);
                userBtn.setForeground(new Color(142, 146, 151));
                userBtn.setBackground(COLOR_SIDEBAR);
                userBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                userBtn.setFocusPainted(false);
                userBtn.setContentAreaFilled(false);
                userBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                mainListPanel.add(userBtn, gbc);
                gbc.gridy++;
            }
        }

        // Filler
        GridBagConstraints gbcFiller = new GridBagConstraints();
        gbcFiller.gridx = 0;
        gbcFiller.gridy = gbc.gridy;
        gbcFiller.weighty = 1.0;
        mainListPanel.add(Box.createGlue(), gbcFiller);

        mainListPanel.revalidate();
        mainListPanel.repaint();
    }
}
