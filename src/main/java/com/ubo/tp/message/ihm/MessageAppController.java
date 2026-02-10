package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Controller pour la vue principale.
 */
public class MessageAppController {

    protected DataManager mDataManager;
    protected MessageAppMainView mView;
    protected User mCurrentUser;

    public MessageAppController(DataManager dataManager, MessageAppMainView view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    public boolean registerUser(String name, String tag, String password) {
        for (User user : this.mDataManager.getUsers()) {
            if (user.getUserTag().equals(tag)) {
                return false;
            }
        }
        User newUser = new User(tag, password, name);
        this.mDataManager.sendUser(newUser);
        return true;
    }

    public boolean authenticate(String tag, String password) {
        for (User user : this.mDataManager.getUsers()) {
            if (user.getUserTag().equals(tag) && user.getUserPassword().equals(password)) {
                this.mCurrentUser = user;
                this.mView.showLoggedIn(user);
                return true;
            }
        }
        return false;
    }

    public void logout() {
        this.mCurrentUser = null;
        this.mView.showLoggedOut();
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }
}
