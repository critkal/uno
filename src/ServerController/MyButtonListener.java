package ServerController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonListener implements ActionListener {
		
	private /*@ spec_public @*/ Server myServer;
	
	/*@ requires server != null;
	 @ assignable server;
	 @ ensures myServer == server;
	 @*/
	public void setServer(Server server){
		myServer = server;
	}
	
	/*@ requires myServer != null;
	 @ assignable myServer;
	 @ ensures myServer == \old(myServer);
	 @*/
	public void drawCard() {
		if(myServer.canPlay)
			myServer.requestCard();	
	}
	
	/*@ requires myServer != null;
	 @ assignable myServer;
	 @ ensures myServer == \old(myServer);
	 @*/
	public void sayUNO() {
		if(myServer.canPlay)
			myServer.submitSaidUNO();
	}

	@Override
	//@ assignable \nothing;
	public void actionPerformed(ActionEvent e) {
	}

	
}
