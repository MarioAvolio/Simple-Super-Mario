
package SuperMario.controller;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import SuperMario.model.SuperMario;
import SuperMario.GameLoop;
import SuperMario.model.Game;
import SuperMario.model.Movements;


public class GameControl extends KeyAdapter
{
	public static boolean jumpOn = false;//to not jump so much
	public static boolean goLeft = false;
	public static boolean goRight = false;
	public static boolean spaceShot = false;
	public static boolean stop = true;
	public static boolean stopGame = false;
	public static int lastMove = Movements.MOVE_RIGHT;
	private SuperMario superMario;
	private Game game;
	
	public static void reset() // RESET MOVEMENTS
	{
		jumpOn = false;
		goLeft = false;
		goRight = false;
		spaceShot = false;
		stop = true;
		stopGame = false;
		lastMove = Movements.MOVE_RIGHT;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		game = Game.getGame();
		stop = false;
		spaceShot = false;
		superMario = game.getSuperMario();
		
		if (GameLoop.stop)
			return;
		
		//gestione delle animazioni e dei moviementi
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE:
			stopGame = true;
			break;
			
		case KeyEvent.VK_RIGHT:	// * GO RIGHT	
			
			game.move(Movements.MOVE_RIGHT,superMario);
			goRight=true;
			lastMove=Movements.MOVE_RIGHT;
			break;
			
		case KeyEvent.VK_LEFT:	// * GO LEFT	
			
			game.move(Movements.MOVE_LEFT,superMario);	
			goLeft = true;
			lastMove = Movements.MOVE_LEFT;
				break;
				
		case KeyEvent.VK_UP: // * JUMP
			
			if (game.onAir() || game.thereIsBlocksOnMe())
				break;
			
			if (!jumpOn)
			{
				jumpOn=true;
				game.move(Movements.MOVE_UP,superMario);//jump
				
				//jump right or left
				if(goRight)
					game.move(Movements.MOVE_RIGHT,superMario);
				else if (goLeft)
					game.move(Movements.MOVE_LEFT,superMario);	
			}
			
			break;
		
		case KeyEvent.VK_SPACE:// * SHOT
			
			if (!Game.getGame().minDistanceForShooting())
				break;
			
			spaceShot = true;
			game.shooting(lastMove,superMario);
			break;	
			
		default :
			stop = true;
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		stop = true;
		
		//restore default animations
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
			goRight=false;
			break;
			
		case KeyEvent.VK_LEFT:
			goLeft=false;
				break;
				
		case KeyEvent.VK_SPACE:
			spaceShot = false;
			break;
		}
	}
}
