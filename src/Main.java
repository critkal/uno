import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import View.MainFrame;

/**
 * 0 lines
 * 0 methods
 * 2035 lines including commented lines.
 * 407 commented lines.
 * 1628 without commented lines.
 */
public class Main {
	
	public static void main(String[] args) {
		
		//Create Frame and invoke it.
		SwingUtilities.invokeLater(new Runnable() {					
			public void run() {
				JFrame frame = new MainFrame();
				frame.setVisible(true);
				frame.setResizable(false);
				frame.setLocation(200, 100);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
			}
		});	
	}
}
