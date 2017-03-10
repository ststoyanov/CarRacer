import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the highscores windows and the highscores.
 */
public class HighScoresWin{
	private JFrame window;
	private JPanel mainPanel;
	private JTextField newName;
	private int scorePlace;
	private int queuedScore;
	private HighScores scores;
	boolean active = false;
	
	/**
	 * Creates an unfinished high scores window.
	 */
	private void createWindow(){
		window = new JFrame("High Scores");
		window.setContentPane(mainPanel);
		window.setLocationByPlatform(true);
		window.pack();
		active = true;
	}
	
	/**
	 * Open the high scores in the window and set appropriate properties to it.
	 * @param scores list of the top 10 high scores
	 */
	public void open(HighScores scores){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10,2));
		
		for(int i = 0;i < 10;i++){
			mainPanel.add(new JLabel(scores.getName(i)));
			mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
		}
		
		createWindow();
		
		window.setFocusableWindowState(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Open the high scores in the window, set the appropriate properties to it and add a new highscore with a space to assign a name to it.
	 * @param scores list of the top 10 high scores
	 * @param newScore the score to be added
	 */
	public void openAdd(HighScores scores, int newScore){
		this.queuedScore = newScore;
		this.scores = scores;
		
		//add the score at it's place and rearange the rest of the scores
		for(int i=0;i<10;i++){
			if(newScore > scores.getScore(i)){
				scorePlace = i;
				for(int j = 9;j>i;j--){
					scores.setHighScore(j,scores.getName(j-1),scores.getScore(j-1));
				}
			break;
			}
		}
		
		mainPanel = new JPanel();
		newName = new JTextField();
		
		mainPanel.setLayout(new GridLayout(10,2));
		
		//display the scores before the new one
		for(int i = 0;i < scorePlace;i++){
			mainPanel.add(new JLabel(scores.getName(i)));
			mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
		}
		
		//display the new score with a space to assign a name to it
		mainPanel.add(newName);
		mainPanel.add(new JLabel(Integer.toString(newScore)));
		
		//display the score after the new one
		for(int i = scorePlace+1;i<10;i++){
			mainPanel.add(new JLabel(scores.getName(i)));
			mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
		}
		
		//when the name is entered finilize the field and add save the score
		newName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(!newName.getText().isEmpty()){
					scores.setHighScore(scorePlace,newName.getText(),newScore);
					queuedScore = -1;
					mainPanel.remove(newName);
					mainPanel.add(new JLabel(scores.getName(scorePlace)),scorePlace*2);
					mainPanel.revalidate();
					mainPanel.repaint();
				}
			}
		});
		
		//if the window is closed without adding a name assign name "Anonymous" to the new score
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose();
			}
		});
		
		//finilize window
		createWindow();
		window.setFocusableWindowState(true);
		window.setVisible(true);
		newName.requestFocusInWindow();
	}
	
	/**
	 * Dispose of the window if there is one
	 * if there is a queued score for adding, add it with name "Anonymous".
	 */
	public void dispose(){
		if(active){
			if(queuedScore>0){
				scores.setHighScore(scorePlace,"Anonymous",queuedScore);
				queuedScore = -1;
			}
			window.dispose();
			active = false;
		}
	}
}