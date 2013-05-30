import java.awt.Color;

/**
 * The computer controlled paddle. Has (currently) three difficulties: impossible, hard, and playMaker.
 * @author Isaac Goldthwaite, Kevin Edwards
 *
 */
public class AI extends Paddle
{
	String difficulty;
	
	public AI(String pos, String diff)
	{
		color = Color.black;
		difficulty = diff;
		position = pos;
	}
	
	/**
	 * called continuously in PongMain and will move the paddle based on the ball's location and the difficulty of the AI.
	 */
	public void act()
	{
		
		if (difficulty == "impossible")
		{
			color = Color.red.darker();
			setPos((PongMain.ball.getY() + (PongMain.ball.diameter / 2)) - (this.height / 2));
		}
		
		if (difficulty == "hard")
		{
			color = Color.cyan.darker().darker();
			this.setDy(10);
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
			this.setDy(2);
			if (PongMain.ball.xPos > PongMain.WIDTH / 1.1)
			{
				color = Color.red;
				this.setDy(Math.abs(PongMain.ball.dy * 3));
			}
			else
			{
				color = Color.darkGray.darker().darker();
			}
			if (PongMain.ball.getY() + (PongMain.ball.diameter / 2) > this.getPos() + (height / 3) && 
					PongMain.ball.getY() + (PongMain.ball.diameter / 2) < this.getPos() + (height /3)*2)
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