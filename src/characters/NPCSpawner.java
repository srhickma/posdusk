package characters;

import java.util.Random;

import main.Broadcaster;
import map.WorldShifter;
import pathfinding.Pathfind;

public class NPCSpawner {
	
	Pathfind pathfind = new Pathfind();
	Random RandomGen = new Random();
	int x = 0, y = 0, xMod = -550, yMod = -3020;
	public NPC currentNPC;
	
	public NPCSpawner(){
		Broadcaster.addClass(this);
	}
	
	public void spawn(){
		if(currentNPC == null)spawnNPC();
	}
	
	public void spawnNPC(){
		boolean spawnValid = false;
		do{
			x = RandomGen.nextInt(756) + ((WorldShifter.soldierX / 5) / 252) * 252 - 252;
			y = RandomGen.nextInt(756) + ((WorldShifter.soldierY / 5 + 504) / 252) * 252 - 252;
			if(x < 0)x = 0;
			if(x > 3275)x = 3275;
			if(y < 0)y = 0;
			if(y > 3023)y = 3023;
			spawnValid = pathfind.checkSpawn(x, y, WorldShifter.soldierX / 5, WorldShifter.soldierY / 5 + 504);
		}while(!spawnValid);
		currentNPC = new NPC(x, y, xMod, yMod, this);
	}
	
	public void shift(int shiftX, int shiftY){
		xMod += shiftX;
		yMod += shiftY;
	}
	
	public void refresh(){
		x = 0;y = 0;xMod = -550;yMod = -3020;
		currentNPC = null;
	}
}
