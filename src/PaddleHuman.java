/*
 * This is the class for the human-controlled paddle
 */

public class PaddleHuman
{
	int yPos = 0, height, score;
	final int XPOS_LEFT = 30, XPOS_RIGHT = PongMain.WIDTH - 38;
	
	public PaddleHuman()
	{
		setPos(30);
		height = 70;
	}
	
	public PaddleHuman(int h)
	{
		setPos(30);
		height = h;
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
		score = s;
	}
	
	public int getScore()
	{
		return score;
	}
}
