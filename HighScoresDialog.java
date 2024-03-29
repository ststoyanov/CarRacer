import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.regex.Pattern;

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
	private void createPanel(int newScore){
		//set up the panels and their layouts
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.LINE_AXIS));
		mainPanel.setPreferredSize(new Dimension(160,185));
		
		JPanel placePanel = new JPanel();
		placePanel.setLayout(new GridLayout(11,1));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(11,1));
		
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(11,1));
		
		//add the title row
		placePanel.add(new JLabel("No."));
		namePanel.add(new JLabel("         NAME"));
		scorePanel.add(new JLabel("SCORE"));
		
		for(int i=0; i<10; i++){
			placePanel.add(new JLabel(Integer.toString(i+1)+"."));
		}
		
		//if no new score, show the current top 10
		if(newScore <= 0){
			focus = false;
			for(int i = 0;i < 10;i++){
				namePanel.add(new JLabel(scores.getName(i)));
				scorePanel.add(new JLabel("  "+Integer.toString(scores.getScore(i))));
			}
		}
		
		//if there is a new score add it and let the user add his name to it
		else{
			focus = true;
			queuedScore = newScore;
			JPanel newNamePanel = new JPanel();
			newNamePanel.setLayout(new BoxLayout(newNamePanel,BoxLayout.LINE_AXIS));
			newName = new JTextField(9);
			((AbstractDocument) newName.getDocument()).setDocumentFilter(new CustomDocumentFilter());
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
				namePanel.add(new JLabel(scores.getName(i)));
				scorePanel.add(new JLabel("  "+Integer.toString(scores.getScore(i))));
			}
			
			//display the new score with a space to assign a name to it
			newNamePanel.add(newName);
			newNamePanel.setPreferredSize(new Dimension(100, 5));
			namePanel.add(newNamePanel);
			scorePanel.add(new JLabel("  "+Integer.toString(newScore)));
			
			//display the scores after the new one
			for(int i = scorePlace+1;i<10;i++){
				namePanel.add(new JLabel(scores.getName(i)));
				scorePanel.add(new JLabel("  "+Integer.toString(scores.getScore(i))));
			}
			
			//when a name is typed and Enter is pressed finilize the field and save the score
			newName.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					if(!newName.getText().isEmpty()){
						scores.setHighScore(scorePlace,newName.getText(),newScore);
						queuedScore = -1;
						newNamePanel.remove(newName);
						newNamePanel.add(new JLabel(scores.getName(scorePlace)));
						newNamePanel.revalidate();
						newNamePanel.repaint();
						dialog.getParent().requestFocus();
					}
				}
			});
		}
		
		//add the objects to the mainPanel and shape it
		mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		mainPanel.add(placePanel);
		mainPanel.add(Box.createRigidArea(new Dimension(3, 0)));
		mainPanel.add(namePanel);
		mainPanel.add(Box.createHorizontalGlue());
		mainPanel.add(scorePanel);
		mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	}
	
	
	/**
	 * Dispose of the window if there is one and save the scores
	 * if there is a queued score for adding, add it with name "NoName".
	 */
	public void dispose(){
		if(active){
			if(queuedScore>0){
				scores.setHighScore(scorePlace,newName.getText(),queuedScore);
				queuedScore = -1;
			}
			try{
				scores.save();
			}catch(Exception ex){ ex.printStackTrace(System.out); }
			dialog.dispose();
			active = false;
		}
	}

	// Filter for the newName text field. Only alphabetical,numerical ,"_" and "-" characters can be entered up to 9 characters.
	private class CustomDocumentFilter extends DocumentFilter{
		private Pattern regexCheck = Pattern.compile("[A-Za-z0-9_-]+");
		private int maxChars = 9;
		
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attrs) throws BadLocationException{
			if (string == null){
				return;
			}
			
			if(regexCheck.matcher(string).matches()&&(fb.getDocument().getLength() + string.length()) <= maxChars) {
				super.insertString(fb,offset,string,attrs);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
		
		@Override
		public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException{
			if(string == null) {
				return;
			}
			
			if(regexCheck.matcher(string).matches()&&(fb.getDocument().getLength() + string.length() - length) <= maxChars){
				fb.replace(offset,length,string,attrs);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}