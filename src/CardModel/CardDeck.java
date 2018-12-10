package CardModel;

import java.awt.Color;
import java.util.LinkedList;

import Interfaces.GameConstants;
import ServerController.MyCardListener;
import View.UNOCard;

/**
 * This Class contains standard 108-Card stack
 */
public class CardDeck implements GameConstants {
	//@ public invariant UNOcards.size() <= 108 && UNOcards.size() >= 0;
	
	//@ public initially UNOcards != null && UNOcards.size() == 108;
	private /*@ spec_public non_null @*/ LinkedList<UNOCard> UNOcards;
	
	/*@ ensures UNOcards.size() == 108;
	@*/
	public CardDeck(){
		
		//Initialize Cards
		UNOcards = new LinkedList<UNOCard>();
		
		addCards();
		addCardListener(CARDLISTENER);
	}
	
	
	//Create 108 cards for this CardDeck
	/*@ requires UNOcards != null;
	  @ ensures UNOcards.size() == 108;
	  @ ensures (\forall int i; 0 <= i && i < UNOcards.size(); 
	  @ 	UNOcards.get(i) instanceof NumberCard || UNOcards.get(i) instanceof ActionCard || UNOcards.get(i) instanceof WildCard);
	  @*/
	private void addCards() {
		for(Color color:UNO_COLORS){
			
			//Create 76 NumberCards --> doubles except 0s.
			for(int num : UNO_NUMBERS){
				int i=0;
				do{
					UNOcards.add(new NumberCard(color, Integer.toString(num)));
					i++;
				}while(num!=0 && i<2);
			}
			
			//Create 24 ActionCards --> everything twice
			for(String type : ActionTypes){
				for(int i=0;i<2;i++)
					UNOcards.add(new ActionCard(color, type));
			}					
		}		
		
		for(String type : WildTypes){
			for(int i=0;i<4;i++){
				UNOcards.add(new WildCard(type));
			}
		}
		
	}
	
	//Cards have MouseListener
	/*@ requires listener != null;
	  @*/
	public void addCardListener(MyCardListener listener){
		for(UNOCard card: UNOcards)
			card.addMouseListener(listener);
	}
	
	/*@ ensures \result == UNOcards;
	  @*/
	public /*@ pure @*/ LinkedList<UNOCard> getCards(){
		return UNOcards;
	}
}
