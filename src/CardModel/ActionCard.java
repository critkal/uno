package CardModel;
import java.awt.Color;

import View.UNOCard;

/**
 * 7 lines
 * 1 method
 */
public class ActionCard extends UNOCard{
	//Constructor
	public ActionCard(){
	}
	
	/*@ requires cardColor != null && cardValue != null;
	  @ requires cardColor == Interfaces.UNOConstants.BLUE || cardColor == Interfaces.UNOConstants.RED || cardColor == Interfaces.UNOConstants.YELLOW || cardColor == Interfaces.UNOConstants.GREEN;
	  @ requires cardValue.equals(Interfaces.UNOConstants.DRAW2PLUS) || cardValue.equals(Interfaces.UNOConstants.SKIP) || cardValue.equals(Interfaces.UNOConstants.REVERSE);
	  @ assignable this.cardColor, this.type, this.value;
	  @ ensures this.cardColor == cardColor;
	  @ ensures this.value == cardValue;
	  @ ensures this.type == Interfaces.UNOConstants.ACTION;
	  @*/
	public ActionCard(Color cardColor, String cardValue){
		super(cardColor, ACTION, cardValue);
	}
}
