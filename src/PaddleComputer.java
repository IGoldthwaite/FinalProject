/*
 * PaddleRight has essentially the same set up as the human-controlled
 * paddle except that it uses the y position of the ball to determine 
 * the paddle location instead of using a mouseMovement listener
 */
public class PaddleComputer{
	
	int yPos = 0, height, score;
	final int XPOS = PongMain.WIDTH - 40;
	
	public PaddleComputer(int ballPos)
	{
		height = 70;
		setPos(ballPos);
		setScore(0);
	}
	
	public PaddleComputer(int h, int ballPos)
	{
		height = h;
		setPos(ballPos);
		setScore(0);
	}
	
	public void setPos(int pos)
	{
		this.yPos = pos;
		if(yPos > (PongMain.HEIGHT - this.height - 8))
		{
			setPos(PongMain.HEIGHT - this.height - 8);
		}
		else if(yPos < 30)
		{
			setPos(30);
		}
	}
	
	public int getPos()
	{
		return yPos;
	}
	//setters and getters for int score
	public void setScore(int s)
	{
		this.score = s;
	}
	
	public int getScore()
	{
		return this.score;
	}

}

