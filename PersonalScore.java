
/**
 * Contains a single personal high score with name and score.
 */
public class PersonalScore{
	private String name;
	private int score;
	
	/**
	 * Creates a personal score with given name and score.
	 * @param name the name of the scorer
	 * @param score the score
	 */
	public PersonalScore(String name,int score){
		this.name = name;
		this.score = score;
	}
	
	/**
	 * Creates an empty score with name "empty" and score 0
	 */
	public PersonalScore(){
		this("empty",0);
	}
	
	/**
	 * Gets the scorer's name
	 * @return the scorer's name 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the score
	 * @return the score
	 */
	public int getScore(){
		return score;
	}
}
