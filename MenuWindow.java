import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the menu.
 */
public class MenuWindow
{
	private MainWindow parent;
	
	private JPanel mainPanel;
	private ChooseCarPanel chooseCarPanel;
	private HighScoresDialog highScoreDlg;

	protected JButton classic;
	protected JButton speedRun;	
	protected JButton highScoresCl;
	protected JButton chooseCar;
	protected JButton highScoresSR;
	protected JButton redCar;
	protected JButton yellowCar;
	protected JButton policeCar;
	
	private JButton defaultButton; //the current default button, takes another JButton as value

	/**
	 * Creates the menu.
	 *
	 * @param parent the parent window of the menu panel
	 */
	public MenuWindow(MainWindow parent){
		this.parent = parent;
		mainPanel = new JPanel();
		
		//create the the menu buttons
		speedRun = new JButton("SPEED RUN"); //starts the speed run game mode
		speedRun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(highScoreDlg != null)
					highScoreDlg.dispose();
				parent.openGame(Racer.SPEED_RUN);
			}
		});
		
		
		classic = new JButton("CLASSIC"); //starts the classic game mode
		classic.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(highScoreDlg != null)
					highScoreDlg.dispose();
				parent.openGame(Racer.CLASSIC);
			}
		});
		
		highScoresCl = new JButton("High scores"); //opens the high scores for the classic game mode
		highScoresCl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(highScoreDlg != null)
					highScoreDlg.dispose();
				highScoreDlg = new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), new HighScoresControl("classic"),-1);
			}
		});
		
		highScoresSR = new JButton("High scores"); //opens the high scores for the speed run game mode
		highScoresSR.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(highScoreDlg != null)
					highScoreDlg.dispose();
				highScoreDlg = new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel), new HighScoresControl("speedrun"),-1);
			}
		});

		chooseCar = new JButton("Choose Car"); //opens the menu for choosing the car
		try { chooseCar.setIcon(new ImageIcon(getClass().getResource(Car.RED_CAR))); //sets the car icon to the choose car button
		} catch (Exception ex) { System.out.println("Icon not found."); }
		chooseCar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(chooseCarPanel == null)
					chooseCarPanel = new ChooseCarPanel(MenuWindow.this);
				
				redCar = chooseCarPanel.redCar;
				yellowCar = chooseCarPanel.yellowCar;
				policeCar = chooseCarPanel.policeCar;
				
				chooseCar.setIcon(null);
				
				mainPanel.add(chooseCarPanel);
				setDefaultButton(redCar);
				disableButtons();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		//add the created content to the panel
		mainPanel.add(classic);
		mainPanel.add(highScoresCl);
		mainPanel.add(speedRun);
		mainPanel.add(highScoresSR);
		mainPanel.add(chooseCar);
		setDefaultButton(classic);
		
		setInputControl();
	}
	
	/**
	 * Set the car type for the game to be run.
	 *
	 * @param carType one of the car types from the Car class.
	 */
	public void setCarType(String carType){
		parent.game.setCarType(carType);
	}
	
	
	/**
	 * Set the default button in the menu.
	 *
	 * @param button the JButton to get focus.
	 */
	protected void setDefaultButton(JButton button){
		defaultButton = button;
		parent.setDefaultButton(button);
	}
	
	/**
	 * Enables the main menu buttons.
	 */
	 protected void enableButtons(){
		classic.setEnabled(true);
		speedRun.setEnabled(true);
		highScoresCl.setEnabled(true);
		chooseCar.setEnabled(true);
		highScoresSR.setEnabled(true);
	 }
	 
	 /**
	 * Disables the main menu buttons.
	 */
	 protected void disableButtons(){
		classic.setEnabled(false);
		speedRun.setEnabled(false);
		highScoresCl.setEnabled(false);
		chooseCar.setEnabled(false);
		highScoresSR.setEnabled(false);
	 }
	 
	/**
	 * Provides the JPanel of the menu.
	 *
	 * @return menu JPanel.
	 */
	public JPanel getPanel(){
		return mainPanel;
	}

	/**
	 * Set up the key events to change the focused button by pressing the arrow keys
	 */
	 private void setInputControl(){
		char[] cheatCode = new char[5];
		 
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "go left");
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "go right");
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "go up");
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "go down");
		
		mainPanel.getActionMap().put("go left", new AbstractAction("go left"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaultButton == classic)
						setDefaultButton(highScoresCl);
				else if(defaultButton == highScoresCl)
						setDefaultButton(classic);
				else if(defaultButton == speedRun)
						setDefaultButton(highScoresSR);
				else if(defaultButton == highScoresSR)
						setDefaultButton(speedRun);
				else if(defaultButton == redCar)
						setDefaultButton(policeCar);
				else if(defaultButton == yellowCar)
						setDefaultButton(redCar);
				else if(defaultButton == policeCar)
						setDefaultButton(yellowCar);
					
				//cheat code handling
				if(defaultButton == redCar || defaultButton == yellowCar || defaultButton == policeCar){
					if(cheatCode[0] == 'L'){
						cheatCode[1] = 'L';
					} else {
						cheatCode[0] = 'L';
						cheatCode[1] = '0';
						cheatCode[2] = '0';
						cheatCode[3] = '0';
						cheatCode[4] = '0';
					}
				}
			}
		});
			
		mainPanel.getActionMap().put("go right", new AbstractAction("go right"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaultButton == classic)
						setDefaultButton(highScoresCl);
				else if(defaultButton == highScoresCl)
						setDefaultButton(classic);
				else if(defaultButton == speedRun)
						setDefaultButton(highScoresSR);
				else if(defaultButton == highScoresSR)
						setDefaultButton(speedRun);
				else if(defaultButton == redCar)
					setDefaultButton(yellowCar);
				else if(defaultButton == yellowCar)
						setDefaultButton(policeCar);
				else if(defaultButton == policeCar)
						setDefaultButton(redCar);

				//cheat code handling
				if(defaultButton == redCar || defaultButton == yellowCar || defaultButton == policeCar){
					if(cheatCode[0] == 'L' && cheatCode[1] == 'L' && cheatCode[2] == 'U'){
						cheatCode[3] = 'R';
					} else {
						cheatCode[0] = '0';
						cheatCode[1] = '0';
						cheatCode[2] = '0';
						cheatCode[3] = '0';
						cheatCode[4] = '0';
					}
				}
			}
		});
		
		mainPanel.getActionMap().put("go down", new AbstractAction("go down"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaultButton == classic || defaultButton == highScoresCl)
						setDefaultButton(speedRun);
				else if(defaultButton == speedRun || defaultButton == highScoresSR)
						setDefaultButton(chooseCar);
					
				//cheat code handling
				if(defaultButton == redCar || defaultButton == yellowCar || defaultButton == policeCar){
					if(cheatCode[0] == 'L' && cheatCode[1] == 'L' && cheatCode[2] == 'U' && cheatCode[3] == 'R'){
						unlockLegacyCar();
					}
					
					cheatCode[0] = '0';
					cheatCode[1] = '0';
					cheatCode[2] = '0';
					cheatCode[3] = '0';
					cheatCode[4] = '0';
				}
			}
		});
		
		mainPanel.getActionMap().put("go up", new AbstractAction("go up"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaultButton == speedRun || defaultButton == highScoresSR)
						setDefaultButton(classic);
				else if(defaultButton == chooseCar)
						setDefaultButton(speedRun);

				//cheat code handling
				if(defaultButton == redCar || defaultButton == yellowCar || defaultButton == policeCar){
					if(cheatCode[0] == 'L' && cheatCode[1] == 'L'){
						cheatCode[2] = 'U';
					} else {
						cheatCode[0] = '0';
						cheatCode[1] = '0';
						cheatCode[2] = '0';
						cheatCode[3] = '0';
						cheatCode[4] = '0';
					}
				}
			}
		});
	 }
	 
	 /**
	  * Unlocks the legacy car and sets it as the carType.
	  */
	 private void unlockLegacyCar(){
		mainPanel.remove(chooseCarPanel);
		enableButtons();
		setDefaultButton(classic);
		setCarType("legacy");	
		try {
			chooseCar.setIcon(new ImageIcon(getClass().getResource("res/legacycar.png")));
		} catch (Exception ex) { System.out.println("Icon not found."); }
		mainPanel.revalidate();
		mainPanel.repaint();
		 
	 }
}
	 
/**
 * Inner class creating the menu for choosing a car.
 */
class ChooseCarPanel extends JPanel implements ActionListener{

	private MenuWindow parent;
	JButton redCar;
	JButton yellowCar;
	JButton policeCar;
	
	/**
	 * Constructor.
	 *
	 * @param parent the menu window to open this menu in
	 */
	public ChooseCarPanel(MenuWindow parent){
		super();
		this.parent = parent;
		
		//create the buttons
		redCar = new JButton();
		redCar.addActionListener(this);
		yellowCar = new JButton();
		yellowCar.addActionListener(this);
		policeCar = new JButton();
		policeCar.addActionListener(this);
		
		//add images to the buttons
		try {
		redCar.setIcon(new ImageIcon(getClass().getResource(Car.RED_CAR)));
		yellowCar.setIcon(new ImageIcon(getClass().getResource(Car.YELLOW_CAR)));
		policeCar.setIcon(new ImageIcon(getClass().getResource(Car.POLICE_CAR)));
		} catch (Exception ex) { System.out.println("Icon not found."); }
		
		//add the buttons to the JPanel
		add(redCar);
		add(yellowCar);
		add(policeCar);
	}
	
	/**
	 * Control the buttons: when a button is clicked set the chosen car type and close the chooseCar menu.
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == redCar){
			parent.setCarType(Car.RED_CAR);
			try {
			parent.chooseCar.setIcon(new ImageIcon(getClass().getResource(Car.RED_CAR)));
			} catch (Exception ex) { System.out.println("Icon not found."); }
		}
		else if(e.getSource() == yellowCar){
			parent.setCarType(Car.YELLOW_CAR);	
			try {
			parent.chooseCar.setIcon(new ImageIcon(getClass().getResource(Car.YELLOW_CAR)));
			} catch (Exception ex) { System.out.println("Icon not found."); }
		}
		else if(e.getSource() == policeCar){
			parent.setCarType(Car.POLICE_CAR);
			try {
			parent.chooseCar.setIcon(new ImageIcon(getClass().getResource(Car.POLICE_CAR)));
			} catch (Exception ex) { System.out.println("Icon not found."); }
		}
		
		parent.getPanel().remove(this);
		parent.enableButtons();
		parent.setDefaultButton(parent.classic);
		parent.getPanel().revalidate();
		parent.getPanel().repaint();
	}
}