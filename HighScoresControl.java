import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Controls the high scores.
 */
public class HighScoresControl{
	private PersonalScore[] highScores;
	File highScoresFile;
	BufferedReader reader;
	PrintWriter writer;
	final String filePath = ".highscores";
	
	/**
	 * Constructor. Loads the high scores.
	 */
	public HighScoresControl(){
		highScores = new PersonalScore[10];
		highScoresFile = new File(filePath);
		try{
			load();
		}catch(IOException ex){ ex.printStackTrace(System.out); }
	}
	
	/**
	 * Load the high scores value from the .highscores file.
	 * If no such file or bad values fill up the top 10 with empty high scores.
	 */
	public void load() throws IOException {
		String name;
		int score;
		int n = 0;
		String line = null;
		String[] scoreLine;
		
		//If the file exists read it line by line and load the high scores into the game
		//Else fill up the top 10 with empty high scores
		if(highScoresFile.exists()){
			reader = new BufferedReader(new FileReader(highScoresFile));
			while((line = reader.readLine()) != null){
				scoreLine = line.split("-");
				name = scoreLine[0];
				score = Integer.parseInt(scoreLine[1]);
				highScores[n] = new PersonalScore(name,score);
				n++;
				if(n > 9)
					break;
			}
			//If the .highscores file is corrupted fill up the top 10 with empty high scores
			if(n < 10 || reader.readLine() != null){
				for(int i = 0; i < 10; i++){
					highScores[i] = new PersonalScore();
				}
			}
		}else{
			for(int i = 0; i < 10; i++){
				highScores[i] = new PersonalScore();
			}
		}
	}
	
	/**
	 * Saves the top 10 high scores into the .highscores file.
	 * Creates the file if it does not exist.
	 */
	public void save() throws IOException{
		if(!highScoresFile.exists()){
			highScoresFile.createNewFile();
		}
		highScoresFile.setWritable(true);
		writer = new PrintWriter(highScoresFile);
		
		for(int i = 0; i < 10; i++){
			writer.println(highScores[i].getName()+"-"+highScores[i].getScore());
		}
		
		writer.flush();
		writer.close();
		
		highScoresFile.setWritable(false);
	}
	
	/**
	 * Get the highest score
	 * @return highest score
	 */
	public int getHighScore(){
		return highScores[0].getScore();
	}
	
	/**
	 * Get the lowest high score.
	 * @return lowest high score
	 */
	public int getLowScore(){
		return highScores[9].getScore();
	}
	
	/**
	 * Get the name of the scorer of a the high score on a certain position
	 * @param place position of the score
	 * @return name of scorer
	 */
	public String getName(int place){
		return highScores[place].getName();
	}
	
	/**
	 * Get the score at a certain position
	 * @param place position of the score
	 * @return number of points scored
	 */
	public int getScore(int place){
		return highScores[place].getScore();
	}
	
	/**
	 * Set the name of the scorer and the number of point of a high score at a certain position.
	 * @param place positon of score
	 * @param name name of the scorer
	 * @param score number of points scored
	 */
	public void setHighScore(int place, String name, int score){
		highScores[place] = new PersonalScore(name,score);
	}
}
