
public class Ball
{
	private int xPos,yPos;
	public int dx = 5, dy = -5;
	
	public Ball()
	{
		setPos(PongMain.WIDTH / 2, PongMain.HEIGHT / 2);
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
		setPos(this.getX() + dx, this.getY() + dy);
	}	

	public void reset()
	{
		setPos(PongMain.WIDTH / 2, PongMain.HEIGHT / 2);
		dx = 5;
		dy = -5;
	}
}


