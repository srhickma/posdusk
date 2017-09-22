package handlers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioHandler {
	
	public static Clip loadAudioClip(String Name){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AudioHandler.class.getResource("/audio/" + Name));
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    return clip;
		}
		catch(Exception ex){
			System.out.println("Error <<" + Name +">> not found");
			return null;
		}
	}
	
	public static void playAudioClip(Clip clip, boolean WaitTillDone, float gain){
		if(!WaitTillDone || (WaitTillDone && !clip.isRunning())){
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(gain);
			clip.stop();
			clip.setMicrosecondPosition(0);
			clip.start();
		}
	}
	
}
