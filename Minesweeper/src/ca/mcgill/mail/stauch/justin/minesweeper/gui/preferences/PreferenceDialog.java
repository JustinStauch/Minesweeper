package ca.mcgill.mail.stauch.justin.minesweeper.gui.preferences;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This dialog will be used to allow the user to change preferences.
 *
 * @author Justin Stauch
 * @since 12 May 2016
 *
 */
public class PreferenceDialog extends JDialog {
	private final JPanel panel;
	
	private static final long serialVersionUID = 1L;

	public PreferenceDialog() {
		panel = new JPanel(null);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setSize(500, 500);
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				
				panel.setBounds(0, 0, 500, 500);
				panel.setBackground(Color.GRAY);
				panel.setVisible(true);
				add(panel);
			}
		});
	}
}