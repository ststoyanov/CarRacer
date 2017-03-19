import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the main window of the game.
 */
public class MainWindow
{
	private JFrame window;
	private static GameWindow game;
	private static MenuWindow menu;
	ImageIcon icon;
	private static int gameMode = 0;
	
	/**
	 * Constructor. Creates the game window.
	 */
	public MainWindow(){
		window = new JFrame();
		
		//Add title and image to the game
        window.setTitle("Racer");
		try{
		icon = new ImageIcon(getClass().getResource("res/icon.png"));
		} catch (Exception e) { 
			System.out.println("Icon not found."); 
		}
		if(icon != null){
			window.setIconImage(icon.getImage());
		}
		
		game = new GameWindow(this);
		
		openMenu();
		
		window.setResizable(false);
		window.setLocation(0,0);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Constructor. Creates the game window with a given title
	 * @param name name of the game
	 */
    public MainWindow(String name){
		this();
		window.setTitle(name);
	}
	
	/**
	 * Set the main window to present the menu.
	 */
	public void openMenu(){
		menu = new MenuWindow(this);
		window.getRootPane().setDefaultButton(menu.getDefaultButton());
		window.getContentPane().removeAll();
		window.setContentPane(menu.getPanel());
		window.pack();
		window.setVisible(true);

	}
	
	/**
	 * Set the main menu to present the game.
	 */
	public void openGame(int gameMode){
		this.gameMode = gameMode;
		
		window.getContentPane().removeAll();
		window.setContentPane(game.getPanel());
		window.getRootPane().setDefaultButton(game.getDefaultButton());
		window.pack();
		window.setVisible(true);
	}

	/**
	 * Initialize the window and run the game.
	 * @param args unused
	 */
	public static void main(String[] args){
		new MainWindow();
		while(true)
			if(game.startGame)
				game.startGame(gameMode);
	}
}
