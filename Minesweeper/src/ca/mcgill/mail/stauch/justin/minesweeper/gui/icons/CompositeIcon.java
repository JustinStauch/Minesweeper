package ca.mcgill.mail.stauch.justin.minesweeper.gui.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

public class CompositeIcon implements Icon {
    private final List<Icon> icons = new ArrayList<Icon>();

    public void addIcon(Icon icon) {
    	icons.add(icon);
    }
    
    public void clear() {
    	icons.removeAll(new ArrayList<Icon>(icons));
    }
    
	@Override
	public int getIconHeight() {
		int max = 0;
		
		for (Icon icon : icons) {
			if (icon.getIconHeight() > max) {
				max = icon.getIconHeight();
			}
		}
		
		return max;
	}

	@Override
	public int getIconWidth() {
		int max = 0;
		
		for (Icon icon : icons) {
			if (icon.getIconWidth() > max) {
				max = icon.getIconWidth();
			}
		}
		
		return max;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		for (Icon icon : icons) {
			icon.paintIcon(c, g, x, y);
		}
	}
}