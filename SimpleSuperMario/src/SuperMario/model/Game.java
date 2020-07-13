package SuperMario.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import SuperMario.config.Settings;
import SuperMario.controller.GameControl;

public class Game 
{
	private static Game game=null;
	private static SuperMario superMario;
	private Bowser bowser;
	private int [][] blocks;
	private int rows;
	private int columns;
	private ArrayList<Shoot> shoots;
	private ArrayList<Mob> mobs;
	private ArrayList<Shoot> shootsBowser;
	private boolean gravity;
	private boolean gameOver;
	private Point finish;
	private static int firstLevel;
	private static int livMap = 1;
	private int outBorder = -10;
	private int maxLevel;

	private Game() 
	{	
		Movements.initialize();// *initialize moviments
		maxLevel = 4;
		firstLevel = 1;
		superMario = null;
		bowser = null;
		shoots = new ArrayList<Shoot>();
		mobs = new ArrayList<Mob>();
		shootsBowser = new ArrayList<Shoot>(); 
		gravity=true;
		gameOver=false;
		try(BufferedReader br=new BufferedReader(new FileReader(Settings.foldResources2+"map"+livMap+".txt")))
		{
			// * INIT ROWS AND COLUMNS
			rows=Integer.parseInt(br.readLine());
			columns=Integer.parseInt(br.readLine());
			
			
			// * INIT MATRIX
			blocks=new int[rows][columns];
			
			int i=0;
			while(br.ready())
			{
				// *System.out.println(j);
				String str=br.readLine();
				String []tmp=str.split(" ");
				
				for (int j=0;j<columns;++j)
				{
					blocks[i][j]=Integer.parseInt(tmp[j]);
					
					if(blocks[i][j] == Settings.FINISH)
					{
						finish=new Point(i, j);
					}
					else if(blocks[i][j] == Settings.SUPER_MARIO)
					{
						superMario=new SuperMario(i,j);
					}
					else if (blocks[i][j] == Settings.BOWSER)
					{
						bowser = new Bowser(i, j);
					}
					else if (Settings.isaMob(blocks[i][j]))
					{
						switch(blocks[i][j])
						{
						case Settings.TURTLE:
							mobs.add(new Turtle(i, j));
							break;
						case Settings.NINJA:
							mobs.add(new Ninja(i, j));
							break;
						case Settings.CROCODILE:
							mobs.add(new Crocodile(i, j));
							break;
						}
					}
				}
				++i;
			}
			
		}
		catch (IOException e) 
		{
			return;
		}
		finally
		{	
			Settings.dimensionBlockY = Settings.HEIGHT / rows;
			Settings.startView = 0;
			Settings.finalView = Settings.visualizedColumns - 1;
		}
	}
	
	// ************** UTILITY **************
	
	public boolean onAir() // if SM is in air
	{
		int value = getMatrixValue(superMario.i+1, superMario.j);
		return value == Settings.SPACE;
	}
	
	public boolean thereIsBlocksOnMe() // if there is a block on SM
	{
		int value = getMatrixValue(superMario.i-1, superMario.j);
		return Settings.isaBlock(value);
	}
	
	public boolean minDistanceForShooting()
	{
		try
		{
			final int minDistance = 2;
			int j = superMario.j;
			for (int i = 0; i < minDistance; i++)
			{
				int direction = superMario.getDirection();
				if (direction != Movements.MOVE_LEFT && direction != Movements.MOVE_RIGHT)
					direction = GameControl.lastMove;
				j = nextValueJ(j, direction);
				int value = getMatrixValue(superMario.i, j);
				if (Settings.isaBlock(value) || value == outBorder || Settings.isaMob(value) || value == Settings.BOWSER)
					return false;
			}
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	
	private int nextValueJ(int j,int direction)
	{
		switch (direction)
		{
		case Movements.MOVE_RIGHT:
			++j;
			break;
		case Movements.MOVE_LEFT:
			--j;
			break;
		default:
			break;
		}
		return j;
	}

	private boolean onTheSameI(Character4M a,Character4M b) throws Exception
	{
		return a.i == b.i;
	}
	
	public static void reset()
	{
		game=new Game();
		GameControl.reset();
	}
	
	public static void resetToFirstLevel()
	{
		livMap = firstLevel;
		reset();
	}
	
	
	public boolean goOutBorder(int i,int j)
	{
		return !(i>=0 && i<rows && j>=0 && j<columns);
	}
	
	public void increseView()
	{
		if (Settings.finalView+1<columns)//not to go further
		{
			Settings.startView+=1;
			Settings.finalView+=1;
		}
	}
	
	public void decreseView()
	{
		if (Settings.startView-1>=0)// not to go further
		{
			Settings.startView-=1;
			Settings.finalView-=1;
		}
	}
	
	public void gravity()
	{
		try
		{
			if (gravity)
			{
				if (bowser != null)
					move(Movements.MOVE_DOWN,bowser);
				if (superMario != null)
					move(Movements.MOVE_DOWN,superMario);
				GameControl.jumpOn=false;
			}
			else
			{
				setGravity(true);
			}
		}
		catch(Exception e)
		{
			return;
		}
	}
	
	public Mob searchMob(int i,int j)
	{
		try
		{
			Point p = new Point(i, j);
			for (int k=0;k<mobs.size();++k)
			{
				Mob mob = mobs.get(k);
				if (mob != null)
				{
					if (Movements.collisionPoint(mob, p))
					{
						return mob;
					}
				}
			}
			return null;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public boolean finish()// * when the level end
	{
		try
		{
			return Movements.collisionPoint(superMario, finish);
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void nextLevel()
	{
		GameControl.reset();
		++livMap;
		if(livMap<=maxLevel)
			game=new Game();
	}
	
	public boolean isLastLevel()
	{
		return livMap == maxLevel;
	}

	
	// ************** MOVE **************
	
	public boolean move(int direction,Character4M character)// * UPcast always OK!
	{
		// * PREC POSITION
		try
		{
			int i=character.i;
			int j=character.j;
			
			if (direction==Movements.MOVE_UP)
			{
				setGravity(false);		
			}
			
			if(Movements.move(direction, character))// *collision
			{
				// * RESTART GAME IF SUPERMARIO IS SHOOTED
				int value = getMatrixValue(character.i, character.j);
				if (Settings.isaMob(value))
				{
					gameOver=true;
				}
				else
					Movements.goBack(direction, character);
				return true;
			}
			else
			{		
				character.setDirection(direction);
				setMatrixValue(i, j, Settings.SPACE);
				setMatrixValue(character.i, character.j, character.getId());
			}	
		}
		catch (Exception e)
		{
			return false;
		}
		

		return false;
	}
	
	private boolean move(Character4M character) throws Exception// * UPcast always OK!
	{
		return move(character.getDirection(),character);
	}
	
	// ************** SHOOTING AND MOBS **************
	
	public void shooting(int direction,Character4M character)// * CREATE SHOOT
	{
		try
		{
			int i = character.getI(),j = nextValueJ(character.j, direction);
			
			Shoot shoot = new Shoot(i,j,direction);
			
			if (getMatrixValue(i, j)==Settings.SPACE && shoot!=null)
			{
				if (character instanceof SuperMario)
				{
					shoots.add(shoot);
				}
				if (character instanceof Bowser)
					shootsBowser.add(shoot);
			}
		}
		catch (Exception e)
		{
			return;
		}
		
	}
	
	
	private boolean removeShoot(Point p1) throws Exception// *upcast
	{
		for (int i=0;i<shoots.size();++i) //* REMOVE SUPER MARIO shoot
		{
			Point p2=shoots.get(i);// *upcast
			if (p2!=null)
			{
				if (Movements.collisionPoint(p1, p2))
				{
					removeSingle(i,p2);
					return true;
				}
			}
		}
		return false;
	}
	
	private void removeMob(Point p1) throws Exception
	{
		for (int i=0;i<mobs.size();++i)
		{
			Mob p2=mobs.get(i);
			if (p2!=null)
			{
				if (Movements.collisionPoint(p1, p2))
				{
					removeSingle(i,p2);
				}
			}
		}
	}
	
	private void removeSingle(int i,Point p) throws Exception
	{
		if (p instanceof Mob)
			mobs.remove(i);
		else if (p instanceof Shoot)
			shoots.remove(i);
		
		setMatrixValue(p.i, p.j, Settings.SPACE);
	}
	
	public void moveMOB()// * move all mobs
	{
		try
		{
			for (int i=0;i<mobs.size();++i)
			{
				Mob mob=mobs.get(i);
				if (mob!=null)
				{	
					if(!Movements.moveMobs(mob))// *collision
					{
						if (getMatrixValue(mob.i, mob.j)==Settings.SHOOT)
						{
							removeShoot(mob);
							mobs.remove(i);
						}
						else 
						{
							// *restart game
							int value=getMatrixValue(mob.i, mob.j);
							if (value==Settings.SUPER_MARIO)
							{
								gameOver=true;
							}
							else
								mob.setDirection(Movements.getReverse(mob.getDirection()));// *change direction mob
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			return;
		}
	}

	public void moveShoots()// *this move all shoots
	{
		try 
		{
			moveSuperMarioShoots();
			moveBowserShoots();
		} 
		catch (Exception e)
		{
			return;
		}
	}
	
	private void moveBowserShoots() throws Exception
	{
		for (int i=0;i<shootsBowser.size();++i)
		{
			int valueMatrix = 0;
			Shoot shoot=shootsBowser.get(i);
			int j = nextValueJ(shoot.j, shoot.getDirection());
			if (j>=0)
				valueMatrix = getMatrixValue(shoot.i, j);
			if (shoot!=null)
			{
				if(!Movements.moveShoot(shoot))// *collision
				{
					if (valueMatrix == Settings.SUPER_MARIO)
					{
						gameOver = true;
					}
					shootsBowser.remove(i);// *if it collides,I remove it
				}
			}
		}
	}

	private void moveSuperMarioShoots() throws Exception
	{
		for (int i=0;i<shoots.size();++i)
		{
			int valueMatrix = 0;
			Shoot shoot = shoots.get(i);
			int j = nextValueJ(shoot.j, shoot.getDirection());
			if (j>=0)
				valueMatrix = getMatrixValue(shoot.i, j);
			if (shoot!=null)
			{
				if(!Movements.moveShoot(shoot))// *collision
				{
					if (valueMatrix == Settings.BOWSER)
					{
						dieBowser();
					}
					else if (!Settings.isaBlock(valueMatrix))
						removeMob(shoot);
					shoots.remove(i);// *if it collides,I remove it
				}
			}
		}
		
	}
	
	// ************** BOWSER **************
	
	private void dieBowser() throws Exception
	{
		bowser.setDie(true);
	}

	public void moveBowser()
	{
		try
		{
			if (bowser != null)
			{
				if (bowser.isDie())
				{
					return;
				}
				
				int j = nextValueJ(bowser.j, bowser.getDirection());
				int i = bowser.i;
				int valueMatrix = getMatrixValue(i, j);
				
				// * CONTROL shoot
				if (valueMatrix == Settings.SHOOT)
				{
					Point p = new Point(i, j);
					if ( removeShoot(p) )
						bowser.setDie(true);
				}// *  END CONTROL shoot
				
				
				int distance = Math.abs(bowser.j-superMario.j);
				if (distance<=1 && onTheSameI(superMario, bowser))
				{
					gameOver = true;
				}
				
				if ( distance <= Settings.visualizedColumns ) // * IF BOWSER IS IN THE RANGE OF VISIBILITY
				{
					// * SET DIRECTION
					int direction = Movements.MOVE_LEFT;

					if (bowser.j <= superMario.j)
					{
						direction = Movements.MOVE_RIGHT; // * CHANGE DIRECTION
					}
					bowser.setDirection(direction);
					
					// * DISTANCE MANAGEMENT *
					if (distance >= Settings.bowserView) // * IF BOWSER CAN'T ATTACK
					{
						bowser.setStop(false);					
						if(move(bowser))// *COLLISION
						{
							if (bowser.getDirection() != Movements.MOVE_DOWN)// * CHANGE LAST DIRECTION
								bowser.setLastDirection(direction);
							bowser.setDirection(Movements.MOVE_UP);
							move(bowser);
						}
						
					}
					else if (Settings.isaBlock(valueMatrix))
					{
						bowser.setStop(true);
						bowser.setNotTheSameI(true);
					}
					else // * BOWSER CAN ATTACK
					{
						
						if (bowser.getDirection() != Movements.MOVE_DOWN) // * CHANGE LAST DIRECTION
							bowser.setLastDirection(bowser.getDirection());
						
						bowser.setStop(true);
						if (onTheSameI(superMario, bowser))
						{
							bowser.setNotTheSameI(false);
							shooting(bowser.getLastDirection(), bowser);
						}
						else
							bowser.setNotTheSameI(true);
					}
					
				}
				
			}
		}
		catch (Exception e) 
		{
			return;
		}
	}
	
	// ************** GETTER AND SETTER **************

	public static Game getGame()
	{
		if (game==null)
			game=new Game();
		return game;
	}
		
	public void setGravity(boolean gravity) 
	{
		this.gravity = gravity;
	}
	
	public SuperMario getSuperMario() 
	{
		return superMario;
	}

	public int[][] getBlocks()
	{
		return blocks;
	}

	public int getRows() 
	{
		return rows;
	}

	public int getColoums()
	{
		return columns;
	}

	public int getMatrixValue(int i,int j)
	{
		if (!goOutBorder(i, j))
			return blocks[i][j];
		return outBorder;
	}
	
	public void setMatrixValue(int i,int j,int value)
	{
		if (!goOutBorder(i, j))
			blocks[i][j]=value;
	}

	public int size()
	{
		return shoots.size();
	}

	public ArrayList<Mob> getMobs()
	{
		return mobs;
	}
	
	public ArrayList<Shoot> getshoots() 
	{
		return shoots;
	}
	
	public boolean gameOver()
	{
		return gameOver;
	}
	
	public static int getLivMap() 
	{
		return livMap;
	}

	public int getMaxLevel()
	{
		return maxLevel;
	}
	
	public Bowser getBowser() 
	{
		return bowser;
	}

	public ArrayList<Shoot> getShootsBowser() 
	{
		return shootsBowser;
	}
	
}
