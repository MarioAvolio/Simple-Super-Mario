package SuperMario.music;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import SuperMario.config.Settings;



public class Sound
{
	private AudioInputStream audioIn;
	private Clip clip;
	
	public Sound(String name)
	{	
		try 
		{
			audioIn = AudioSystem.getAudioInputStream(new File(Settings.foldResources2+"Sounds"+File.separator+name+".wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			clip = null;
		}
	}
	
	public void startLoop()
	{
		if(clip != null) 
		{
			if(clip.getFramePosition() == clip.getFrameLength())
				clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);	
		}
	}
	
	public void stop() 
	{
		if(clip != null) {
			clip.stop();
		}
	}
	
	public void startFastSound()
	{
		if (clip != null)
		{
			clip.start();
		}
	}
}
