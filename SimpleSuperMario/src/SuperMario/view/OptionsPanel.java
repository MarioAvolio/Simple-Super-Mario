package SuperMario.view;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import SuperMario.ChangePanel;
import SuperMario.config.Settings;
import SuperMario.controller.GameControl;
import SuperMario.model.Game;

public class OptionsPanel extends GeneralPanel 
{
	private static final long serialVersionUID = 1L;
	private JButton cont;
	private JButton restart;
	private JButton end;
	
	
	public OptionsPanel() 
	{
		super("Options", Settings.WIDTH, Settings.HEIGHT);
		addEnd();
		addCont();
		addRestart();
		addKeyListener(this);
	}
	
	private void addEnd()// * ADD END BUTTON
	{
		Image exitImage = null;
		try 
		{
			Image tmp = ImageIO.read(getClass().getResource(folder+"end.png"));
			exitImage = tmp.getScaledInstance(widthButton, heightButton, Image.SCALE_SMOOTH);			
		}
		catch (Exception e) 
		{
			return;
		}
		
		end = new JButton(new ImageIcon(exitImage));
		super.setButton(end);
		
		end.addActionListener(new ActionListener() //Anonymous Class
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);								
			}
		});
	}
	
	private void addCont()// * ADD CONTINUE BUTTON
	{
		Image contImage = null;
		try 
		{
			Image tmp = ImageIO.read(getClass().getResource(folder+"cont.png"));
			contImage = tmp.getScaledInstance(widthButton, heightButton, Image.SCALE_SMOOTH);			
		}
		catch (Exception e) 
		{
			return;
		}
		
		cont = new JButton(new ImageIcon(contImage));
		super.setButton(cont);
		
		/**** ACTION ****/
		cont.addActionListener(new ActionListener() //Anonymous Class
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				goToGamePanel();
			}
		});
	}
	
	private void goToGamePanel()
	{
		GameControl.stopGame = false;
		ChangePanel.goToGamePanel();
	}
	
	private void addRestart()// * ADD RESTART BUTTON
	{
		Image restartImage = null;
		try 
		{
			Image tmp = ImageIO.read(getClass().getResource(folder+"restart.png"));
			restartImage = tmp.getScaledInstance(widthButton, heightButton, Image.SCALE_SMOOTH);			
		}
		catch (Exception e) 
		{
			return;
		}
		
		restart = new JButton(new ImageIcon(restartImage));
		super.setButton(restart);
		
		/**** ACTION ****/
		restart.addActionListener(new ActionListener() //Anonymous Class
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				GameControl.stopGame = false;
				Game.reset();
				ChangePanel.goToGamePanel();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	}
	

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			goToGamePanel();
		}
	}

}	
