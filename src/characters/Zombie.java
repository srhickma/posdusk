package characters;

import handlers.AudioHandler;
import handlers.LoadImageHandler;
import handlers.RenderCallHandler;
import handlers.RotateImageHandler;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;

import main.*;
import map.*;
import misc.Structure;
import pathfinding.*;

public class Zombie {
	
	BufferedImage zombie;
	BufferedImage zombieImage1 = LoadImageHandler.loadImage("Zombie.png");
	BufferedImage zombieImage2 = LoadImageHandler.loadImage("Zombie2.png");
	Random RandomGen = new Random();
	public static Clip[] groanSFX = new Clip[4];
	public int xPos = 190, yPos = 632, targetX = 0, targetY = 0, xMod = -550, yMod = -3020, xDis = 0, yDis = 0, time = 0, groanTime = 0, pathIndex = 2;
	boolean needPath = false, dead = false;
	String name = "";
	Main main;
	Pathfind pathfind = new Pathfind();
	public int health = 1000;
	List<Pathfind.Node> path = null;
	long pTime = 0L;
	ZombieSpawner zombieSpawner;
	
	public Zombie(Main mainb, int xPosb, int yPosb, int xModb, int yModb, String nameb, int offset, ZombieSpawner zombieSpawnerb){
		name = nameb;
		xPos = xPosb;
		yPos = yPosb;
		xMod = xModb;
		yMod = yModb;
		main = mainb;
		time = offset;
		health += Clock.day * 15;
		zombieSpawner = zombieSpawnerb;
		zombie = zombieImage1;
		Broadcaster.addClass(this);
		start();
	}
	
	public void start(){
		RenderCallHandler.addRenderCall(zombie, xPos - 100, yPos - 100, 153, 153, name);
		for(int x = 0; x < 4; x ++)
			groanSFX[x] = AudioHandler.loadAudioClip("ZombieGroan" + String.valueOf(x + 1) + ".wav");
		updateImage();
		pathfind();
	}
	
	public void update(){
		if(!dead){
			if(health < 1){
				Broadcaster.removeClassInside(this);
				RenderCallHandler.removeRenderCall(name);
				zombieSpawner.removeZombie(this, true);
				dead = true;
				Soldier.killedZombie();
			}
			if(health > 500)zombie = zombieImage1;
			else zombie = zombieImage2;
			double distanceToSoldier = Math.sqrt(Math.pow((WorldShifter.soldierX - xPos * 5), 2) + Math.pow((WorldShifter.soldierY - (yPos * 5 - 2520)), 2));
			if(distanceToSoldier > 1000 || pTime > 10000000){
				Broadcaster.removeClassInside(this);
				RenderCallHandler.removeRenderCall(name);
				zombieSpawner.removeZombie(this, false);
				dead = true;
			}
			time ++;
			if(time > 10 && !Soldier.dead){
				if((xPos * 5 - 30  < WorldShifter.soldierX && xPos * 5 + 30  > WorldShifter.soldierX) && (yPos * 5 - 30 < WorldShifter.soldierY + 2520 && yPos * 5 + 30 > WorldShifter.soldierY + 2520))Soldier.health -= 15;
				if(needPath && !pathBlocked()){
					pathfind();
					pathIndex = 2;
					needPath = false;
				}
				else if(!pathBlocked()){pathIndex ++;}
				try{
					xPos = path.get(path.size() - pathIndex).x;
					yPos = path.get(path.size() - pathIndex).y;
				}
				catch(Exception e){}
				time = 0;
				updateImage();
			}
			groanTime ++;
			if(groanTime > 100){
				AudioHandler.playAudioClip(groanSFX[RandomGen.nextInt(4)], true, (float)-(distanceToSoldier / 10));
				groanTime = 0;
			}
		}
	}
	
	public void updateImage(){
		try{
			xDis = xPos * 5 - 1810 + xMod - (path.get(path.size() - pathIndex - 1).x * 5 - 1810 + xMod);
			yDis = yPos * 5 - 1760 + yMod - (path.get(path.size() - pathIndex - 1).y * 5 - 1760 + yMod);
		}
		catch(Exception e){}
		RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(zombie, (Math.toDegrees(Math.atan2(xDis, yDis))) * -1, 185, 105), xPos * 5 + xMod - 59, yPos * 5 + yMod - 32, 153, 153, name);
	}
	
	public void shift(int shiftX, int shiftY){
		xMod += shiftX;
		yMod += shiftY;
		needPath = true;
		updateImage();
	}
	
	public void pathfind(){
		long lasPTime = System.nanoTime();
		path = pathfind.search(xPos, yPos, WorldShifter.soldierX / 5, WorldShifter.soldierY / 5 + 504);
		pTime = System.nanoTime() - lasPTime;
	}
	
	public boolean pathBlocked(){
		int zX = xPos * 5, zY = yPos * 5, zXOrig = xPos * 5, zYOrig = yPos * 5 - 2520;
		for(Structure s : Soldier.structures){
			if(Math.sqrt(Math.pow((zXOrig - s.xPos), 2) + Math.pow((zYOrig - s.yPos), 2)) < 125){
				zX = path.get(path.size() - pathIndex - 1).x * 5;
				zY = path.get(path.size() - pathIndex - 1).y * 5 - 2520;
				if((zX - 10 >= s.xPos && zX - 10 <= s.xPos + s.width && ((zY - 10 < s.yPos + s.height && zY - 10 > s.yPos) || (zY + 10 < s.yPos + s.height && zY + 10 > s.yPos) || (zY < s.yPos + s.height && zY > s.yPos))) || (zX + 10 >= s.xPos && zX + 10 <= s.xPos + s.width && ((zY - 10 < s.yPos + s.height && zY - 10 > s.yPos) || (zY + 10 < s.yPos + s.height && zY + 10 > s.yPos) || (zY < s.yPos + s.height && zY > s.yPos)) || (zY - 10 >= s.yPos && zY - 10 <= s.yPos + s.height && ((zX - 10 < s.xPos + s.width && zX - 10 > s.xPos) || (zX + 10 < s.xPos + s.width && zX + 10 > s.xPos) || (zX < s.xPos + s.width && zX > s.xPos))) || (zY + 10 >= s.yPos && zY + 10 <= s.yPos + s.height && ((zX - 10 < s.xPos + s.width && zX - 10 > s.xPos) || (zX + 10 < s.xPos + s.width && zX + 10 > s.xPos) || (zX < s.xPos + s.width && zX > s.xPos))))){
					s.lowerHealth();
					if(s.type != 8){
						if(!(s.type == 9 && s.open))return true;
					}
					else health -= 100;
				}
			}
		}
		return false;
	}
	
	public void refresh(){
		Broadcaster.removeClassInside(this);
		RenderCallHandler.removeRenderCall(name);
		zombieSpawner.removeZombie(this, true);
		dead = true;
	}
	
}
