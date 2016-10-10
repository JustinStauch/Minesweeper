package ca.mcgill.mail.stauch.justin.minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.GameField;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.TopPanel;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.preferences.PreferenceDialog;
import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

public class Board extends JFrame {
	private final JMenuBar menuBar;
	private final JDialog preferenceMenu;
	
	private final int buttonSideLength = 36;
    private final int leftMargin = 5;
    private final int topMargin = 100;
    private final int bottomMargin = 25;
	
    private final int BORDER_WIDTH = 0;
    
	private static final long serialVersionUID = 1L;
	
	public Board(Game game) {
		final int width = game.getWidth();
		final int height = game.getHeight();
		
		final JPanel mainPanel = new JPanel();
		final JPanel bufferPanel = new JPanel();
		final TopPanel topPanel = new TopPanel(game);
		
		final GameField boardPanel = new GameField(game, width, height);
		
		menuBar = new JMenuBar();
		preferenceMenu = new PreferenceDialog();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setSize((width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin + 2 * BORDER_WIDTH,
						(height + 1)  * buttonSideLength + topMargin + bottomMargin + 2 * BORDER_WIDTH);
				setBackground(Color.getHSBColor(0, 0, 0.75F));
				setLayout(new BorderLayout());
				
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
				mainPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
				//mainPanel.setBounds(0, 0, (width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin + 2 * BORDER_WIDTH,
					//	(height + 1)  * buttonSideLength + topMargin + bottomMargin + 2 * BORDER_WIDTH);
				mainPanel.setVisible(true);
				mainPanel.setEnabled(true);
				
				add(mainPanel, BorderLayout.CENTER);
				
				bufferPanel.setLayout(new BoxLayout(bufferPanel, BoxLayout.Y_AXIS));
				bufferPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
//				bufferPanel.setBounds(0, 0, (width) * buttonSideLength + buttonSideLength / 2,
//						           (height + 1)  * buttonSideLength + topMargin + bottomMargin);
				bufferPanel.setVisible(true);
				bufferPanel.setEnabled(true);
				
				mainPanel.add(Box.createHorizontalGlue());
				mainPanel.add(bufferPanel);
				mainPanel.add(Box.createHorizontalGlue());
				
				//add(bufferPanel);
				
				bufferPanel.add(Box.createVerticalGlue());
				
				bufferPanel.add(topPanel);
				
				bufferPanel.add(Box.createVerticalGlue());
				
				boardPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
				boardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createLoweredBevelBorder()));
				
				bufferPanel.add(boardPanel);
				
				topPanel.setMaximumWidth((int) boardPanel.getMaximumSize().getWidth());
				
				bufferPanel.add(Box.createVerticalGlue());
				
				
				JMenu gameMenu = new JMenu("Game");
				gameMenu.setMnemonic(KeyEvent.VK_A);
				menuBar.add(gameMenu);
				
				JMenuItem menuItem;
				
				menuItem = new JMenuItem("New Game");
				menuItem.setMnemonic(KeyEvent.VK_N);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
				
				menuItem.addActionListener(new ActionListener() {

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
				
				gameMenu.add(menuItem);
				
				menuItem = new JMenuItem("Preferences");
				menuItem.setMnemonic(KeyEvent.VK_P);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
				
				menuItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						preferenceMenu.setVisible(true);
					}
				});
				
				gameMenu.add(menuItem);
				
				setJMenuBar(menuBar);
			}
		});
	}
}