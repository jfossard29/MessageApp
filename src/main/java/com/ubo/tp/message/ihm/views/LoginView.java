package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.ihm.controllers.ILoginController;

import javax.swing.*;
import java.awt.*;

/**
 * Vue dédiée à l'authentification et l'enregistrement.
 * Design inspiré de Discord (et fait par Gemini).
 */
public class LoginView extends JPanel {

    protected ILoginController mController;
    protected boolean isRegistrationMode = true;

    // Couleurs Discord-like
    private final Color COLOR_BACKGROUND = new Color(54, 57, 63);       // Gris foncé fond
    private final Color COLOR_PANEL = new Color(47, 49, 54);            // Gris un peu plus foncé pour les conteneurs
    private final Color COLOR_INPUT = new Color(64, 68, 75);            // Gris champs texte
    private final Color COLOR_TEXT = new Color(220, 221, 222);          // Blanc cassé
    private final Color COLOR_ACCENT = new Color(88, 101, 242);         // "Blurple" (Bleu/Violet Discord)

    // Composants
    private JTextField mNomField;
    private JTextField mTagField;
    private JPasswordField mPassField;
    private JLabel mLabelNom;

    public LoginView(ILoginController controller) {
        this.mController = controller;
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new GridBagLayout()); // Pour centrer le panneau
        this.setBackground(COLOR_BACKGROUND);

        // Panneau central (la "carte" de login)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(COLOR_PANEL);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        centerPanel.add(titleLabel, gbc);

        // Sous-titre
        JLabel subTitleLabel = new JLabel("Nous sommes ravis de vous revoir.");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitleLabel.setForeground(Color.GRAY);
        subTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        centerPanel.add(subTitleLabel, gbc);

        // Champs
        gbc.insets = new Insets(5, 0, 5, 0);
        
        // Nom (visible uniquement en enregistrement)
        mLabelNom = createLabel("NOM D'AFFICHAGE");
        gbc.gridy = 2;
        centerPanel.add(mLabelNom, gbc);

        mNomField = createTextField();
        gbc.gridy = 3;
        centerPanel.add(mNomField, gbc);

        // Tag
        centerPanel.add(createLabel("TAG UTILISATEUR"), createGbc(4));
        mTagField = createTextField();
        centerPanel.add(mTagField, createGbc(5));

        // Password
        centerPanel.add(createLabel("MOT DE PASSE"), createGbc(6));
        mPassField = createPasswordField();
        centerPanel.add(mPassField, createGbc(7));

        // Bouton Valider
        JButton btnValider = new JButton("S'inscrire") {
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
        styleButton(btnValider);
        gbc.gridy = 8;
        gbc.insets = new Insets(20, 0, 10, 0);
        centerPanel.add(btnValider, gbc);

        // Lien pour changer de mode
        JButton btnSwitch = new JButton("Tu as déjà un compte ? Connecte-toi");
        btnSwitch.setForeground(COLOR_ACCENT);
        btnSwitch.setContentAreaFilled(false);
        btnSwitch.setBorderPainted(false);
        btnSwitch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(btnSwitch, gbc);

        // Actions
        btnSwitch.addActionListener(e -> {
            isRegistrationMode = !isRegistrationMode;
            if (isRegistrationMode) {
                mLabelNom.setVisible(true);
                mNomField.setVisible(true);
                btnValider.setText("S'inscrire");
                titleLabel.setText("Créer un compte");
                subTitleLabel.setText("Rejoignez la communauté.");
                btnSwitch.setText("Tu as déjà un compte ? Connecte-toi");
            } else {
                mLabelNom.setVisible(false);
                mNomField.setVisible(false);
                btnValider.setText("Se connecter");
                titleLabel.setText("Bon retour !");
                subTitleLabel.setText("Nous sommes ravis de vous revoir.");
                btnSwitch.setText("Besoin d'un compte ? Inscris-toi");
            }
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        btnValider.addActionListener(e -> {
            if (mController != null) {
                String tag = mTagField.getText();
                String pwd = new String(mPassField.getPassword());
                
                if (isRegistrationMode) {
                    String nom = mNomField.getText();
                    if(nom.isEmpty() || tag.isEmpty() || pwd.isEmpty()) return;
                    
                    boolean success = mController.registerUser(nom, tag, pwd);
                    if (!success) {
                        JOptionPane.showMessageDialog(this, "Ce tag existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(tag.isEmpty() || pwd.isEmpty()) return;
                    
                    boolean success = mController.authenticate(tag, pwd);
                    if (!success) {
                        JOptionPane.showMessageDialog(this, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.add(centerPanel);
    }

    private GridBagConstraints createGbc(int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        return gbc;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(185, 187, 190));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(COLOR_INPUT);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_TEXT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setBackground(COLOR_INPUT);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_TEXT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return field;
    }

    private void styleButton(JButton btn) {
        btn.setBackground(COLOR_ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false); // Important pour le custom painting
        btn.setOpaque(false); // Important pour le custom painting
    }
}
