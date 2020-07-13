package SuperMario.model;
import SuperMario.config.Settings;

public class Crocodile extends Mob
{
	public Crocodile(int i, int j, int direction)
	{
		super(i, j, direction, Settings.CROCODILE);
	}

	public Crocodile(int i, int j)
	{
		this(i, j, Movements.MOVE_RIGHT);
	}
}
