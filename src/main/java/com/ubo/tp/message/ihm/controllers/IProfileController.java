package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.datamodel.User;

public interface IProfileController {
    void updateName(String newName);
    void deleteAccount();
    User getCurrentUser();
}
