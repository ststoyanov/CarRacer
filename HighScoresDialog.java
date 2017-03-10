import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Creates a high score dialog, displaying the top 10 scores.
 * Adds a new high score if there is one and lets the user assign his name to it.
 */
public class HighScoresDialog{
	private JDialog dialog;
	private JPanel mainPanel;
	private JTextField newName;
	private HighScoresControl scores;
	private int scorePlace;
	private int queuedScore = -1;
	private boolean focus;
	private boolean active = false;
	
	/**
	 * Constructor. Creates a HighScoresDialog with the top 10 high scores stored 
	 * and adds a new one. (If new score is less than 0, no new scores are added)
	 * @param frame the frame owner of the dialog
	 * @param scores list of the current top 10 high scores
	 * @param newScore the new high score to be added
	 */
	public HighScoresDialog(JFrame frame, HighScoresControl scores, int newScore){
		this.scores = scores;
		createPanel(newScore);
		createDialog(frame);
	}
	
	/**
	 * Constructor. Creates a HighScoresDialog with the top 10 high scores stored 
	 * @param frame the frame owner of the dialog
	 * @param scores list of the current top 10 high scores
	 */
	public HighScoresDialog(JFrame frame, HighScoresControl scores){
		this(frame,scores,-1);
	}
	
	/**
	 * Creates the high scores dialog.
	 * @param frame the frame owner of the dialog
	 */
	private void createDialog(JFrame frame){
		dialog = new JDialog(frame,"High Scores");
		active = true;
		

		dialog.setContentPane(mainPanel);
		dialog.setLocationRelativeTo(frame);
		dialog.setResizable(false);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		dialog.pack();
		dialog.setFocusableWindowState(focus);
		dialog.setVisible(true);
	}
	
	/**
	 * Create the panel displaying the high scores.
	 * Add new high score if necessary
	 * @param newScore the new high score
	 */
	public void createPanel(int newScore){
		//set up the panel and it's layout
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10,2));
		mainPanel.setPreferredSize(new Dimension(150,150));
		
		//if no new score, show the current top 10
		if(newScore <= 0){
			focus = false;
			for(int i = 0;i < 10;i++){
				mainPanel.add(new JLabel(scores.getName(i)));
				mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
			}
		}
		
		//if there is a new score add it and let the user add his name to it
		else{
			focus = true;
			queuedScore = newScore;
			newName = new JTextField();
			newName.requestFocusInWindow();
			
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

			//display the scores before the new one
			for(int i = 0;i < scorePlace;i++){
				mainPanel.add(new JLabel(scores.getName(i)));
				mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
			}
			
			//display the new score with a space to assign a name to it
			mainPanel.add(newName);
			mainPanel.add(new JLabel(Integer.toString(newScore)));
			
			//display the scores after the new one
			for(int i = scorePlace+1;i<10;i++){
				mainPanel.add(new JLabel(scores.getName(i)));
				mainPanel.add(new JLabel(Integer.toString(scores.getScore(i))));
			}
			
			//when a name is typed and Enter is pressed finilize the field and save the score
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
		}
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
			dialog.dispose();
			active = false;
		}
	}
}