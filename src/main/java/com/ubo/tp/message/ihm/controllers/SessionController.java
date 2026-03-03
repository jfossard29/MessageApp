package main.java.com.ubo.tp.message.ihm.controllers;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import java.util.HashSet;
import java.util.Set;

public class SessionController implements ISessionController, IDatabaseObserver {

    private final DataManager mDataManager;
    private final MessageAppController mMainController;
    private Channel mSelectedChannel;
    private final Set<ISessionControllerObserver> mObservers = new HashSet<>();

    public SessionController(DataManager dataManager, MessageAppController mainController) {
        this.mDataManager = dataManager;
        this.mMainController = mainController;
        this.mDataManager.addObserver(this);
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

    @Override
    public void selectChannel(Channel channel) {
        this.mSelectedChannel = channel;
        for (ISessionControllerObserver observer : mObservers) {
            observer.onChannelSelected(channel);
        }
    }

    @Override
    public Channel getSelectedChannel() {
        return this.mSelectedChannel;
    }

    @Override
    public void addObserver(ISessionControllerObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void removeObserver(ISessionControllerObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        if (mSelectedChannel != null && addedMessage.getRecipient().equals(mSelectedChannel.getUuid())) {
            for (ISessionControllerObserver observer : mObservers) {
                observer.onChannelSelected(mSelectedChannel);
            }
        }
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {
        if (mSelectedChannel != null && deletedMessage.getRecipient().equals(mSelectedChannel.getUuid())) {
            for (ISessionControllerObserver observer : mObservers) {
                observer.onChannelSelected(mSelectedChannel);
            }
        }
    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {
        if (mSelectedChannel != null && modifiedMessage.getRecipient().equals(mSelectedChannel.getUuid())) {
            for (ISessionControllerObserver observer : mObservers) {
                observer.onChannelSelected(mSelectedChannel);
            }
        }
    }

    @Override
    public void notifyUserAdded(User addedUser) {
        for (ISessionControllerObserver observer : mObservers) {
            observer.onUsersUpdated();
        }
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        for (ISessionControllerObserver observer : mObservers) {
            observer.onUsersUpdated();
        }
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        for (ISessionControllerObserver observer : mObservers) {
            observer.onUsersUpdated();
        }
    }

    @Override
    public void notifyChannelAdded(Channel addedChannel) {
    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {
        if (mSelectedChannel != null && deletedChannel.getUuid().equals(mSelectedChannel.getUuid())) {
            this.mSelectedChannel = null;
            for (ISessionControllerObserver observer : mObservers) {
                observer.onChannelSelected(null);
            }
        }
    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {
        if (mSelectedChannel != null && modifiedChannel.getUuid().equals(mSelectedChannel.getUuid())) {
            this.mSelectedChannel = modifiedChannel;
            for (ISessionControllerObserver observer : mObservers) {
                observer.onChannelSelected(mSelectedChannel);
            }
        }
    }
}
