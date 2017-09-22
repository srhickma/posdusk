package characters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

import main.*;
import misc.BulletTrail;
import misc.Loot;
import misc.Structure;
import handlers.*;

public class Soldier {
	
	static BufferedImage soldierImage = null;
	public static List<Structure> structures = new ArrayList<Structure>();
	public static Main main;
	public static HUD hUD;
	static BufferedImage[][] wTextures = new BufferedImage[11][5];
	public static int[] wDamages = {250, 750, 200, 350, 300, 250, 1100, 350, 450, 200, 1250}, wRanges = {400, 1000, 40, 40, 300, 150, 150, 350, 1000, 200, 1000}, wReloadTimes = {2500, 3200, 0, 0, 7000, 1600, 4000, 4000, 2500, 1900, 3200}, wRateOfFires = {100, 1250, 750, 750, 75, 0, 1000, 500, 0, 50, 1250}, wAmmos = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, wAmmoInMags = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, wMaxAmmos = {30, 5, 0, 0, 100, 10, 8, 6, 20, 30, 5}, items = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, tradePrices = {200, 100, 20, 250, 75, 125, 100, 175, 175, 150, 350, 50, 40, 100, 300, 50, 150, 200, 50, 150};
	public static String[] wNames = {"Assault Rifle", "Bolt-Action Rifle", "Fist", "Knife", "LMG", "Pistol", "Pump-Action Shotgun", "Revolver", "Semi-Auto Rifle", "SMG", "Sniper Rifle"}, tradeNames = {"Assault Rifle Ammo", "Bolt-Action Rifle Ammo", "Knife", "LMG Ammo", "Pistol Ammo", "Pump-Action Shotgun Ammo", "Revolver Ammo", "Semi-Auto Rifle Ammo", "SMG Ammo", "Sniper Rifle Ammo", "Medkit", "Canned Food", "Water Bottle", "GPS", "Map", "Wooden Barricade", "Metal Barricade", "Concrete Barricade", "Barbed Wire", "Door"};
	public static boolean[] wAutos = {true, false, false, false, true, false, false, false, false, true, false}, wOwned = {false, false, true, false, false, false, false, false, false, false, false};
	static Clip[] wSounds = new Clip[11];
	public static Clip footstepsSFX = AudioHandler.loadAudioClip("Footsteps.wav"), chaChingSFX = AudioHandler.loadAudioClip("ChaChing.wav"), thudSFX = AudioHandler.loadAudioClip("Thud.wav"), clickSFX = AudioHandler.loadAudioClip("Click.wav");
	public static int xPos = 261, yPos = 179, health = 100, hunger = 100, thirst = 100, damageTimer = 0, xDis = 0, yDis = 0, w = 0, wState = 0, wDamage = 0, wRange = 0, wReloadTime = 0, wRateOfFire = 0, wMaxAmmo = 0, wGlowX = 0, itemOffset = 0, structureIndex = 0, structuresSpawned = 0, money = 0;
	public static String wName = "";
	public static boolean dead = false, wAuto = false, wWaited = true, shot = false, wReloading = false, leftFistLast = false, inItems = true, mapUp = false, gpsUp = false, buildingStructure = false, structureQueued = false, inBarbedWire = false, overLoot = false, overNPC = false, trading = false, overDoor = false, interactQueued = false;
	static double shotX = 0, shotY = 0;
	public static double rotX = 0, rotY = 0, structureRotation = 0;
	public static Loot loot;
	public static NPC nPC;
	public static Structure door;
	
	public static void configureWeapons(){
		wTextures[0][0] = LoadImageHandler.loadImage("Soldier_AssaultRifle.png");wTextures[0][1] = LoadImageHandler.loadImage("Soldier_AssaultRifle2.png");
		wTextures[1][0] = LoadImageHandler.loadImage("Soldier_Bolt-ActionRifle.png");wTextures[1][1] = LoadImageHandler.loadImage("Soldier_Bolt-ActionRifle2.png");
		wTextures[2][0] = LoadImageHandler.loadImage("Soldier_Fists.png");wTextures[2][1] = LoadImageHandler.loadImage("Soldier_FistsL1.png");wTextures[2][2] = LoadImageHandler.loadImage("Soldier_FistsL2.png");wTextures[2][3] = LoadImageHandler.loadImage("Soldier_FistsR1.png");wTextures[2][4] = LoadImageHandler.loadImage("Soldier_FistsR2.png");
		wTextures[3][0] = LoadImageHandler.loadImage("Soldier_Knife.png");wTextures[3][1] = LoadImageHandler.loadImage("Soldier_Knife1.png");wTextures[3][2] = LoadImageHandler.loadImage("Soldier_Knife2.png");
		wTextures[4][0] = LoadImageHandler.loadImage("Soldier_LMG.png");wTextures[4][1] = LoadImageHandler.loadImage("Soldier_LMG2.png");
		wTextures[5][0] = LoadImageHandler.loadImage("Soldier_Pistol.png");wTextures[5][1] = LoadImageHandler.loadImage("Soldier_Pistol2.png");
		wTextures[6][0] = LoadImageHandler.loadImage("Soldier_Pump-ActionShotgun.png");wTextures[6][1] = LoadImageHandler.loadImage("Soldier_Pump-ActionShotgun2.png");
		wTextures[7][0] = LoadImageHandler.loadImage("Soldier_Revolver.png");wTextures[7][1] = LoadImageHandler.loadImage("Soldier_Revolver1.png");
		wTextures[8][0] = LoadImageHandler.loadImage("Soldier_Semi-AutoRifle.png");wTextures[8][1] = LoadImageHandler.loadImage("Soldier_Semi-AutoRifle2.png");
		wTextures[9][0] = LoadImageHandler.loadImage("Soldier_SMG.png");wTextures[9][1] = LoadImageHandler.loadImage("Soldier_SMG2.png");
		wTextures[10][0] = LoadImageHandler.loadImage("Soldier_SniperRifle.png");wTextures[10][1] = LoadImageHandler.loadImage("Soldier_SniperRifle2.png");
		for(int x = 0; x < 11; x ++)
			wSounds[x] = AudioHandler.loadAudioClip(Soldier.wNames[x].replaceAll(" ", "") + ".wav");
		setWeapon(2);
	}
	
	public static void start(Main mainb){
		main = mainb;
		RenderCallHandler.addRenderCall(soldierImage, 261, 179, 153, 153, "Soldier");
		KeyboardInputHandler.addListenKey(81, "switchWeapon", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(69, "queueInteract", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(49, "useItem", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(50, "useItem", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(51, "useItem", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(52, "useItem", Soldier.class, null, true);
		KeyboardInputHandler.addListenKey(53, "useItem", Soldier.class, null, true);
	}
	
	public static void update(){
		if(!dead){
			if(shot){
				new BulletTrail(RaycastHandler.raycast(shotX, shotY, wDamage, wRange, w));
				if(w == 2 && leftFistLast)setWState(3);
				else setWState(1);
				WaitHandler.addWait(100, "wSSwitch");
				shot = false;
			}
			if(structureQueued)buildStructure();
			if(health < 1)dead = true;
			damageTimer ++;
			if(damageTimer > 100){
				damageTimer = 0;
				if(thirst < 1)health -= 5;
				if(hunger < 1)health -= 2;
				if(inBarbedWire)health -= 10;
			}
			if(interactQueued)interact();
			xDis = 425 - main.mouseX;
			yDis = 250 - main.mouseY;
			double angle = Math.toDegrees(Math.atan2(xDis, yDis));
			if(angle < 0)angle = 180 - (angle * -1) + 180;
			int quadrant = (int) angle / 90;
			angle %= 90;
			rotX = Math.cos(Math.toRadians(angle)) * 20;
			rotY = Math.sin(Math.toRadians(angle)) * 20;
			if(quadrant == 1){
				double rotXb = rotX;
				rotX = rotY;
				rotY = rotXb;
				rotX *= -1;
			}
			else if(quadrant == 2){
				rotX *= -1;
				rotY *= -1;
			}
			else if(quadrant == 3){
				double rotXb = rotX;
				rotX = rotY;
				rotY = rotXb;
				rotY *= -1;
			}
		}
		if(w == 10)RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(soldierImage, (Math.toDegrees(Math.atan2(xDis, yDis))) * -1, 185, 125), 366, 213, 153, 153, "Soldier");
		else RenderCallHandler.updateRenderCall(RotateImageHandler.rotateImage(soldierImage, (Math.toDegrees(Math.atan2(xDis, yDis))) * -1, 185, 105), 366, 218, 153, 153, "Soldier");
	}
	
	public static void setWeapon(int index){
		w = index;
		WaitHandler.removeWait("wSSwitch");
		WaitHandler.removeWait("wSSwitchD");
		setWState(0);
		wGlowX = w * 55 + 15;
		wName = wNames[w];
		wAuto = wAutos[w];
		wDamage = wDamages[w];
		wRange = wRanges[w];
		wReloadTime = wReloadTimes[w];
		wRateOfFire = wRateOfFires[w];
		wMaxAmmo = wMaxAmmos[w];
		wReloading = false;
	}
	
	public static void shootAuto(double xb, double yb){if(wAuto && wWaited && !wReloading && !buildingStructure && !trading)shoot(xb, yb);}

	public static void shootSemi(double xb, double yb){if(!wAuto && wWaited && !wReloading && !buildingStructure && !trading)shoot(xb, yb);}
	
	public static void shoot(double xb, double yb){
		if(wAmmoInMags[w] > 0 || w == 2 || w == 3){
			AudioHandler.playAudioClip(wSounds[w], false, 0);
			shotX = xb;
			shotY = yb;
			shot = true;
			if(w != 2 && w != 3)wAmmoInMags[w] --;
			if(wRateOfFire != 0){
				wWaited = false;
				WaitHandler.addWait(wRateOfFire, "wWaited");
			}
		}
		else if(wAmmos[w] > 0){
			wReloading = true;
			WaitHandler.addWait(wReloadTime, "wRWaited");
		}
		else{
			AudioHandler.playAudioClip(clickSFX, false, -5);
			wWaited = false;
			WaitHandler.addWait(wRateOfFire, "wWaited");
		}
	}
	
	public static void reload(){
		if(wAmmoInMags[w] + wAmmos[w] >= wMaxAmmo){
			wAmmos[w] -= wMaxAmmo - wAmmoInMags[w];
			wAmmoInMags[w] = wMaxAmmo;
		}
		else{
			wAmmoInMags[w] += wAmmos[w];
			wAmmos[w] = 0;
		}
		wReloading = false;
	}
	
	public static void setWState(int state){
		wState = state;
		soldierImage = wTextures[w][wState];
	}
	
	public static void switchWeapon(int k){
		int wb = w;
		do{
			wb ++;
			wb %= 11;
		}while(!wOwned[wb]);
		setWeapon(wb);
	}
	
	public static void useItem(int k){
		if(items[k - 49 + itemOffset] > 0){
			if(itemOffset == 0){
				if(k == 49 && health < 100){
					int newHealth = health + 50;
					health = newHealth - (newHealth % 100) * (newHealth / 100);
					items[k - 49] --;
				}
				else if(k == 50 && hunger < 100){
					int newHunger = hunger + 20;
					hunger = newHunger - (newHunger % 100) * (newHunger / 100);
					items[k - 49] --;
				}
				else if(k == 51 && thirst < 100){
					int newThirst = thirst + 25;
					thirst = newThirst - (newThirst % 100) * (newThirst / 100);
					items[k - 49] --;
				}
				else if(k == 52)gpsUp = !gpsUp;
				else if(k == 53)mapUp = !mapUp;
			}
			else{
				buildingStructure = true;
				structureIndex = k - 49;
			}
		}
	}
	
	public static void queueStructure(){structureQueued = true;}
	
	public static void buildStructure(){
		structures.add(new Structure(hUD, structureIndex + itemOffset, main.mouseX, main.mouseY, Soldier.structureRotation, structuresSpawned));
		AudioHandler.playAudioClip(thudSFX, false, 0);
		buildingStructure = false;
		structureQueued = false;
		items[structureIndex + itemOffset] --;
		structuresSpawned ++;
	}
	
	public static void killedZombie(){money += 10;}
	
	public static void queueInteract(int k){
		interactQueued = true;
	}
	
	public static void interact(){
		interactQueued = false;
		if(overNPC || trading)trading = !trading;
		else if(overLoot){
			if(loot.index > 10)items[loot.index - 11] ++;
			else{
				wOwned[loot.index] = true;
				wAmmos[loot.index] += wMaxAmmos[loot.index]; 
			}
			loot.removeLoot();
			loot = null;
			overLoot = false;
		}
		else if(overDoor){
			door.interact();
		}
	}
	
	public static void trade(double x, double y){
		for(int i = 0; i < 20; i ++){
			if(x < 275 && x > 70 && y < 55 + 20 * i && y > 36 + 20 * i && Soldier.money >= Soldier.tradePrices[i]){
				AudioHandler.playAudioClip(chaChingSFX, false, 2);
				if(i == 2)wOwned[3] = true;
				else if(i > 9)items[i - 10] ++;
				else{
					if(i > 2)i ++;
					wAmmos[i] += wMaxAmmos[i];
				}
				Soldier.money -= Soldier.tradePrices[i];
				break;
			}
		}
	}
	
	public static void refresh(){
		structures = new ArrayList<Structure>();
		wAmmos = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};wAmmoInMags = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};items = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		wOwned = new boolean[]{false, false, true, false, false, false, false, false, false, false, false};
		health = 100;hunger = 100;thirst = 100;damageTimer = 0; xDis = 0;yDis = 0;wGlowX = 0;itemOffset = 0;structureIndex = 0;structuresSpawned = 0;money = 0;
		dead = false;wWaited = true;shot = false;wReloading = false;leftFistLast = false;inItems = true;mapUp = false;gpsUp = false;buildingStructure = false;structureQueued = false;inBarbedWire = false;overLoot = false;overNPC = false;trading = false;overDoor = false;interactQueued = false;
		rotX = 0;rotY = 0;structureRotation = 0;
		loot = null;
		nPC = null;
		door = null;
		setWeapon(2);
	}
	
}
