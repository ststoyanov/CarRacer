import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the menu.
 */
public class MenuWindow
{
	private JPanel mainPanel;
	JButton speedRun;
	JButton classic;
	JButton defaultButton;
	
	/**
	 * Creates the menu.
	 *
	 * @param parent the parent window of the menu panel
	 */
	public MenuWindow(MainWindow parent){
		mainPanel = new JPanel();
		
		speedRun = new JButton("SPEED RUN");
		speedRun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parent.openGame(Racer.SPEED_RUN);
			}
		});
		mainPanel.add(speedRun);
		
		classic = new JButton("CLASSIC");
		classic.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parent.openGame(Racer.CLASSIC);
			}
		});
		mainPanel.add(classic);
		
		setDefaultButton(speedRun);
	}
	
	/**
	 * Set the default button in the menu.
	 *
	 * @param button the JButton to get focus.
	 */
	private void setDefaultButton(JButton button){
		defaultButton = button;
	}
	
	/**
	 * Get the current defaul button in the menu.
	 *
	 * @return default menu button.
	 */
	public JButton getDefaultButton(){
		return defaultButton;
	}
	
	/**
	 * Provides the JPanel of the menu.
	 *
	 * @return menu JPanel.
	 */
	public JPanel getPanel(){
		return mainPanel;
	}
}
