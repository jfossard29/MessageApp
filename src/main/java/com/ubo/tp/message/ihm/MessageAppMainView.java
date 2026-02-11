package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IChannelController;
import main.java.com.ubo.tp.message.ihm.controllers.ILoginController;
import main.java.com.ubo.tp.message.ihm.controllers.ISessionController;
import main.java.com.ubo.tp.message.ihm.views.LoginView;
import main.java.com.ubo.tp.message.ihm.views.HomeView;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de la vue principale de l'application.
 * Agit comme un conteneur principal gérant la navigation entre les vues.
 */
public class MessageAppMainView {
    
    protected JFrame mFrame;
    protected JPanel mMainContainer;
    protected CardLayout mLayout;

    // Vues enfants
    protected LoginView mLoginView;
    protected HomeView mHomeView;

    public void init(ILoginController loginController, ISessionController sessionController, IChannelController channelController) {
        // Configuration de la fenêtre principale
        mFrame = new JFrame("MessageApp");
        mFrame.setSize(1200, 800); // Taille plus grande pour le style desktop
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null); // Centrer à l'écran

        // Layout principal
        mLayout = new CardLayout();
        mMainContainer = new JPanel(mLayout);
        
        mLoginView = new LoginView(loginController);
        mHomeView = new HomeView(sessionController, channelController);

        mMainContainer.add(mLoginView, "LOGIN");
        mMainContainer.add(mHomeView, "SESSION");

        mFrame.add(mMainContainer, BorderLayout.CENTER);
        
        this.initIcon();
        
        // Afficher la vue de login par défaut
        mLayout.show(mMainContainer, "LOGIN");
    }

    public void showLoggedIn(User user) {
        mHomeView.setUser(user);
        mLayout.show(mMainContainer, "SESSION");
    }

    public void showLoggedOut() {
        mLayout.show(mMainContainer, "LOGIN");
    }

    protected void initIcon() {
        Image imageIcone = new ImageIcon("src/main/resources/images/logo_20.png").getImage();
        mFrame.setIconImage(imageIcone);
    }

    public void show() {
        mFrame.setVisible(true);
    }
}
