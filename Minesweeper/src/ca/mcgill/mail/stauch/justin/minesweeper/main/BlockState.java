package ca.mcgill.mail.stauch.justin.minesweeper.main;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum BlockState {
    UNOPENED("Unopened.png"),
    FLAGGED("Flagged.png"),
    QUESTION_MARK("QuestionMark.png"),
    MINE("Mine.png"),
    CLICKED_MINE("ClickedMine.png"),
    NOT_A_MINE("NotAMine.png"),
    BLANK("Blank.png"),
    ONE("One.png"),
    TWO("Two.png"),
    THREE("Three.png"),
    FOUR("Four.png"),
    FIVE("Five.png"),
    SIX("Six.png"),
    SEVEN("Seven.png"),
    EIGHT("Eight.png");
	
	private final String file;
	
	private BlockState(String file) {
		this.file = file;
	}
	
	public Icon getIcon() {
		return new ImageIcon(file);
	}
	
	/**
	 * Gets the BlockState with the given number on the face.
	 * 
	 * @param num The number of mines the block is touching.
	 * @return A BlockState representing the given number of mines that it is touching.
	 */
	public static BlockState fromNumber(short num) {
		switch (num) {
		    case 1:  return ONE;
		    case 2:  return TWO;
		    case 3:  return THREE;
		    case 4:  return FOUR;
		    case 5:  return FIVE;
		    case 6:  return SIX;
		    case 7:  return SEVEN;
		    case 8:  return EIGHT;
		    default: return BLANK;
		}
	}
}