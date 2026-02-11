package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.session.ISession;
import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.User;

import java.util.Set;

public class ChannelController implements IChannelController {

    private final DataManager mDataManager;
    private final ISession mSession;

    public ChannelController(DataManager dataManager, ISession session) {
        this.mDataManager = dataManager;
        this.mSession = session;
    }

    @Override
    public boolean createChannel(String name) {
        User creator = mSession.getConnectedUser();
        if (creator == null || name == null || name.trim().isEmpty()) {
            return false;
        }

        // Vérifier si le canal existe déjà (optionnel, mais recommandé)
        for (Channel c : mDataManager.getChannels()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }

        Channel newChannel = new Channel(creator, name);
        mDataManager.sendChannel(newChannel);
        return true;
    }

    @Override
    public Set<Channel> getAllChannels() {
        return mDataManager.getChannels();
    }
}
