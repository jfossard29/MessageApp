package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.session.ISession;
import main.java.com.ubo.tp.message.core.session.ISessionObserver;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.MessageAppMainView;

/**
 * Controller principal de l'application.
 * Coordonne les sous-contrôleurs et la navigation.
 */
public class MessageAppController implements ISessionObserver {

    protected DataManager mDataManager;
    protected ISession mSession;
    protected MessageAppMainView mView;

    protected LoginController mLoginController;
    protected SessionController mSessionController;
    protected ChannelController mChannelController;

    public MessageAppController(DataManager dataManager, ISession session, MessageAppMainView view) {
        this.mDataManager = dataManager;
        this.mSession = session;
        this.mView = view;
        
        // On s'abonne aux changements de session
        this.mSession.addObserver(this);
        
        // Initialisation des sous-contrôleurs
        this.mLoginController = new LoginController(mDataManager, this);
        this.mSessionController = new SessionController(mDataManager, this);
        this.mChannelController = new ChannelController(mDataManager, session);
    }

    public void initView() {
        // Initialisation de la vue avec les contrôleurs
        this.mView.init(mLoginController, mSessionController, mChannelController);
    }

    /**
     * Appelé par LoginController quand une connexion réussit.
     */
    public void loginSuccess(User user) {
        // On délègue à la session
        this.mSession.connect(user);
    }

    /**
     * Appelé par SessionController pour se déconnecter.
     */
    public void logout() {
        // On délègue à la session
        this.mSession.disconnect();
    }

    public User getCurrentUser() {
        return this.mSession.getConnectedUser();
    }

    @Override
    public void notifyLogin(User connectedUser) {
        // La session nous notifie d'une connexion -> on met à jour la vue
        this.mView.showLoggedIn(connectedUser);
    }

    @Override
    public void notifyLogout() {
        // La session nous notifie d'une déconnexion -> on met à jour la vue
        this.mView.showLoggedOut();
    }
}
