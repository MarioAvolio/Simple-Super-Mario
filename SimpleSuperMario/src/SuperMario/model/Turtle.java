package SuperMario.model;
import SuperMario.config.Settings;

public class Turtle extends Mob
{
	public Turtle(int i, int j, int direction) 
	{
		super(i, j, direction,Settings.TURTLE);

	}

	public Turtle(int i, int j) 
	{
		this(i,j,Movements.MOVE_LEFT);
	}
}
