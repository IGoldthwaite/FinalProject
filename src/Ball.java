
public class Ball 
{
	private int xPos, yPos, hitCount;
	public int dx, dy;
	
	public Ball()
	{
		dx = 5;
		dy = -5;
		setPos(250, 140); //set to half board height, and half board width
	}
	
	public Ball(int dX, int dY)
	{
		dx = dX;
		dy = dY;
		setPos(250, 140);
	}
	
	public void setPos(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public void move()
	{
		setPos(this.getY() + dx, this.getY() + dy);
	}
	
	public void reset()
	{
		setPos(250, 140);
		dx = 5;
		dy = -5;
	}
}
