package characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pathfinding.Pathfind;
import main.*;
import map.WorldShifter;

public class ZombieSpawner {
	
	Main main;
	Pathfind pathfind = new Pathfind();
	long lasTime = System.currentTimeMillis();
	Random RandomGen = new Random();
	static int zombiesAlive = 0, zombiesSpawned = 0;
	int x = 0, y = 0, xMod = -550, yMod = -3020;
	public static List<Zombie> zombies = new ArrayList<Zombie>();
	boolean zombieRemoved = false;
	
	public ZombieSpawner(Main mainb){
		main = mainb;
		Broadcaster.addClass(this);
	}
	
	public void spawn(){
		if(zombieRemoved){
			spawnZombie();
			zombieRemoved = false;
		}
		if(Clock.zombiesCanSpawn && !Soldier.dead){
			int random = RandomGen.nextInt(10000);
			if(random < 21 + Clock.day * 3 && zombiesAlive < 25){
				spawnZombie();
			}
		}
	}
	
	public void spawnZombie(){
		boolean spawnValid = false;
		do{
			x = RandomGen.nextInt(756) + ((WorldShifter.soldierX / 5) / 252) * 252 - 252;
			y = RandomGen.nextInt(756) + ((WorldShifter.soldierY / 5 + 504) / 252) * 252 - 252;
			if(x < 0)x = 0;
			if(x > 3275)x = 3275;
			if(y < 0)y = 0;
			if(y > 3023)y = 3023;
			if(Math.sqrt(Math.pow((WorldShifter.soldierX - x * 5), 2) + Math.pow((WorldShifter.soldierY - (y * 5 - 2520)), 2)) > 1000)spawnValid = false;
			else spawnValid = pathfind.checkSpawn(x, y, WorldShifter.soldierX / 5, WorldShifter.soldierY / 5 + 504);
		}while(!spawnValid);
		zombies.add(new Zombie(main, x, y, xMod, yMod, ("Z" + String.valueOf(zombiesSpawned)), zombiesSpawned % 20, this));
		zombiesSpawned ++;
		zombiesAlive ++;
	}
	
	public void removeZombie(Zombie z, boolean died){
		if(zombies.contains(z))zombies.remove(zombies.indexOf(z));
		zombiesAlive --;
		if(!died)zombieRemoved = true;
	}
	
	public void shift(int shiftX, int shiftY){
		xMod += shiftX;
		yMod += shiftY;
	}
	
	public void refresh(){
		zombiesAlive = 0;zombiesSpawned = 0;
		x = 0;y = 0;xMod = -550;yMod = -3020;
		zombies = new ArrayList<Zombie>();
		zombieRemoved = false;
	}
	
}
