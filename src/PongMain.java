import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;

public class PongMain extends Applet implements KeyListener, ActionListener
{
	Ball ball;
	PaddleHuman pLeft;
	PaddleComputer pRight;
	Graphics bufferGraphics;
	Image offscreen;
	Font newFont = new Font("sansserif", Font.BOLD, 20);
	
	final int WIDTH = 500, HEIGHT = 300;
	long currentTime;
	
	public void init()
	{
		setSize(500, 300);
		ball = new Ball();
		pLeft = new PaddleHuman();
		pRight = new PaddleComputer(ball.getY() - 35);
		
		addKeyListener(this);
		setBackground(Color.white);
		
		offscreen = createImage(WIDTH, HEIGHT);
		bufferGraphics = offscreen.getGraphics();
	}
	
	public void start()
	{
		currentTime = System.currentTimeMillis();
		
		Timer time = new Timer(15, this);		//1000 / 15 = 67 fps
		
		time.start();
		
		while(pLeft.getScore() < 5 && pRight.getScore() < 5)
		{
			//run continuously
		}
		
		time.stop();
		currentTime = System.currentTimeMillis();
		
		repaint();
	}
	
	public void checkCollision()
	{
		if (ball.getY() == 0 || ball.getY() == 290) 	//Top of grid -10 (ball diameter)
		{
			ball.dy = (ball.dy * -1);
		}
		
		if ((ball.getX() == 40) && hitPaddle())
		{
			ball.dx = (ball.dx * -1);
		}
		
		if ((ball.getX() == 460) && hitPaddle())
		{
			ball.dx = (ball.dx * -1);
		}
		
		if (ball.getX() == 0)
		{
			pRight.setScore(pRight.getScore() + 1);
			ball.reset();
		}
		
		if (ball.getX() == 490)							//Grid right side -10 (ball diameter)
		{
			pLeft.setScore(pLeft.getScore() + 1);
			ball.reset();
		}
	}
	
	public boolean hitPaddle()
	{
		boolean didHit = false;
		
		if ((pLeft.getPos() - 10) <= ball.getY() && (pLeft.getPos() + 70) > ball.getY())
		{
			didHit = true;
		}
		
		return didHit;
	}
	
	public void paint(Graphics g)
	{
		bufferGraphics.clearRect(0,0,WIDTH,HEIGHT);
		bufferGraphics.setColor(Color.black);
		bufferGraphics.fillRect(pLeft.xPos, pLeft.getPos(), 10, 70);
		bufferGraphics.fillRect(pRight.xPos, pRight.getPos(), 10, 70);
		bufferGraphics.setColor(Color.darkGray);
		bufferGraphics.setFont(newFont);
		bufferGraphics.drawString("Futile", 150, 15);
		bufferGraphics.drawString(""+pRight.getScore(), 300, 15);
		bufferGraphics.fillRect(240, 0, 20, 300);
		
		if (pRight.getScore() == 10)
		{
			bufferGraphics.drawString("Player Two Wins!", 40, 150);
		}
		else if (pLeft.getScore() == 10)
		{
			bufferGraphics.drawString("Player One Wins!", 40, 150);
		}
		
		bufferGraphics.setColor(Color.red);
		bufferGraphics.fillRect(ball.getX(), ball.getY(), 10, 10);
		g.drawImage(offscreen, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		ball.move();
		pRight.setPos(ball.getY() - 35);
		checkCollision();
		repaint();
	}

	public void keyPressed(KeyEvent arg0)
	{
		int key = arg0.getKeyCode();
		
		if (key == KeyEvent.VK_W)
		{
			pLeft.setPos(pLeft.getPos() + 10);
		}
		else if (key == KeyEvent.VK_D)
		{
			pLeft.setPos(pLeft.getPos() + 10);
		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		
	}
}
