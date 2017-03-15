import javafx.scene.image.Image;
/**
 * Loads a sprite to be used in the GameArena. 
 * This class represents a Sprite object. When combined with the GameArena class,
 * instances of the Sprite class can be displayed on the screen.
 */
public class Sprite extends Image
{	
	private double xPosition;			// The X coordinate of centre of this Sprite
	private double yPosition;			// The Y coordinate of centre of this Sprite
	private double width;				// The width of this Sprite
	private double height;				// The height of this Sprite

	/**
	 * Obtains the current position of this Sprite.
	 * @return the X coordinate of this Sprite within the GameArena.
	 */
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Sprite.
	 * @return the Y coordinate of this Sprite within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Sprite to the given X co-ordinate
	 * @param x the new x co-ordinate of this Sprite
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Sprite to the given Y co-ordinate
	 * @param y the new y co-ordinate of this Sprite
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}

	/**
	 * Obtains the width of this Sprite.
	 * @return the width of this Sprite,in pixels.
	 */
	public double getCurrentWidth()
	{
		return width;
	}

    /**
     * Defines a new width for this Sprite.
     * @param width The new width of this Sprite, in pixels.
	 */
	public void setWidth(double width)
	{
		this.width = width;
	}

	/**
	 * Obtains the height of this Sprite.
	 * @return the height of this Sprite,in pixels.
	 */
	public double getCurrentHeight()
	{
		return height;
	}

    /**
     * Defines a new height for this Sprite.
     * @param height The new height of this Sprite, in pixels.
	 */
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	/**
	 * Constructor, create a Sprite from the given URL.
	 * @param url URL to the Sprite png
	 * @param x the intial xPosition of the sprite
	 * @param y the intial yPosition of the sprite
	 * @param w the intial width of the sprite
	 * @param h the intial height of the sprite
	 */
	public Sprite(String url, double x, double y, double w, double h)
	{
		super(url);
		xPosition = x;
		yPosition = y;
		width = w;
		height = h;
	}	

	/**
	 * Check if the Sprite is touching a given Rectangle.
	 * @param r the given Rectange
	 * @return returns true if the Sprite is touching the Rectangle, otherwise returns false
	 */
    public boolean isTouching(Rectangle r)
    {
        return (xPosition - width/2 < r.getXPosition() + r.getWidth()/2 &&		
                xPosition + width/2 > r.getXPosition() - r.getWidth()/2 &&
                yPosition - height/2 < r.getYPosition() + r.getHeight()/2 &&
                yPosition + height/2 > r.getYPosition() - r.getHeight()/2);
    }


}
