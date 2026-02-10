package main.java.com.ubo.tp.message.ihm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Classe de la vue principale de l'application.
 */
public class MessageAppMainView1 {
    
    protected JFrame mFrame;

    public void init() {
        // Conteneur principal
        mFrame = new JFrame("MessageApp");
        mFrame.setSize(400, 300);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image ImageIcone = new ImageIcon("src/main/resources/images/logo_20.png").getImage();
        Icon icone = new ImageIcon("src/main/resources/images/logo_20.png");

        mFrame.setIconImage(ImageIcone);

        // Menu déroulant
        JMenu menu = new JMenu("Fichier");

        // Item "Quitter"
        JMenuItem menuItem = new JMenuItem("Quitter");
        menu.add(menuItem);
        menuItem.addActionListener(e -> System.exit(0));

        mFrame.setJMenuBar(new JMenuBar());
        mFrame.getJMenuBar().add(menu);

        // Item "Ouvrir"
        JMenuItem menuOpen = getJMenuItem(mFrame);
        menu.add(menuOpen);

        //Menu "?"
        JMenuItem menuAbout = new JMenuItem("?");
        menuAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(mFrame,
                    "UBO M2-TIIL Département Informatique",
                    "A propos",
                    JOptionPane.INFORMATION_MESSAGE,
                    icone);
        });

        mFrame.getJMenuBar().add(menuAbout);
    }

    public void show() {
        mFrame.setVisible(true);
    }

    private JMenuItem getJMenuItem(JFrame frame) {
        JMenuItem menuOpen = new JMenuItem("Ouvrir");
        menuOpen.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            }
        });
        return menuOpen;
    }
}
