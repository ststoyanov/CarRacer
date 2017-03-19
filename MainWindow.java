import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Models the main window of the game.
 */
public class MainWindow
{
	private JFrame window;
	JLayeredPane layeredPanel;
	private static GameWindow game;
	private static MenuWindow menu;
	ImageIcon icon;
	private static int gameMode = 0;
	
	/**
	 * Constructor. Creates the game window.
	 */
	public MainWindow(){
		window = new JFrame();
		layeredPanel = new JLayeredPane();
		
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
		
		//open the game in the background
		game = new GameWindow(this);
		game.getPanel().setBounds( 0, 0,  Racer.SCREEN_WIDTH, Racer.SCREEN_HEIGHT );
		layeredPanel.add(game.getPanel(),JLayeredPane.DEFAULT_LAYER);
		
		openMenu();
		
		//finalize and show window
		layeredPanel.setPreferredSize(new Dimension(800, 600));
		window.setContentPane(layeredPanel);
		window.setResizable(false);
		window.setLocation(0,0);
		window.pack();
		window.setVisible(true);
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
	 * Disable the game window and open the menu.
	 */
	public void openMenu(){
		game.disableButtons();
		
		menu = new MenuWindow(this);
		
		menu.getPanel().setBounds( 300, 200,  200, 200 );
		layeredPanel.add(menu.getPanel(),JLayeredPane.PALETTE_LAYER);
		window.getRootPane().setDefaultButton(menu.getDefaultButton());
		
		window.revalidate();
		window.repaint();
		
		//set up the key events
		//change the focused button by pressing the arrow keys
		menu.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "change button");
		menu.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "change button");
		menu.getPanel().getActionMap().put("change button", new AbstractAction("change button"){
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getRootPane().setDefaultButton(menu.getNextButton());
			}
		});
	}
	
	/**
	 * Close the menu window and enable the game.
	 */
	public void openGame(int gameMode){
		this.gameMode = gameMode;
		game.enableButtons();
		layeredPanel.remove(0);
		window.getRootPane().setDefaultButton(game.getDefaultButton());
		window.revalidate();
		window.repaint();
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
