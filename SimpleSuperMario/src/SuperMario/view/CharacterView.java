package SuperMario.view;
import SuperMario.config.Settings;
import SuperMario.controller.GameControl;
import SuperMario.model.Game;
import SuperMario.model.Movements;

public class CharacterView extends HandlerView
{
	private static CharacterView instance = null;
	private final static String folder=Settings.foldResources+"/Pg/";

	private CharacterView() 
	{
		super(12,3,0,5,2,8,9,6,7,10,11,folder);
	}
	
	public static CharacterView getInstance() 
	{
		if(instance == null)
			instance = new CharacterView();
		return instance;
	}
	
	private void jumpFunction() throws Exception
	{
		setSprite(getJumpRight());
		if (GameControl.lastMove == Movements.MOVE_LEFT)
			setSprite(getJumpLeft());
	}
	
	@Override
	public void update() throws Exception
	{		
		if (Game.getGame().gameOver())
		{
			setSprite(getDieRight());
			if (GameControl.lastMove == Movements.MOVE_LEFT)
			{
				setSprite(getDieLeft());	
			}

			super.update();
			return;
		}
		
		
		if (  !GameControl.stop &&  (GameControl.goLeft || GameControl.goRight))
		{
			int direction = Movements.MOVE_RIGHT;
			if (GameControl.goLeft)
				direction = Movements.MOVE_LEFT;
			
			if ( !GameControl.jumpOn )
				super.moveGeneral(direction); //sprite right left
			else if ( GameControl.jumpOn )
			{
				jumpFunction();
			}
		}
	
		else if ( !GameControl.goRight || !GameControl.spaceShot || !GameControl.goLeft)  
		{
			setSprite(getStartSpriteRight());
			if (GameControl.lastMove == Movements.MOVE_LEFT)//stop sprite
				setSprite(getStartSpriteLeft());
		}

		if (GameControl.jumpOn)
		{
			jumpFunction();
		}
		
		if (GameControl.spaceShot)
		{
			setSprite(getShotRight());
			if (GameControl.lastMove == Movements.MOVE_LEFT)
				setSprite(getShotLeft());
		}
		
		
		super.update();
	}
}
