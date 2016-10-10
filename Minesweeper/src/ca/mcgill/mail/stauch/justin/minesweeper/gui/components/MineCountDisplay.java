package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

public class MineCountDisplay extends JPanel implements Observer {
    
	private static final long serialVersionUID = 1L;

	private final Game game;
	private final DigitalDisplay display;
	
	public MineCountDisplay(Game game, int size) {
		this.game = game;
		display = new DigitalDisplay(size);
		game.addObserver(this);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setLayout(new BorderLayout());
				
				display.displayNumber(game.getMinesLeft());
				
				setPreferredSize(display.getPreferredSize());
				
				add(display, BorderLayout.CENTER);
			}
		});
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o.equals(game)) {
			display.displayNumber(game.getMinesLeft());
		}
	}
}