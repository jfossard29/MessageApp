package main.java.com.ubo.tp.message.ihm;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.views.MessageView;
import main.java.com.ubo.tp.message.ihm.views.UserView;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Classe de test pour visualiser les composants UI isolément.
 */
public class MessageAppPlayground {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MessageApp Playground");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(new Color(54, 57, 63)); // Fond Discord

            // Test UserView
            User user1 = new User(UUID.randomUUID(), "AliceTag", "alice123", "Alice Wonderland");
            UserView userView = new UserView(user1);
            mainPanel.add(userView);

            // Test MessageView
            UUID recipientUuid = UUID.randomUUID(); // Destinataire fictif
            Message message1 = new Message(user1, recipientUuid, "Salut tout le monde ! Ceci est un message de test.");
            MessageView messageView1 = new MessageView(message1);
            mainPanel.add(messageView1);

            User user2 = new User(UUID.randomUUID(), "BobTag", "bob456", "Bob Builder");
            Message message2 = new Message(user2, recipientUuid, "Bonjour Alice ! Comment vas-tu ?");
            MessageView messageView2 = new MessageView(message2);
            mainPanel.add(messageView2);

            frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
