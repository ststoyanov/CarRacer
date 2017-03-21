import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the main window of the game.
 */
public class MainWindow
{
	private JFrame window;
	JLayeredPane mainPanel;
	
	public static GameWindow game;
	public static MenuWindow menu;
	
	/**
	 * Constructor; creates the game window.
	 */
	public MainWindow(){
		window = new JFrame();
		mainPanel = new JLayeredPane();
		
		//Add title and icon to the game
        window.setTitle("Racer");
		try{
			ImageIcon icon = new ImageIcon(getClass().getResource("res/icon.png"));
			window.setIconImage(icon.getImage());
		} catch (Exception e) { System.out.println("Icon not found."); }
		
		//open the game in the background
		game = new GameWindow(this);
		game.getPanel().setBounds( 0, 0,  Racer.SCREEN_WIDTH, Racer.SCREEN_HEIGHT );
		mainPanel.add(game.getPanel(),JLayeredPane.DEFAULT_LAYER);
		
		//open the menu
		menu = new MenuWindow(this);
		menu.getPanel().setBounds( 300, 200,  220, 200 );
		mainPanel.add(menu.getPanel(),JLayeredPane.PALETTE_LAYER);
		
		//finalize and show window
		mainPanel.setPreferredSize(new Dimension(800, 600));
		window.setContentPane(mainPanel);
		window.setResizable(false);
		window.setLocation(0,0);
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Constructor; creates the game window with a given title
	 *
	 * @param name name of the game
	 */
    public MainWindow(String name){
		this();
		window.setTitle(name);
	}
	
	/**
	 * Disable the game window and open the menu.
	 */
	public void openMenu(){
		//disable the game
		game.disableButtons();
		game.setMode(0);
		
		//open the menu
		mainPanel.add(menu.getPanel(),JLayeredPane.PALETTE_LAYER);
		window.revalidate();
		window.repaint();
	}
	
	/**
	 * Close the menu window and enable the game.
	 *
	 * @param gameMode the game mode to start
	 */
	public void openGame(int gameMode){
		//close the menu
		mainPanel.remove(0);
		
		//enable the game
		game.enableButtons();
		game.setMode(gameMode);
		window.getRootPane().setDefaultButton(game.getDefaultButton());
		window.revalidate();
		window.repaint();
	}

	/**
	 * Sets the button which can be used by pressing the Enter key.
	 *
	 * @param button the button to be used with Enter
	 */
	public void setDefaultButton(JButton button){
		window.getRootPane().setDefaultButton(button);
	}
	
	/**
	 * Initialize the window and run the game.
	 *
	 * @param args unused
	 */
	public static void main(String[] args){
		new MainWindow();
		while(true)
			if(game.startGame)
				game.startGame();
	}
}
