package SuperMario.model;

abstract public class Character4M extends CharacterGeneral
{	
	public Character4M(int i, int j, int direction, int id) 
	{
		super(i, j, direction, id);
	}

	public Character4M(int i, int j, int id)
	{
		super(i, j, id);
	}

	final public void moveRight()
	{
		this.j+=1;
	}
	
	final public void moveLeft()
	{
		this.j-=1;
	}
	
	final public void moveDown()
	{
		this.i+=1;
	}
	
	final public void moveUp()
	{
		this.i-=1;
	}
}
