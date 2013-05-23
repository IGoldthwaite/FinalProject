import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Menu
{
	static Font font1 = new Font("font1", Font.BOLD, 50), font2 = new Font("font2", Font.BOLD, 30);
	static int bLeftLeft 	= (PongMain.WIDTH / 4) - 110,
				bLeftRight 	= ((PongMain.WIDTH / 4) - 110) + 50,
				bLeftTop 	= (PongMain.HEIGHT / 2) - 35, 
				bLeftBottom = ((PongMain.HEIGHT / 2) - 35) + 208, 
				bRightLeft 	= (PongMain.WIDTH - (PongMain.WIDTH / 4) - 90), 
				bRightRight = (PongMain.WIDTH - (PongMain.WIDTH / 4) - 90) + 50, 
				bRightTop	= (PongMain.HEIGHT / 2) - 35, 
				bRightBottom= (PongMain.HEIGHT / 2) - 35 + 180;
	
	public static void drawMenu(Graphics g)
	{
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Pong", (PongMain.WIDTH / 2) - 50, PongMain.HEIGHT / 4);
		g.setFont(font2);
		g.drawString("Single Player", (PongMain.WIDTH / 4) - 100, PongMain.HEIGHT / 2);
		g.drawString("Two Player", (PongMain.WIDTH - (PongMain.WIDTH / 4)) - 80, PongMain.HEIGHT / 2);
		g.drawRect(bLeftLeft, bLeftTop, 208, 50);
		g.drawRect(bRightLeft, bRightTop, 180, 50);
	}
}
