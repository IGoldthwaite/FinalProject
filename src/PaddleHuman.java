/*
 * This is the class for the human-controlled paddle
 */

public class PaddleHuman
{
	int yPos = 0, height, score;
	final int XPOS = 30;
	
	public PaddleHuman()
	{
		setPos(120);
		height = 70;
	}
	
	public PaddleHuman(int h)
	{
		setPos(120);
		height = h;
	}
	
	public void setPos(int pos)
	{
		this.yPos = pos;
		if(yPos > (PongMain.HEIGHT - this.height))
		{
			setPos(PongMain.HEIGHT - this.height);
		}
		else if(yPos < 0)
		{
			setPos(0);
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
}
