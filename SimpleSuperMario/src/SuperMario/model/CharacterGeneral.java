package SuperMario.model;

abstract public class CharacterGeneral extends Point
{
	private int direction;
	private int id;
	
	public CharacterGeneral(int i, int j,int direction,int id)
	{
		super(i, j);
		this.setDirection(direction);
		this.id=id;
	}
	
	public CharacterGeneral(int i, int j,int id) 
	{
		this(i,j,Movements.MOVE_RIGHT,id);
	}

	final public int getDirection()
	{
		return direction;
	}

	final public void setDirection(int direction) 
	{
		this.direction = direction;
	}

	final public int getId() 
	{
		return id;
	}
	
}
