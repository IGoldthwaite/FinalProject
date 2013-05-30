import java.awt.Color;


public class AI extends Paddle
{
	String difficulty;
	
	public AI(String pos, String diff)
	{
		color = Color.darkGray.darker().darker().darker().darker().darker();
		difficulty = diff;
		position = pos;
	}
	
	public void act()
	{
		
		if (difficulty == "impossible")
		{
			setPos((PongMain.ball.getY() + PongMain.ball.diameter) - (this.height / 2));
		}
		
		if (difficulty == "easy")
		{
			this.setDy(5);
			if (PongMain.ball.getY() + (PongMain.ball.diameter / 2) > this.getPos() + (height / 3) && 
					PongMain.ball.getY() + (PongMain.ball.diameter / 2) < this.getPos() + ((height / 3)*2))
			{
				//makes the AI paddle less shaky
			}
			else if ((this.getPos() + (this.height / 2)) > (PongMain.ball.getY() + (PongMain.ball.diameter / 2)))
			{
				this.setPos(this.getPos() - dy);
			}
			else
			{
				this.setPos(this.getPos() + dy);
			}
		}
		
		if (difficulty == "playMaker")
		{
			this.setDy(3);
			if (PongMain.ball.xPos > PongMain.WIDTH / 1.1)
			{
				color = Color.red;
				this.setDy(Math.abs(PongMain.ball.dy * 2));
			}
			else
			{
				color = Color.darkGray.darker().darker().darker().darker().darker();
			}
			if (PongMain.ball.getY() + (PongMain.ball.diameter / 2) > this.getPos() + (height / 2.5) && 
					PongMain.ball.getY() + (PongMain.ball.diameter / 2) < this.getPos() + ((height / 2.5)*2))
			{
				//makes the AI paddle less shaky
			}
			else if ((this.getPos() + (this.height / 2)) > (PongMain.ball.getY() + PongMain.ball.diameter))
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