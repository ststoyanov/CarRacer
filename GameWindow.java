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
	private static Racer racer;
	
	/**
	 * Creates the game window.
	 */
	public GameWindow(){
		window = new JFrame();
        window.setTitle("Racer");
		
		racer = new Racer();
		
		window.setContentPane(racer.getPanel());
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
	 * Initialize the window and start the game.
	 * @param args unused
	 */
	public static void main(String[] args){
		new GameWindow();
		racer.start();
		while(racer.isPlaying()){
			racer.update();
		}
	}
}
