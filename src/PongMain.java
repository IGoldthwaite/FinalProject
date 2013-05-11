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
	
	boolean leftWin = false, rightWin = false, leftHit = false, rightHit = false, counting = false;
	int counter = 0;
	
	static int leftSetHeight, rightSetHeight, ballSetDiameter, leftDy, rightDy;
	
	static final int WIDTH = 1300, HEIGHT = 600,
					LEFT_UP = 0, LEFT_DOWN = 1, RIGHT_UP = 2, RIGHT_DOWN = 3;
	
	BitSet buttonsPressed = new BitSet(4);
	
	public PongMain()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Pong Game");
		addKeyListener(this);
		setBackground(Color.white);
		
		ball = new Ball(100);
		pLeft = new PaddleHuman(70);
		pRight = new PaddleHuman(70);
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		
		leftSetHeight = pLeft.height;
		rightSetHeight = pRight.height;
		leftDy = pLeft.dy;
		rightDy = pRight.dy;
		ballSetDiameter = ball.diameter;
		
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
		if (leftHit)
		{
			graphics.setColor(Color.red);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.setColor(Color.blue);
			graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		}
		else if (rightHit)
		{
			graphics.setColor(Color.blue);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.setColor(Color.red);
			graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		}
		else
		{
			graphics.setColor(Color.blue);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		}
		
		//draw scores
		graphics.setColor(Color.white);
		graphics.setFont(newFont);
		graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 50, 50);
		graphics.drawString(""+ pRight.getScore(), (WIDTH / 2) + 50, 50);
		
		//draw ball
		graphics.setColor(Color.red);
		graphics.fillOval(ball.getX(), ball.getY(), ball.diameter, ball.diameter);
		
		//draw winner screen
		graphics.setColor(Color.red);
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
		
		//draw image onto the frame
		g.drawImage(screen,0,0,this);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
//	public boolean delayed(int time)
//	{
//		counter++;
//		if (counter < time)
//		{
//			return false;
//		}
//		counter = 0;
//		return true;
//	}
	
	public void checkCollision()
	{
		if (counting)
		{
			counter++;
			ball.setDiameter(ball.diameter - 1);
		}
		
		if (counter > 10)
		{
			leftHit = false;
			rightHit = false;
			counting = false;
			counter = 0;
		}
		
		//ball hits top or bottom of screen
		if (ball.getY() <= 30 || (ball.getY() + ball.diameter) >= PongMain.HEIGHT - 8)
		{
			ball.dy = (ball.dy * -1);
		}
		
		//ball hits left paddle
		if ((ball.getX() <= (pLeft.XPOS_LEFT+10)) && leftHitPaddle())
		{
			leftHit = true;
			counting = true;
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
		if (((ball.getX() + ball.diameter) >= pRight.XPOS_RIGHT) && rightHitPaddle())
		{
			rightHit = true;
			counting = true;
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
		if (ball.getX() <= 8 && !leftHitPaddle())
		{
			pRight.setScore(pRight.getScore() + 1);
			ball.reset();
			pLeft.setHeight(leftSetHeight);
		}
		
		//ball goes past right paddle
		if ((ball.getX() + ball.diameter) >= PongMain.WIDTH - 8 && !rightHitPaddle())
		{
			pLeft.setScore(pLeft.getScore() + 1);
			ball.reset();
			pRight.setHeight(rightSetHeight);
		}
	}
	
	public boolean rightHitPaddle()
	{
		boolean didHit = false;
		if ((pRight.getPos() - ball.diameter) <= ball.getY() && (pRight.getPos() + pRight.height) > ball.getY())
		{
			didHit = true;
		}
		return didHit;
	}
	
	public boolean leftHitPaddle()
	{
		boolean didHit = false;
		if ((pLeft.getPos() - ball.diameter) <= ball.getY() && (pLeft.getPos() + pLeft.height) > ball.getY())
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
		
//		if (buttonsPressed.get(RIGHT_UP))
//		{
//			pRight.setPos(pRight.getPos() - pLeft.dy);
//		}
//		
//		if (buttonsPressed.get(RIGHT_DOWN))
//		{
//			pRight.setPos(pRight.getPos() + pLeft.dy);
//		}
	}
	
	public void gameReset()
	{
		ball.reset();
		pLeft.height = leftSetHeight;
		pRight.height = rightSetHeight;
		pLeft.setScore(0);
		pRight.setScore(0);
		rightWin = false;
		leftWin = false;
	}

	public void actionPerformed(ActionEvent e) 
	{
		ball.move();
		checkCollision();
		pRight.setPos((ball.getY() + (ball.diameter / 2) - (pRight.height / 2)));
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