
public class PaddleComputer
{
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
	
	public void setScore(int s)
	{
		this.score = s;
	}
	
	public int getScore()
	{
		return this.score;
	}

}

