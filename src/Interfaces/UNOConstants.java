package Interfaces;
import java.awt.Color;

/**
 * 23 lines
 * 0 methods
 */
public interface UNOConstants {
	
	//Colors
	//@ public constraint \old(RED) == RED;
	public static Color RED = new Color(192,80,77);
	
	//@ public constraint \old(BLUE) == BLUE;
	public static Color BLUE = new Color(31,73,125);
	
	//@ public constraint \old(GREEN) == GREEN;
	public static Color GREEN = new Color(0,153,0);
	
	//@ public constraint \old(YELLOW) == YELLOW;
	public static Color YELLOW = new Color(255,204,0);
	
	//@ public constraint \old(BLACK) == BLACK;
	public static Color BLACK = new Color(0,0,0);
	
	//Types
	//@ public constraint \old(NUMBERS) == NUMBERS;
	//@ public invariant NUMBERS == 1;
	public static int NUMBERS = 1;
	
	//@ public constraint \old(ACTION) == ACTION;
	//@ public invariant ACTION == 2;
	public static int ACTION = 2;
	
	//@ public constraint \old(WILD) == WILD;
	//@ public invariant WILD == 3;
	public static int WILD = 3;
	
	//ActionCard Characters
	//@ public constraint \old(charREVERSE) == charREVERSE;
	Character charREVERSE = (char) 8634;							//Decimal

	//@ public constraint \old(charSKIP) == charSKIP;
	Character charSKIP    = (char) Integer.parseInt("2718",16); 	//Unicode
	
	//ActionCard Functions
	//@ public constraint \old(REVERSE) == REVERSE;
	//@ public invariant REVERSE == charREVERSE.toString();
	public static String REVERSE = charREVERSE.toString();
	
	//@ public constraint \old(SKIP) == SKIP;
	//@ public invariant SKIP == charSKIP.toString();
	public static String SKIP	= charSKIP.toString();
	
	//@ public constraint \old(DRAW2PLUS) == DRAW2PLUS;
	//@ public invariant DRAW2PLUS.equals("2+");
	public static String DRAW2PLUS = "2+";
	
	//Wild card functions
	//@ public constraint \old(W_COLORPICKER) == W_COLORPICKER;
	//@ public invariant W_COLORPICKER.equals("W");
	public static String W_COLORPICKER = "W";

	//@ public constraint \old(W_DRAW4PLUS) == W_DRAW4PLUS;
	//@ public invariant W_DRAW4PLUS.equals("4+");
	public static String W_DRAW4PLUS = "4+";	
}
