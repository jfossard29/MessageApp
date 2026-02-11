package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.datamodel.Channel;
import java.util.Set;

public interface IChannelController {
    /**
     * Crée un nouveau canal.
     * @param name Nom du canal
     * @return true si la création a réussi
     */
    boolean createChannel(String name);

    /**
     * Récupère tous les canaux.
     */
    Set<Channel> getAllChannels();
}
