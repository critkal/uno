package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import GameModel.Player;
import Interfaces.CardInterface;
import Interfaces.GameConstants;

public class PlayerPanel extends JPanel implements GameConstants {

	private /*@ spec_public nullable @*/Player player;
	private /*@ spec_public nullable @*/String name;

	private /*@ spec_public nullable @*/Box myLayout;
	private /*@ spec_public nullable @*/JLayeredPane cardHolder;
	private /*@ spec_public nullable @*/Box controlPanel;

	private /*@ spec_public nullable @*/JButton draw;
	private /*@ spec_public nullable @*/JButton sayUNO;
	private /*@ spec_public nullable @*/JLabel nameLbl;
	private /*@ spec_public nullable @*/MyButtonHandler handler;

	// Constructor
	public PlayerPanel(Player newPlayer) {
		setPlayer(newPlayer);

		myLayout = Box.createHorizontalBox();
		cardHolder = new JLayeredPane();
		cardHolder.setPreferredSize(new Dimension(600, 175));

		// Set
		setCards();
		setControlPanel();

		myLayout.add(cardHolder);
		myLayout.add(Box.createHorizontalStrut(40));
		myLayout.add(controlPanel);
		add(myLayout);

		// Register Listeners
		handler = new MyButtonHandler();
		draw.addActionListener(BUTTONLISTENER);
		draw.addActionListener(handler);
		
		sayUNO.addActionListener(BUTTONLISTENER);
		sayUNO.addActionListener(handler);
	}

	public void setCards() {
		cardHolder.removeAll();

		// Origin point at the center
		Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
		int offset = calculateOffset(cardHolder.getWidth(),
				player.getTotalCards());

		int i = 0;
		for (UNOCard card : player.getAllCards()) {
			card.setBounds(origin.x, origin.y, CardInterface.CARDSIZE.width,
					CardInterface.CARDSIZE.height);
			cardHolder.add(card, i++);
			cardHolder.moveToFront(card);
			origin.x += offset;
		}
		repaint();
	}

	/*@ pure
	@ ensures \result == player;
	@*/
	public Player getPlayer() {
		return player;
	}

	/*@ requires player != null;
	@ assignable this.player;
	@ ensures this.player == player;
	@*/
	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
	}

	/*@ requires playername.lenght >= 0;
	@ requires player != null;
	@ assignable name;
	@ ensures name == playerName;
	@*/
	public void setPlayerName(String playername) {
		this.name = playername;
	}

	private void setControlPanel() {
		draw = new JButton("Draw");
		sayUNO = new JButton("Say UNO");
		nameLbl = new JLabel(name);

		// style
		draw.setBackground(new Color(79, 129, 189));
		draw.setFont(new Font("Arial", Font.BOLD, 20));
		draw.setFocusable(false);

		sayUNO.setBackground(new Color(149, 55, 53));
		sayUNO.setFont(new Font("Arial", Font.BOLD, 20));
		sayUNO.setFocusable(false);

		nameLbl.setForeground(Color.WHITE);
		nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

		controlPanel = Box.createVerticalBox();
		controlPanel.add(nameLbl);
		controlPanel.add(draw);
		controlPanel.add(Box.createVerticalStrut(15));
		controlPanel.add(sayUNO);
	}

	/*@ requires width != null && totalCards != null;
	@ requires totalCards <= 8 || totalCards > 8;
	@ ensures total cards <= 8 ==> \result == 71; 
	@*/
	private int calculateOffset(int width, int totalCards) {
		int offset = 71;
		if (totalCards <= 8) {
			return offset;
		} else {
			double o = (width - 100) / (totalCards - 1);
			return (int) o;
		}
	}

	/*@ requires totalCards >= 8 || totalCards < 8;
	@ assignable p;
	@ ensures \result != null;
	@ ensures \result == p;
	@*/
	private Point getPoint(int width, int totalCards) {
		Point p = new Point(0, 20);
		if (totalCards >= 8) {
			return p;
		} else {
			p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
			return p;
		}
	}
	
	class MyButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if(player.isMyTurn()){
				
				if(e.getSource()==draw)
					BUTTONLISTENER.drawCard();
				else if(e.getSource()==sayUNO)
					BUTTONLISTENER.sayUNO();
			}
		}
	}
}
