import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class PongMain extends Frame implements MouseMotionListener, ActionListener
{
	Ball ball;
	PaddleHuman pLeft;
	PaddleComputer pRight;
	Graphics graphics;
	BufferedImage screen;
	Timer time;
	
	static final int WIDTH = 500;
	static final int HEIGHT = 300;
	long currentTime;
	boolean running = false;
	
	public PongMain()
	{
		System.out.println("Constructing frame starting...");
		setSize(WIDTH, HEIGHT);
		ball = new Ball();
		pLeft = new PaddleHuman(30);
		pRight = new PaddleComputer(70, ball.getY() - 35);
		addMouseMotionListener(this);
		setBackground(Color.white);
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics = screen.getGraphics();
		time = new Timer(15, this);
		time.start();
		System.out.println("Constructing frame finished.");
	}
	
	public void stop()
	{
		System.out.println("stop() called.");
	}
	
	public void paint(Graphics g)
	{
		System.out.println("paint() running...");
		System.out.println(pRight.score);
		//first, we clear off whatever was previously on the image
		graphics.clearRect(0,0,WIDTH,HEIGHT); 
		
		//we now draw our two paddles in blue
		graphics.setColor(Color.blue);
		
		//the XPOS is a final int, so it never changes, but the 
		//yPos does. We make the paddles 10 * 70.
		//Left side
		graphics.fillRect(pLeft.XPOS,pLeft.getPos(), 10, pLeft.height);
		
		//Right side
		graphics.fillRect(pRight.XPOS, pRight.getPos(), 10, pRight.height);
		
		//this draws our mid-court line and our scores in white
		graphics.setColor(Color.white);
		
		//we show our player's hopeless circumstances
		graphics.drawString("Futile", 150, 15);
		
		//we get the score from our PaddleRight object
		graphics.drawString(""+ pRight.getScore(),300,15);
		
		//mid-court divider
		//graphics.fillRect(240,0,20,300);
		
		/*if (pRight.score == 3)
		{
			graphics.drawString("YOU LOSE", 150, 150);
			this.time.stop();
		}*/
		
		//we draw the ball
		graphics.setColor(Color.red);
		
		graphics.fillRect(ball.getX(),ball.getY(),10,10);
		
		//finally, we draw the offscreen image to the applet
		g.drawImage(screen,0,0,this);
		
		//this line makes sure that all the monitors are up to date before 
		//proceeding
		Toolkit.getDefaultToolkit().sync();
		System.out.println("paint() finished.");
	}
	
	public void update(Graphics g)
	{
		System.out.println("update() running...");
		paint(g);
		System.out.println("update() finished.");
	}
	
	public void checkCollision()
	{
		System.out.println("checkCollision() running...");
		if (ball.getY() == 0 || ball.getY() == 290)
		{
			ball.dy = (ball.dy * -1);
		}
		
		if ((ball.getX() <= 40) && hitPaddle())
		{
			ball.dx = (ball.dx * -1);
		}
		
		if (ball.getX() >= 460)
		{
			ball.dx = (ball.dx * -1);
		}
		
		if (ball.getX() == 0)
		{
			pRight.setScore(pRight.getScore() + 1);
			ball.reset();
		}
		System.out.println("checkCollision() finished.");
	}
	
	public boolean hitPaddle()
	{
		System.out.println("hitPaddle() running...");
		boolean didHit = false;
		if ((pLeft.getPos() - 10) <= ball.getY() && (pLeft.getPos() + pLeft.height) > ball.getY())
		{
			didHit = true;
		}
		System.out.println("hitPaddle() finished.");
		return didHit;
	}

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("actionPerformed() running...");
		ball.move();
		pRight.setPos(ball.getY() - (pRight.height/2));
		checkCollision();
		repaint();
		System.out.println("actionPerformed() finished.");
	}

	public void mouseDragged(MouseEvent arg0) 
	{
		
	}

	public void mouseMoved(MouseEvent arg0) 
	{
		pLeft.setPos(arg0.getY() - 35);
	}
	
	public static void main(String[] args)
	{
		System.out.println("main starting...");
		Frame frm = new PongMain();
		frm.setVisible(true);
		frm.repaint();
	}
}