import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the CarRacer game window.
 */
public class MenuWindow
{
	private JPanel mainPanel;
	JButton speedRun;
	JButton classic;
	JButton defaultButton;
	
	public MenuWindow(MainWindow window){
		mainPanel = new JPanel();
		speedRun = new JButton("SPEED RUN");
		speedRun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				window.openGame(Racer.SPEED_RUN);
			}
		});
		classic = new JButton("CLASSIC");
		classic.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				window.openGame(Racer.CLASSIC);
			}
		});
		setDefaultButton(speedRun);
		mainPanel.add(speedRun);
		mainPanel.add(classic);
	}
	
	private void setDefaultButton(JButton button){
		defaultButton = button;
	}
	
	public JButton getDefaultButton(){
		return defaultButton;
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
}
