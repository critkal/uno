package GameModel;

import java.util.Stack;

import javax.swing.JOptionPane;

import CardModel.WildCard;
import Interfaces.GameConstants;
import View.UNOCard;

/**
 * 74 lines
 * 15 methods
 */
public class Game implements GameConstants {
	/*@ public initially players != null;
	  @ public invariant players.length >= 2;
	  @ public invariant (\exists int i; 0 <= i && i < players.length;
	  @ 	players[i].isMyTurn());
	  @ public constraint (\forall int i; 0 <= i && i < players.length;
	  @ 	\old(players[i]) == players[i]);
	  @*/
	private /*@ spec_public nullable @*/ Player[] players;
	
	/*@ public initially isOver == false;
	  @ public invariant (isOver == true) <==> (\exists int i; 0 <= i && i < players.length;
	  @ 	players[i].getTotalCards() == 0);
	  @*/
	private /*@ spec_public nullable @*/ boolean isOver;
	
	/*@ public initially GAMEMODE == Interfaces.GameConstants.vsPC || GAMEMODE == Interfaces.GameConstants.MANUAL;
	  @ public invariant GAMEMODE == Interfaces.GameConstants.vsPC || GAMEMODE == Interfaces.GameConstants.MANUAL;
	  @ public constraint \old(GAMEMODE) == GAMEMODE;
	  @*/
	private /*@ spec_public nullable @*/ int GAMEMODE;
	
	/*@ public constraint \old(pc) == pc;
	  @*/
	private /*@ spec_public nullable @*/ PC pc;
	
	/*@ public initially dealer != null;
	  @ public invariant dealer != null;
	  @ public constraint \old(dealer) == dealer;
	  @*/
	//@ public initially dealer != null;
	private /*@ spec_public nullable @*/ Dealer dealer;
	
	/*@ public initially cardStack != null;
	  @ public invariant cardStack != null;
	  @ public invariant cardStack.size() >= 0;
	  @ public constraint \old(cardStack) == cardStack;
	  @*/
	//@ public initially cardStack != null;
	private /*@ spec_public non_null @*/ Stack<UNOCard> cardStack;
	
	
	/*@ requires mode == Interfaces.GameConstants.vsPC || mode == Interfaces.GameConstants.MANUAL;
	  @*/
	public Game(int mode){
		
		GAMEMODE = mode;
		
		//Create players
		String name = (GAMEMODE==MANUAL) ? JOptionPane.showInputDialog("Player 1") : "PC";	
		String name2 = JOptionPane.showInputDialog("Player 2");
		
		if(GAMEMODE==vsPC)
			pc = new PC();
		
		Player player1 = (GAMEMODE==vsPC) ? pc : new Player(name);
		Player player2 = new Player(name2);		
		player2.toggleTurn();				//Initially, player2's turn		
			
		players = new Player[]{player1, player2};			
		
		//Create Dealer
		dealer = new Dealer();
		cardStack = dealer.shuffle();
		dealer.spreadOut(players);
		
		isOver = false;
	}

	/*@ ensures \result == players;
	  @*/
	public /*@ pure @*/ Player[] getPlayers() {
		return players;
	}

	/*@ requires dealer != null;
	  @*/
	public /*@ pure @*/ UNOCard getCard() {
		return dealer.getCard();
	}
	
	/*@ requires playedCard != null && players != null;
	  @ ensures (\forall int i; 0 < i && i < players.length;
	  @ 	!players[i].hasCard(playedCard));
	  @*/	
	public void removePlayedCard(UNOCard playedCard) {

		for (Player p : players) {
			if (p.hasCard(playedCard)){
				p.removeCard(playedCard);
				
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					infoPanel.setError(p.getName() + " Forgot to say UNO");
					p.obtainCard(getCard());
					p.obtainCard(getCard());
				}else if(p.getTotalCards()>2){
					p.setSaidUNOFalse();
				}
			}			
		}
	}
	
	//give player a card
	/*@ requires topCard != null;
	  @ requires players != null;
	  @ ensures remainingCards() == \old(remainingCards()) - 1;
	  @*/
	public void drawCard(UNOCard topCard) {

		boolean canPlay = false;

		for (Player p : players) {
			if (p.isMyTurn()) {
				UNOCard newCard = getCard();
				p.obtainCard(newCard);
				canPlay = canPlay(topCard, newCard);
				break;
			}
		}

		if (!canPlay)
			switchTurn();
	}

	public void switchTurn() {
		for (Player p : players) {
			p.toggleTurn();
		}
		whoseTurn();
	}
	
	//Draw cards x times
	/*@ requires times == 2 || times == 4;
	  @ requires players != null;
	  @ ensures remainingCards() == \old(remainingCards()) - times;
	  @*/
	public void drawPlus(int times) {
		for (Player p : players) {
			if (!p.isMyTurn()) {
				for (int i = 1; i <= times; i++)
					p.obtainCard(getCard());
			}
		}
	}
	
	//response whose turn it is
	/*@ requires players != null;
	  @*/
	public /*@ pure @*/void whoseTurn() {
		for (Player p : players) {
			if (p.isMyTurn()){
				infoPanel.updateText(p.getName() + "'s Turn");
				System.out.println(p.getName() + "'s Turn");
			}
		}
		infoPanel.setDetail(playedCardsSize(), remainingCards());
		infoPanel.repaint();
	}
	
	//return if the game is over
	/*@ requires cardStack != null;
	  @ requires players != null;
	  @ ensures (cardStack.isEmpty() || (\exists int i; 0 <= i && i < players.length;
	  @ 	!players[i].hasCards()))  ==> \result == true;
	  @*/
	public boolean isOver() {
		if(cardStack.isEmpty()){
			isOver= true;
			return isOver;
		}
		
		for (Player p : players) {
			if (!p.hasCards()) {
				isOver = true;
				break;
			}
		}
		
		return isOver;
	}

	/*@ requires cardStack != null;
	  @ ensures \result == cardStack.size();
	  @*/
	public /*@ pure @*/ int remainingCards() {
		return cardStack.size();
	}

	/*@ requires players != null;
	  @ ensures (\forall int i; 0 <= i && i < players.length;
	  @ 	\result[i] == players[i].totalPlayedCards());
	  @*/
	public int[] playedCardsSize() {
		int[] nr = new int[2];
		int i = 0;
		for (Player p : players) {
			nr[i] = p.totalPlayedCards();
			i++;
		}
		return nr;
	}

	//Check if this card can be played
	/*@ requires topCard != null && newCard != null;
	  @ ensures \result == false || \result == true;
	  @ ensures \result == true <==> 
	  @ 	topCard.getColor().equals(newCard.getColor()) 
	  @		|| topCard.getValue().equals(newCard.getValue()) 
	  @ 	|| topCard.getType() == Interfaces.UNOConstants.WILD && ((WildCard) topCard).getWildColor().equals(newCard.getColor())
	  @		|| newCard.getType() == Interfaces.UNOConstants.WILD;
	  @*/
	private boolean canPlay(UNOCard topCard, UNOCard newCard) {

		// Color or value matches
		if (topCard.getColor().equals(newCard.getColor())
				|| topCard.getValue().equals(newCard.getValue()))
			return true;
		// if chosen wild card color matches
		else if (topCard.getType() == WILD)
			return ((WildCard) topCard).getWildColor().equals(newCard.getColor());

		// suppose the new card is a wild card
		else if (newCard.getType() == WILD)
			return true;

		// else
		return false;
	}

	//Check whether the player said or forgot to say UNO
	/*@ requires players != null;
	  @ ensures (\forall int i; 0 <= i && i < players.length;
	  @ 	(\old(players[i]).getTotalCards() == 1 && !\old(players[i]).getSaidUNO())
	  @ 		==> players[i].getTotalCards() == \old(players[i]).getTotalCards() + 2);
	  @ ensures (\forall int i; 0 <= i && i < players.length;
	  @ 	(\old(players[i]).getTotalCards() == 1 && \old(players[i]).getSaidUNO())
	  @ 		==> players[i].getTotalCards() == 1);
	  @*/
	public void checkUNO() {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					infoPanel.setError(p.getName() + " Forgot to say UNO");
					p.obtainCard(getCard());
					p.obtainCard(getCard());
				}
			}
		}		
	}

	/*@ requires players != null;
	  @ requires (\forall int i; 0 <= i && i < players.length;
	  @ 	players[i] != null);
	  @ ensures (\forall int i; 0 <= i && i < players.length;
	  @ 	players[i].isMyTurn() && players[i].getTotalCards() == 2 ==> players[i].getSaidUNO() == true);
	  @*/
	public void setSaidUNO() {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 2) {
					p.saysUNO();
					infoPanel.setError(p.getName() + " said UNO");
				}
			}
		}
	}
	
	/*@ requires pc != null;
	  @ requires GAMEMODE == Interfaces.GameConstants.vsPC;
	  @ ensures \result == pc.isMyTurn();
	  @*/
	public boolean isPCsTurn(){
		if(pc.isMyTurn()){
			return true;
		}
		return false;
	}

	//if it's PC's turn, play it for pc
	/*@ requires pc != null;
	  @ requires topCard != null;
	  @ requires GAMEMODE == Interfaces.GameConstants.vsPC;
	  @*/
	public void playPC(UNOCard topCard) {		
		
		if (pc.isMyTurn()) {
			boolean done = pc.play(topCard);
			
			if(!done)
				drawCard(topCard);
		}
	}
}
