package CardModel;

import java.awt.Color;

import View.UNOCard;

/**
 * 14 lines
 * 3 methods
 */
public class WildCard extends UNOCard {
	private /*@ spec_public nullable @*/ Color chosenColor;
	
	public WildCard() {
	}
	
	/*@ requires cardValue != null;
	  @ requires cardValue.equals(Interfaces.UNOConstants.W_COLORPICKER) || cardValue.equals(Interfaces.UNOConstants.W_DRAW4PLUS);
	  @ assignable this.cardColor, this.type, this.value;
	  @ ensures this.cardColor == Interfaces.UNOConstants.BLACK;
	  @ ensures this.type == Interfaces.UNOConstants.WILD;
	  @ ensures this.value == cardValue;
	  @*/
	public WildCard(String cardValue){
		super(BLACK, WILD, cardValue);
	}
	
	/*@ requires wildColor != null;
	  @ requires wildColor == Interfaces.UNOConstants.RED || wildColor == Interfaces.UNOConstants.BLUE || wildColor == Interfaces.UNOConstants.YELLOW || wildColor == Interfaces.UNOConstants.GREEN;
	  @ assignable chosenColor;
	  @ ensures chosenColor == wildColor;
	  @*/
	public void useWildColor(Color wildColor){
		chosenColor = wildColor;
	}
	
	/*@ ensures \result == chosenColor;
	  @ assignable \nothing;
	 @*/
	public /*@ pure @*/ Color getWildColor(){
		return chosenColor;
	}

}
