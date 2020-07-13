package SuperMario.view;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import SuperMario.ChangePanel;
import SuperMario.Main;
import SuperMario.config.Settings;
import SuperMario.model.Game;
import SuperMario.model.Mob;
import SuperMario.music.Sound;

public class GamePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Image background;
	private JButton nextLevelButton;
	private JButton restartButton;
	private JButton finish;
	private JLabel gameOver;
	public static boolean stopAll;
	private boolean death;
	private boolean win;
	private boolean isEnd;
	private Sound deathSound;
	private Sound winSound;
	
	public GamePanel() 
	{
		// * SOUND
		deathSound = new Sound("death");
		winSound = new Sound("win");
		
		death = false;
		win = false;
		isEnd = false;
		setLayout(null);
		
		addGameOver();
		addBackground();
		addNextLevelButton();
		addRestart();
		addFinish();
		stopAll = true;
		
		
	}
	
	/******* DRAW FUNCTION *********/
	
	private void drawBackground(Graphics g)
	{
		g.drawImage(background,0,0,null);
	}
	
	private void drawBlocks(Graphics g) throws Exception
	{
		int [][]map=Game.getGame().getBlocks();
		int rows=Game.getGame().getRows();
		
		for (int i=0;i<rows;++i)
		{
			for (int j=Settings.startView,k=0;j<=Settings.finalView;++j,++k)
			{
				Image img=null;
				/********** REPAINT MOB *********/
				if(Settings.isaMob(map[i][j]))
				{
					MobView mw = MobView.getInstance();
					Mob mob = Game.getGame().searchMob(i, j);
					HandlerView hv = mw.getView(mob);
					img = hv.getCurrentImage();
				}
				else/********** REPAINT PG *********/
				{
					switch(map[i][j])
					{
					case Settings.SUPER_MARIO:
						CharacterView cv = CharacterView.getInstance();
						img=cv.getCurrentImage();
						break;
					case Settings.SHOOT:
						ShotView sv=ShotView.getInstance();
						img=sv.getImageShot();
						break;	
						
					case Settings.BOWSER:
						BowserView bw = BowserView.getInstance();
						img = bw.getCurrentImage();
						break;
						
					default://blocks
						BlocksView bv=BlocksView.getBlocksView();
						if(bv.getBlock(map[i][j])!=null)
							img=bv.getBlock(map[i][j]);
						break;
					}
				}
				if(img!=null)/********** REPAINT BLOCKS *********/
					g.drawImage(img, k*Settings.dimensionBlockX,i*Settings.dimensionBlockY, Settings.dimensionBlockX,Settings.dimensionBlockY,null);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		try
		{
			super.paintComponent(g);
			drawBackground(g);
			drawBlocks(g);
		}
		catch (Exception e) 
		{
			return;
		}
	}
	
	public void update() throws Exception
	{
		if(!stopAll)
		{	
			nextLevelButton.setVisible(false);
		    restartButton.setVisible(false);
			gameOver.setVisible(false);
			finish.setVisible(false);
			MobView.getInstance().update();
			ShotView.getInstance().update();
			BowserView.getInstance().update();
			CharacterView.getInstance().update();//character sprite
			repaint();
		}
		
	
		if (Game.getGame().gameOver())
		{
			if (!death)
			{
				deathSound.startFastSound();//start death sound
				Main.stopBackgroundSound();
				death = true;
			}
			
			gameOver.setVisible(true);
			restartButton.setVisible(true);
			stopAll = true;
		}
		
		if (Game.getGame().finish() && !isEnd) /** IF THE CURRENT LEVEL IS FINE **/
		{
			isEnd = true; // do not enter again in this function
			if (Game.getGame().isLastLevel())
			{
				finish.setVisible(true);
				if (!win)
				{
					winSound.startFastSound();//start win sound
					Main.stopBackgroundSound();
					win = true;
				}
			}
			else
				nextLevelButton.setVisible(true);
			stopAll=true;//stop game
		}
	}
	
	/******* ANOTHER FUNCTION *******/
	
	private void setOptionButton(JButton button)
	{
		button.setVisible(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	}
	
	private Rectangle getRectangle (int width,int height,int swap)
	{
		return new Rectangle(Settings.WIDTH/2-(width/2),Settings.HEIGHT/swap-(height/2),width,height);
	}
	
	
	private void addFinish()
	{
		finish = new JButton();
		final int buttonWidth=Settings.dimensionButtonX;
		final int buttonHeight=Settings.dimensionButtonY;

		String fold2=Settings.foldResources+"/finish.png";
		Image img = null;

		// ***************** INSERT ICON **************
		try 
		{
			img = ImageIO.read(getClass().getResource(fold2)).getScaledInstance(buttonWidth, buttonHeight,Image.SCALE_SMOOTH);
		}
		catch (IOException e)
		{
			return;
		}
		
		/**********FINISH BUTTON ***********/
		finish = new JButton(new ImageIcon(img));
		finish.setBounds(getRectangle(buttonWidth, buttonHeight, 2));
		setOptionButton(finish);
		
		finish.addActionListener(new ActionListener() 
	    {//anonymous class
	        public void actionPerformed(ActionEvent e)
	        {
	        	isEnd = false;
	        	win = false;
	        	finish.setVisible(false);
	        	winSound.stop();
	        	winSound = new Sound ("win");
	        	Main.startBackgroundSound();
	        	Game.resetToFirstLevel();
	        	addBackground();
	        	ChangePanel.goToMenuPanel();
	        }
	    }); 
		add(finish);
	}
	
	/****** CHANGE BACKGROUND FOR EACH LEVEL *******/
	private void addBackground()
	{
		@SuppressWarnings("static-access")
		int liv = Game.getGame().getLivMap();
		String tmp =Settings.foldResources2+"background"+liv+".jpg";
		try 
		{
			BufferedImage img = ImageIO.read(new File(tmp));
			background=img.getScaledInstance(Settings.WIDTH, Settings.HEIGHT, Image.SCALE_SMOOTH);
		} 
		catch (Exception e) 
		{
			return;
		}
	}
	
	private void addRestart()
	{
		final int buttonWidth = Settings.dimensionButtonX;
		final int buttonHeight = Settings.dimensionButtonY;
		String fold2=Settings.foldResources+"/restart.png";
		
		Image restartImage = null;

		// ***************** INSERT ICON **************
		try 
		{
			restartImage = ImageIO.read(getClass().getResource(fold2)).getScaledInstance(buttonWidth, buttonHeight,Image.SCALE_SMOOTH);
		}
		catch (Exception e)
		{
			return;
		}
		
		/**********RESTART BUTTON ***********/
		restartButton = new JButton(new ImageIcon(restartImage));
		restartButton.setBounds(getRectangle(buttonWidth, buttonHeight, 4));
		setOptionButton(restartButton);
	    
	    restartButton.addActionListener(new ActionListener() 
	    {//anonymous class
	        public void actionPerformed(ActionEvent e)
	        {
	        	//* SOUND
	        	deathSound.stop();
	        	deathSound = new Sound("death");
	        	Main.startBackgroundSound();//restart background music
	        	
	        	//* GAME RESET
	            Game.reset();
	            
	            
	            restartButton.setVisible(false);
	            gameOver.setVisible(false);
	            stopAll=false;//continue game
	            try 
	            {
					update();
				} catch (Exception e1)
	            {
					return;
				}

	        	death = false;//in future i will play again "death" music
	        }
	    }); 
		add(restartButton);
	}
	
	private void addGameOver() //game over
	{
		final int width = Settings.dimensionButtonX;
		final int height = Settings.dimensionButtonY;
		
		// ***************** INSERT ICON **************
		String fold=Settings.foldResources+"/gameOver.png";
		
		Image gameOverImage = null;
		try 		
		{
			gameOverImage = ImageIO.read(getClass().getResource(fold)).getScaledInstance(width, height,Image.SCALE_SMOOTH);
		}
		catch (Exception e)
		{
			return;
		}
	    
	    /************* GAME OVER LABEL ************/
		gameOver = new JLabel(new ImageIcon(gameOverImage));
		gameOver.setBounds(getRectangle(width, height, 2));
		gameOver.setVisible(false);//to defaulT
		add(gameOver);
		
	}
	
	private void addNextLevelButton()//next level
	{
	    final int size = Settings.dimensionSquareButton;
		String fold=Settings.foldResources+"/nextLevel.png";
		
		// ***************** INSERT ICON **************
		Image img=null;
		try 
		{
			img=ImageIO.read(getClass().getResource(fold)).getScaledInstance(size, size,Image.SCALE_SMOOTH);
		} 
		catch (Exception e1) 
		{
			return;
		}
		
		
		/*************** INSERT BUTTON **********/
	    nextLevelButton = new JButton(new ImageIcon(img));
	    nextLevelButton.setBounds(getRectangle(size, size, 2));
	    setOptionButton(nextLevelButton);
	    nextLevelButton.addActionListener(new ActionListener()
	    {//anonymous class
	        public void actionPerformed(ActionEvent e) 
	        {
	        	Game.getGame().nextLevel();
	        	//MobView.reset();
	        	isEnd = false;
	            nextLevelButton.setVisible(false);
	            stopAll=false;//continue game
	        	addBackground();//change background level
	        }
	    });  
	    this.add(nextLevelButton);
	}

	
}
