

public class Paddle 
{
	final int XPOS_LEFT = 30, XPOS_RIGHT = PongMain.WIDTH - 40;
	int yPos, height, score, dy, hitCount;
	String position;
	
	public Paddle()
	{
		setPos((PongMain.HEIGHT - 30) / 2);
		height = 70;
		dy = 7;
	}
	
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
	
	public void setDy(int dy)
	{
		this.dy = dy;
		
		if (this.dy > PongMain.HEIGHT - this.height - 38)
		{
			this.dy = PongMain.HEIGHT - this.height - 38;
		}
	}
	
	public int getPos()
	{
		return yPos;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public boolean canHitBall(Ball ball)
	{
		boolean didHit = false;
		if ((this.getPos() - ball.diameter) <= ball.getY() && (this.getPos() + this.height) > ball.getY())
		{
			didHit = true;
		}
		return didHit;
	}
	
	public void checkHits(Ball ball)
	{
		if (position == "left")
		{
			if ((ball.getX() <= (this.XPOS_LEFT+10)) && canHitBall(ball))
			{
				hitCount++;
				ball.dx = (ball.dx * -1);
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
		
		else if (position == "right")
		{
			if (((ball.getX() + ball.diameter) >= this.XPOS_RIGHT) && canHitBall(ball))
			{
				hitCount++;
				ball.dx = (ball.dx * -1);
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
	}
	
	public void updateScore(Ball ball)
	{
		if (position == "left")
		{
			if (ball.getX() <= 8 && !canHitBall(ball))
			{
				if (PongMain.SINGLE_PLAYER)
				{
					hitCount = 0;
					PongMain.pAI.setScore(PongMain.pAI.getScore() + 1);
					ball.reset();
					setHeight(PongMain.leftSetHeight);
				}
				else
				{
					hitCount = 0;
					PongMain.pRight.setScore(PongMain.pRight.getScore() + 1);
					ball.reset();
					setHeight(PongMain.leftSetHeight);
				}
			}
		}
		
		else if (position == "right")
		{
			if ((ball.getX() + ball.diameter) >= PongMain.WIDTH - 8 && !canHitBall(ball))
			{
				hitCount = 0;
				PongMain.pLeft.setScore(PongMain.pLeft.getScore() + 1);
				ball.reset();
				setHeight(PongMain.rightSetHeight);
			}
		}
	}
	
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