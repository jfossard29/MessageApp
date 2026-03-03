package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.Message;

import java.util.Set;

/**
 * Interface pour le contrôleur de chat.
 * Gère l'envoi et la récupération des messages.
 */
public interface IChatController {

    /**
     * Envoie un message dans un canal donné.
     * @param text Le contenu du message.
     * @param channel Le canal destinataire.
     */
    void sendMessage(String text, Channel channel);

    /**
     * Récupère les messages d'un canal donné.
     * @param channel Le canal dont on veut les messages.
     * @return La liste des messages.
     */
    Set<Message> getMessagesForChannel(Channel channel);
}
