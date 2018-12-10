package ServerController;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import CardModel.WildCard;
import GameModel.Game;
import GameModel.Player;
import Interfaces.GameConstants;
import View.Session;
import View.UNOCard;

public class Server implements GameConstants {
	
	/*@ public initially game != null;
	 @*/
	private /*@ spec_public nullable @*/Game game;
	
	/*@ public initially session != null;
	 @*/
	private /*@ spec_public nullable @*/Session session;
	private /*@ spec_public nullable @*/Stack<UNOCard> playedCards;
	/*@ public constraint (\old(canPlay) == false) ==> canPlay == false;
	@*/
	public boolean canPlay;
	private /*@ spec_public nullable @*/int mode;

	/*@ assignable game, mode, playedCards, session;
	 @*/
	public Server() {

		mode = requestMode();
		game = new Game(mode);
		playedCards = new Stack<UNOCard>();

		// First Card
		UNOCard firstCard = game.getCard();
		modifyFirstCard(firstCard);

		playedCards.add(firstCard);
		session = new Session(game, firstCard);

		game.whoseTurn();
		canPlay = true;
	}

	//return if it's 2-Player's mode or PC-mode
	/*@ assignable \nothing;
	 @*/
	private int requestMode() {

		Object[] options = { "vs PC", "Manual", "Cancel" };

		int n = JOptionPane.showOptionDialog(null,
				"Choose a Game Mode to play", "Game Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == 2)
			System.exit(1);

		return GAMEMODES[n];
	}
	
	//coustom settings for the first card
	/*@ 	requires firstCard.getType() == WILD;
	 @ 	assignable \nothing;
	 @ 	ensures \old(firstCard) == firstCard;
	 @ also
	 @ 	requires firstCard.getType() != WILD;
	 @ 	assignable \nothing;
	 @ 	ensures \old(firstCard) == firstCard;
	 @	signals (Exception) true;
	  @*/
	private void modifyFirstCard(UNOCard firstCard) {
		firstCard.removeMouseListener(CARDLISTENER);
		if (firstCard.getType() == WILD) {
			int random = new Random().nextInt() % 4;
			try {
				((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
			} catch (Exception ex) {
				System.out.println("something wrong with modifyFirstcard");
			}
		}
	}
	
	//return Main Panel

	/*@ requires session != null;
	 @ ensures \result == session;
	 @*/
	public Session getSession() {
		return this.session;
	}
	
	
	//request to play a card
	/*@ requires game != null;
	 @ assignable playedCards, game, session;
	 @*/
	public void playThisCard(UNOCard clickedCard) {

		// Check player's turn
		if (!isHisTurn(clickedCard)) {
			infoPanel.setError("It's not your turn");
			infoPanel.repaint();
		} else {

			// Card validation
			if (isValidMove(clickedCard)) {

				clickedCard.removeMouseListener(CARDLISTENER);
				playedCards.add(clickedCard);
				game.removePlayedCard(clickedCard);

				// function cards ??
				switch (clickedCard.getType()) {
				case ACTION:
					performAction(clickedCard);
					break;
				case WILD:
					performWild((WildCard) clickedCard);
					break;
				default:
					break;
				}

				game.switchTurn();
				session.updatePanel(clickedCard);
				checkResults();
			} else {
				infoPanel.setError("invalid move");
				infoPanel.repaint();
			}
			
		}
		
		
		
		if(mode==vsPC && canPlay){
			if(game.isPCsTurn()){
				game.playPC(peekTopCard());
			}
		}
	}
	
	//Check if the game is over
	/*@ requires game != null;
	 @ assignable canPlay;
	 @*/
	private void checkResults() {

		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("GAME OVER");
		}
	}
	
	//check player's turn
	/*@ requires game != null;
	 @ ensures \result == true <==> (\exists int i; 0 <= i && i < game.players.length;
	 @ 	game.players[i].hasCard(clickedCard) && game.players[i].isMyTurn());
	 @*/
	public boolean isHisTurn(UNOCard clickedCard) {

		for (Player p : game.getPlayers()) {
			if (p.hasCard(clickedCard) && p.isMyTurn())
				return true;
		}
		return false;
	}

	//check if it is a valid card

	/*@ requires playedCard != null;
	@ assignable playedCard;
 	@*/
	public boolean isValidMove(UNOCard playedCard) {
		UNOCard topCard = peekTopCard();

		if (playedCard.getColor().equals(topCard.getColor())
				|| playedCard.getValue().equals(topCard.getValue())) {
			return true;
		}

		else if (playedCard.getType() == WILD) {
			return true;
		} else if (topCard.getType() == WILD) {
			Color color = ((WildCard) topCard).getWildColor();
			if (color.equals(playedCard.getColor()))
				return true;
		}
		return false;
	}

	// ActionCards
	/*@ 	requires game != null;
	@ 	assignable game;
	@ also
	@ 	requires game != null;
	@ 	assignable \nothing;
	@*/
	private void performAction(UNOCard actionCard) {

		// Draw2PLUS
		if (actionCard.getValue().equals(DRAW2PLUS))
			game.drawPlus(2);
		else if (actionCard.getValue().equals(REVERSE))
			game.switchTurn();
		else if (actionCard.getValue().equals(SKIP))
			game.switchTurn();
	}

	/*@	requires game != null;
	 @	assignable game, mode;
	 @*/
	private void performWild(WildCard functionCard) {		

		//System.out.println(game.whoseTurn());
		if(mode==1 && game.isPCsTurn()){			
			int random = new Random().nextInt() % 4;
			functionCard.useWildColor(UNO_COLORS[Math.abs(random)]);
		}
		else{
			
			ArrayList<String> colors = new ArrayList<String>();
			colors.add("RED");
			colors.add("BLUE");
			colors.add("GREEN");
			colors.add("YELLOW");
			
			String chosenColor = (String) JOptionPane.showInputDialog(null,
					"Choose a color", "Wild Card Color",
					JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
	
			functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
		}
		
		if (functionCard.getValue().equals(W_DRAW4PLUS))
			game.drawPlus(4);
	}
	
	/*@ requires game != null;
	 @ assignable game;
	 @*/
	public void requestCard() {
		game.drawCard(peekTopCard());
		
		if(mode==vsPC && canPlay){
			if(game.isPCsTurn())
				game.playPC(peekTopCard());
		}
		
		session.refreshPanel();
	}
	
	/*@ requires playedCards != null;
	 @ ensures \result == playedCards.peek();
	 @*/
	public UNOCard peekTopCard() {
		return playedCards.peek();
	}

	/*@ requires game != null;
	 @ assignable game;
	 @*/
	public void submitSaidUNO() {
		game.setSaidUNO();
	}
}
