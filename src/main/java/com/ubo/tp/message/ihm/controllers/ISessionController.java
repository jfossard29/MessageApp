package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.datamodel.User;
import java.util.Set;

/**
 * Interface pour le contrôleur de la vue principale (session).
 */
public interface ISessionController {
    /**
     * Déconnecte l'utilisateur courant.
     */
    void logout();

    /**
     * Récupère l'utilisateur courant.
     * @return L'utilisateur connecté ou null.
     */
    User getCurrentUser();

    /**
     * Récupère la liste des utilisateurs enregistrés.
     * @return La liste des utilisateurs.
     */
    Set<User> getAllUsers();
}
