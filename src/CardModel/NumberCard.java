package CardModel;
import java.awt.Color;

import View.UNOCard;
/**
 * 7 lines
 * 1 method
 */
public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	/*@ requires cardColor != null && cardValue != null;
	  @ requires Integer.parseInt(cardValue) >= 0 && Integer.parseInt(cardValue) <= 9;
	  @ requires cardColor == BLUE || cardColor == RED || cardColor == YELLOW || cardColor == GREEN;
	  @ assignable this.cardColor, this.type, this.value;
	  @ ensures this.cardColor == cardColor;
	  @ ensures this.value == cardValue;
	  @ ensures this.type == Interfaces.UNOConstants.NUMBERS;
	  @*/
	public NumberCard(Color cardColor, String cardValue){
		super(cardColor, NUMBERS, cardValue);		
	}

}