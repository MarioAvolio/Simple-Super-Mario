package SuperMario.view;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import SuperMario.config.Settings;

abstract public class GeneralPanel extends JPanel implements KeyListener // * multiple inheritance
{
	private static final long serialVersionUID = 1L;
	private Image background;
	protected int width;
	protected int height;
	protected final int widthButton = Settings.dimensionButtonX;
	protected final int heightButton = Settings.dimensionButtonY;
	protected String folder;
	private GridBagConstraints gbc;
	
	public GeneralPanel(String title,int width,int height) 
	{
		this.width = width;
		this.height = height;
		folder = Settings.foldResources+"/"+title+"/";
		
		// * SET LAYOUT
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		// * SET TITLE
		TitledBorder border = BorderFactory.createTitledBorder(title);
		setBorder(border);
		
		// * CENTER BUTTON
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		setFocusable(true);
		addBackground();
	}
	
	final protected void setButton(JButton button)// * ADD BUTTON
	{
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		add(button,gbc);// * ADD BUTTON AT THE CENTER OF FRAME
	}
	
	final private void addBackground()
	{
		try 
		{
			Image tmp = ImageIO.read(getClass().getResource(folder+"background.png"));
			background = tmp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		}
		catch (Exception e) 
		{
			return;
		}
	}
	
	/****** DRAW *******/
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(background,0,0,null);
	}

	@Override
	abstract public void keyPressed(KeyEvent e);

	@Override
	final public void keyTyped(KeyEvent e) {}

	@Override
	final public void keyReleased(KeyEvent e) {}
}
