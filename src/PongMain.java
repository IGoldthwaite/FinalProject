
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
	static boolean SINGLE_PLAYER = false, RIGHT_SERVE = false;	
	static Ball ball;
	static HumanPaddle pLeft, pRight;
	static AI pAI;
	
	Graphics graphics;
	BufferedImage screen;
	Timer time;
	Font newFont = new Font("font1", Font.BOLD, 20);
	
	static final int WIDTH = 1300, HEIGHT = 600;
	
	static int leftSetHeight, rightSetHeight, AISetHeight, ballSetDiameter, leftSetDy, rightSetDy, AISetDy;
	
	int hitCount = 0;
	
	boolean leftWin, rightWin, grow, ballGrow, party;
	
	public PongMain()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Pong Game");
		addKeyListener(this);
		
		ball = new Ball(20);
		pLeft = new HumanPaddle("left", 'w', 's');
		pRight = new HumanPaddle("right", '8', '2');
		pAI = new AI("right", "playMaker");
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		
		ballSetDiameter = ball.diameter;
		leftSetHeight = pLeft.height;
		leftSetDy = pLeft.dy;
		
		if (SINGLE_PLAYER)
		{
			AISetHeight = pAI.height;
			AISetDy = pAI.dy;
		}
		else
		{
			rightSetHeight = pRight.height;
			rightSetDy = pRight.dy;
		}
		
		//calles actionPerformed every 67 times per second (1000 / 15);
		time = new Timer(15, this);
		time.start();
		
		//allows for exiting via the frame's X button
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	/**
	 * draws and displays the game: the frame, paddles, ball, and scores.
	 */
	public void paint(Graphics g)
	{
		graphics.clearRect(0,0,WIDTH,HEIGHT);
		
		if (SINGLE_PLAYER)
		{
			//draws paddles
			graphics.setColor(pLeft.color);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.setColor(pAI.color);
			graphics.fillRect(pAI.XPOS_RIGHT, pAI.getPos(), 10, pAI.height);
			//draws the scores
			graphics.setColor(Color.white);
			graphics.setFont(newFont);
			graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 100, 50);
			graphics.drawString(""+ pAI.getScore(), (WIDTH / 2) + 50, 50);
			//draws the ball and ball trail
			graphics.setColor(Color.red.darker().darker().darker().darker());
			graphics.fillOval(ball.getX() - ball.dx*2, ball.getY() - ball.dy*2, ball.diameter - 2, ball.diameter - 2);
			graphics.setColor(Color.red.darker().darker());
			graphics.fillOval(ball.getX() - ball.dx, ball.getY() - ball.dy, ball.diameter - 1, ball.diameter - 1);
			graphics.setColor(Color.red);
			graphics.fillOval(ball.getX(), ball.getY(), ball.diameter, ball.diameter);
			graphics.setColor(Color.red);
			//if the AI wins
			if (pAI.getScore() >= 5 && !leftWin)
			{
				rightWin = true;
				graphics.drawString("YOU LOSE", (WIDTH / 2) - 70, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 120, 150);
				party = true;
			}
			//if the human player wins
			else if (pLeft.getScore() >= 5 && !rightWin)
			{
				leftWin = true;
				graphics.drawString("YOU WIN", (WIDTH / 2) - 65, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 120, 150);
				party = true;
			}
		}
		else
		{
			//draw paddles
			graphics.setColor(Color.blue);
			graphics.fillRect(pLeft.XPOS_LEFT, pLeft.getPos(), 10, pLeft.height);
			graphics.fillRect(pLeft.XPOS_RIGHT, pRight.getPos(), 10, pRight.height);
			//draw scores
			graphics.setColor(Color.white);
			graphics.setFont(newFont);
			graphics.drawString(""+ pLeft.getScore(), (WIDTH / 2) - 50, 50);
			graphics.drawString(""+ pRight.getScore(), (WIDTH / 2) + 50, 50);
			//draw ball and ball trail
			graphics.setColor(Color.red.darker().darker().darker().darker());
			graphics.fillOval(ball.getX() - ball.dx*2, ball.getY() - ball.dy*2, ball.diameter - 2, ball.diameter - 2);
			graphics.setColor(Color.red.darker().darker());
			graphics.fillOval(ball.getX() - ball.dx, ball.getY() - ball.dy, ball.diameter - 1, ball.diameter - 1);
			graphics.setColor(Color.red);
			graphics.fillOval(ball.getX(), ball.getY(), ball.diameter, ball.diameter);
			
			//if the right player wins
			if (pRight.getScore() >= 5 && !leftWin)
			{
				rightWin = true;
				graphics.drawString("YOU WIN", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU LOSE", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 80, 150);
				party = true;
			}
			//if the left player wins
			else if (pLeft.getScore() >= 5 && !rightWin)
			{
				leftWin = true;
				graphics.drawString("YOU LOSE", (WIDTH / 2) + 230, 100);
				graphics.drawString("YOU WIN", (WIDTH / 2) - 300, 100);
				graphics.drawString("Press R To Play Again", (WIDTH /2) - 85, 150);
				party = true;
			}
		}
		//when one side wins
		if (party)
		{
			if (ball.diameter > 50)
			{
				grow = false;
			}
			else if (ball.diameter < 10)
			{
				grow = true;
			}
			if (ball.dy > 15)
			{
				ballGrow = false;
			}
			else if (ball.dy < -15)
			{
				ballGrow = true;
			}
			if (grow)
			{
				pLeft.height += 1;
				pAI.height += 1;
				pRight.height += 1;
				ball.diameter += 1;
			}
			else
			{
				pLeft.height -= 1;
				pAI.height -= 1;
				pRight.height -= 1;
				ball.diameter -= 1;
			}
			if (ballGrow)
			{
				ball.dy += 1;
			}
			else
			{
				ball.dy -= 1;
			}
		}
		g.drawImage(screen,0,0,this);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	/**
	 * checks for ball collision and score updates
	 */
	public void checkCollision()
	{
		pLeft.checkHits(ball);
		if (SINGLE_PLAYER)
		{
			pAI.checkHits(ball);
		}
		else
		{
			pRight.checkHits(ball);
		}
	}
	
	/**
	 * resets the entire game back to its starting condition
	 */
	public void gameReset()
	{
		RIGHT_SERVE = false;
		hitCount = 0;
		ball.reset();
		pLeft.reset();
		rightWin = false;
		leftWin = false;
		party = false;
		
		if (SINGLE_PLAYER)
		{
			pAI.reset();
		}
		else
		{
			pRight.reset();
		}
	}

	/**
	 * called by the timer every 67 times per second (1000/15)
	 */
	public void actionPerformed(ActionEvent e) 
	{
		ball.move();
		pLeft.act();
		checkCollision();
		repaint();
		if (SINGLE_PLAYER)
		{
			pAI.act();
		}
		else
		{
			pRight.act();
		}
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
		
		if (SINGLE_PLAYER)
		{
			if (key == pLeft.upButton)
			{
				pLeft.up = true;
			}
			if (key == pLeft.downButton)
			{
				pLeft.down = true;
			}
		}
		else
		{
			if (key == pLeft.upButton)
			{
				pLeft.up = true;
			}
			if (key == pLeft.downButton)
			{
				pLeft.down = true;
			}
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
		
		if (SINGLE_PLAYER)
		{
			if (key == pLeft.upButton)
			{
				pLeft.up = false;
			}
			if (key == pLeft.downButton)
			{
				pLeft.down = false;
			}
		}
		else
		{
			if (key == pLeft.upButton)
			{
				pLeft.up = false;
			}
			if (key == pLeft.downButton)
			{
				pLeft.down = false;
			}
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
	
	//defines and displays the frame.
	public static void main(String[] args)
	{
		Frame frm = new PongMain();
		frm.setVisible(true);
		frm.repaint();
	}
}