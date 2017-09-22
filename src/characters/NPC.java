package characters;

import handlers.LoadImageHandler;
import handlers.RenderCallHandler;
import handlers.RotateImageHandler;

import java.awt.image.BufferedImage;

import main.Broadcaster;
import map.WorldShifter;

public class NPC {
	BufferedImage nPCImage = LoadImageHandler.loadImage("Soldier_Fists.png");
	public int xPos, yPos, xMod, yMod;
	NPCSpawner nPCSpawner;
	
	public NPC(int xPos, int yPos, int xMod, int yMod, NPCSpawner nPCSpawner){
		this.xPos = xPos * 5;
		this.yPos = yPos * 5 - 2520;
		this.xMod = xMod;
		this.yMod = yMod;
		this.nPCSpawner = nPCSpawner;
		Broadcaster.addClass(this);
		RenderCallHandler.addRenderCall(nPCImage, xPos + xMod - 59, yPos + yMod - 32 + 2520, 153, 153, "NPC");
	}
	
	public void shift(int shiftX, int shiftY){
		double distanceToSoldier = Math.sqrt(Math.pow((WorldShifter.soldierX - xPos), 2) + Math.pow((WorldShifter.soldierY - yPos), 2));
		Soldier.overNPC = distanceToSoldier < 40;
		if(distanceToSoldier < 40)Soldier.nPC = this;
		else Soldier.nPC = null;
		if(distanceToSoldier > 2000)removeNPC();
		xMod += shiftX;
		yMod += shiftY;
		int xDis = xPos - WorldShifter.soldierX;
		int yDis = yPos - WorldShifter.soldierY;
		RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(nPCImage, (Math.toDegrees(Math.atan2(xDis, yDis))) * -1, 185, 105), xPos + xMod - 59, yPos + yMod - 32 + 2520, 153, 153, "NPC");
	}
	
	public void removeNPC(){
		nPCSpawner.currentNPC = null;
		Broadcaster.removeClassInside(this);
		RenderCallHandler.removeRenderCall("NPC");
	}
	
	public void refresh(){removeNPC();}
}
