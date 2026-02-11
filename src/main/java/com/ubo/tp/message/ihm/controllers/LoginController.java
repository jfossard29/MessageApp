package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.datamodel.User;

public class LoginController implements ILoginController {

    private final DataManager mDataManager;
    private final MessageAppController mMainController;

    public LoginController(DataManager dataManager, MessageAppController mainController) {
        this.mDataManager = dataManager;
        this.mMainController = mainController;
    }

    @Override
    public boolean registerUser(String name, String tag, String password) {
        for (User user : this.mDataManager.getUsers()) {
            if (user.getUserTag().equals(tag)) {
                return false;
            }
        }
        User newUser = new User(tag, password, name);
        this.mDataManager.sendUser(newUser);
        
        // Connexion automatique après enregistrement via le contrôleur principal
        this.mMainController.loginSuccess(newUser);

        return true;
    }

    @Override
    public boolean authenticate(String tag, String password) {
        for (User user : this.mDataManager.getUsers()) {
            if (user.getUserTag().equals(tag) && user.getUserPassword().equals(password)) {
                this.mMainController.loginSuccess(user);
                return true;
            }
        }
        return false;
    }
}
