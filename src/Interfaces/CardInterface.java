package Interfaces;
import java.awt.*;

/**
 * 32 lines
 * 6 methods
 */
public interface CardInterface{
	//@ public constraint \old(WIDTH) == WIDTH;
	//@ public invariant WIDTH == 50;
	/*@ spec_public @*/ int WIDTH = 50;

	//@ public constraint \old(HEIGHT) == HEIGHT;
	//@ public invariant HEIGHT == 75;
	/*@ spec_public @*/ int HEIGHT = 75;
	
	//@ public constraint \old(SMALL) == SMALL;
	Dimension SMALL = new Dimension(WIDTH,HEIGHT);
	
	//@ public constraint \old(MEDIUM) == MEDIUM;
	/*@ spec_public non_null @*/ Dimension MEDIUM = new Dimension(WIDTH*2,HEIGHT*2);
	
	//@ public constraint \old(BIG) == BIG;
	/*@ spec_public non_null @*/ Dimension BIG = new Dimension(WIDTH*3,HEIGHT*3);	
	
	//Default card size
	//@ public constraint \old(CARDSIZE) == CARDSIZE;
	//@ public invariant CARDSIZE == MEDIUM;
	/*@ spec_public non_null @*/ Dimension CARDSIZE = MEDIUM;
	
	//Default offset
	//@ public invariant OFFSET == 71;
	//@ public constraint \old(OFFSET) == OFFSET;
	/*@ spec_public @*/ int OFFSET = 71;
	
	//@ public model instance Color color;
	/*@ requires newColor != null;
	  @ ensures color == newColor;
	  @*/
	void setColor(Color newColor);
	
	/*@ ensures \result == color;
	  @*/
	/*@ pure @*/ Color getColor();
	
	//@ public model instance String value;
	/*@ requires newValue != null;
	  @ ensures value == newValue;
	  @*/
	void setValue(String newValue);
	
	/*@ ensures \result == value;
	  @*/
	/*@ pure @*/ String getValue();
	
	//@ public model instance int type;
	/*@ requires newType > 0 && newType < 4;
	  @ ensures type == newType;
	  @*/
	void setType(int newType);
	
	/*@ ensures \result == type;
	  @*/
	/*@ pure @*/ int getType();
}
