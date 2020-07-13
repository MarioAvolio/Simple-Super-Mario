package SuperMario.model;

import SuperMario.config.Settings;

public class Bowser extends Character4M
{
	private int lastDirection;
	private boolean stop;
	private boolean notTheSameI;
	private boolean die;
	
	public Bowser(int i, int j)
	{
		super(i, j,Movements.MOVE_LEFT,Settings.BOWSER);
		setStop(true);
		setNotTheSameI(false);
		setDie(false);
		lastDirection = Movements.MOVE_LEFT;
	}
	
	public int getLastDirection() 
	{
		return lastDirection;
	}
	
	public void setLastDirection(int lastDirection)
	{
		this.lastDirection = lastDirection;
	}
	
	public boolean isStop()
	{
		return stop;
	}
	
	public void setStop(boolean stop)
	{
		this.stop = stop;
	}
	
	public boolean isNotTheSameI() 
	{
		return notTheSameI;
	}
	
	public void setNotTheSameI(boolean notTheSameI) 
	{
		this.notTheSameI = notTheSameI;
	}
	
	public boolean isDie()
	{
		return die;
	}
	
	public void setDie(boolean die) 
	{
		this.die = die;
	}
	
}
