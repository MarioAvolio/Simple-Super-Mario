package SuperMario.view;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import SuperMario.config.Settings;
import SuperMario.model.Game;
import SuperMario.model.Mob;

public class MobView
{
	private static MobView instance=null;
	private Map<Mob, HandlerView> sprite;
	private ArrayList<Mob> mobs;
	private Game game;
	
	private MobView() 
	{
		game = Game.getGame();
		mobs = game.getMobs();
		sprite = new HashMap<>();
		for (int i = 0; i < mobs.size() ; i++) //map mob -> sprite
		{
			Mob mob = mobs.get(i);
			addMob(mob);
		}	
	}
	
	
	
	private void addMob(Mob mob)
	{
		HandlerView hw = null;
		
		if (mob!=null)
		{
			switch (mob.getId())
			{
			case Settings.CROCODILE:
				hw = new HandlerView(6,0,3,2,5,Settings.foldResources+"/Crocodile/");
				break;
			case Settings.NINJA:
				hw = new HandlerView(6,0,3,2,5,Settings.foldResources+"/Ninja/");
				break;
			case Settings.TURTLE:
				hw = new HandlerView(6,0,3,2,5,Settings.foldResources+"/Turtle/");
				break;
			}
			
			sprite.put(mob,hw);	//add sprite	
		}
	}
	
	public static void reset()
	{
		instance = new MobView();
	}
	
	public static MobView getInstance()
	{
		if (instance == null)
			instance = new MobView();
		return instance;
	}
	
	public void update() throws Exception//update sprite
	{
		mobs = Game.getGame().getMobs();
		ArrayList<Mob> eraseMob = new ArrayList<Mob>();
		
		for (Mob elem : sprite.keySet())
		{
			if (!mobs.contains(elem))
				eraseMob.add(elem);
		}
		
		for (int i = 0; i < eraseMob.size() ; i++) //remove sprite of death mob 
		{
			Mob mob = eraseMob.get(i);
			sprite.remove(mob);
		}
		
		
		for (int i = 0; i < mobs.size() ; i++) //map mob -> sprite
		{
			Mob mob = mobs.get(i);
			if (mob!=null)
			{
				if (sprite.containsKey(mob))
				{
					HandlerView hw = sprite.get(mob);
					hw.moveGeneral(mob.getDirection());
					hw.update();
				}
				else
				{
					addMob(mob);
				}
			}
		}	
	}
	
	public HandlerView getView(Mob mob) throws Exception
	{
		if (mob!=null)
		{
			if (sprite.containsKey(mob))
			{
				HandlerView hw = sprite.get(mob);
				return hw;
			}
		}
		return null;
	}
}
