package SuperMario.view;

import java.awt.Image;

import javax.swing.ImageIcon;

import SuperMario.config.Settings;
import SuperMario.model.Game;

public class ShotView 
{
	private Image imageShot;
	private static ShotView instance;
	
	private ShotView()
	{
		try
		{
			instance=null;
			imageShot=new ImageIcon(getClass().getResource(Settings.foldResources+"/Shot/shot.gif")).getImage();
		}
		catch (Exception e) 
		{
			return;
		}
	}

	public static ShotView getInstance()
	{
		if(instance==null)
			instance=new ShotView();
		return instance;
	}
	
	public Image getImageShot() throws Exception
	{
		return imageShot;
	}
	
	public void update() throws Exception
	{
		Game.getGame().moveShoots();
	}
}
