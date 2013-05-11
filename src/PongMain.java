import java.awt.Color;
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
	
	static final int WIDTH = 1300, 
					HEIGHT = 600;
	
	static final int LEFT_UP = 0, 
					LEFT_DOWN = 1, 
					RIGHT_UP = 2, 
					RIGHT_DOWN = 3;
	
	BitSet bitset = new BitSet(4);
	
	public PongMain()
	{
		setSize(WIDTH, HEIGHT);
		addKeyListener(this);
		setBackground(Color.white);
		
		ball = new Ball();
		pLeft = new PaddleHuman(70);
		pRight = new PaddleHuman(70);
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		
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
		graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 50, 50);
		graphics.drawString(""+ pRight.getScore(), (WIDTH / 2) + 50, 50);
		
		//draw ball
		graphics.setColor(Color.red);
		graphics.fillRect(ball.getX(), ball.getY(), 10, 10);
		
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
		}
		
		//ball goes past right paddle
		if (ball.getX() >= PongMain.WIDTH - 8 && !hitPaddle())
		{
			pLeft.setScore(pLeft.getScore() + 1);
			ball.reset();
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
		if (bitset.get(LEFT_UP))
		{
			pLeft.setPos(pLeft.getPos() - 7);
		}
		
		if (bitset.get(LEFT_DOWN))
		{
			pLeft.setPos(pLeft.getPos() + 7);
		}
		
		if (bitset.get(RIGHT_UP))
		{
			pRight.setPos(pRight.getPos() - 7);
		}
		
		if (bitset.get(RIGHT_DOWN))
		{
			pRight.setPos(pRight.getPos() + 7);
		}
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
	}
	
	public void keyPressed(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == 'w')
		{
			bitset.set(LEFT_UP, true);
		}
		
		if (key == 's')
		{
			bitset.set(LEFT_DOWN, true);
		}
		
		if (key == '2')
		{
			bitset.set(RIGHT_DOWN, true);
		}
		
		if (key == '8')
		{
			bitset.set(RIGHT_UP, true);
		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
		char key = arg0.getKeyChar();
		if (key == 'w')
		{
			bitset.set(LEFT_UP, false);
		}
		
		if (key == 's')
		{
			bitset.set(LEFT_DOWN, false);
		}
		
		if (key == '2')
		{
			bitset.set(RIGHT_DOWN, false);
		}
		
		if (key == '8')
		{
			bitset.set(RIGHT_UP, false);
		}
	}
	
	public static void main(String[] args)
	{
		Frame frm = new PongMain();
		frm.setVisible(true);
		frm.repaint();
	}
}