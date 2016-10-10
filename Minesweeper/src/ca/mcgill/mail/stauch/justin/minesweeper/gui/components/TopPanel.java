package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int height;
	
	public TopPanel(Game game) {
		final FaceButton face = new FaceButton(game);
		final MineCountDisplay mines = new MineCountDisplay(game, 3);
		final GameTimer timer = new GameTimer(game, 3);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setLayout(new GridBagLayout());
				setBackground(Color.getHSBColor(0, 0, 0.75F));
				setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createLoweredBevelBorder()));
				
                GridBagConstraints cons;
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.LINE_START;
				cons.weightx = 0.5;
				
				add(mines, cons);
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.CENTER;
				
				add(face, cons);
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.LINE_END;
				cons.weightx = 0.5;
				
				add(timer, cons);
				height = timer.getHeight();
			}
		});
	}
	
	public void setMaximumWidth(int width) {
		setMaximumSize(new Dimension(width, height));
	}
}