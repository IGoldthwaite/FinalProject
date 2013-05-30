import java.awt.Color;

/**
 * The human controlled paddle that extends Paddle.
 * @author Isaac Goldthwaite, Kevin Edwards
 *
 */
public class HumanPaddle extends Paddle
{
	char upButton, downButton;
	
	public HumanPaddle(String pos, char up, char down)
	{
		color = Color.blue;
		position = pos;
		upButton = up;
		downButton = down;
	}
	
	/**
	 * called continuously in PongMain and will move the paddle if the human player presses the up/down keys.
	 */
	public void act()
	{
		if (up)
		{
			setPos(getPos() - dy);
		}
		if (down)
		{
			setPos(getPos() + dy);
		}
	}
}