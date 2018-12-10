package GameModel;

import java.util.LinkedList;

import View.UNOCard;
/**
 * 50 lines
 * 16 methods
 */
public class Player {
	//@ public constraint \old(name) == name;
	//@ public initially name != null;
	private /*@ spec_public nullable @*/ String name;
	private /*@ spec_public @*/ boolean isMyTurn = false;
	private /*@ spec_public @*/ boolean saidUNO = false;

	//@ public initially myCards != null;
	private /*@ spec_public nullable @*/ LinkedList<UNOCard> myCards;

	//@ public invariant playedCards >= 0;
	private /*@ spec_public @*/ int playedCards = 0;

	public Player(){
		myCards = new LinkedList<>();
	}

	public Player(/*@ non_null @*/ String player){
		setName(player);
		myCards = new LinkedList<>();
	}

	public void setName(/*@ non_null @*/ String newName){
		name = newName;
	}

	public /*@ pure @*/ String getName(){
		return this.name;
	}

	/*@ requires card != null;
	  @ requires myCards != null;
	  @ assignable myCards;
	  @ ensures myCards.size() == \old(myCards.size()) + 1;
	  @ ensures myCards.get(myCards.size() - 1) == card;
	  @ ensures (\forall int i; 0 <= i && i < myCards.size() - 1;
	  @ 	myCards.get(i) == \old(myCards).get(i));
	  @*/
	public void obtainCard(/*@ non_null @*/ UNOCard card){
		myCards.add(card);
	}

	/*@ ensures \result == myCards; 
	 @*/
	public /*@ pure @*/ LinkedList<UNOCard> getAllCards(){
		return myCards;
	}

	/*@ ensures \result == myCards.size(); 
	 @*/
	public /*@ pure @*/ int getTotalCards(){
		return myCards.size();
	}

	/*@ requires thisCard != null;
	  @ ensures \result == true || \result == false;
	  @ ensures \result == (\exists int i; 0 <= i && i < myCards.size();
	  @ 		myCards.get(i) == thisCard);
	 @*/
	public /*@ pure @*/ boolean hasCard(/*@ non_null @*/ UNOCard thisCard){
		return myCards.contains(thisCard);
	}

	/*@ requires thisCard != null;
	  @ assignable myCards, playedCards;
	  @ ensures playedCards == \old(playedCards) + 1;
	  @ ensures myCards.size() == \old(myCards.size()) - 1;
	 @*/
	public void removeCard(/*@ non_null @*/ UNOCard thisCard){
		myCards.remove(thisCard);
		playedCards++;
	}

	//@ ensures \result == playedCards;
	public /*@ pure @*/ int totalPlayedCards(){
		return playedCards;
	}

	/*@ assignable isMyTurn;
	  @ ensures \old(isMyTurn) == false <==> isMyTurn == true;
	  @ ensures \old(isMyTurn) == true <==> isMyTurn == false;
	 @*/
	public void toggleTurn(){
		isMyTurn = (isMyTurn) ? false : true;
	}

	//@ ensures \result == isMyTurn;
	public /*@ pure @*/ boolean isMyTurn(){
		return isMyTurn;
	}

	//@ ensures \result == myCards.size() > 0;
	public /*@ pure @*/ boolean hasCards(){
		return (myCards.isEmpty()) ? false : true;
	}

	//@ ensures \result == saidUNO;
	public /*@ pure @*/ boolean getSaidUNO(){
		return saidUNO;
	}

	/*@ assignable saidUNO;
	  @ ensures saidUNO == true;
	  @*/
	public void saysUNO(){
		saidUNO = true;
	}

	/*@ assignable saidUNO;
	  @ ensures saidUNO == false;
	  @*/
	public void setSaidUNOFalse(){
		saidUNO = false;
	}

	/*@ assignable myCards;
	  @ ensures myCards != null && myCards.size() == 0;
	  @*/
	public void setCards(){
		myCards = new LinkedList<>();
	}
}