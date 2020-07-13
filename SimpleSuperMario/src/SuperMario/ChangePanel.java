package SuperMario;
import javax.swing.JFrame;
import javax.swing.JPanel;
import SuperMario.view.GamePanel;
import SuperMario.view.MenuPanel;
import SuperMario.view.OptionsPanel;

public class ChangePanel
{
	private static JFrame f;
	private static GamePanel gamePanel;
	private static MenuPanel menuPanel;
	private static OptionsPanel optionPanel;
	
	public static void init(JFrame f, GamePanel gamePanel) 
	{
		ChangePanel.f = f;
		ChangePanel.gamePanel = gamePanel;
		ChangePanel.menuPanel = new MenuPanel();
		ChangePanel.optionPanel = new OptionsPanel();
		f.add(menuPanel);
	}

	public static void goToGamePanel()
	{
		addPanel(gamePanel);
	}
	
	private static void addPanel(JPanel panel) // * GENERAL ADD PANEL
	{
		f.getContentPane().removeAll();
		f.add(panel);
		panel.revalidate();
		panel.setFocusable(true);
		panel.requestFocus();
		f.repaint();
	}
	
	public static void goToOptionPanel()
	{
		addPanel(optionPanel);
	}
	
	public static void goToMenuPanel()
	{
		addPanel(menuPanel);
	}
	
}
