package ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingWorker;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.FaceIcon;
import ca.mcgill.mail.stauch.justin.minesweeper.main.BlockState;
import ca.mcgill.mail.stauch.justin.minesweeper.main.Minesweeper;

public class MouseFaceListener implements MouseListener {
    private Minesweeper main;
    private int x, y;
	
	public MouseFaceListener(Minesweeper main, int x, int y) {
		this.main = main;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
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
	public void mousePressed(MouseEvent event) {
		if (!main.isActive()) {
			return;
		}
		
		if (event.getButton() == 3) {
			if (main.getState(x, y) == BlockState.UNOPENED || main.getState(x, y) == BlockState.QUESTION_MARK || main.getState(x, y) == BlockState.FLAGGED) {
				return;
			}
		}
		main.faceScared();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!main.isActive()) {
			return;
		}
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				
				if (event.getButton() == 3) {
					main.blockFlagged(x, y);
				}
				
				return null;
			}
			
			protected void done() {
				main.faceNotScared();
			}
		};
		
		worker.execute();
	}
}
