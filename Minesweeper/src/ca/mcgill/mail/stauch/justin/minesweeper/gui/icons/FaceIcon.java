package ca.mcgill.mail.stauch.justin.minesweeper.gui.icons;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class FaceIcon implements Icon {
    public static final FaceIcon NORMAL = new FaceIcon(new ImageIcon("HappyFace.png"));
    public static final FaceIcon SCARED = new FaceIcon(new ImageIcon("ScaredFace.png"));
    public static final FaceIcon DEAD = new FaceIcon(new ImageIcon("DeadFace.png"));
    public static final FaceIcon WIN = new FaceIcon(new ImageIcon("WinFace.png"));
	
	
	private final Icon face;
	
	private FaceIcon(Icon face) {
		this.face = face;
	}

	@Override
	public int getIconHeight() {
		return face.getIconHeight();
	}
	
	@Override
	public int getIconWidth() {
		return face.getIconWidth();
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		face.paintIcon(c, g, x, y);
	}
}