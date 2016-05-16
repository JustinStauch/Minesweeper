package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.CompositeIcon;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.ShiftedIcon;

public class DigitalDisplay extends JLabel {
	
	private static final long serialVersionUID = 1L;

	private static final Icon[] DIGITS = {
    		new ImageIcon("0.png"),
    		new ImageIcon("1.png"),
    		new ImageIcon("2.png"),
    		new ImageIcon("3.png"),
    		new ImageIcon("4.png"),
    		new ImageIcon("5.png"),
    		new ImageIcon("6.png"),
    		new ImageIcon("7.png"),
    		new ImageIcon("8.png"),
    		new ImageIcon("9.png")
    };
	
	private final CompositeIcon icon = new CompositeIcon();
	private final int size;
	
	public DigitalDisplay(int size) {
		this.size = size;
		setIcon(icon);
		displayNumber(0);
		setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}
	
	public void displayNumber(int num) {
		icon.clear();
		
		for (int i = size - 1; i >= 0; i--) {
			icon.addIcon(new ShiftedIcon(DIGITS[(num / (int) Math.pow(10, i)) % 10], (size - i - 1) * DIGITS[0].getIconWidth(), 0));
		}
		
		repaint();
	}
}