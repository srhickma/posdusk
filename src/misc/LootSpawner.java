package misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pathfinding.Pathfind;
import characters.Soldier;
import main.Broadcaster;
import map.WorldShifter;

public class LootSpawner {
	
	Pathfind pathfind = new Pathfind();
	Random RandomGen = new Random();
	int lootSpawned = 0;
	int x = 0, y = 0, xMod = -550, yMod = -3020;
	public static List<Loot> loot = new ArrayList<Loot>();
	
	public LootSpawner(){
		Broadcaster.addClass(this);
	}
	
	public void spawn(){
		if(!Soldier.dead && !Soldier.main.game.inMenu){
			int random = RandomGen.nextInt(10000);
			if(random < 15)spawnLoot();
		}
	}
	
	public void spawnLoot(){
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
		int ran = RandomGen.nextInt(1000);
		int index = 0;
		String type = "";
		if(ran <  200){
			type = "Water Bottle";
			index = 13;
		}
		else if(ran < 400){
			type = "Canned Food";
			index = 12;
		}
		else if(ran < 475){
			type = "Knife";
			index = 3;
		}
		else if(ran < 550){
			type = "Wooden Barricade";
			index = 16;
		}
		else if(ran < 600){
			type = "Pistol";
			index = 5;
		}
		else if(ran < 650){
			type = "GPS";
			index = 14;
		}
		else if(ran < 690){
			type = "Revolver";
			index = 7;
		}
		else if(ran < 730){
			type = "Map";
			index = 15;
		}
		else if(ran < 765){
			type = "Barbed Wire";
			index = 19;
		}
		else if(ran < 795){
			type = "Bolt-Action Rifle";
			index = 1;
		}
		else if(ran < 825){
			type = "Metal Barricade";
			index = 17;
		}
		else if(ran < 850){
			type = "Pump-Action Shotgun";
			index = 6;
		}
		else if(ran < 875){
			type = "Metal Door";
			index = 20;
		}
		else if(ran < 900){
			type = "Semi-Auto Rifle";
			index = 8;
		}
		else if(ran < 920){
			type = "Sniper Rifle";
			index = 10;
		}
		else if(ran < 940){
			type = "Concrete Barricade";
			index = 18;
		}
		else if(ran < 955){
			type = "SMG";
			index = 9;
		}
		else if(ran < 967){
			type = "Assault Rifle";
			index = 0;
		}
		else if(ran < 975){
			type = "LMG";
			index = 4;
		}
		else if(ran < 1000){
			type = "Medkit";
			index = 11;
		}
		loot.add(new Loot(x, y, xMod, yMod, ("L" + String.valueOf(lootSpawned)), this, type, index));
		lootSpawned ++;
	}
	
	public void removeLoot(Loot l){if(loot.contains(l))loot.remove(loot.indexOf(l));}
	
	public void shift(int shiftX, int shiftY){
		xMod += shiftX;
		yMod += shiftY;
	}
	
	public void refresh(){
		lootSpawned = 0;
		x = 0;y = 0;xMod = -550;yMod = -3020;
		loot = new ArrayList<Loot>();
	}
	
}
