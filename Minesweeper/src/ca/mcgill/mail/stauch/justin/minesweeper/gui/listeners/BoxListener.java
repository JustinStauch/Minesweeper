package ca.mcgill.mail.stauch.justin.minesweeper.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import ca.mcgill.mail.stauch.justin.minesweeper.main.Minesweeper;

public class BoxListener implements ActionListener {
	private final Minesweeper main;
    private final int x, y;
	
	public BoxListener(Minesweeper main, int x, int y) {
		this.main = main;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				main.blockSelected(x, y);
				
				return null;
			}
		};
		
		worker.execute();
	}
}