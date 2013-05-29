
public class Paddle 
{
	final int XPOS_LEFT = 30, XPOS_RIGHT = PongMain.WIDTH - 40;
	int yPos, height, score, dy, hitCount, topSection, midSection, botSection;
	String position;
	boolean up, down;
	
	public Paddle()
	{
		setPos((PongMain.HEIGHT - 30) / 2);
		height = 70;
		dy = 7;
		topSection = yPos + (height / 3);
		midSection = yPos + (height * (2/3));
		botSection = yPos + (height);
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
		topSection = yPos + (height / 3);
		midSection = yPos + (height / 3)*2;
		botSection = yPos + (height);
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
	
	//returns 0 if the ball hits the top third of the paddle, a 1 if it hits the middle, and a 2 if it hits the bottom third.
	public int hitLocation(Ball ball)
	{
		//bottom of paddle
		if (ball.getY() < this.botSection && (ball.getY() + (ball.diameter / 2)) > this.midSection)
		{
			System.out.println(2);
			return 2;
		}
		//middle of paddle
		else if (((ball.getY() + (ball.diameter / 2)) < this.midSection) && (ball.getY() + (ball.diameter / 2)) > this.topSection)
		{
			System.out.println(1);
			return 1;
		}
		//top of paddle
		else if (((ball.getY() + (ball.diameter / 2)) < this.topSection) && (ball.getY() + ball.diameter) > this.yPos)
		{
			System.out.println(0);
			return 0;
		}
		else
		{
			System.out.println(-1);
			return -1;
		}
	}
	
	//Will return true if the ball is in the paddle's borders, false otherwise.
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
	
	//checks to see if the ball has passed by or hit the paddle. Changes the ball's movement appropriately.
	public void checkHits(Ball ball)
	{
		if (position == "left")
		{
			if ((ball.getX() <= (this.XPOS_LEFT+10)) && canHitBall(ball))
			{
				hitCount++;
				ball.dx = (ball.dx * -1);
				if (this.up)
				{
					ball.dy = -(Math.abs(ball.dy)+3);
				}
				else if (this.down)
				{
					ball.dy = Math.abs(ball.dy)+3;
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
			if (PongMain.SINGLE_PLAYER)
			{
				if ((ball.getX() + ball.diameter) >= PongMain.WIDTH - 8 && !canHitBall(ball))
				{
					hitCount = 0;
					PongMain.pLeft.setScore(PongMain.pLeft.getScore() + 1);
					ball.reset();
					setHeight(PongMain.AISetHeight);
				}
			}
			else
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
	}
	
	public void reset()
	{
		setScore(0);
		hitCount = 0;
		
		if (PongMain.SINGLE_PLAYER)
		{
			if (position == "left")
			{
				this.height = PongMain.leftSetHeight;
			}
			else if (position == "right")
			{
				System.out.println(PongMain.AISetHeight);
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