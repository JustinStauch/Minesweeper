package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JFrame {
	private Minesweeper main;
	private JPanel panel;
	private JButton[][] board;
	private final JButton face;
	private JLabel minesLeft;
	
	final int buttonSideLength = 36;
    final int leftMargin = 10;
    final int topMargin = 100;
    final int bottomMargin = 25;
	
	private static final long serialVersionUID = 1L;
	
	public Board(Minesweeper main, int width, int height) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.main = main;
		this.setSize((width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin,
				(height + 1)  * buttonSideLength + topMargin + bottomMargin);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.gray);
		panel.setBounds(0, 0, (width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin,
				           (height + 1)  * buttonSideLength + topMargin + bottomMargin);
		panel.setVisible(true);
		panel.setEnabled(true);
		add(panel);
		createButtons(width, height);
		
		face = new JButton();
		face.setBounds((((width) * buttonSideLength + buttonSideLength / 2 + 2 * leftMargin) / 2) - 49, 10, 76, 76);
		face.setIcon(new ImageIcon("HappyFace.png"));
		face.addMouseListener(new FaceAction(main));
		face.setVisible(true);
		face.setEnabled(true);
		panel.add(face);
		
		minesLeft = new JLabel("", SwingConstants.CENTER);
		minesLeft.setVisible(false);
		minesLeft.setBounds(10, 10, 160, 100);
		minesLeft.setOpaque(true);
		minesLeft.setForeground(Color.RED);
		panel.add(minesLeft);
	}
	
	public void updateStates(BlockState[][] states) {
		int x, y;
		
		panel.setVisible(false);
		for (x = 0; x < states.length; x++) {
			for (y = 0; y < states[x].length; y++) {
				board[x][y].setIcon(new ImageIcon(states[x][y].getIconFile()));
			}
		}
		
		panel.setVisible(true);
	}
	
	public void setMinesLeft(int mines) {
		String newText = Integer.toString(mines);
		while (newText.length() < 3) {
			newText = "0" + newText;
		}
		minesLeft.setText(newText);
		resizeMinesLeftText();
		minesLeft.setVisible(true);
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
	public void setFace(int type) {
		ImageIcon icon;
		
		switch (type) {
		    case 1:  icon = new ImageIcon("ScaredFace.png");
		             break;
		    case 2:  icon = new ImageIcon("DeadFace.png");
                     break;
		    case 3:  icon = new ImageIcon("WinFace.png");
                     break;
		    default: icon = new ImageIcon("HappyFace.png");
                     break;
		}
		
		face.setIcon(icon);
	}
	
	private void resizeMinesLeftText() {
		Font font = minesLeft.getFont();
		String num = minesLeft.getText();
		
		int stringWidth = minesLeft.getFontMetrics(font).stringWidth(num);
		int compWidth = minesLeft.getWidth();
		
		double ratio = (double) compWidth / (double) stringWidth;
		
		int newSize = (int) (font.getSize() * ratio);
		int compHeight = minesLeft.getHeight();
		
		int rightSize = newSize < compHeight ? newSize : compHeight;
		
		minesLeft.setFont(new Font(font.getName(), Font.PLAIN, rightSize));	
	}
	
	private void createButtons(int width, int height){
		
		board = new JButton[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				JButton button = new JButton();
				button.setBounds((x * buttonSideLength) + leftMargin,
						(y * buttonSideLength) + topMargin,
						buttonSideLength, buttonSideLength);
				
				button.setIcon(new ImageIcon("Unopened.png"));
				button.setEnabled(true);
				button.setVisible(true);
				button.addMouseListener(new PressAction(main, x, y));
				board[x][y] = button;
				panel.add(button);
			}
		}
	}
}