import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the menu.
 */
public class MenuWindow
{
	private JPanel mainPanel;
	private JButton speedRun;
	private JButton classic;
	private JButton defaultButton;
	private JButton highScoresCl;
	private JButton chooseCar;
	private JButton highScoresSR;
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
		
		
		classic = new JButton("CLASSIC");
		classic.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parent.openGame(Racer.CLASSIC);
			}
		});
		
		highScoresCl = new JButton("High scores");
		highScoresCl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), new HighScoresControl("classic"),-1);
			}
		});
		
		highScoresSR = new JButton("High scores");
		highScoresSR.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), new HighScoresControl("speedrun"),-1);
			}
		});

		chooseCar = new JButton("Choose Car");
		
		mainPanel.add(classic);
		mainPanel.add(highScoresCl);
		mainPanel.add(speedRun);
		mainPanel.add(highScoresSR);
		mainPanel.add(chooseCar);
		setDefaultButton(classic);
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
	
	public JButton getNextButton(){
		if(defaultButton == classic)
			defaultButton = speedRun;
		else
			defaultButton = classic;
		
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
