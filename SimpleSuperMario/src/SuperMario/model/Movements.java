package SuperMario.model;
import java.util.HashMap;
import java.util.Map;

import SuperMario.config.Settings;


abstract public class Movements 
{
	public final static int MOVE_RIGHT = 0;
	public final static int MOVE_LEFT = 1;
	public final static int MOVE_DOWN = 2;
	public final static int MOVE_UP=3;
	private static Map<Integer,Integer> reverse=new HashMap<>();//it serves for collision
	
	public static void initialize()
	{
		reverse.put(MOVE_RIGHT,MOVE_LEFT);
		reverse.put(MOVE_LEFT,MOVE_RIGHT);
		reverse.put(MOVE_UP,MOVE_DOWN);
		reverse.put(MOVE_DOWN,MOVE_UP);
	}
	
	private static void movementView(Character4M character) throws Exception
	{
		//increse view if it don't go out of the border
		if (character instanceof SuperMario)
		{
			if (character.j>=Settings.visualizedColumns/2+Settings.startView)
				Game.getGame().increseView();
			else
				Game.getGame().decreseView();
		}

	}
	
	@SuppressWarnings("null")
	public static int getReverse(int direction)
	{
		try
		{
			return reverse.get(direction);
		}
		catch(Exception e)
		{
			return (Integer) null;
		}
	}
	
	public static boolean move(int direction,Character4M character)
	{
		try
		{
			switch(direction)
			{
				case MOVE_RIGHT:
					character.moveRight();
					movementView(character);//camera view
					break;
				case MOVE_LEFT:
					character.moveLeft();
					movementView(character);//camera view
					break;
				case MOVE_DOWN:
					character.moveDown();
					break;
				case MOVE_UP:
					character.moveUp();
					break;
				default:
					break;
			}

			return collision(character);
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	
	private static boolean collision(Character4M character) throws Exception
	{		
		int i2=character.i;
		int j2=character.j;
		int value=Game.getGame().getMatrixValue(i2, j2);
			
		boolean condition = value!=Settings.SPACE && value!=Settings.SHOOT && value!=Settings.FINISH;
		
		if (Game.getGame().goOutBorder(i2, j2) || condition)
			return true;
		
		return false;		
	}
	
	public static void goBack(int direction,Character4M character)//if will be a collision
	{
		try
		{
			move(getReverse(direction),character);	
		}
		catch (Exception e)
		{
			return;
		}
	}
	
	
	public static boolean moveShoot(Shoot shot)//this move a single shot 
	{
		try
		{
			return move2D(shot, shot.getId());
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static boolean moveMobs(Mob mob)
	{
		try
		{
			return move2D(mob, mob.getId());
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	private static boolean move2D(Character2M character,int value) throws Exception//Upcast always OK!
	{
		int i=character.i, j=character.j;
		
		switch(character.getDirection())
		{
			case MOVE_RIGHT:
				character.moveRight();
				break;
			case MOVE_LEFT:
				character.moveLeft();
				break;
			default:
				break;
		}
		
		int valueOldMatrix =  Game.getGame().getMatrixValue(i, j);
		int valueMatrix = Game.getGame().getMatrixValue(character.i, character.j);
		
		if(valueOldMatrix == value)//movement
		{
			Game.getGame().setMatrixValue(i, j, Settings.SPACE);//set space to old position
		}
		
		if(valueMatrix != Settings.SPACE )//collision
		{
			if(character instanceof Mob)
				if(!Settings.isaMob(valueMatrix))
					return false;
			if (character instanceof Shoot && valueMatrix != Settings.FINISH)
			{
				return false;
			}
		}
		
		
		Game.getGame().setMatrixValue(character.i,character.j,value);
		return true;
	}
	
	public static boolean collisionPoint(Point p1,Point p2)//Upcast always OK!
	{
		try
		{
			return (p1.i==p2.i && p1.j==p2.j);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	
}
