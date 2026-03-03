package main.java.com.ubo.tp.message.ihm.views;

import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.IChatController;
import main.java.com.ubo.tp.message.ihm.controllers.ISessionController;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatView extends JPanel implements ISessionController.ISessionControllerObserver {

    protected ISessionController mSessionController;
    protected IChatController mChatController;
    private JLabel mChannelTitle;
    private JPanel mMessagesPanel;

    // Couleurs Discord-like
    private final Color COLOR_MAIN = new Color(54, 57, 63);
    private final Color COLOR_TEXT = new Color(220, 221, 222);
    private final Color COLOR_HEADER = new Color(47, 49, 54);

    public ChatView(ISessionController sessionController, IChatController chatController) {
        this.mSessionController = sessionController;
        this.mChatController = chatController;
        this.mSessionController.addObserver(this);
        this.initGui();
    }

    private void initGui() {
        this.setLayout(new GridBagLayout());
        this.setBackground(COLOR_MAIN);
        GridBagConstraints gbc = new GridBagConstraints();

        // Header Chat
        JPanel chatHeader = new JPanel(new GridBagLayout());
        chatHeader.setBackground(COLOR_HEADER);
        chatHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(30, 30, 30)));

        mChannelTitle = new JLabel("Sélectionnez un canal");
        mChannelTitle.setForeground(COLOR_TEXT);
        mChannelTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mChannelTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbcChatHeader = new GridBagConstraints();
        gbcChatHeader.gridx = 0;
        gbcChatHeader.gridy = 0;
        gbcChatHeader.weightx = 1.0;
        gbcChatHeader.anchor = GridBagConstraints.WEST;
        chatHeader.add(mChannelTitle, gbcChatHeader);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(chatHeader, gbc);

        // Zone de messages
        mMessagesPanel = new JPanel(new GridBagLayout()); // Utilisation de GridBagLayout
        mMessagesPanel.setBackground(COLOR_MAIN);

        // Wrapper pour aligner en haut si nécessaire, mais ici on veut coller en bas
        JPanel messagesWrapper = new JPanel(new BorderLayout());
        messagesWrapper.setBackground(COLOR_MAIN);
        messagesWrapper.add(mMessagesPanel, BorderLayout.NORTH); // Astuce : on met le panel en haut d'un wrapper, mais on gère le collage en bas DANS le panel

        JScrollPane scrollPane = new JScrollPane(mMessagesPanel); // On scroll directement le panel GridBag
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_MAIN);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPane, gbc);

        // Zone de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(COLOR_MAIN);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField inputField = new JTextField();
        inputField.setBackground(new Color(64, 68, 75));
        inputField.setForeground(COLOR_TEXT);
        inputField.setCaretColor(COLOR_TEXT);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputField.addActionListener(e -> {
            String text = inputField.getText();
            if (!text.isEmpty()) {
                mChatController.sendMessage(text, mSessionController.getSelectedChannel());
                inputField.setText("");
            }
        });

        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.gridx = 0;
        gbcInput.gridy = 0;
        gbcInput.weightx = 1.0;
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(inputField, gbcInput);

        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(inputPanel, gbc);
    }

    @Override
    public void onChannelSelected(Channel channel) {
        if (channel != null) {
            mChannelTitle.setText("# " + channel.getName());
            
            mMessagesPanel.removeAll();
            
            GridBagConstraints gbcMsg = new GridBagConstraints();
            gbcMsg.gridx = 0;
            gbcMsg.gridy = 0;
            gbcMsg.weightx = 1.0;
            gbcMsg.fill = GridBagConstraints.HORIZONTAL;
            gbcMsg.anchor = GridBagConstraints.NORTH;

            // Filler pour pousser tout vers le bas
            // On ajoute un composant vide qui prend tout l'espace vertical restant
            JPanel filler = new JPanel();
            filler.setOpaque(false);
            GridBagConstraints gbcFiller = new GridBagConstraints();
            gbcFiller.gridx = 0;
            gbcFiller.gridy = 0;
            gbcFiller.weightx = 1.0;
            gbcFiller.weighty = 1.0; // Prend tout l'espace vertical disponible
            gbcFiller.fill = GridBagConstraints.BOTH;
            mMessagesPanel.add(filler, gbcFiller);
            
            Set<Message> messagesSet = mChatController.getMessagesForChannel(channel);
            User currentUser = mSessionController.getCurrentUser();
            
            // Trier les messages par date d'émission
            List<Message> sortedMessages = messagesSet.stream()
                .sorted(Comparator.comparingLong(Message::getEmissionDate))
                .collect(Collectors.toList());
            
            int gridY = 1; // On commence après le filler
            for (Message msg : sortedMessages) {
                boolean isCurrentUser = currentUser != null && msg.getSender().getUserTag().equals(currentUser.getUserTag());
                
                gbcMsg.gridy = gridY++;
                gbcMsg.weighty = 0.0; // Les messages ne prennent pas d'espace vertical supplémentaire
                mMessagesPanel.add(new MessageView(msg, isCurrentUser), gbcMsg);
            }
            
            mMessagesPanel.revalidate();
            mMessagesPanel.repaint();
            
            // Scroll automatique vers le bas
            SwingUtilities.invokeLater(() -> {
                JScrollPane scrollPane = (JScrollPane) mMessagesPanel.getParent().getParent();
                if (scrollPane != null) {
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());
                }
            });

        } else {
            mChannelTitle.setText("Sélectionnez un canal");
            mMessagesPanel.removeAll();
            mMessagesPanel.revalidate();
            mMessagesPanel.repaint();
        }
    }

    @Override
    public void onUsersUpdated() {
        // Ne fait rien ici, géré par SidebarView
    }
}
