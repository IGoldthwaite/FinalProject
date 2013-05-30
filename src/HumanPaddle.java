import java.awt.Color;


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