package Interfaces;

import java.awt.Color;

import ServerController.MyButtonListener;
import ServerController.MyCardListener;
import View.InfoPanel;

/**
 * 17 lines
 * 0 methods
 */
public interface GameConstants extends UNOConstants {
	//@ public invariant TOTAL_CARDS == 108;
	//@ public constraint \old(TOTAL_CARDS) == TOTAL_CARDS;
	public static int TOTAL_CARDS = 108;
	
	//@ public invariant FIRSTHAND == 8;
	//@ public constraint \old(FIRSTHAND) == FIRSTHAND;
	public static int FIRSTHAND = 8;
	
	//@ public constraint \old(UNO_COLORS) == UNO_COLORS;
	Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
	
	//@ public constraint \old(WILD_CARDCOLOR) == WILD_CARDCOLOR;
	Color WILD_CARDCOLOR = BLACK;

	//@ public constraint \old(UNO_NUMBERS) == UNO_NUMBERS;
	int[] UNO_NUMBERS =  {0,1,2,3,4,5,6,7,8,9};	
	
	//@ public constraint \old(ActionTypes) == ActionTypes;
	String[] ActionTypes = {REVERSE,SKIP,DRAW2PLUS};	
	
	//@ public constraint \old(WildTypes) == WildTypes;
	String[] WildTypes = {W_COLORPICKER, W_DRAW4PLUS};
	
	//@ public constraint \old(vsPC) == vsPC;
	//@ public invariant vsPC == 1;
	public static int vsPC = 1;

	//@ public constraint \old(MANUAL) == MANUAL;
	//@ public invariant MANUAL == 2;
	public static int MANUAL = 2;
	
	//@ public constraint \old(GAMEMODES) == GAMEMODES;
	int[] GAMEMODES = {vsPC, MANUAL};
	
	//@ public constraint \old(CARDLISTENER) == CARDLISTENER;
	MyCardListener CARDLISTENER = new MyCardListener();
	
	//@ public constraint \old(BUTTONLISTENER) == BUTTONLISTENER;
	MyButtonListener BUTTONLISTENER = new MyButtonListener();
	
	//@ public constraint \old(infoPanel) == infoPanel;
	InfoPanel infoPanel = new InfoPanel();
}
