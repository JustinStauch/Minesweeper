package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.util.Random;

public class Minesweeper {
    private int level, minesLeft, height, width;
    private boolean[][] field;
    private Board board;
    private BlockState[][] states;
    private boolean firstMove, active;
	
    public Minesweeper(int level) {
    	this.level = level;
    	field = createField(level);
    	states = trackBlockStates();
    	board = new Board(this, field.length, field[0].length);
    	board.setMinesLeft(minesLeft);
    	board.setVisible(true);
    	firstMove = true;
    	active = true;
    }
    
	public static void main(String args[]) {
		new Minesweeper(0);
	}
	
	public void resetGame() {
		field = createField(level);
		states = trackBlockStates();
		board.updateStates(states);
		firstMove = true;
		active = true;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getMinesLeft() {
		return minesLeft;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public BlockState getState(int x, int y) {
		return states[x][y];
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
	}
	
	public BlockState[][] blockSelected(int x, int y) {
		if (!active) {
			return states;
		}
		
		if (states[x][y] != BlockState.UNOPENED && states[x][y] != BlockState.QUESTION_MARK) {
			return states;
		}
		
		if (firstMove) {
			firstMove(x, y);
		}
		
		if (field[x][y]) {
			gameOver();
			states[x][y] = BlockState.CLICKED_MINE;
			return states;
		}
		
	    short minesTouching = 0;
		
		for (int i = x - 1; i <= x + 1; i++) {
			if (i < 0 || i >= states.length) {
				continue;
			}
			for (int j = y - 1; j <= y + 1; j++) {
				if (j < 0 || j >= states[i].length) {
					continue;
				}
				if (field[i][j]) {
					if (i == x && j == y) {
						continue;
					}
					minesTouching++;
				}
			}
		}
		
		states[x][y] = BlockState.fromNumber(minesTouching);
		
		if (minesTouching == 0) {
			for (int i = x - 1; i <= x + 1; i++) {
				if (i < 0 || i >= states.length) {
					continue;
				}
				for (int j = y - 1; j <= y + 1; j++) {
					if (j < 0 || j >= states[i].length) {
						continue;
					}
					blockSelected(i, j);
				}
			}
		}
		
		checkForWin();
		
		return states;
	}
	
	public BlockState[][] blockFlagged(int x, int y) {
		if (!active) {
			return states;
		}
		
		if (states[x][y] == BlockState.UNOPENED) {
			minesLeft--;
			states[x][y] = BlockState.FLAGGED;
		}
		else if (states[x][y] == BlockState.FLAGGED) {
			states[x][y] = BlockState.QUESTION_MARK;
			minesLeft++;
		}
		else if (states[x][y] == BlockState.QUESTION_MARK) {
			states[x][y] = BlockState.UNOPENED;
		}
		else {
            short minesTouching = 0;
            short flagsTouching = 0;
			
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
			
			if (flagsTouching == minesTouching) {
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
							blockSelected(i, j);
						}
					}
				}
			}
		}
		
		return states;
	}
	
	private void gameOver() {
		active = false;
		
		board.setFace(2);
		
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
		
		active = false;
		
		board.setFace(3);
				
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				if (field[x][y]) {
					states[x][y] = BlockState.FLAGGED;
				}
			}
	    }
		
		board.updateStates(states);
	}
	
	private boolean[][] createField(int level) {
		int mines;
		
		switch (level) {
		    case 2:  height = 16;
		             width = 30;
		             mines = 99;
		             break;
		    case 1:  height = 16;
		             width = 16;
		             mines = 40;
		             break;
		    case 0:  height = 8;
                     width = 8;
                     mines = 10;
                     break;
		    default: height = 32;
		             width = 70;
		             mines = 32 * 70 - 1;
		             break;
		}
		
		minesLeft = mines;
		
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