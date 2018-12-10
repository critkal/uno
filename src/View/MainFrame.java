package View;


import javax.swing.JFrame;
import Interfaces.GameConstants;
import ServerController.Server;

/**
 * 8 lines
 * 1 method
 */
public class MainFrame extends JFrame implements GameConstants {

	/*@initially mainPanel != null;
	@ invariant mainPanel != null;
	@*/
	private /*@ spec_public nullable @*/Session mainPanel;

	/*@initially server != null;
	@ invariant server != null;
	@*/
	private /*@ spec_public nullable @*/Server server;
	
	/*@ requires server != null;
	@ assignable mainPanel;
	@*/
	public MainFrame(){	
		server = new Server();
		CARDLISTENER.setServer(server);
		BUTTONLISTENER.setServer(server);
		
		mainPanel = server.getSession();
		add(mainPanel);
	}
}
