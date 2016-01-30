package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class PressAction implements MouseListener {
    private Minesweeper main;
    private int x, y;
	
	public PressAction(Minesweeper main, int x, int y) {
		this.main = main;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		switch (event.getButton()) {
            case 1: main.getBoard().updateStates(main.blockSelected(x, y));
                    break;
            case 3: main.getBoard().updateStates(main.blockFlagged(x, y));
                    main.getBoard().setMinesLeft(main.getMinesLeft());
                    break;
        }
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
		main.getBoard().setFace(1);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!main.isActive()) {
			return;
		}
		
		main.getBoard().setFace(0);
	}
}
