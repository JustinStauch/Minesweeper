package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners.BoxListener;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners.MouseFaceListener;
import ca.mcgill.mail.stauch.justin.minesweeper.main.BlockState;
import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

/**
 * Panel to hold all the blocks of the field.
 *
 * @author Justin Stauch
 * @since 1 June 2016
 *
 */
public class GameField extends JPanel implements Observer {
    
	private static final long serialVersionUID = 1L;
	
	private final JButton[][] board;
    private final Game game;
	
	public GameField(Game game, int width, int height) {
		board = new JButton[width][height];
		this.game = game;
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setLayout(new GridLayout(height, width));
				setMaximumSize(new Dimension(BlockState.UNOPENED.getIcon().getIconWidth() * width, BlockState.UNOPENED.getIcon().getIconHeight() * height));
					
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						JButton button = new JButton();
						button.setIcon(BlockState.UNOPENED.getIcon());
						button.setPressedIcon(BlockState.BLANK.getIcon());
						button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), button.getIcon().getIconHeight()));
						button.setEnabled(true);
						button.setVisible(true);
						button.addMouseListener(new MouseFaceListener(game, x, y));
						button.addActionListener(new BoxListener(game, x, y));
						board[x][y] = button;
						add(button);
					}
				}
			}	
		});
		
		game.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(BlockState.UNOPENED.getIcon().getIconWidth() * board[0].length + "\t" + getWidth());
		if (o.equals(game)) {
			refreshStates();
		}
	}
	
	public void refreshStates() {
		BlockState[][] states = game.getStates();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int x, y;
				
				setVisible(false);
				for (x = 0; x < states.length; x++) {
					for (y = 0; y < states[x].length; y++) {
						board[x][y].setIcon(states[x][y].getIcon());
					}
				}
				
				setVisible(true);
			}
		});
	}
}