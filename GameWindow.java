import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the CarRacer game window.
 */
public class GameWindow
{
	private HighScoresDialog highScoresDialog;
	private HighScoresControl scores;
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
	 * Constructor. Creates the game window.
	 */
	public GameWindow(){
		window = new JFrame();
        window.setTitle("Racer");
		window.setIconImage(new ImageIcon(getClass().getResource("res/icon.png")).getImage());
		//create content
		mainPanel = new JPanel();
		buttonPanel = new JPanel();
		scorePanel = new JPanel();
		playButton = new JButton("PLAY");
		stopButton = new JButton("STOP");
		curScore = new JLabel("Score: 0");
		scores = new HighScoresControl();
		highScore = new JLabel("High Score: " + scores.getHighScore());
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
		window.setLocation(0,0);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Constructor. Creates the game window with a given title
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
		racer.getPanel().requestFocusInWindow();
		if(highScoresDialog != null)
			highScoresDialog.dispose();
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
		
		if(racer.getScore() > scores.getHighScore()){
			highScore.setText("High Score: " + racer.getScore());
		}
		
		if(racer.getScore() > scores.getLowScore())
			highScoresDialog = new HighScoresDialog(window,scores,racer.getScore());
		else
			highScoresDialog = new HighScoresDialog(window,scores,-1);
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
