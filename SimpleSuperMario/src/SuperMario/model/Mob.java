package SuperMario.model;

abstract public class Mob extends Character2M
{
	public Mob(int i, int j,int direction,int mobId)
	{
		super(i, j,direction,mobId);
	}

	public Mob(int i, int j,int mobId) 
	{
		super(i, j,mobId);
	}
	
}
