//
//public class AI extends Paddle
//{
//	String difficulty;
//	
//	public AI(String pos, String diff)
//	{
//		difficulty = diff;
//		position = pos;
//	}
//	
//	public void act()
//	{
//		if (difficulty == "impossible")
//		{
//			setPos(PongMain.ball.getY() - (this.height / 2) + (PongMain.ball.diameter / 2));
//		}
//	}
//}

public class AI extends Paddle 
{
	String difficulty;
	int desY; // I wrote this in github, its probably gooing to have some errors
	int estDy;
	int count = 0;

	public AI(String pos, String diff) 
	{
		difficulty = diff;
		position = pos;
	}

	public void act() 
	{
		if (difficulty == "impossible") 
		{
			setPos(PongMain.ball.getY() - (this.height / 2) + (PongMain.ball.diameter / 2));
		}
		if (difficulty == "medium") 
		{
			desY = PongMain.ball.getY() + PongMain.ball.dy;
			desAct();
		}
		if (difficulty == "hard") 
		{
			estDy = PongMain.ball.dy;
			if ((PongMain.ball.getY() < 50 || PongMain.ball.getY() > PongMain.HEIGHT - 50)
					&& count > 10) 
			{
				estDy = -estDy;
				count = 0;
			}
			desY = PongMain.ball.getY() + estDy;
			desAct();
			count++;
		}
		if (difficulty == "isaac level") 
		{
			setPos(PongMain.ball.getX());
		}
	}

	public void desAct() 
	{
		if (desY > this.yPos) 
		{
			this.yPos += this.dy;
		} 
		else if (desY < this.yPos)
		{
			this.yPos -= this.dy;
		}
	}
}
