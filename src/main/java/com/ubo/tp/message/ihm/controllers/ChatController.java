package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.session.ISession;
import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatController implements IChatController {

    private final DataManager mDataManager;
    private final ISession mSession;

    public ChatController(DataManager dataManager, ISession session) {
        this.mDataManager = dataManager;
        this.mSession = session;
    }

    @Override
    public void sendMessage(String text, Channel channel) {
        if (channel != null && text != null && !text.trim().isEmpty()) {
            User sender = mSession.getConnectedUser();
            if (sender != null) {
                Message newMessage = new Message(sender, channel.getUuid(), text);
                mDataManager.sendMessage(newMessage);
            }
        }
    }

    @Override
    public Set<Message> getMessagesForChannel(Channel channel) {
        if (channel == null) {
            return new HashSet<>();
        }
        return mDataManager.getMessages().stream()
                .filter(m -> m.getRecipient().equals(channel.getUuid()))
                .collect(Collectors.toSet());
    }
}
