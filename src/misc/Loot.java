package misc;

import java.awt.image.BufferedImage;

import characters.Soldier;
import handlers.LoadImageHandler;
import handlers.RenderCallHandler;
import main.Broadcaster;
import map.WorldShifter;

public class Loot {
	
	BufferedImage lootImage = LoadImageHandler.loadImage("LootCrate.png");
	public int xPos, yPos, xMod, yMod, index = 0;
	public String type;
	public String name;
	LootSpawner lootSpawner;
	
	public Loot(int xPosb, int yPosb, int xModb, int yModb, String nameb, LootSpawner lootSpawnerb, String typeb, int indexb){
		xPos = xPosb * 5;
		yPos = yPosb * 5 - 2520;
		xMod = xModb;
		yMod = yModb;
		name = nameb;
		type = typeb;
		index = indexb;
		lootSpawner = lootSpawnerb;
		Broadcaster.addClass(this);
		RenderCallHandler.addRenderCall(lootImage, xPos + xMod - 10, yPos + yMod - 10 + 2520, 20, 20, name);
	}
	
	public void shift(int shiftX, int shiftY){
		if(Math.sqrt(Math.pow((WorldShifter.soldierX - xPos), 2) + Math.pow((WorldShifter.soldierY - yPos), 2)) < 20){
			Soldier.overLoot = true;
			Soldier.loot = this;
		}
		xMod += shiftX;
		yMod += shiftY;
		RenderCallHandler.updateRenderCall(lootImage, xPos + xMod - 10, yPos + yMod - 10 + 2520, 20, 20, name);
	}
	
	public void removeLoot(){
		Broadcaster.removeClass(this);
		RenderCallHandler.removeRenderCall(name);
		lootSpawner.removeLoot(this);
	}
	
	public void refresh(){
		Broadcaster.removeClassInside(this);
		RenderCallHandler.removeRenderCall(name);
		lootSpawner.removeLoot(this);
	}
	
}
