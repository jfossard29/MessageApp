package main.java.com.ubo.tp.message.ihm;

import java.io.File;

import main.java.com.ubo.tp.message.core.DataManager;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.core.session.ISession;
import main.java.com.ubo.tp.message.core.session.Session;
import main.java.com.ubo.tp.message.datamodel.Channel;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.controllers.MessageAppController;

import javax.swing.*;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageApp implements IDatabaseObserver {
	/**
	 * Base de données.
	 */
	protected DataManager mDataManager;

	/**
	 * Session de l'application.
	 */
	protected ISession mSession;

	/**
	 * Vue principale de l'application.
	 */
	protected MessageAppMainView mMainView;

	/**
	 * Controller de l'application.
	 */
	protected MessageAppController mController;

	/**
	 * Constructeur.
	 *
	 * @param dataManager
	 */
	public MessageApp(DataManager dataManager) {
		this.mDataManager = dataManager;
		this.mDataManager.addObserver(this);
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		this.mSession = new Session();
		this.mMainView = new MessageAppMainView();
		this.mController = new MessageAppController(mDataManager, mSession, mMainView);
		this.mController.initView();
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		// "." correspond au répertoire courant (la racine du projet lors de l'exécution)
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogTitle("Sélectionnez le répertoire d'échange");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(mMainView.mFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File directory = chooser.getSelectedFile();
			if(isValidExchangeDirectory(directory)) {
				this.initDirectory(directory.getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(mMainView.mFrame, "Répertoire invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
				initDirectory();
			}
		} else {
			System.exit(0);
		}
	}

	/**
	 * Indique si le fichier donné est valide pour servir de répertoire d'échange
	 *
	 * @param directory , Répertoire à tester.
	 */
	protected boolean isValidExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	protected void initDirectory(String directoryPath) {
		mDataManager.setExchangeDirectory(directoryPath);
	}

	public void show() {
		this.mMainView.show();
	}

	@Override
	public void notifyMessageAdded(Message addedMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyMessageDeleted(Message deletedMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyMessageModified(Message modifiedMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyChannelAdded(Channel addedChannel) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyChannelDeleted(Channel deletedChannel) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyChannelModified(Channel modifiedChannel) {
		// TODO Auto-generated method stub
	}
}
