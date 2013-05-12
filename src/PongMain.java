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
	
	boolean leftWin = false, rightWin = false, leftHit = false, rightHit = false, counting = false, hasHitLeft = false, hasHitRight = false, leftMoving = false, rightMoving = false;
	int counter = 0, hitCount = 0;
	
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
		
		ball = new Ball(20);
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
		System.out.println(hitCount);
		
		//clear off previous image
		graphics.clearRect(0,0,WIDTH,HEIGHT);
		
		//draw paddles
		if (leftHit)
		{
			graphics.setColor(Color.red);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.setColor(Color.blue);
			graphics.fillRect(pRight.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
		}
		else if (rightHit)
		{
			graphics.setColor(Color.blue);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.setColor(Color.red);
			graphics.fillRect(pRight.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
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
		
		//draw ball and trail
		graphics.setColor(Color.red.darker().darker().darker().darker());
		graphics.fillOval(ball.getX() - ball.dx*2, ball.getY() - ball.dy*2, ball.diameter - 2, ball.diameter - 2);
		graphics.setColor(Color.red.darker().darker());
		graphics.fillOval(ball.getX() - ball.dx, ball.getY() - ball.dy, ball.diameter - 1, ball.diameter - 1);
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
	
	public void ballHit(String side, Ball ball)
	{
		if (side == "left")
		{
			if ((ball.getX() <= (pLeft.XPOS_LEFT+10)) && leftHitPaddle(ball) && !hasHitLeft)
			{
				hitCount++;
//				leftHit = true;
//				counting = true;
//				hasHitLeft = true;
				ball.dx = (ball.dx * -1);
				if (ball.dx > 0)
				{
					ball.dx += 1;
				}
				else
				{
					ball.dx -= 1;
				}
			}
		}
		
		else if (side == "right")
		{
			if (((ball.getX() + ball.diameter) >= pRight.XPOS_RIGHT) && rightHitPaddle(ball) && !hasHitRight)
			{
				hitCount++;
//				rightHit = true;
//				counting = true;
//				hasHitRight = true;
				ball.dx = (ball.dx * -1);
				if (ball.dx > 0)
				{
					ball.dx += 1;
				}
				else
				{
					ball.dx -= 1;
				}
			}
		}
	}
	
	public void ballScore(String side, Ball ball)
	{
		if (side == "left")
		{
			if (ball.getX() <= 8 && !leftHitPaddle(ball))
			{
				hitCount = 0;
				pRight.setScore(pRight.getScore() + 1);
				ball.reset();
				pLeft.setHeight(leftSetHeight);
			}
		}
		
		else if (side == "right")
		{
			if ((ball.getX() + ball.diameter) >= PongMain.WIDTH - 8 && !rightHitPaddle(ball))
			{
				hitCount = 0;
				pLeft.setScore(pLeft.getScore() + 1);
				ball.reset();
				pRight.setHeight(rightSetHeight);
			}
		}
	}
	
	public void checkCollision()
	{
		if (counting)
		{
			counter++;
			pLeft.setHeight(pLeft.height - 1);
//			ball.setDiameter(ball.diameter - 1);
		}
		
		if (counter > 10)
		{
			hasHitLeft = false;
			hasHitRight = false;
			leftHit = false;
			rightHit = false;
			counting = false;
			counter = 0;
		}
		
		//ball hits left paddle
		ballHit("left", ball);
		
		//ball hits right paddle
		ballHit("right", ball);
		
		//ball goes past left paddle
		ballScore("left", ball);
		
		//ball goes past right paddle
		ballScore("right", ball);
	}
	
	public boolean rightHitPaddle(Ball ball)
	{
		boolean didHit = false;
		if ((pRight.getPos() - ball.diameter) <= ball.getY() && (pRight.getPos() + pRight.height) > ball.getY())
		{
			didHit = true;
		}
		return didHit;
	}
	
	public boolean leftHitPaddle(Ball ball)
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
		
		if (buttonsPressed.get(RIGHT_UP))
		{
			pRight.setPos(pRight.getPos() - pRight.dy);
		}
		
		if (buttonsPressed.get(RIGHT_DOWN))
		{
			pRight.setPos(pRight.getPos() + pRight.dy);
		}
	}
	
	public void gameReset()
	{
		ball.reset();
		hitCount = 0;
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
//		pRight.setPos((ball.getY() + (ball.diameter / 2) - (pRight.height / 2)));
//		pLeft.setPos((ball.getY() + (ball.diameter / 2) - (pLeft.height / 2)));
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