import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the CarRacer game window and controls the game.
 */
public class GameWindow
{
	private HighScoresDialog highScoresDialog;
	private HighScoresControl scores;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel scorePanel;
	private Racer racer;
	private JButton playButton;
	private JButton stopButton;
	private JButton backButton;
	private JLabel curScore;
	public JLabel modeName;
	private JLabel highScore;
	public volatile boolean  startGame = false;
	private JButton defaultButton;
	private MainWindow parent;
	private int gameMode = 0;
	
	/**
	 * Constructor.
	 *
	 * @param parent the parent window of the game.
	 */
	public GameWindow(MainWindow parent){
		this.parent = parent;
		createAndShowGUI();
	}

	/**
	 * Models the GUI.
	 */ 
	private void createAndShowGUI(){
		//create content
		mainPanel = new JPanel();
		buttonPanel = new JPanel();
		scorePanel = new JPanel();
		playButton = new JButton("PLAY");
		stopButton = new JButton("STOP");
		backButton = new JButton("Back to menu");
		curScore = new JLabel("Score: 0");
		modeName = new JLabel("");
		scores = new HighScoresControl();
		highScore = new JLabel("High Score: " + scores.getHighScore());
		racer = new Racer();

		//setting up the buttons
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				startGame = true;
			}
		});
		stopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				racer.stop();
			}
		});
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(highScoresDialog != null)
					highScoresDialog.dispose();
				parent.openMenu();
			}
		});
		
		//add content to the main panel
		scorePanel.add(curScore);
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(modeName);
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(highScore);
		mainPanel.add(scorePanel);
		mainPanel.add(racer.getPanel());
		buttonPanel.add(playButton);
		buttonPanel.add(backButton);
		mainPanel.add(buttonPanel);
		disableButtons();
		
		//set layout
		scorePanel.setLayout(new BoxLayout(scorePanel,BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new GridLayout(1,2));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
		
		setDefaultButton(playButton);
	}
	
	/**
	 * Provides the JPanel of the menu.
	 *
	 * @return menu JPanel.
	 */
	public JPanel getPanel(){
		return mainPanel;
	}
	
	public void enableButtons(){
		playButton.setEnabled(true);
		backButton.setEnabled(true);
	}
	
	public void disableButtons(){
		playButton.setEnabled(false);
		backButton.setEnabled(false);
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
	 * Start the game.
	 */
	public void startGame(int gameMode){
		startGame = false;
		if(this.gameMode != gameMode)
			setMode(gameMode);
		racer.getPanel().requestFocusInWindow();
		
		if(highScoresDialog != null)
			highScoresDialog.dispose();
		
		//remodel the button panel
		playButton.setEnabled(false);
		buttonPanel.remove(backButton);
		buttonPanel.add(stopButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		
		//play the game
		racer.start(gameMode);
		while(racer.isPlaying()){
			racer.update();
			curScore.setText("Score: " + racer.getScore());
		}
		endGame();
	}
	
	public void startGame(){
		this.startGame(gameMode);
	}
	
	public void setMode(int gameMode){
		this.gameMode = gameMode;
		if(gameMode == Racer.SPEED_RUN)
			modeName.setText("Speed Run");
		else if(gameMode == Racer.CLASSIC)
			modeName.setText("Classic");
		else 
			modeName.setText("");
	}
	/**
	 * End the game and display high scores.
	 */
	public void endGame(){
		racer.stop();
		playButton.setEnabled(true);
		buttonPanel.remove(stopButton);
		buttonPanel.add(backButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		
		//Refresh the high score
		if(racer.getScore() > scores.getHighScore()){
			highScore.setText("High Score: " + racer.getScore());
		}
		
		//open the high scores
		if(racer.getScore() > scores.getLowScore())
			highScoresDialog = new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel),scores,racer.getScore());
		else
			highScoresDialog = new HighScoresDialog((JFrame) SwingUtilities.getWindowAncestor(mainPanel),scores,-1);
	}
}
