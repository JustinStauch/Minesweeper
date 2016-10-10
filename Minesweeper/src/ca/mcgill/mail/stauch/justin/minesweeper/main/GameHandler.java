package ca.mcgill.mail.stauch.justin.minesweeper.main;

import ca.mcgill.mail.stauch.justin.minesweeper.gui.Board;

public class GameHandler {

	public static void main(String[] args) {
		Game game = new Game(Difficulty.MEDIUM);
		Board board = new Board(game);
		board.setVisible(true);
		game.startGame();
	}
}