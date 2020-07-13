package SuperMario.view;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import SuperMario.config.Settings;

public class BlocksView // * blocks management
{ 
	private Map<Integer,Image> blocks;
	private static BlocksView instance= null;
	
	private BlocksView()
	{
		blocks = new HashMap<>();
		StringBuilder addres=new StringBuilder(Settings.foldResources+"/Blocks/");
		try
		{
			for(int i=Settings.startBlocks,j=1;i<=Settings.finalBlocks;++i,++j)
			{
				Image img=ImageIO.read(getClass().getResourceAsStream(addres+"block"+j+".jpg"));
				blocks.put(i,img);
			}
		}
		catch(Exception e)
		{
			return;
		}
	}
	
	public static BlocksView getBlocksView()
	{
		if (instance==null)
			instance=new BlocksView();
		return instance;
	}
	
	public Image getBlock(int type) throws Exception
	{
		if (blocks.get(type)!=null)
		{
			return blocks.get(type);
		}
		
		return null;
	}
	
}
