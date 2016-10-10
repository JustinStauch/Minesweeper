package ca.mcgill.mail.stauch.justin.minesweeper.gui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class DigitalTimer extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Timer timer;
	private final DigitalDisplay display;
	
	private int currentTime = 0;
	
    public DigitalTimer(int size) {
    	display = new DigitalDisplay(3);
    	
    	timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTime++;
				display.displayNumber(currentTime);
			}
    		
    	});
    	
    	SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setLayout(new BorderLayout());
				
				display.displayNumber(currentTime);
				
				setPreferredSize(display.getPreferredSize());
				add(display, BorderLayout.CENTER);
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
				display.displayNumber(currentTime);
			}
    		
    	});
    }
}