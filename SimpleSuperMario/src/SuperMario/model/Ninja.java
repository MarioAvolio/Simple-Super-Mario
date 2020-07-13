package SuperMario.model;
import SuperMario.config.Settings;

public class Ninja extends Mob
{

	public Ninja(int i, int j, int direction) 
	{
		super(i, j, direction, Settings.NINJA);
	}

	public Ninja(int i, int j) 
	{
		this(i, j, Movements.MOVE_RIGHT);
	}

}
