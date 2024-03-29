import javax.swing.*;
/**
 * uses the GameArena APIs to implement a simple top down racing game.
 *
 * The graphical output of the game is provided as a Swing component,
 * so that it can be added into any Swing application, just like a JButton etc.
 *
 * To allow users to control the game as they see fit, start(), stop() and update()
 * methods are provided. start() should be used to create a new game, stop() to terminate
 * a running game, and update() should be called in a loop to update gameplay and graphics
 *
 * Simple example of use:
 *
 * <pre>
 *
 *  JFrame window = new JFrame();
 *  Racer r = new Racer();
 *  window.setTitle("Racer");
 *  window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
 *  window.setContentPane(r.getPanel());
 *  window.setVisible(true);
 *
 *  r.start();
 *
 *  while(r.isPlaying())
 *      r.update();
 *
 * </pre>
 *
 * @author Joe Finney (joe@comp.lancs.ac.uk)
 */
public class Racer 
{
	//static variables
    public static final double PLAYER_SPEED = 2.5;
    public static final int ROAD_SEGMENT_WIDTH = 200;
    public static final int ROAD_SEGMENT_HEIGHT = 4;
    public static final int ROAD_CURVE_SPEED = 2;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
	public static final int ROAD_LEFT_BOUND = 300;
	public static final int ROAD_RIGHT_BOUND = 600;
	public static final double INITIAL_SPEED = 1.5;
	public static final int LEVEL_UP_SCORE = 500;
	
	//static game modes
	public static final int CLASSIC = 1;
	public static final int SPEED_RUN = 2;

    private GameArena arena;
    private Car player;
    private RoadSegment[] road = new RoadSegment[SCREEN_HEIGHT / ROAD_SEGMENT_HEIGHT + 1];
	private Ball coin;
	
	private String carType = Car.RED_CAR;
    private double currentRoadX = SCREEN_WIDTH/2;
    private double speed = INITIAL_SPEED;
    private boolean playing = false;
	private boolean obstFlag = false;
    private int score = 0;
	private int curveDirection = 0; //-1 for left, 1 for right
	private double randProb = 0; // variable for controling the probability of random event
	private int gameMode = 0;
	public int coins = 0;
	

    /**
     * Creates a new instance of the Racer racing game.
     */
    public Racer()
    {
        arena = new GameArena(SCREEN_WIDTH, SCREEN_HEIGHT, false);
    }

    /**
     * Provides a Swing component in which the Racer game runs.
     * This component can be added to a Swing panel to display the game on screen.
     *
     * @return A Swing component for this game.
     */
    public JComponent getPanel()
    {
        return arena.getPanel();
    }

    /**
     * Provides the player's current score in the game.
     * @return The player's current score.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Starts a new game in a chosen game mode, if the game is not already running.
	 *
	 * @param gameMode the game mode to be started (Racer.SPEED_RUN or Racer.CLASSIC).
     */
    public void start(int gameMode)
    {
        if(!playing)
        {
			//set the game mode
			this.gameMode = gameMode;
			
            // Create the player's car
            player = new Car(carType,SCREEN_WIDTH/2, SCREEN_HEIGHT - 100, 40,70, arena);
			
			currentRoadX = SCREEN_WIDTH/2;
			
            // Create the initial road layout
            for (int s = road.length-1; s >= 0 ; s--)
            {
                road[s] = new RoadSegment(currentRoadX, -ROAD_SEGMENT_HEIGHT, ROAD_SEGMENT_WIDTH, ROAD_SEGMENT_HEIGHT+0.5, arena);
                road[s].setYPosition(s*ROAD_SEGMENT_HEIGHT);
				road[s].setYSpeed(INITIAL_SPEED);
            }
			
			//reset game variables
			curveDirection = 1;
			speed = INITIAL_SPEED;
            score = 0;
            playing = true;
        }
    }

    /**
     * Stops a currently running game.
     */
    public void stop()
    {
        if(playing)
        {
            playing = false;
            arena.exit();
        }
    }

    /**
     * Determines if the game is currently being played.
     *
     * @return false if the game has not been started or on game over, true if the game is actively running.
     */
    public boolean isPlaying()
    {
        return playing;
    }

    /**
     * Updates the game state to allow the road and player character to move on the screen.
     *
     * This method should be called in a loop (once per frame) to advance gameplay in response to time
     * and user input. The method uses the GameArena pause() method to ensure the game runs at a constant speed.
     */
    public void update()
    {
        if(playing)
        {
			score++;
			
			//depending on the game mode control the difficulty 
			if(gameMode == SPEED_RUN){
				if(score%500 == 0)
					speedUp(0.5);
			} else {
				if(score%1000 == 0)
					speedUp();
			}

			//move the car left or right
            double speed = 0;
            if (arena.leftPressed())
                speed -= PLAYER_SPEED + this.speed/5;

            if (arena.rightPressed())
                speed += PLAYER_SPEED + this.speed/5;

            player.setXSpeed(speed);

            player.move();
			
			//go forward
            for (int i=0; i<road.length; i++)
            {
                if (road[i] != null)
                    road[i].move();
            }

            // Recycle any segments that have crolled off screen...
            recycleRoadSegments();

            if (hasCrashed())
                stop();
        }

        arena.pause();
    }
	
    /**
     * Provides a randomly generated, thin slice of road. 
     * This method is used periodically to create new road on the screen in front of the player's car.
     *
     * @return A new randomly generated RoadSegment
     */
    private RoadSegment nextRoadSegment()
    {
		//depending on game mode create an according road segment
		if(gameMode == SPEED_RUN){
			//checks if the road is out of bounds and if so changes the direction
			//also randomly changes the direction with increasing chance the longer it hasn't changed
			if(currentRoadX <= ROAD_LEFT_BOUND || currentRoadX >= ROAD_RIGHT_BOUND || Math.random() < randProb){
				if(curveDirection == 1 && currentRoadX > ROAD_LEFT_BOUND){
					curveDirection = -1;
				} else if(currentRoadX < ROAD_RIGHT_BOUND){
					curveDirection = 1;
				}
				randProb = 0;
			} else {
				randProb += 1.0 / (SCREEN_HEIGHT / ROAD_SEGMENT_HEIGHT * (10 * ROAD_CURVE_SPEED));
			}
			
			currentRoadX += Math.random() * ROAD_CURVE_SPEED * curveDirection;
		} else if(gameMode == CLASSIC) {
			//add obstacles on random intervals
			//chance of obstacle appearing increases the longer there hasn't been one
			if(Math.random() < randProb){
					obstFlag = true;
					randProb = -1;
			}
			randProb += 0.01;
		}
		
        RoadSegment s = new RoadSegment(currentRoadX, -ROAD_SEGMENT_HEIGHT, ROAD_SEGMENT_WIDTH, ROAD_SEGMENT_HEIGHT+0.5, arena);
		
		//add random obstacle if needed
		if(obstFlag){
			s.addObstacle((int)(Math.round(Math.random()*4)%4));
			obstFlag = false;
		}
		
        s.setYSpeed(speed);
        return s;
    }

    /**
     * Removes any parts of road that have scrolled off the bottom of the screen.
     */
    private void recycleRoadSegments()
    {
        for (int i=0; i<road.length; i++)
        {
            if (road[i].getYPosition() > SCREEN_HEIGHT)
            {
                double y = road[i].getYPosition();
                road[i].remove();
                road[i] = nextRoadSegment(); 
                road[i].setYPosition(y - SCREEN_HEIGHT - ROAD_SEGMENT_HEIGHT);
            }
        }
    }

    /**
     * Determines if the player has crased (driven off road)
     *
     * @return true is the player is touching the kerb/grass, false otherwise.
     */
    private boolean hasCrashed()
    {
        for (int i=0; i<road.length; i++)
        {
            if (player.isTouching(road[i]))
                return true;
        }

        return false;
    }
	
	/**
	 * Set the car type for the game.
	 *
	 * @param carType type of car (static variable of Car class)
	 */
	public void setCarType(String carType){
		this.carType = carType;
	}
	
	/**
	 * Speeds up the game by a given value.
	 *
	 * @param up speed up by that much.
	 */
	private void speedUp(double up){
		speed += up;
		for(int i=0; i<road.length; i++)
		{
			road[i].setYSpeed(speed);
		}
	}
	
	/**
	 * Speeds up the game by 0.5.
	 */
	private void speedUp(){
		speedUp(1);
	}

	/**
     * A simple example of usage
     *
     * @param args unused.
     */
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        Racer r = new Racer();

        window.setTitle("Racer");
        window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        window.setContentPane(r.getPanel());
        window.setVisible(true);

        r.start(SPEED_RUN);

        while(r.isPlaying())
            r.update();
    }
}
