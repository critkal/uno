package GameModel;

import java.util.LinkedList;

import View.UNOCard;

public class Player {

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

	public void obtainCard(/*@ non_null @*/ UNOCard card){
		myCards.add(card);
	}

	public /*@ pure @*/ LinkedList<UNOCard> getAllCards(){
		return myCards;
	}

	public /*@ pure @*/ int getTotalCards(){
		return myCards.size();
	}

	public /*@ pure @*/ boolean hasCard(/*@ non_null @*/ UNOCard thisCard){
		return myCards.contains(thisCard);
	}

	public void removeCard(/*@ non_null @*/ UNOCard thisCard){
		myCards.remove(thisCard);
		playedCards++;
	}

	//@ ensures \result == playedCards;
	public /*@ pure @*/ int totalPlayedCards(){
		return playedCards;
	}

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