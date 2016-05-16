package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameClock extends DigitalDisplay {

	private static final long serialVersionUID = 1L;

	private final Timer timer;
	
	private int currentTime;
	
    public GameClock(int size) {
    	super(size);
    	
    	timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTime++;
				displayNumber(currentTime);
			}
    		
    	});
    }
    
    public void startTime() {
    	timer.start();
    }
    
    public void stopTime() {
    	timer.stop();
    }
    
    public void resetTime() {
    	timer.stop();
    	currentTime = 0;
    	SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				displayNumber(currentTime);
			}
    		
    	});
    }
}