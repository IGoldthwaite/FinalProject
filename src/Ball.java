
/**
 * The class that defines and controls a ball within the game.
 * @author Isaac Goldthwaite, Kevin Edwards
 *
 */
public class Ball
{
	public int xPos, yPos, dx, dy, diameter;
	
	public Ball()
	{
		dx = 5;
		dy = (int) (Math.random()*10 -4);
		diameter = 10;
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
	}
	
	public Ball(int d)
	{
		dx = 5;
		dy = (int) (Math.random()*10 -4);
		diameter = d;
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
	}
	
	/**
	 * sets the x and y coordinates of the ball.
	 * @param x X coordinate of the ball
	 * @param y Y coordinate of the ball
	 */
	public void setPos(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}
	
	/**
	 * sets the diameter of the ball.
	 * @param d diameter of the ball
	 */
	public void setDiameter(int d)
	{
		this.diameter = d;
		if (this.diameter < 10)
		{
			this.diameter = 10;
		}
		
		if (this.diameter > PongMain.HEIGHT - 38)
		{
			this.diameter = PongMain.HEIGHT - 38;
		}
	}
	
	/**
	 * @return X coordinate of the ball.
	 */
	public int getX()
	{
		return xPos;
	}
	
	/**
	 * @return Y coordinate of the ball.
	 */
	public int getY()
	{
		return yPos;
	}
	
	/**
	 * Moves the ball according to its dx and dy values;
	 * Will not move past the borders of the screen
	 */
	public void move()
	{
		setPos(this.getX() + dx, this.getY() + dy);
		if (this.getY() <= 30 || (this.getY() + this.diameter) >= PongMain.HEIGHT - 8)
		{
			this.dy = (this.dy * -1);
		}
	}	

	/**
	 * Resets the ball to the center of the field and its dy, diameter, and which side to serve to.
	 */
	public void reset()
	{
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
		if (PongMain.RIGHT_SERVE)
		{
			dx = -5;
		}
		else
		{
			dx = 5;
		}
		dy = (int) (Math.random()*10 -4);
		diameter = PongMain.ballSetDiameter;
	}
}

