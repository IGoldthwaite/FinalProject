import java.awt.Frame;


public class FrameLoader 
{
	public static void main(String[] args)
	{
		Frame frm = new PongMain();
		frm.setVisible(true);
		frm.repaint();
	}
}
