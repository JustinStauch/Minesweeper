package ca.mcgill.mail.stauch.justin.minesweeper.main;

public final class Difficulty {
    public static final Difficulty EASY = new Difficulty(10, 9, 9);
    public static final Difficulty MEDIUM = new Difficulty(40, 16, 16);
    public static final Difficulty HARD = new Difficulty(99, 16, 30);
	
	private final int mines;
	private final int height;
	private final int width;
	
	public Difficulty(int mines, int height, int width) {
		this.mines = mines;
		this.height = height;
		this.width = width;
	}
	
	public int getMines() {
		return mines;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}