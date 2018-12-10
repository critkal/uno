package Interfaces;

import java.awt.Color;

import ServerController.MyButtonListener;
import ServerController.MyCardListener;
import View.InfoPanel;


public interface GameConstants extends UNOConstants {
	
	public static int TOTAL_CARDS = 108;
	public static int FIRSTHAND = 8;
	
	Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
	Color WILD_CARDCOLOR = BLACK;
	
	int[] UNO_NUMBERS =  {0,1,2,3,4,5,6,7,8,9};	
	String[] ActionTypes = {REVERSE,SKIP,DRAW2PLUS};	
	String[] WildTypes = {W_COLORPICKER, W_DRAW4PLUS};
	
	public static int vsPC = 1;
	public static int MANUAL = 2;
	
	int[] GAMEMODES = {vsPC, MANUAL};
	
	MyCardListener CARDLISTENER = new MyCardListener();
	MyButtonListener BUTTONLISTENER = new MyButtonListener();
	
	InfoPanel infoPanel = new InfoPanel();
}
