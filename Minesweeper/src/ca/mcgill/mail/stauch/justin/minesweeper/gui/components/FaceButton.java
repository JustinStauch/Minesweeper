package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.FaceIcon;
import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

/**
 * The button displayed at the top of the screen.
 *
 * @author Justin Stauch
 * @since Jun 1, 2016
 *
 */
public class FaceButton extends JButton implements Observer {

	private static final long serialVersionUID = 1L;

	private final Game game;
	
	public FaceButton(Game game) {
		this.game = game;
		game.addObserver(this);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setIcon(FaceIcon.NORMAL);
				setPreferredSize(new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight()));
				
				addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

							@Override
							protected Void doInBackground() throws Exception {
								game.resetGame();
								
								return null;
							}
						};
						
						worker.execute();
					}
				});
			}
		});
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o.equals(game)) {
			setIcon(game.getDesiredFaceIcon());
		}
	}
}