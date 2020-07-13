package SuperMario.config;
import java.io.File;

public class Settings 
{
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	public static int visualizedColumns = 15; // * DEFAULT VISUALIZED COLUMNS FULL HD DISPLAY
	
	public static int dimensionBlockX = Math.round (Settings.WIDTH / Settings.visualizedColumns); // APPROXIMATE
	public static int dimensionBlockY;
	
	public static final int dimensionButtonX = WIDTH/7;
	public static final int dimensionButtonY = HEIGHT/7;
	public static final int dimensionSquareButton = WIDTH/15;
	
	public static int startView;// * START VISION
	public static int finalView;//*  FINAL VISION
	public static final int bowserView = 5;
	
	
	public static final int SUPER_MARIO=1;
	public static final int TURTLE=11;
	public static final int NINJA=12;
	public static final int CROCODILE=13;
	public static final int SHOOT=10;
	public static final int SPACE=0;
	public static final int FINISH=-1;
	public static final int BOWSER = 20;
	public static final int startBlocks=2;
	public static final int finalBlocks=5;
	
	
	public static final String foldResources="/SuperMario/resources";
	public static final String foldResources2="src"+File.separator+"SuperMario"+File.separator+"resources"+File.separator;
	
	public static boolean isaMob(int value)
	{
		return (value==TURTLE || value==NINJA || value==CROCODILE);
	}
	
	public static boolean isaBlock(int value)
	{
		return value>=startBlocks && value<=finalBlocks;
	}
}
