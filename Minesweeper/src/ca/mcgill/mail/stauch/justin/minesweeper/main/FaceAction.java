package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FaceAction implements MouseListener {
	private Minesweeper main;
	
	public FaceAction(Minesweeper main) {
		this.main = main;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		main.resetGame();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}