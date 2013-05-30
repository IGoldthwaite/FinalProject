import java.awt.Color;

/**
 * The Base Paddle used both by HumanPaddle and the AI
 * @author Isaac Goldthwaite, Kevin Edwards
 *
 */
public class Paddle 
{
	final int XPOS_LEFT = 30, XPOS_RIGHT = PongMain.WIDTH - 40;
	int yPos, height, score, dy;
	String position;
	boolean up, down;
	double perpAngle, angle, magnitude;
	Color color;
	
	public Paddle()
	{
		setPos((PongMain.HEIGHT - 30) / 2);
		height = 70;
		dy = 7;
	}
	
	/**
	 * Sets the Y position of the paddle
	 * @param pos position of paddle
	 */
	public void setPos(int pos)
	{
		this.yPos = pos;
		if (yPos > (PongMain.HEIGHT - this.height - 8))
		{
			setPos(PongMain.HEIGHT - this.height - 8);
		}
		else if (yPos < 30)
		{
			setPos(30);
		}
	}
	
	/**
	 * Sets the hight of the paddle
	 * @param height height of the paddle
	 */
	public void setHeight(int height)
	{
		this.height = height;
		
		if (this.height < 10)
		{
			this.height = 10;
		}
		else if (this.height > PongMain.HEIGHT - 38)
		{
			this.height = PongMain.HEIGHT - 38;
		}
	}
	
	/**
	 * Sets the dy value of the paddle
	 * @param dy vertical amount that the paddle moves
	 */
	public void setDy(int dy)
	{
		this.dy = dy;
		
		if (this.dy > PongMain.HEIGHT - this.height - 38)
		{
			this.dy = PongMain.HEIGHT - this.height - 38;
		}
	}
	
	/**
	 * @return Y position of the paddle
	 */
	public int getPos()
	{
		return yPos;
	}
	
	/**
	 * sets the score of the paddle
	 * @param s score
	 */
	public void setScore(int s)
	{
		score = s;
	}
	
	/**
	 * @return the paddle's score
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * will return true if the ball is within the paddle's borders, false otherwise
	 * @param ball the ball being detected by the paddle
	 * @return
	 */
	public boolean canHitBall(Ball ball)
	{
		if ((this.getPos() - ball.diameter) <= ball.getY() && (this.getPos() + this.height) > ball.getY())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks to see if the ball has passed by or hit the paddle. Changes the ball's movement appropriately.
	 * Also updates the scores for each paddle.
	 * @param ball
	 */
	public void checkHits(Ball ball)
	{
		if (position == "left")
		{
			//if the ball hits the left paddle
			if ((ball.getX() <= (this.XPOS_LEFT + 10)) && canHitBall(ball))
			{
				ball.dx = (ball.dx * -1);
				if (this.up)
				{
					ball.dy -= 3;
					if (ball.dx > 0)
					{
						ball.dx += 1;
					}
					else
					{
						ball.dx -= 1;
					}
				}
				else if (this.down)
				{
					ball.dy += 3;
					if (ball.dx > 0)
					{
						ball.dx += 1;
					}
					else
					{
						ball.dx -= 1;
					}
				}
			}
			//if the ball passes by the left paddle
			else if (ball.getX() <= (this.XPOS_LEFT + 10) && !canHitBall(ball))
			{
				PongMain.RIGHT_SERVE = true;
				ball.reset();
				if (PongMain.SINGLE_PLAYER)
				{
					PongMain.pAI.setScore(PongMain.pAI.getScore() + 1);
				}
				else
				{
					PongMain.pRight.setScore(PongMain.pRight.getScore() + 1);
				}
			}
		}
		else if (position == "right")
		{
			//if the ball hits the right paddle
			if ((ball.getX() + ball.diameter) >= this.XPOS_RIGHT && canHitBall(ball))
			{
				ball.dx = (ball.dx * -1);
				if (this.up)
				{
					ball.dy -= 2;
				}
				else if (this.down)
				{
					ball.dy += 2;
				}
				if (ball.dx > 0)
				{
					ball.dx += 1;
				}
				else
				{
					ball.dx -= 1;
				}
			}
			//if the ball passes by the left paddle
			else if ((ball.getX() + ball.diameter) >= this.XPOS_RIGHT && !canHitBall(ball))
			{
				PongMain.RIGHT_SERVE = false;
				ball.reset();
				PongMain.pLeft.setScore(PongMain.pLeft.getScore() + 1);
			}
		}
	}
	
	/**
	 * resets this paddle's score and height
	 */
	public void reset()
	{
		setScore(0);
		if (PongMain.SINGLE_PLAYER)
		{
			if (position == "left")
			{
				this.height = PongMain.leftSetHeight;
			}
			else if (position == "right")
			{
				this.height = PongMain.AISetHeight;
			}
		}
		else
		{
			if (position == "left")
			{
				this.height = PongMain.leftSetHeight;
			}
			else if (position == "right")
			{
				this.height = PongMain.rightSetHeight;
			}
		}
	}
}