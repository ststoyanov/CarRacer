import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the main window of the game.
 */
public class MainWindow
{
	private static final int MENU_WIN	= 0;
	private static final int SPEED_RUN  = 1;
	
	private JFrame window;
	private static SpeedRun speedRun;
	private static MenuWindow menu;
	private static int active = MENU_WIN;
	
	/**
	 * Constructor. Creates the game window.
	 */
	public MainWindow(){
		window = new JFrame();
        window.setTitle("Racer");
		window.setIconImage(new ImageIcon(getClass().getResource("res/icon.png")).getImage());
		
		speedRun = new SpeedRun();
		openMenu();
		window.setResizable(false);
		window.setLocation(0,0);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void openMenu(){
		menu = new MenuWindow(this);
		active = MENU_WIN;
		
		window.getContentPane().removeAll();
		window.setContentPane(menu.getPanel());
		window.pack();
		window.setVisible(true);

	}
	
	public void openSpeedRun(){
		
		active = SPEED_RUN;
		
		window.getContentPane().removeAll();
		window.setContentPane(speedRun.getPanel());
		window.getRootPane().setDefaultButton(speedRun.getDefaultButton());
		window.pack();
		window.setVisible(true);
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
	 * Initialize the window and run the game.
	 * @param args unused
	 */
	public static void main(String[] args){
		new MainWindow();
		while(true)
			if(speedRun.startGame)
				speedRun.startGame();
	}
}
