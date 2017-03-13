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
	
	/**
	 * Constructor. Creates the top 10 high scores.
	 */
	public HighScoresControl(){
		highScores = new PersonalScore[10];
		highScoresFile = new File(".highscores");
		load();
	}
	
	private void load() {
		String name;
		int score;
		String line = null;
		String[] scoreLine;
		
		if(highScoresFile.exists()){
			try{
				reader = new BufferedReader(new FileReader(highScoresFile));
			}catch(Exception e){
				e.printStackTrace(System.out);
			}
		
			try{
				line = reader.readLine();
			}catch(IOException e){e.printStackTrace(System.out);}
		
			if(line!=null){
				for(int i = 0; i < 10; i++){
					try{
						scoreLine = line.split("-");
						name = scoreLine[0];
						score = Integer.parseInt(scoreLine[1]);
						highScores[i] = new PersonalScore(name,score);
						line = reader.readLine();
					}catch(IOException e){e.printStackTrace(System.out);}
				}
			}else{
				for(int i = 0; i < 10; i++){
					highScores[i] = new PersonalScore();
				}
			}
		}else{
			for(int i = 0; i < 10; i++){
				highScores[i] = new PersonalScore();
				}
		}
	//	highScoresFile.setWritable(false);
	}
	
	private void save(){
		if(!highScoresFile.exists()){
			try{
				highScoresFile.createNewFile();
			}catch(IOException e){}
		}
		try{
			writer = new PrintWriter(highScoresFile);
		}catch(IOException e){e.printStackTrace(System.out);}
		//highScoresFile.setWritable(true);
		for(int i = 0; i < 10; i++){
			writer.println(highScores[i].getName()+"-"+highScores[i].getScore());
		}
		writer.flush();
		writer.close();
		//highScoresFile.setWritable(false);
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
		save();
	}
}
