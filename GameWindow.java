import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the CarRacer game window.
 */
public class GameWindow
{
	private JFrame window;
	private JPanel mainPanel;
	private Racer racer;
	private JButton playButton;
	private JButton stopButton;
	private JLabel curScore;
	private volatile boolean  startGame = false;
	
	/**
	 * Creates the game window.
	 */
	public GameWindow(){
		window = new JFrame();
        window.setTitle("Racer");
		
		//create content
		playButton = new JButton("PLAY");
		stopButton = new JButton("STOP");
		curScore = new JLabel("Score: 0");
		racer = new Racer();
		JPanel buttonPanel = new JPanel();
		mainPanel = new JPanel();
		
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
				endGame();
			}
		});
		
		//add content to the main panel
		mainPanel.add(curScore);
		mainPanel.add(racer.getPanel());
		buttonPanel.add(playButton);
		buttonPanel.add(stopButton);
		mainPanel.add(buttonPanel);
		
		//set layout
		buttonPanel.setLayout(new GridLayout(1,2));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
		
		window.getRootPane().setDefaultButton(playButton);
		window.setContentPane(mainPanel);
		window.setResizable(false);
		window.pack();
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
		playButton.setEnabled(true);
		stopButton.setEnabled(false);
		racer.stop();
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
