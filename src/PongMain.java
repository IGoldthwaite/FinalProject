import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.BitSet;
import javax.swing.Timer;

public class PongMain extends Frame implements KeyListener, ActionListener
{
	Ball ball;
	PaddleHuman pLeft, pRight;
	Graphics graphics;
	BufferedImage screen;
	Timer time;
	Font newFont = new Font("font1", Font.BOLD, 20);
	
	static int leftHeight, rightHeight, leftDy, rightDy;
	
	boolean leftWin, rightWin;
	
	static final int WIDTH = 1300, HEIGHT = 600,
					LEFT_UP = 0, LEFT_DOWN = 1, RIGHT_UP = 2, RIGHT_DOWN = 3;
	
	BitSet buttonsPressed = new BitSet(4);
	
	public PongMain()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Pong Game");
		addKeyListener(this);
		setBackground(Color.white);
		
		ball = new Ball();
		pLeft = new PaddleHuman(70);
		pRight = new PaddleHuman(70);
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		
		leftHeight = pLeft.height;
		rightHeight = pRight.height;
		leftDy = pLeft.dy;
		rightDy = pRight.dy;
		
		//calls actionPerformed() at 67 (1000/15) FPS
		time = new Timer(15, this);
		time.start();
	}
	
	public void stop()
	{
	}
	
	public void paint(Graphics g)
	{
		//clear off previous image
		graphics.clearRect(0,0,WIDTH,HEIGHT); 
		
		//draw paddles
		graphics.setColor(Color.blue);
		graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
		graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		
		//draw scores
		graphics.setColor(Color.white);
		graphics.setFont(newFont);
		graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 50, 50);
		graphics.drawString(""+ pRight.getScore(), (WIDTH / 2) + 50, 50);
		
		//draw ball
		graphics.setColor(Color.red);
		graphics.fillRect(ball.getX(), ball.getY(), 10, 10);
		
		//draw winner screen
		graphics.setColor(Color.red);
		if (pRight.getScore() >= 5 && !leftWin)
		{
			graphics.drawString("YOU WIN", (WIDTH / 2) + 200, 100);
			graphics.drawString("YOU LOSE", (WIDTH / 2) - 250, 100);
			graphics.drawString("Press R To Play Again", (WIDTH /2) - 80, 150);
			rightWin = true;
		}
		else if (pLeft.getScore() >= 5 && !rightWin)
		{
			graphics.drawString("YOU LOSE", (WIDTH / 2) + 200, 100);
			graphics.drawString("YOU WIN", (WIDTH / 2) - 250, 100);
			graphics.drawString("Press R To Play Again", (WIDTH /2) - 80, 150);
			leftWin = true;
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
		//ball hits top or bottom of screen
		if (ball.getY() <= 30 || ball.getY() >= PongMain.HEIGHT - 18)
		{
			ball.dy = (ball.dy * -1);
		}
		
		//ball hits left paddle
		if ((ball.getX() <= pLeft.XPOS_LEFT+10) && hitPaddle())
		{
			ball.dx = (ball.dx * -1);
			//pLeft.setHeight(pLeft.height - 10);
			if (ball.dx > 0)
			{
				ball.dx += 1;
			}
			else
			{
				ball.dx -= 1;
			}
		}
		
		//ball hits right paddle
		if ((ball.getX() >= pRight.XPOS_RIGHT) && hitPaddle())
		{
			ball.dx = (ball.dx * -1);
			//pRight.setHeight(pRight.height - 10);
			if (ball.dx > 0)
			{
				ball.dx += 1;
			}
			else
			{
				ball.dx -= 1;
			}
		}
		
		//ball goes past left paddle
		if (ball.getX() <= 8 && !hitPaddle())
		{
			pRight.setScore(pRight.getScore() + 1);
			ball.reset();
			pLeft.setHeight(leftHeight);
		}
		
		//ball goes past right paddle
		if (ball.getX() >= PongMain.WIDTH - 18 && !hitPaddle())
		{
			pLeft.setScore(pLeft.getScore() + 1);
			ball.reset();
			pRight.setHeight(rightHeight);
		}
	}
	
	public boolean hitPaddle()
	{
		boolean didHit = false;
		if ((pLeft.getPos() - 10) <= ball.getY() && (pLeft.getPos() + pLeft.height) > ball.getY())
		{
			didHit = true;
		}
		if ((pRight.getPos() - 10) <= ball.getY() && (pRight.getPos() + pRight.height) > ball.getY())
		{
			didHit = true;
		}
		return didHit;
	}
	
	public void movePaddles()
	{
		if (buttonsPressed.get(LEFT_UP))
		{
			pLeft.setPos(pLeft.getPos() - pLeft.dy);
		}
		
		if (buttonsPressed.get(LEFT_DOWN))
		{
			pLeft.setPos(pLeft.getPos() + pLeft.dy);
		}
		
		if (buttonsPressed.get(RIGHT_UP))
		{
			pRight.setPos(pRight.getPos() - pLeft.dy);
		}
		
		if (buttonsPressed.get(RIGHT_DOWN))
		{
			pRight.setPos(pRight.getPos() + pLeft.dy);
		}
	}
	
	public void gameReset()
	{
		ball.reset();
		pLeft.height = leftHeight;
		pRight.height = rightHeight;
		pLeft.setScore(0);
		pRight.setScore(0);
		rightWin = false;
		leftWin = false;
	}

	public void actionPerformed(ActionEvent e) 
	{
		ball.move();
		checkCollision();
		movePaddles();
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
		if (key == 'w')
		{
			buttonsPressed.set(LEFT_UP, true);
		}
		
		if (key == 's')
		{
			buttonsPressed.set(LEFT_DOWN, true);
		}
		
		if (key == '2')
		{
			buttonsPressed.set(RIGHT_DOWN, true);
		}
		
		if (key == '8')
		{
			buttonsPressed.set(RIGHT_UP, true);
		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == 'w')
		{
			buttonsPressed.set(LEFT_UP, false);
		}
		
		if (key == 's')
		{
			buttonsPressed.set(LEFT_DOWN, false);
		}
		
		if (key == '2')
		{
			buttonsPressed.set(RIGHT_DOWN, false);
		}
		
		if (key == '8')
		{
			buttonsPressed.set(RIGHT_UP, false);
		}
	}
	
	public static void main(String[] args)
	{
		Frame frm = new PongMain();
		frm.setVisible(true);
		frm.repaint();
		System.out.println(frm.getInsets());
	}
}