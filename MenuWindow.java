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
	
	public MenuWindow(MainWindow window){
		mainPanel = new JPanel();
		speedRun = new JButton("SPEED RUN");
		speedRun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				window.openSpeedRun();
			}
		});
		mainPanel.add(speedRun);
		mainPanel.add(new JButton("CLASSIC"));
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
}
