package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.session.ISession;
import main.java.com.ubo.tp.message.datamodel.User;

public class ProfileController implements IProfileController {

    private final DataManager mDataManager;
    private final ISession mSession;

    public ProfileController(DataManager dataManager, ISession session) {
        this.mDataManager = dataManager;
        this.mSession = session;
    }

    @Override
    public void updateName(String newName) {
        User user = mSession.getConnectedUser();
        if (user != null && newName != null && !newName.trim().isEmpty()) {
            user.setName(newName);
            mDataManager.sendUser(user);
        }
    }

    @Override
    public void deleteAccount() {
        User user = mSession.getConnectedUser();
        if (user != null) {
            // On déconnecte d'abord l'utilisateur
            mSession.disconnect();
            
            // Puis on supprime son compte (fichier)
            mDataManager.deleteUserAccount(user);
        }
    }

    @Override
    public User getCurrentUser() {
        return mSession.getConnectedUser();
    }
}
