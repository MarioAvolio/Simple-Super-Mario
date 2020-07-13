package SuperMario.view;
import SuperMario.config.Settings;
import SuperMario.model.Bowser;
import SuperMario.model.Game;
import SuperMario.model.Movements;

public class BowserView extends HandlerView
{
	private final static String folder=Settings.foldResources+"/Bowser/";
	private static BowserView instance = null;
	private Bowser bowser;
	
	private BowserView()
	{
		super(14,0,4,3,7,11,10,9,8,12,13,folder); // * SPRITE
	}
	
	public static BowserView getInstance()
	{
		if (instance == null)
			instance = new BowserView();
		return instance;
	}
	

	@Override
	public void update() throws Exception
	{
		
		if (Game.getGame().getBowser()!=null)
			bowser = Game.getGame().getBowser();
		else
			return;

		if (bowser.isDie()) // * SET DEATH SPRITE
		{
			if (bowser.getDirection() == Movements.MOVE_LEFT )
				setSprite(getDieLeft());
			else if (bowser.getDirection() == Movements.MOVE_RIGHT )
				setSprite(getDieRight());
		}	
		else if (bowser.isStop()) // * SET STOP SPRITE
		{
			switch(bowser.getLastDirection())
			{
			case Movements.MOVE_RIGHT:
				if (!bowser.isNotTheSameI())
					setSprite(getShotRight());
				else
					setSprite(getStartSpriteRight());
				break;
			case Movements.MOVE_LEFT:
				if (!bowser.isNotTheSameI())
					setSprite(getShotLeft());
				else
					setSprite(getStartSpriteLeft());
				break;
			default:
				setSprite(getStartSpriteRight());
				if (bowser.getDirection() == Movements.MOVE_LEFT)
				{
					setSprite(getStartSpriteLeft());
				}
				break;
			}
		}
		else  // * SET MOVEMENT SPRITE
		{
			if (bowser.getDirection() == Movements.MOVE_RIGHT || bowser.getDirection() == Movements.MOVE_LEFT)
			{
				super.moveGeneral(bowser.getDirection()); //sprite right left
			}
			else if (bowser.getDirection() == Movements.MOVE_UP)
			{
				setSprite(getJumpRight());
				if (bowser.getLastDirection() == Movements.MOVE_LEFT)
				{
					setSprite(getJumpLeft());
				}
			}
		}
		
		super.update();
	}
	
}
