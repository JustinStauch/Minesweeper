package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.CompositeIcon;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.ShiftedIcon;

public class DigitalDisplay extends JPanel {
	
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
	private final JLabel display;
	
	public DigitalDisplay(int size) {
		this.size = size;
		display = new JLabel();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setLayout(new BorderLayout());
				
				displayNumber(0);
				setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
				display.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
				display.setIcon(icon);
				
				add(display, BorderLayout.CENTER);
			}	
		});
	}
	
	public void displayNumber(int num) {
		icon.clear();
		
		for (int i = size - 1; i >= 0; i--) {
			icon.addIcon(new ShiftedIcon(DIGITS[(num / (int) Math.pow(10, i)) % 10], (size - i - 1) * DIGITS[0].getIconWidth(), 0));
		}
		
		repaint();
	}
}