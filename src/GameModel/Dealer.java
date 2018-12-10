package GameModel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import CardModel.CardDeck;
import Interfaces.GameConstants;
import View.UNOCard;

public class Dealer implements GameConstants {
	//@ public initially cardDeck != null;
	private /*@ spec_public nullable @*/CardDeck cardDeck;
	private /*@ spec_public nullable @*/Stack<UNOCard> CardStack;	
	
	/*@ ensures cardDeck != null;
	  @*/
	public Dealer(){
		this.cardDeck = new CardDeck();
	}
	
	//Shuffle cards
	/*@ requires cardDeck != null;
	  @ requires cardDeck.getCards().size() == Interfaces.GameConstants.TOTAL_CARDS;
	  @ assignable cardDeck, CardStack;
	  @ ensures CardStack.size() == \old(cardDeck.getCards().size());
	  @ ensures cardDeck.getCards().size() == 0;
	  @ ensures \result == CardStack;
	  @*/
	public Stack<UNOCard> shuffle(){
		
		LinkedList<UNOCard> DeckOfCards = cardDeck.getCards();
		LinkedList<UNOCard> shuffledCards = new LinkedList<UNOCard>();
		
		while(!DeckOfCards.isEmpty()){
			int totalCards = DeckOfCards.size();
			
			Random random = new Random();
			int pos = (Math.abs(random.nextInt()))% totalCards;
			
			UNOCard randomCard = DeckOfCards.get(pos);
			DeckOfCards.remove(pos);
			shuffledCards.add(randomCard);
		}
		
		CardStack = new Stack<UNOCard>();
		for(UNOCard card : shuffledCards){
			CardStack.add(card);
		}
		
		return CardStack;
	}
	
	//Spread cards to players - 8 each
	/*@ requires CardStack != null;
	  @ requires CardStack.size() == Interfaces.GameConstants.TOTAL_CARDS;
	  @ requires players != null && players.length >= 2;
	  @ assignable CardStack;
	  @ ensures CardStack.size() == \old(CardStack.size()) - players.length * Interfaces.GameConstants.FIRSTHAND;
	  @ ensures (\forall int i; 0 <= i && i < players.length; 
	  @ 	players[i].getTotalCards() == Interfaces.GameConstants.FIRSTHAND);
	  // ensures (\forall int i; 0 <= i && i < players.length;
	  // 				((Stack) \old(CardStack)).containsAll(((LinkedList) players[i].getAllCards())));
	  @*/
	public void spreadOut(Player[] players) {
		for(int i=1;i<=FIRSTHAND;i++){
			for(Player p : players){
				p.obtainCard(CardStack.pop());
			}
		}		
	}
	
	/*@ requires CardStack != null;
	  @ assignable CardStack;
	  @ ensures CardStack.size() == \old(CardStack.size()) - 1;
	  @*/
	public UNOCard getCard(){
		return CardStack.pop();
	}
}
