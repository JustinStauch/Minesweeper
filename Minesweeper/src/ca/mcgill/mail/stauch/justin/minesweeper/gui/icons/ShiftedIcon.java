package ca.mcgill.mail.stauch.justin.minesweeper.gui.icons;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ShiftedIcon implements Icon {
    private final Icon icon;
	private final int shiftX;
    private final int shiftY;
    
    public ShiftedIcon(Icon icon, int shiftX, int shiftY) {
    	this.icon = icon;
    	this.shiftX = shiftX;
    	this.shiftY = shiftY;
    }

	@Override
	public int getIconHeight() {
		return icon.getIconHeight() + shiftY;
	}

	@Override
	public int getIconWidth() {
		return icon.getIconWidth() + shiftX;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		icon.paintIcon(c, g, x + shiftX, y + shiftY);
	}
}