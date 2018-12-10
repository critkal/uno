package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Interfaces.CardInterface;
import Interfaces.UNOConstants;

/**
 * 11 lines
 * 4 methods
 */
public abstract class UNOCard extends JPanel implements CardInterface, UNOConstants {
	
	//@ public initially cardColor != null;
	private /*@ spec_public nullable @*/Color cardColor;
	
	//@ public initially value != null;
	private /*@ spec_public nullable @*/String value;
	private /*@ spec_public @*/int type = 0;
	private /*@ spec_public @*/Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
	private /*@ spec_public @*/Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);
	
	public UNOCard(){
	}
	
	public UNOCard(Color cardColor, int cardType, String cardValue){
		this.cardColor = cardColor;
		this.type = cardType;
		this.value = cardValue;
		
		this.setPreferredSize(CARDSIZE);
		this.setBorder(defaultBorder);
		
		this.addMouseListener(new MouseHandler());
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, cardWidth, cardHeight);
		
		int margin = 5;
		g2.setColor(cardColor);
		g2.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);
		
		g2.setColor(Color.white);
		AffineTransform org = g2.getTransform();
		g2.rotate(45,cardWidth*3/4,cardHeight*3/4);		

		g2.fillOval(0,cardHeight*1/4,cardWidth*3/5, cardHeight);
		g2.setTransform(org);		
		
		//Value in the center		
		Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth/2+5);		
		FontMetrics fm = this.getFontMetrics(defaultFont);
		int StringWidth = fm.stringWidth(value)/2;
		int FontHeight = defaultFont.getSize()*1/3;
		
		g2.setColor(cardColor);
		g2.setFont(defaultFont);
		g2.drawString(value, cardWidth/2-StringWidth, cardHeight/2+FontHeight);
		
		//Value in the corner
		defaultFont = new Font("Helvetica", Font.ITALIC, cardWidth/5);		
		fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth(value)/2;
		FontHeight = defaultFont.getSize()*1/3;
		
		g2.setColor(Color.white);
		g2.setFont(defaultFont);
		g2.drawString(value, 2*margin,5*margin);		
	}	
	
	/**
	 * My Mouse Listener 
	 */
	class MouseHandler extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e){
			setBorder(focusedBorder);
		}
		
		public void mouseExited(MouseEvent e){
			setBorder(defaultBorder);
		}
	}
	

	public void setCardSize(Dimension newSize){
		this.setPreferredSize(newSize);
	}
	
	/*@ assignable cardColor;
	 @ ensures cardColor == newColor;
	 @*/
	public void setColor(Color newColor) {
		this.cardColor = newColor;
	}

	//@ pure
	public Color getColor() {
		return cardColor;
	}

	@Override
	public void setValue(String newValue) {
		this.value = newValue;		
	}

	@Override
	//@ pure
	public String getValue() {
		return value;
	}

	@Override
	public void setType(int newType) {
		this.type = newType;
	}

	@Override
	//@ pure
	public int getType() {
		return type;
	}
}
