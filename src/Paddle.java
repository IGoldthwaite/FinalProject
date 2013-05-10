
public class Paddle 
{
	protected int yPos = 0, xPos, score;
	
	public void setPos(int pos)
	{
		this.yPos = pos;
		
		if (yPos > 230)
		{
			setPos(230);
		}
		else if (yPos < 0)
		{
			setPos(0);
		}
	}
	
	public int getPos()
	{
		return yPos;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
}
