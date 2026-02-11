package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.datamodel.User;

import java.util.Set;

public class SessionController implements ISessionController {

    private final DataManager mDataManager;
    private final MessageAppController mMainController;

    public SessionController(DataManager dataManager, MessageAppController mainController) {
        this.mDataManager = dataManager;
        this.mMainController = mainController;
    }

    @Override
    public void logout() {
        this.mMainController.logout();
    }

    @Override
    public User getCurrentUser() {
        return this.mMainController.getCurrentUser();
    }

    @Override
    public Set<User> getAllUsers() {
        return this.mDataManager.getUsers();
    }
}
