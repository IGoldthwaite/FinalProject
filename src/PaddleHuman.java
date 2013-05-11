
public class PaddleHuman
{
	int yPos, height, score, dy;
	final int XPOS_LEFT = 30, XPOS_RIGHT = PongMain.WIDTH - 40;
	
	public PaddleHuman()
	{
		setPos((PongMain.HEIGHT - 30) / 2);
		height = 70;
		dy = 7;
	}
	
	public PaddleHuman(int h)
	{
		setPos((PongMain.HEIGHT - 30) / 2);
		setHeight(h);
		dy = 7;
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
	
	public void setHeight(int height)
	{
		this.height = height;
		
		if (this.height < 10)
		{
			this.height = 10;
		}
		
		if (this.height > PongMain.HEIGHT - 38)
		{
			this.height = PongMain.HEIGHT - 38;
		}
	}
	
	public void setDy(int dy)
	{
		this.dy = dy;
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
}
