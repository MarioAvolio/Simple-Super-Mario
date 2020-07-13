package SuperMario;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import SuperMario.config.Settings;
import SuperMario.controller.GameControl;
import SuperMario.music.Sound;
import SuperMario.view.GamePanel;

public class Main
{
	private static Sound soundLoop;//background sound
	
	public static void main(String[] args)
	{
		
		/*
		 *  MARIO AVOLIO
		 *  MATRICOLA : 200737
		 */
		
		JFrame f=new JFrame();
		
		
		/*** GAME PANEL ***/
		GamePanel gamePanel=new GamePanel();
		gamePanel.addKeyListener(new GameControl());
		soundLoop = new Sound("sound");
		soundLoop.startLoop();
		
		/*** CHANGE PANEL ***/
		ChangePanel.init(f, gamePanel);
		
		f.setTitle("Simple Super Mario");
		try
		{
			ImageIcon logo = new ImageIcon(Settings.foldResources2+"logo.png");
			f.setIconImage(logo.getImage());
		}
		catch(Exception e)
		{
			return;
		}
		
		f.setUndecorated(true); // Remove title bar
		f.setVisible(true);
		f.setSize(Settings.WIDTH,Settings.HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameLoop thread=new GameLoop(gamePanel);
		thread.start();
	}
	
	public static void startBackgroundSound()
	{
		try
		{
			soundLoop.startLoop();
		}
		catch (Exception e)
		{
			return;
		}
	}
	
	public static void stopBackgroundSound()
	{
		try
		{
			soundLoop.stop();
		}
		catch (Exception e)
		{
			return;
		}
	}

}
