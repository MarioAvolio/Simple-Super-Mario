package SuperMario.view;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import SuperMario.model.Movements;

public class HandlerView
{
	private ArrayList<Image> images;//sprite images
	private int index;// idx of images
	private Image currentImage;//first standard image
	
	private boolean moveRight;
	private boolean moveLeft;
	private int totalSprite;
	private int startSpriteLeft;
	private int startSpriteRight;
	private int finalSpriteLeft;
	private int finalSpriteRight;
	private int shotRight;
	private int shotLeft;
	private int jumpRight;
	private int jumpLeft;	
	private int dieLeft;
	private int dieRight;
	
	public HandlerView(int totalSprite, int startSpriteLeft, int startSpriteRight,
			int finalSpriteLeft, int finalSpriteRight, int shotRight, int shotLeft,
			int jumpRight,int jumpLeft,int dieLeft,int dieRight,String folder) 
	{
		this(totalSprite, startSpriteLeft, startSpriteRight, finalSpriteLeft, finalSpriteRight, folder);
		this.shotRight = shotRight;
		this.shotLeft = shotLeft;
		this.jumpRight = jumpRight;
		this.jumpLeft = jumpLeft;
		this.dieLeft = dieLeft;
		this.dieRight = dieRight;
	}
	
	public HandlerView(int totalSprite, int startSpriteLeft, int startSpriteRight,
			int finalSpriteLeft, int finalSpriteRight,String folder) 
	{
		images = new ArrayList<Image>();
		this.moveRight = false;
		this.moveLeft = false;
		this.totalSprite = totalSprite;
		this.startSpriteLeft = startSpriteLeft;
		this.startSpriteRight = startSpriteRight;
		this.finalSpriteLeft = finalSpriteLeft;
		this.finalSpriteRight = finalSpriteRight;
		index = 0;
		for(int i = 0; i < totalSprite; i++) 
		{		
			try
			{
				Image img = ImageIO.read(getClass().getResourceAsStream(folder+i+".png"));
				images.add(img);
			}
			catch (IOException e)
			{
				currentImage=null;
			}
		}
		currentImage = images.get(startSpriteRight);
	}
	
	
	public void moveGeneral(int direction)
	{
		switch(direction)
		{
		case Movements.MOVE_LEFT:
			moveLeft();
			break;
		case Movements.MOVE_RIGHT:
			moveRight();
			break;
		}
	}
	

	public void moveRight() 
	{
		moveRight = true;
		moveLeft = false;
	}
	
	public void moveLeft() 
	{
		moveRight = false;
		moveLeft = true;
	}
	
	private void stop(int spriteId) 
	{
		moveRight = false;
		moveLeft = false;
		index = spriteId;
	}
	
	
	public void setSprite(int spriteId)
	{
		if (images.get(spriteId)==null)
			return;
		stop(spriteId);
	}
	
	
	public void update() throws Exception
	{
		if(moveRight)
		{
			index++;
			if(index > finalSpriteRight || index<startSpriteRight)
				index = startSpriteRight;
		}
		else if (moveLeft)
		{
			index++;
			if(index > finalSpriteLeft || index<startSpriteLeft)
				index = startSpriteLeft;
		}
		currentImage = images.get(index);
	}
	
	public Image getCurrentImage() {
		return currentImage;
	}

	public boolean isMoveRight() {
		return moveRight;
	}

	public boolean isMoveLeft() {
		return moveLeft;
	}

	public int getShotRight() {
		return shotRight;
	}

	public int getShotLeft() {
		return shotLeft;
	}

	public int getTotalSprite() {
		return totalSprite;
	}

	public int getStartSpriteLeft() {
		return startSpriteLeft;
	}

	public int getStartSpriteRight() {
		return startSpriteRight;
	}

	public int getFinalSpriteLeft() {
		return finalSpriteLeft;
	}

	public int getFinalSpriteRight() {
		return finalSpriteRight;
	}

	public int getJumpRight() {
		return jumpRight;
	}

	public int getJumpLeft() {
		return jumpLeft;
	}

	public int getDieLeft() {
		return dieLeft;
	}

	public int getDieRight() {
		return dieRight;
	}
	
}
