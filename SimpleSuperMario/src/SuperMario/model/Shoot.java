package SuperMario.model;

import SuperMario.config.Settings;

public class Shoot extends Character2M
{	
	public Shoot(int i, int j,int direction) 
	{
		super(i, j,direction,Settings.SHOOT);
	}
}
