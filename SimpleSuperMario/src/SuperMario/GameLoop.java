package SuperMario;
import SuperMario.controller.GameControl;
import SuperMario.model.Game;
import SuperMario.view.GamePanel;

public class GameLoop extends Thread
{
	private GamePanel panel;
	public static boolean stop;
	
	public GameLoop(GamePanel panel) 
	{
		stop = false;
		this.panel=panel;
	}
	
	@Override
	public void run() 
	{
		super.run();
		while(true)
		{
			try 
			{
				Thread.sleep(120);
				
				if (!GameControl.stopGame)
				{
					if (!GamePanel.stopAll)
					{
						Game g=Game.getGame();
						g.gravity();
						g.moveMOB();
						g.moveBowser();
						panel.update();
					}
					
					stop = GamePanel.stopAll;
				}
				else
				{
					ChangePanel.goToOptionPanel(); // open Options Panel
				}
				
			}
			catch (Exception e) 
			{
				break;
			}
			
		}
	}
}
