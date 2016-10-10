package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import ca.mcgill.mail.stauch.justin.minesweeper.main.Game;

public class GameTimer extends DigitalTimer {

	private static final long serialVersionUID = 1L;

	public GameTimer(Game game, int size) {
		super(size);
		game.setTimer(this);
	}
}