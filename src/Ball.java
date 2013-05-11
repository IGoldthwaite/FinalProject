
public class Ball
{
	private int xPos, yPos;
	public int dx, dy, diameter;
	
	public Ball()
	{
		dx = 5;
		dy = -5;
		diameter = 10;
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
	}
	
	public Ball(int d)
	{
		dx = 5;
		dy = -5;
		diameter = d;
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
	}
	
	public void setPos(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}
	
	public void setDiameter(int d)
	{
		this.diameter = d;
		if (this.diameter < 10)
		{
			this.diameter = 10;
		}
		
		if (this.diameter > PongMain.HEIGHT - 38)
		{
			this.diameter = PongMain.HEIGHT - 38;
		}
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
		setPos(this.getX() + dx, this.getY() + dy);
	}	

	public void reset()
	{
		setPos((PongMain.WIDTH / 2) - (diameter / 2), (PongMain.HEIGHT / 2) - (diameter / 2));
		dx = 5;
		dy = -5;
		diameter = PongMain.ballSetDiameter;
	}
}


