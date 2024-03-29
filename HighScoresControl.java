import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

/**
 * Controls the high scores and the .highscores file.
 */
public class HighScoresControl{
	private PersonalScore[] highScores;
	private File highScoresFile;
	private BufferedReader reader;
	private PrintWriter writer;
	private final String fileType = ".highscores";
	
	/**
	 * Constructor. Loads the high scores.
	 *
	 * @param filePath path of the high scores file without type
	 */
	public HighScoresControl(String filePath){
		highScores = new PersonalScore[10];
		highScoresFile = new File(filePath+fileType);
		try{
			load();
		}catch(IOException ex){  System.out.println(ex.getMessage());}
	}
	
	/**
	 * Load the high scores value from the .highscores file.
	 * If no such file or bad values fill up the top 10 with empty high scores.
	 */
	public void load() throws IOException {
		String name;
		int score;
		int lineNumber = 0;
		String line = null;
		String[] scoreLine;
		boolean fileError = false;
		Pattern regexScore = Pattern.compile("[A-Za-z0-9_-]{1,9}+=+[0-9]+");
		
		//If the file exists read it line by line and load the high scores into the game
		//Else fill up the top 10 with empty high scores
		if(highScoresFile.exists()){
			reader = new BufferedReader(new FileReader(highScoresFile));
			while((line = reader.readLine()) != null){
				if(!regexScore.matcher(line).matches()){
					fileError = true;
					break;
				}
				scoreLine = line.split("=");
				name = scoreLine[0];
				score = Integer.parseInt(scoreLine[1]);
				highScores[lineNumber] = new PersonalScore(name,score);
				lineNumber++;
				if(lineNumber > 10){
					break;
				}
			}
		
			if(lineNumber != 10)
				fileError = true;
			
			reader.close();
		} else {
			fileError = true;
		}
		
		//If the .highscores file is corrupted or missing fill up the top 10 with empty high scores
		if(fileError){
			highScores[0] = new PersonalScore("John_Doe",3000);
			highScores[1] = new PersonalScore("John_Doe",2500);
			highScores[2] = new PersonalScore("John_Doe",2200);
			highScores[3] = new PersonalScore("John_Doe",2000);
			highScores[4] = new PersonalScore("John_Doe",1800);
			highScores[5] = new PersonalScore("John_Doe",1500);
			highScores[6] = new PersonalScore("John_Doe",1250);
			highScores[7] = new PersonalScore("John_Doe",1100);
			highScores[8] = new PersonalScore("John_Doe",1000);
			highScores[9] = new PersonalScore("John_Doe",800);			
			throw(new IOException("Corrupted or missing .highscores file"));
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
			writer.println(highScores[i].getName()+"="+highScores[i].getScore());
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
