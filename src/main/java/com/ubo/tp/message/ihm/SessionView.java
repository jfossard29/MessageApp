package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;

/**
 * Vue principale une fois l'utilisateur connecté.
 * Structure : Sidebar (Canaux) + Main Content (Chat).
 */
public class SessionView extends JPanel {

    protected IMessageAppMainViewListener mListener;
    protected User mCurrentUser;

    // Couleurs Discord-like
    private final Color COLOR_SIDEBAR = new Color(32, 34, 37);          // Sidebar très foncée
    private final Color COLOR_MAIN = new Color(54, 57, 63);             // Fond principal
    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_HEADER = new Color(47, 49, 54);           // Entête

    public SessionView(IMessageAppMainViewListener listener) {
        this.mListener = listener;
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new BorderLayout());

        // 1. Sidebar (Liste des canaux)
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setLayout(new BorderLayout());

        // Header Sidebar (Nom de l'app ou du serveur)
        JPanel sidebarHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sidebarHeader.setBackground(COLOR_SIDEBAR);
        sidebarHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(20, 20, 20)));
        JLabel appName = new JLabel("MessageApp");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        appName.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebarHeader.add(appName);
        sidebar.add(sidebarHeader, BorderLayout.NORTH);

        // Liste des canaux (Placeholder)
        JPanel channelsList = new JPanel();
        channelsList.setBackground(COLOR_SIDEBAR);
        sidebar.add(channelsList, BorderLayout.CENTER);

        // User Info (Bas de la sidebar)
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(41, 43, 47)); // Un peu plus clair que la sidebar
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
        logoutBtn.setBackground(new Color(237, 66, 69)); // Rouge
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(false);
        logoutBtn.addActionListener(e -> {
            if(mListener != null) mListener.logout();
        });

        userPanel.add(userLabel, BorderLayout.CENTER);
        userPanel.add(logoutBtn, BorderLayout.EAST);
        sidebar.add(userPanel, BorderLayout.SOUTH);


        // 2. Main Content (Zone de chat)
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(COLOR_MAIN);

        // Header Chat
        JPanel chatHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatHeader.setBackground(COLOR_HEADER);
        chatHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(30, 30, 30)));
        JLabel channelTitle = new JLabel("# général");
        channelTitle.setForeground(COLOR_TEXT);
        channelTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        channelTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chatHeader.add(channelTitle);
        mainContent.add(chatHeader, BorderLayout.NORTH);

        // Zone de messages
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(COLOR_MAIN);
        chatArea.setForeground(COLOR_TEXT);
        mainContent.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Zone de saisie
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(COLOR_MAIN);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextField inputField = new JTextField();
        inputField.setBackground(new Color(64, 68, 75));
        inputField.setForeground(COLOR_TEXT);
        inputField.setCaretColor(COLOR_TEXT);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        mainContent.add(inputPanel, BorderLayout.SOUTH);

        // Assemblage
        this.add(sidebar, BorderLayout.WEST);
        this.add(mainContent, BorderLayout.CENTER);

        // Stockage des références pour mise à jour
        this.putClientProperty("userLabel", userLabel);
    }

    public void setUser(User user) {
        this.mCurrentUser = user;
        JLabel userLabel = (JLabel) this.getClientProperty("userLabel");
        if (userLabel != null && user != null) {
            userLabel.setText(user.getUserTag());
        }
    }
}
