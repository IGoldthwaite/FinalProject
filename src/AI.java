

public class AI extends Paddle
{
	String difficulty;
	
	public AI(String pos, String diff)
	{
		difficulty = diff;
		position = pos;
	}
	
	public void act()
	{
		if (difficulty == "impossible")
		{
			setPos(PongMain.ball.getY() - (this.height / 2));
		}
	}
}