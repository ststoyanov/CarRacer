import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the CarRacer game window.
 */
public class GameWindow
{
	private HighScoresWin highScoresWindow = new HighScoresWin();
	private HighScores scores = new HighScores();
	private int highScoreInt = scores.getHighScore();
	private JFrame window;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel scorePanel;
	private Racer racer;
	private JButton playButton;
	private JButton stopButton;
	private JLabel curScore;
	private JLabel highScore;
	private volatile boolean  startGame = false;
	
	/**
	 * Creates the game window.
	 */
	public GameWindow(){
		window = new JFrame();
        window.setTitle("Racer");
		
		//create content
		mainPanel = new JPanel();
		buttonPanel = new JPanel();
		scorePanel = new JPanel();
		playButton = new JButton("PLAY");
		stopButton = new JButton("STOP");
		curScore = new JLabel("Score: 0");
		highScore = new JLabel("High Score: " + highScoreInt);
		racer = new Racer();

		//setting up the buttons
		stopButton.setEnabled(false);
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
		
		//add content to the main panel
		scorePanel.add(curScore);
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(highScore);
		mainPanel.add(scorePanel);
		mainPanel.add(racer.getPanel());
		buttonPanel.add(playButton);
		buttonPanel.add(stopButton);
		mainPanel.add(buttonPanel);
		
		//set layout
		scorePanel.setLayout(new BoxLayout(scorePanel,BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new GridLayout(1,2));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
		
		//finalize window
		window.getRootPane().setDefaultButton(playButton);
		window.setContentPane(mainPanel);
		window.setResizable(false);
		window.pack();
		window.setLocationByPlatform(true);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the game window with a title
	 * @param name name of the game
	 */
    public GameWindow(String name){
		this();
		window.setTitle(name);
	}
	
	/**
	 * Start the game.
	 */
	public void startGame(){
		startGame = false;
		highScoresWindow.dispose();
		playButton.setEnabled(false);
		stopButton.setEnabled(true);
		racer.start();
		while(racer.isPlaying()){
			racer.update();
			curScore.setText("Score: " + racer.getScore());
		}
		endGame();
	}
	
	/**
	 * End the game.
	 */
	public void endGame(){
		racer.stop();
		playButton.setEnabled(true);
		stopButton.setEnabled(false);
		if(racer.getScore() > highScoreInt){
			highScoreInt = racer.getScore();
			highScore.setText("High Score: " + highScoreInt);
		}
		if(racer.getScore() > scores.getLowScore())
			highScoresWindow.openAdd(scores,racer.getScore());
		else
			highScoresWindow.open(scores);

	}
	
	/**
	 * Initialize the window and run the game.
	 * @param args unused
	 */
	public static void main(String[] args){
		GameWindow game = new GameWindow();
		while(true){
			if(game.startGame)
				game.startGame();
		}
	}
}
