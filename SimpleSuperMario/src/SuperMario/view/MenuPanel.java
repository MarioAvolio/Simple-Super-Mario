package SuperMario.view;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import SuperMario.ChangePanel;
import SuperMario.config.Settings;

public class MenuPanel extends GeneralPanel 
{
	private static final long serialVersionUID = 1L;
	private JButton start;
	private JButton end;


	public MenuPanel()
	{
		super("Menu", Settings.WIDTH, Settings.HEIGHT);
		addStart();
		addEnd();
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
		catch (IOException e) 
		{
			return;
		}
		
		end = new JButton(new ImageIcon(exitImage));
		end.addActionListener(new ActionListener() //Anonymous Class
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);								
			}
		});
		super.setButton(end);
	}
	
	private void addStart()// * ADD START BUTTON
	{
		Image startImage = null;
		try 
		{
			Image tmp = ImageIO.read(getClass().getResource(folder+"start.png"));
			startImage = tmp.getScaledInstance(widthButton, heightButton, Image.SCALE_SMOOTH);			
		}
		catch (IOException e) 
		{
			return;
		}
		
		start = new JButton(new ImageIcon(startImage));
		super.setButton(start);
		
		/**** ACTION ****/
		start.addActionListener(new ActionListener() //Anonymous Class
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				GamePanel.stopAll = false;
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
			System.exit(0);
	}

		
}
