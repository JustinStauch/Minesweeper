package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.Board;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.GameField;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.components.GameTimer;
import ca.mcgill.mail.stauch.justin.minesweeper.gui.icons.FaceIcon;

/**
 * Main class of the program.
 * 
 * This class handles the game play. It tracks the underlying mines, and the sate of each space on the board.
 * 
 * This is a minesweeper game. It consists of a grid of covered boxes.
 * In a certain number of these boxes, there is a mine.
 * The size of the field, and the number of mines depends on difficulty,
 * but the information will always be available to the player.
 * 
 * The player clicks a box, revealing the underlying space.
 * If the space is a mine, the player loses.
 * If it is not a mine, it will display the number of mines in adjacent spaces.
 * This count includes boxes above, below, and to either side of the box,
 * as well as the spaces on the corners of the box.
 * 
 * The goals is to open every box that is not a mine without selecting a mine.
 * 
 * Right clicking a box flags it as a mine.
 * Right clicking a number space that is touching its number of flags
 * opens its non-flagged adjacent boxes.
 *
 * @author Justin Stauch
 *
 */
public class Game extends Observable {
    private final Difficulty level; //The difficulty of the game.
    private int minesLeft, height, width; //Parameters for the game. The number of mines total - the number of flags.
    private boolean[][] field; //The grid of the game. True indicates a mine in the space.
    //private Board board; //The main JFrame handling the GUI.
    private BlockState[][] states; //A representation of what should be displayed for each space.
    private boolean firstMove, active; //Tracks different handling of the first move, and when the game is done.
    private FaceIcon desiredFace;
    private GameTimer timer;
	
    /**
     * Construct a game of the given difficulty.
     * 
     * @param level The difficulty to build it with.
     */
    public Game(Difficulty level) {
    	this.level = level;
    	minesLeft = this.level.getMines();
    	height = this.level.getHeight();
    	width = this.level.getWidth();
    	field = createField();
    	states = trackBlockStates();
    }
	
    public void startGame() {
    	firstMove = true;
    	active = true;
    	desiredFace = FaceIcon.NORMAL;
    	
    	setChanged();
    	notifyObservers();
    }
    
	/**
	 * Resets all the variables to their original states,
	 * so another game can be played.
	 */
	public void resetGame() {
		minesLeft = level.getMines();
    	height = level.getHeight();
    	width = level.getWidth();
		field = createField();
		states = trackBlockStates();
		resetTime();
		firstMove = true;
		active = true;
		desiredFace = FaceIcon.NORMAL;
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if a game is in progress to see if to ignore certain inputs.
	 * 
	 * @return If a game is in progress.
	 */
	public boolean isActive() {
		return active;
	}
	
	public int getWidth() {
		return level.getWidth();
	}
	
	public int getHeight() {
		return level.getHeight();
	}
	
	/**
	 * Get the state being shown at the given coordinates.
	 * 
	 * @param x The x value to check.
	 * @param y The y value to check.
	 * @return A BlockState representing what the user sees at the given space.
	 */
	public BlockState getState(int x, int y) {
		return states[x][y];
	}
	
	/**
	 * Gets a copy of the current states array.
	 * 
	 * It is only necessary to make a copy of each of the second arrays;
	 * BlockStates are immutable, and unique.
	 * 
	 * @return A copy of the array of the current block states.
	 */
	public BlockState[][] getStates() {
		BlockState[][] statesCopy = new BlockState[states.length][];
		for (int i = 0; i < statesCopy.length; i++) {
			statesCopy[i] = states[i].clone();
		}
		
		return statesCopy;
	}
	
	public int getMinesLeft() {
		return minesLeft;
	}
	
	/*public void setFace(FaceIcon icon) {
		board.setFace(icon);
	}*/
	
	/**
	 * Gets the icon that the face should display.
	 * 
	 * This is used to pull information for the Observer Design Pattern.
	 * 
	 * @return The icon that the face should display.
	 */
	public FaceIcon getDesiredFaceIcon() {
		return desiredFace;
	}
	
	public void faceScared() {
		desiredFace = FaceIcon.SCARED;
		setChanged();
		notifyObservers();
	}
	
	public void faceNotScared() {
		desiredFace = FaceIcon.NORMAL;
		setChanged();
		notifyObservers();
	}
	
	public void setTimer(GameTimer timer) {
		this.timer = timer;
	}
	
	/**
	 * Places the mines after the first move to insure that the first move isn't a mine.
	 * 
	 * There is a chance that when the player clicks the first square, there is a mine there.
	 * This is pointless as the player had no way of knowing,
	 * so a new board is made until this square is a blank.
	 * 
	 * Simply presenting a number is also pointless, as the player will just have to make another guess.
	 * This is why it goes until the spot is blank.
	 * 
	 * @param x The x coordinate of the clicked space.
	 * @param y The y coordinate of the clicked space.
	 */
	public void firstMove(int x, int y) {
		//Generate an initial board.
		field = shuffle(field, 500);
		while (true) {
			//Spend less time making changes.
			field = shuffle(field, 100);
			
			//There was a mine, try again.
			if (field[x][y]) {
				continue;
			}
			
			boolean touchingAMine = false;
			
			//Check the adjacent spaces.
			for (int i = x - 1; i <= x + 1; i++) {
				
				//Make sure that the space is on the board, and not off the edge.
				if (i < 0 || i >= states.length) {
					continue;
				}
				
				for (int j = y - 1; j <= y + 1; j++) {
					
					//Make sure it is in the right spot.
					if (j < 0 || j >= states[i].length) {
						continue;
					}
					
					//It's a mine, stop searching, and make a new board.
					if (field[i][j]) {
						touchingAMine = true;
						break;
					}
				}
				
				//No point in searching more.
				if (touchingAMine) {
					break;
				}
			}
			
			//It's not touching a mine, the board is good.
			if (!touchingAMine) {
				break;
			}
		}
		
		firstMove = false;
		startTime();
	}
	
	/**
	 * Handles the event that a block is selected.
	 * 
	 * @param x The x value of the clicked block.
	 * @param y The y value of the clicked block.
	 */
	public void blockSelected(int x, int y) {
		blockOpened(x, y);
		
		notifyObservers();
	}
	
	/**
	 * Does the recursion for opening a block.
	 * 
	 * This is separated from blockSelected(int, int)
	 * because it prevents notifying the observers multiple times.
	 * 
	 * @param x The x value of the clicked block.
	 * @param y The y value of the clicked block.
	 */
	private void blockOpened(int x, int y) {
		//Do nothing if there is no game being played.
		if (!active) {
			return;
		}
		
		//Only Unopened, and question mark blocks can be opened.
		if (states[x][y] != BlockState.UNOPENED && states[x][y] != BlockState.QUESTION_MARK) {
			return;
		}
		
		//The first move requires making the field.
		if (firstMove) {
			firstMove(x, y);
		}
		
		//A mine was clicked.
		if (field[x][y]) {
			gameOver(); //Handle the game over.
			
			//Display the area that was clicked as a mine.
			states[x][y] = BlockState.CLICKED_MINE;
			
			//Update the GUI.
			setChanged();
			return;
		}
		
	    short minesTouching = 0;
		
	    //Check each space.
		for (int i = x - 1; i <= x + 1; i++) {
			//Make sure the space is not off the board.
			if (i < 0 || i >= states.length) {
				continue;
			}
			for (int j = y - 1; j <= y + 1; j++) {
				//Make sure the space is not off the board.
				if (j < 0 || j >= states[i].length) {
					continue;
				}
				//Count the mine.
				if (field[i][j]) {
					minesTouching++;
				}
			}
		}
		
		//Convert the number of mines touching to a BlockState value.
		states[x][y] = BlockState.fromNumber(minesTouching);
		
		//If the space touches no mines, then open all the adjacent spaces.
		if (minesTouching == 0) {
			for (int i = x - 1; i <= x + 1; i++) {
				if (i < 0 || i >= states.length) {
					continue;
				}
				for (int j = y - 1; j <= y + 1; j++) {
					if (j < 0 || j >= states[i].length) {
						continue;
					}
					blockOpened(i, j);
				}
			}
		}
		
		//Check if every space that is not a mine has been selected.
		checkForWin();
		
		setChanged();
	}
	
	/**
	 * Handle when a block is right clicked.
	 * 
	 * @param x The x value of the clicked block.
	 * @param y The y value of the clicked block.
	 */
	public void blockFlagged(int x, int y) {
		//Check if a game is being played.
		if (!active) {
			return;
		}
		
		//Change an unopened block to a flagged block, knock down the count of mines not flagged.
		if (states[x][y] == BlockState.UNOPENED) {
			minesLeft--;
			states[x][y] = BlockState.FLAGGED;
		}
		//Flagged to a question mark, put the mine count back up.
		else if (states[x][y] == BlockState.FLAGGED) {
			states[x][y] = BlockState.QUESTION_MARK;
			minesLeft++;
		}
		//Change the flag to a question mark.
		else if (states[x][y] == BlockState.QUESTION_MARK) {
			states[x][y] = BlockState.UNOPENED;
		}
		//It's a number, check if the surrounding blocks can be opened.
		else {
            short minesTouching = 0;
            short flagsTouching = 0;
			
            //Check the adjacent spaces, and count the number of flags, and mines it is touching.
			for (int i = x - 1; i <= x + 1; i++) {
				if (i < 0 || i >= states.length) {
					continue;
				}
				for (int j = y - 1; j <= y + 1; j++) {
					if (j < 0 || j >= states[i].length) {
						continue;
					}
					if (i == x && j == y) {
						continue;
					}
					if (field[i][j]) {
						minesTouching++;
					}
					if (states[i][j] == BlockState.FLAGGED) {
						flagsTouching++;
					}
				}
			}
			
			//If there is at least one adjacent flag per adjacent mine, open all of its adjacent unopened spaces.
			if (flagsTouching >= minesTouching) {
				for (int i = x - 1; i <= x + 1; i++) {
					if (i < 0 || i >= states.length) {
						continue;
					}
					for (int j = y - 1; j <= y + 1; j++) {
						if (j < 0 || j >= states[i].length) {
							continue;
						}
						if (i == x && j == y) {
							continue;
						}
						if (states[i][j] == BlockState.UNOPENED || states[i][j] == BlockState.QUESTION_MARK) {
							blockOpened(i, j);
						}
					}
				}
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	private void startTime() {
		timer.startTime();
	}
	
	private void stopTime() {
		timer.stopTime();
	}
	
	private void resetTime() {
		timer.resetTime();
	}
	
	private void gameOver() {
		stopTime();
		active = false;
		
		desiredFace = FaceIcon.DEAD;
		
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				if (field[x][y]) {
					if (states[x][y] != BlockState.FLAGGED) {
						states[x][y] = BlockState.MINE;
					}
				}
				else if (states[x][y] == BlockState.FLAGGED) {
					states[x][y] = BlockState.NOT_A_MINE;
				}
			}
		}
	}
	
	private void checkForWin() {
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				if (!field[x][y]) {
					if (states[x][y] == BlockState.UNOPENED || states[x][y] == BlockState.QUESTION_MARK || states[x][y] == BlockState.FLAGGED) {
						return;
					}
				}
			}
	    }
		
		stopTime();
		active = false;
		
		desiredFace = FaceIcon.WIN;
				
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				if (field[x][y]) {
					states[x][y] = BlockState.FLAGGED;
				}
			}
	    }
		
		minesLeft = 0;
	}
	
	private boolean[][] createField() {
		int mines = minesLeft;
		
		boolean[][] field = new boolean[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field[x][y] = mines > 0;
				mines--;
			}
		}
		
		return field;
	}
	
	private BlockState[][] trackBlockStates() {
		BlockState[][] blockStates = new BlockState[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				blockStates[x][y] = BlockState.UNOPENED;
			}
		}
		
		return blockStates;
	}
	
	private boolean[][] shuffle(boolean[][] arr, int times) {
		if (times == 0) {
			return shuffle(arr);
		}
		
		return shuffle(shuffle(arr), times - 1);
	}
	
	private boolean[][] shuffle(boolean[][] arr) {
		Random random = new Random();
		
		int x1, x2, y1, y2;
		boolean temp;
		
		for (int x = 0; x < 100; x++) {
			x1 = random.nextInt(arr.length);
			y1 = random.nextInt(arr[x1].length);
			
			x2 = random.nextInt(arr.length);
			y2 = random.nextInt(arr[x2].length);
			
			temp = arr[x1][y1];
			arr[x1][y1] = arr[x2][y2];
			arr[x2][y2] = temp;
		}
		
		return arr;
	}
}