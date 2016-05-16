package ca.mcgill.mail.stauch.justin.minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.DigitalDisplay;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.GameClock;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.FaceIcon;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners.BoxListener;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners.MouseFaceListener;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.preferences.PreferenceDialog;
import ca.mcgill.mail.stauch.justin.minesweeper.main.BlockState;
import ca.mcgill.mail.stauch.justin.minesweeper.main.Minesweeper;

public class Board extends JFrame {
	private final Minesweeper main;
	
	private final JPanel bufferPanel;
	private final JPanel mainPanel;
	private final JPanel topPanel;
	private final JPanel boardPanel;
	
	private JButton[][] board;
	private final JButton face;
	private final DigitalDisplay minesLeft;
	private final GameClock clock;
	private final JMenuBar menuBar;
	private final JDialog preferenceMenu;
	
	private final int buttonSideLength = 36;
    private final int leftMargin = 10;
    private final int topMargin = 100;
    private final int bottomMargin = 25;
	
    private final int BORDER_WIDTH = 0;
    
	private static final long serialVersionUID = 1L;
	
	public Board(Minesweeper main, int width, int height) {
		this.main = main;
		
		bufferPanel = new JPanel();
		mainPanel = new JPanel();
		topPanel = new JPanel();
		boardPanel = new JPanel();
		
		face = new JButton();
		minesLeft = new DigitalDisplay(3);
		clock = new GameClock(3);
		menuBar = new JMenuBar();
		preferenceMenu = new PreferenceDialog();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setSize((width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin + 2 * BORDER_WIDTH,
						(height + 1)  * buttonSideLength + topMargin + bottomMargin + 2 * BORDER_WIDTH);
				setBackground(Color.getHSBColor(0, 0, 0.75F));
				
				//bufferPanel.setLayout(new BorderLayout());
				
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
				mainPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
				mainPanel.setBounds(0, 0, (width) * buttonSideLength + buttonSideLength / 2,
						           (height + 1)  * buttonSideLength + topMargin + bottomMargin);
				mainPanel.setVisible(true);
				mainPanel.setEnabled(true);
				
				add(mainPanel);
				
				topPanel.setLayout(new GridBagLayout());
				topPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
				topPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createLoweredBevelBorder()));
				
				GridBagConstraints cons;
				
				minesLeft.setVisible(false);
				minesLeft.setBounds(10, 10, 135, 75);
				minesLeft.setOpaque(true);
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.LINE_START;
				cons.weightx = 0.5;
				
				topPanel.add(minesLeft, cons);
				
				//face.setBounds((((width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin) / 2) - 23, 10, 46, 46);
				face.setIcon(FaceIcon.NORMAL);
				face.setPreferredSize(new Dimension(face.getIcon().getIconWidth(), face.getIcon().getIconHeight()));
				
				face.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

							@Override
							protected Void doInBackground() throws Exception {
								main.resetGame();
								
								return null;
							}
						};
						
						worker.execute();
					}
				});
				
				face.setVisible(true);
				face.setEnabled(true);
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.CENTER;
				
				topPanel.add(face, cons);
				
				clock.setVisible(true);
				clock.setBounds(mainPanel.getWidth() - 135 - 30, 10, 135, 75);
				clock.setOpaque(true);
				
				cons = new GridBagConstraints();
				cons.anchor = GridBagConstraints.LINE_END;
				cons.weightx = 0.5;
				
				topPanel.add(clock, cons);
				
				topPanel.setMaximumSize(new Dimension(mainPanel.getWidth() - (leftMargin * 2), clock.getHeight()));
				
				mainPanel.add(topPanel);
				
				createButtons(width, height);
				
				boardPanel.setBackground(Color.getHSBColor(0, 0, 0.75F));
				boardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createLoweredBevelBorder()));
				
				
				
				
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
								main.resetGame();
								
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
	
	public void updateStates(BlockState[][] states) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int x, y;
				
				mainPanel.setVisible(false);
				for (x = 0; x < states.length; x++) {
					for (y = 0; y < states[x].length; y++) {
						board[x][y].setIcon(states[x][y].getIcon());
					}
				}
				
				mainPanel.setVisible(true);
			}
		});
	}
	
	public void startTiming() {
		clock.setVisible(true);
		clock.startTime();
	}
	
	public void stopTiming() {
		clock.stopTime();
	}
	
	public void resetTime() {
		clock.resetTime();
	}
	
	public void setMinesLeft(int mines) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				minesLeft.displayNumber(mines);
				minesLeft.setVisible(true);
			}
		});
	}
	
	/**
	 * Sets the face icon at the top of the window.
	 * 
	 * The face that it sets is based on the number that is given.
	 *     0: Normal smiley face.
	 *     1: The scared face because a space was clicked on.
	 *     2: The dead face for a loss.
	 *     3: The glasses face for a win.
	 * 
	 * @param type
	 */
	public void setFace(FaceIcon icon) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				face.setIcon(icon);
			}
		});
	}
	
	public void faceNotScared() {
		if (face.getIcon().equals(FaceIcon.SCARED)) {
			setFace(FaceIcon.NORMAL);
		}
	}
	
	private void createButtons(int width, int height){
		boardPanel.setLayout(new GridLayout(height, width));
		boardPanel.setPreferredSize(new Dimension(BlockState.UNOPENED.getIcon().getIconWidth() * width, BlockState.UNOPENED.getIcon().getIconHeight() * height));
		
		board = new JButton[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				JButton button = new JButton();
				button.setIcon(BlockState.UNOPENED.getIcon());
				button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), button.getIcon().getIconHeight()));
				button.setEnabled(true);
				button.setVisible(true);
				button.addMouseListener(new MouseFaceListener(main, x, y));
				button.addActionListener(new BoxListener(main, x, y));
				board[x][y] = button;
				boardPanel.add(button);
			}
		}
		
		
		
		mainPanel.add(boardPanel);
	}
}