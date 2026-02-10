package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Classe de la vue principale de l'application.
 */
public class MessageAppMainView {
    
    protected JFrame mFrame;

    protected JPanel mMainContainer;

    protected CardLayout mLayout;

    protected MessageAppController mController;

    protected boolean isRegistrationMode = true;

    // Composants accessibles pour mise à jour
    protected JButton mLoginButton;
    protected JButton mChannelsButton;
    protected JLabel mUserTagLabel;

    public void setController(MessageAppController controller) {
        this.mController = controller;
    }

    public void init() {
        // Conteneur principal
        mFrame = new JFrame("MessageApp");
        mFrame.setSize(800, 600);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mLayout = new CardLayout();
        mMainContainer = new JPanel(mLayout);

        this.initIcon();
        this.initMenuBar();

        // Panel d'accueil (Boutons Login et Canaux)
        JPanel homePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        mLoginButton = new JButton("Login");
        mChannelsButton = new JButton("Canaux");
        mChannelsButton.setVisible(false); // Caché par défaut

        mUserTagLabel = new JLabel("");
        mUserTagLabel.setFont(new Font("Serif", Font.BOLD, 14));
        mUserTagLabel.setForeground(Color.BLUE);
        mUserTagLabel.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 0;
        homePanel.add(mLoginButton, gbc);
        gbc.gridx = 1;
        homePanel.add(mChannelsButton, gbc);
        
        // Affichage du tag en haut ou en dessous
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        homePanel.add(mUserTagLabel, gbc);

        // Ecran d'enregistrement / Connexion
        JPanel panelSubscribe = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSub = new GridBagConstraints();
        gbcSub.insets = new Insets(5, 5, 5, 5);
        gbcSub.fill = GridBagConstraints.HORIZONTAL;

        // Boutons de choix de mode
        JButton btnEnregistrement = new JButton("Enregistrement");
        JButton btnConnexion = new JButton("Connexion");
        
        btnEnregistrement.setBackground(Color.LIGHT_GRAY);
        btnConnexion.setBackground(null);

        JPanel modePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        modePanel.add(btnEnregistrement);
        modePanel.add(btnConnexion);

        gbcSub.gridx = 0; gbcSub.gridy = 0; gbcSub.gridwidth = 2;
        panelSubscribe.add(modePanel, gbcSub);

        // Champs du formulaire
        JLabel labelNom = new JLabel("Nom d'affichage");
        JTextField nom = new JTextField(15);
        
        gbcSub.gridwidth = 1; gbcSub.gridy = 1; gbcSub.gridx = 0;
        panelSubscribe.add(labelNom, gbcSub);

        gbcSub.gridx = 1;
        panelSubscribe.add(nom, gbcSub);

        gbcSub.gridy = 2; gbcSub.gridx = 0;
        panelSubscribe.add(new JLabel("Tag"), gbcSub);

        gbcSub.gridx = 1;
        JTextField tag = new JTextField(15);
        panelSubscribe.add(tag, gbcSub);

        gbcSub.gridy = 3; gbcSub.gridx = 0;
        panelSubscribe.add(new JLabel("Password"), gbcSub);

        gbcSub.gridx = 1;
        JTextField pwd = new JTextField(15);
        panelSubscribe.add(pwd, gbcSub);

        // Gestion des modes
        btnEnregistrement.addActionListener(e -> {
            isRegistrationMode = true;
            labelNom.setVisible(true);
            nom.setVisible(true);
            btnEnregistrement.setBackground(Color.LIGHT_GRAY);
            btnConnexion.setBackground(null);
            panelSubscribe.revalidate();
            panelSubscribe.repaint();
        });

        btnConnexion.addActionListener(e -> {
            isRegistrationMode = false;
            labelNom.setVisible(false);
            nom.setVisible(false);
            btnConnexion.setBackground(Color.LIGHT_GRAY);
            btnEnregistrement.setBackground(null);
            panelSubscribe.revalidate();
            panelSubscribe.repaint();
        });

        JButton valider = new JButton("Valider");
        valider.addActionListener(e -> {
            if (mController != null) {
                if (isRegistrationMode) {
                    boolean success = mController.registerUser(nom.getText(), tag.getText(), pwd.getText());
                    if (success) {
                        mLayout.show(mMainContainer, "HOME");
                    } else {
                        JOptionPane.showMessageDialog(mFrame, "Erreur : Ce tag existe déjà.", "Erreur d'enregistrement", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    boolean success = mController.authenticate(tag.getText(), pwd.getText());
                    if (!success) {
                        JOptionPane.showMessageDialog(mFrame, "Erreur : Tag ou mot de passe incorrect.", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                    }
                    // Si succès, le controller appellera showLoggedIn qui changera la vue
                }
            }
        });

        gbcSub.gridy = 4; gbcSub.gridx = 0; gbcSub.gridwidth = 2;
        panelSubscribe.add(valider, gbcSub);

        // Action du bouton Login / Logout
        mLoginButton.addActionListener(e -> {
            if (mController != null && mController.getCurrentUser() != null) {
                mController.logout();
            } else {
                mLayout.show(mMainContainer, "SUBSCRIBE");
            }
        });

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> mLayout.show(mMainContainer, "HOME"));
        gbcSub.gridy = 5 ; gbcSub.gridx = 0; gbcSub.gridwidth = 2;
        panelSubscribe.add(retour, gbcSub);

        mMainContainer.add(homePanel, "HOME");
        mMainContainer.add(panelSubscribe, "SUBSCRIBE");

        mFrame.add(mMainContainer, BorderLayout.CENTER);
        mLayout.show(mMainContainer, "HOME");
    }

    public void showLoggedIn(User user) {
        mLoginButton.setText("Logout");
        mChannelsButton.setVisible(true);
        mUserTagLabel.setText("Connecté en tant que : @" + user.getUserTag());
        mUserTagLabel.setVisible(true);
        mLayout.show(mMainContainer, "HOME");
    }

    public void showLoggedOut() {
        mLoginButton.setText("Login");
        mChannelsButton.setVisible(false);
        mUserTagLabel.setText("");
        mUserTagLabel.setVisible(false);
        mLayout.show(mMainContainer, "HOME");
    }

    protected void initIcon() {
        Image imageIcone = new ImageIcon("src/main/resources/images/logo_20.png").getImage();
        mFrame.setIconImage(imageIcone);
    }

    protected void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Fichier
        JMenu menu = new JMenu("Fichier");
        menu.add(createExitMenuItem());
        menu.add(getJMenuItem(mFrame));
        menuBar.add(menu);

        // Menu A propos
        menuBar.add(createAboutMenuItem());

        mFrame.setJMenuBar(menuBar);
    }

    private JMenuItem createExitMenuItem() {
        JMenuItem menuItem = new JMenuItem("Quitter");
        menuItem.addActionListener(e -> System.exit(0));
        return menuItem;
    }

    private JMenuItem createAboutMenuItem() {
        Icon icone = new ImageIcon("src/main/resources/images/logo_20.png");
        JMenuItem menuAbout = new JMenuItem("?");
        menuAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(mFrame,
                    "UBO M2-TIIL Département Informatique",
                    "A propos",
                    JOptionPane.INFORMATION_MESSAGE,
                    icone);
        });
        return menuAbout;
    }

    public void show() {
        mFrame.setVisible(true);
    }

    private JMenuItem getJMenuItem(JFrame frame) {
        JMenuItem menuOpen = new JMenuItem("Ouvrir");
        menuOpen.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            }
        });
        return menuOpen;
    }


}
