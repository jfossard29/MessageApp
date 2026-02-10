package main.java.com.ubo.tp.message.ihm;

/**
 * Interface pour écouter les événements de la vue principale.
 */
public interface IMessageAppMainViewListener {
    
    /**
     * Tente d'enregistrer un nouvel utilisateur.
     * @return true si l'enregistrement a réussi, false sinon.
     */
    boolean registerUser(String name, String tag, String password);

    /**
     * Tente d'authentifier un utilisateur.
     * @return true si l'authentification a réussi, false sinon.
     */
    boolean authenticate(String tag, String password);

    /**
     * Déconnecte l'utilisateur courant.
     */
    void logout();
}
