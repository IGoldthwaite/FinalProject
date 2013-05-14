import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class PongMain extends Frame implements KeyListener, ActionListener
{
	static Ball ball;
	HumanPaddle pLeft, pRight;
	AI pAI;
	
	Graphics graphics;
	BufferedImage screen;
	Timer time;
	Font newFont = new Font("font1", Font.BOLD, 20);
	
	static final int WIDTH = 1300, HEIGHT = 600;
	
	static int leftSetHeight, rightSetHeight, ballSetDiameter, leftSetDy, rightSetDy;
	
	boolean leftWin = false, rightWin = false, leftHit = false, rightHit = false, counting = false, 
			hasHitLeft = false, hasHitRight = false, leftMoving = false, rightMoving = false;
	
	int hitCount = 0;
	
	public PongMain()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Pong Game");
		addKeyListener(this);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		ball = new Ball(20);
		pLeft = new HumanPaddle("left", 'w', 's');
//		pRight = new HumanPaddle("right", '8', '2');
		pAI = new AI("right", "impossible");
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		
		leftSetHeight = pLeft.height;
		leftSetDy = pLeft.dy;
		if (pRight != null)
		{
			rightSetHeight = pRight.height;
			rightSetDy = pRight.dy;
		}
		ballSetDiameter = ball.diameter;
		
		//calls actionPerformed() at 67 (1000/15) FPS
		time = new Timer(15, this);
		time.start();
	}
	
	public void paint(Graphics g)
	{
		//clear off previous image
		graphics.clearRect(0,0,WIDTH,HEIGHT);
		
		//draw paddles
		graphics.setColor(Color.blue);
		graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
		if (pRight != null)
		{
			graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		}
		else
		{
			graphics.fillRect(pAI.XPOS_RIGHT, pAI.getPos(), 10, pAI.height);
		}
		
		//draw scores
		graphics.setColor(Color.white);
		graphics.setFont(newFont);
		if (pRight != null)
		{
			graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 50, 50);
			graphics.drawString(""+ pRight.getScore(), (WIDTH / 2) + 50, 50);
		}
		else
		{
			graphics.drawString("Try Harder", (WIDTH / 2) - 100, 50);
			graphics.drawString(""+ pAI.getScore(), (WIDTH / 2) + 50, 50);
		}
		
		//draw ball and trail
		graphics.setColor(Color.red.darker().darker().darker().darker());
		graphics.fillOval(ball.getX() - ball.dx*2, ball.getY() - ball.dy*2, ball.diameter - 2, ball.diameter - 2);
		graphics.setColor(Color.red.darker().darker());
		graphics.fillOval(ball.getX() - ball.dx, ball.getY() - ball.dy, ball.diameter - 1, ball.diameter - 1);
		graphics.setColor(Color.red);
		graphics.fillOval(ball.getX(), ball.getY(), ball.diameter, ball.diameter);
		
		//draw winner screen
		graphics.setColor(Color.red);
		if (pRight != null)
		{
			if (pRight.getScore() >= 5 && !leftWin)
			{
				rightWin = true;
				graphics.drawString("YOU WIN", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU LOSE", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 80, 150);
			}
			else if (pLeft.getScore() >= 5 && !rightWin)
			{
				leftWin = true;
				graphics.drawString("YOU LOSE", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU WIN", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 85, 150);
			}
		}
		else
		{
			if (pAI.getScore() >= 5 && !leftWin)
			{
				rightWin = true;
				graphics.drawString("YOU WIN", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU LOSE", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 80, 150);
			}
			else if (pLeft.getScore() >= 5 && !rightWin)
			{
				leftWin = true;
				graphics.drawString("YOU LOSE", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU WIN", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 85, 150);
			}
		}
		
		//draw image onto the frame
		g.drawImage(screen,0,0,this);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void checkCollision()
	{
		pLeft.checkHits(ball);
		pLeft.updateScore(ball);
		if (pRight != null)
		{
			pRight.checkHits(ball);
			pRight.updateScore(ball);
		}
		else
		{
			pAI.checkHits(ball);
			pAI.updateScore(ball);
		}
	}
	
	public void gameReset()
	{
		hitCount = 0;
		ball.reset();
		pLeft.reset();
		if (pRight != null)
		{
			pRight.reset();
		}
		else
		{
			pAI.reset();
		}
		rightWin = false;
		leftWin = false;
	}

	public void actionPerformed(ActionEvent e) 
	{
		ball.move();
		pLeft.act();
		if (pRight != null)
		{
			pRight.act();
		}
		else
		{
			pAI.act();
		}
		checkCollision();
		repaint();
	}
	
	public void keyTyped(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == 27)
		{
			System.exit(0);
		}
		if (key == 'r')
		{
			gameReset();
		}
	}
	
	public void keyPressed(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == pLeft.upButton)
		{
			pLeft.up = true;
		}
		if (key == pLeft.downButton)
		{
			pLeft.down = true;
		}
		if (pRight != null)
		{
			if (key == pRight.upButton)
			{
				pRight.up = true;
			}
			if (key == pRight.downButton)
			{
				pRight.down = true;
			}
		}
	}
	
	public void keyReleased(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == pLeft.upButton)
		{
			pLeft.up = false;
		}
		if (key == pLeft.downButton)
		{
			pLeft.down = false;
		}
		if (pRight != null)
		{
			if (key == pRight.upButton)
			{
				pRight.up = false;
			}
			if (key == pRight.downButton)
			{
				pRight.down = false;
			}
		}
	}
}