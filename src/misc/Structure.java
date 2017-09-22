package misc;

import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import characters.Soldier;
import main.Broadcaster;
import main.HUD;
import map.WorldShifter;
import handlers.AudioHandler;
import handlers.RenderCallHandler;
import handlers.RotateImageHandler;

public class Structure {
	
	BufferedImage image;
	HUD hUD;
	Clip doorSFX = AudioHandler.loadAudioClip("Door.wav");
	double rotation;
	public int name, x, y, type, xPos, yPos, width, height, health;
	public boolean open = false;
	
	public Structure(HUD hUDb, int typeb, int xb, int yb, double rotationb, int nameb){
		Broadcaster.addClass(this);
		hUD = hUDb;
		rotation = rotationb;
		name = nameb;
		x = xb;
		y = yb;
		type = typeb;
		image = hUD.itemImages[type];
		if(type == 9){
			width = 46;
			height = 10;
		}
		else{
			width = 80;
			height = 22;
		}
		switch(type){
		case(5):
			health = 50;
			break;
		case(6):
			health = 100;
			break;
		case(7):
			health = 150;
			break;
		case(8):
			health = 50;
			break;
		case(9):
			health = 100;
			break;
		}
		if(Math.abs(rotation % 360) == 90f || Math.abs(rotation % 360) == 270f){
			int widthb = width;
			width = height;
			height = widthb;
		}
		xPos = Math.round(x) - 425 + WorldShifter.soldierX - width / 2;
		yPos = Math.round(y) - 250 + WorldShifter.soldierY - height / 2;
		RenderCallHandler.addRenderCall(RotateImageHandler.rotateImage(image, rotation, 125, 125), x - 40, y - 40, 80, 80, "S" + String.valueOf(name));
		RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(image, rotation, 125, 125), x - 40, y - 40, 80, 80, "S" + String.valueOf(name));
	}
	
	public void shift(int shiftX, int shiftY){
		x += shiftX;
		y += shiftY;
		RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(image, rotation, 125, 125), x - 40, y - 40, 80, 80, "S" + String.valueOf(name));
	}
	
	public void interact(){
		open = !open;
		if(open)image = hUD.openedDoorImage;
		else image = hUD.itemImages[type];
		AudioHandler.playAudioClip(doorSFX, false, 0);
		RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(image, rotation, 125, 125), x - 40, y - 40, 80, 80, "S" + String.valueOf(name));
	}
	
	public void lowerHealth(){
		health -= 2;
		if(health < 1){
			Broadcaster.removeClassInside(this);
			RenderCallHandler.removeRenderCall("S" + String.valueOf(name));
			Soldier.structures.remove(Soldier.structures.indexOf(this));
		}
	}
	
	public void refresh(){
		Broadcaster.removeClassInside(this);
		RenderCallHandler.removeRenderCall("S" + String.valueOf(name));
		Soldier.structures.remove(Soldier.structures.indexOf(this));
	}
	
}
