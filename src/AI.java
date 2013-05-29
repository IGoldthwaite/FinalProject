
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
			setPos((PongMain.ball.getY() + PongMain.ball.diameter) - (this.height / 2));
		}
		if (difficulty == "hard")
		{
			this.setDy(5);
			if ((this.getPos() + (this.height / 2)) > (PongMain.ball.getY() + PongMain.ball.diameter))
			{
				this.setPos(this.getPos() - dy);
			}
			else
			{
				this.setPos(this.getPos() + dy);
			}
		}
		if (difficulty == "medium")
		{
			this.setDy(5);
			if (PongMain.ball.xPos > PongMain.WIDTH / 2 && !this.canHitBall(PongMain.ball))
			{
				if ((this.getPos() + (this.height / 2)) > (PongMain.ball.getY() + PongMain.ball.diameter))
				{
					this.setPos(this.getPos() - dy);
				}
				else
				{
					this.setPos(this.getPos() + dy);
				}
			}
		}
	}
}