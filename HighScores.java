
/**
 * Controls the high scores.
 */
public class HighScores{
	private PersonalScore[] highScores = new PersonalScore[10];
	
	/**
	 * Creates the top 10 high scores.
	 */
	public HighScores(){
		highScores[0] = new PersonalScore("zabraih",2000);
		highScores[1] = new PersonalScore("John", 1800);
		highScores[2] = new PersonalScore("Jack", 1700);
		highScores[3] = new PersonalScore("Mark", 1500);
		highScores[4] = new PersonalScore("Dan", 1200);
		highScores[5] = new PersonalScore("Jane", 1100);
		highScores[6] = new PersonalScore("Steven", 1000);
		highScores[7] = new PersonalScore("Lissy", 740);
		highScores[8] = new PersonalScore("Michael", 500);
		highScores[9] = new PersonalScore("Rob", 250);
		
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
