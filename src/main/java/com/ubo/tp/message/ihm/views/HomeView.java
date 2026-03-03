package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IChannelController;
import main.java.com.ubo.tp.message.ihm.controllers.IChatController;
import main.java.com.ubo.tp.message.ihm.controllers.ISessionController;

import javax.swing.*;
import java.awt.*;

/**
 * Vue principale une fois l'utilisateur connecté.
 * Contient la SidebarView et la ChatView.
 */
public class HomeView extends JPanel {

    protected ISessionController mSessionController;
    protected IChannelController mChannelController;
    protected IChatController mChatController;
    
    // Sous-vues
    protected SidebarView mSidebarView;
    protected ChatView mChatView;

    public HomeView(ISessionController sessionController, IChannelController channelController, IChatController chatController) {
        this.mSessionController = sessionController;
        this.mChannelController = channelController;
        this.mChatController = chatController;
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialisation des sous-vues
        this.mSidebarView = new SidebarView(this.mSessionController, this.mChannelController);
        this.mChatView = new ChatView(this.mSessionController, this.mChatController);

        // Ajout de la Sidebar (à gauche)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0; // La sidebar a une largeur fixe (définie dans SidebarView)
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(mSidebarView, gbc);

        // Ajout de la ChatView (au centre/droite)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Prend tout l'espace restant
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(mChatView, gbc);
    }

    public void setUser(User user) {
        // Propager l'utilisateur à la sidebar pour mise à jour de l'affichage
        if (mSidebarView != null) {
            mSidebarView.setUser(user);
        }
    }
}
