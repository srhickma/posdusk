package main;

import handlers.CanvasUpdateHandler;
import handlers.KeyboardInputHandler;
import handlers.LoadImageHandler;
import handlers.RenderCallHandler;
import handlers.RotateImageHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import map.Map;
import map.WorldShifter;
import characters.Soldier;

public class HUD {
	
	Main main;
	public BufferedImage icon = LoadImageHandler.loadImage("Posdusk Icon.png");
	public BufferedImage hud = new BufferedImage(860,510,BufferedImage.TYPE_INT_ARGB);
	Graphics2D hudGraphics = hud.createGraphics();
	BufferedImage wGlowImage = new BufferedImage(60,60,BufferedImage.TYPE_INT_ARGB);
	Graphics2D wGlowGraphics = wGlowImage.createGraphics();
	BufferedImage hudL = LoadImageHandler.loadImage("HUDL.png");
	BufferedImage hudR = LoadImageHandler.loadImage("HUDR.png");
	public BufferedImage openedDoorImage = RotateImageHandler.rotateImage(LoadImageHandler.loadImage("Door.png"), 90D, 75, 125);
	public BufferedImage[] itemImages = new BufferedImage[10];
	BufferedImage[] structureImages = new BufferedImage[5];
	BufferedImage[] wImages = new BufferedImage[11];
	Font titleF = new Font("CourierNew", Font.PLAIN, 35);
	Font largeF = new Font("CourierNew", Font.PLAIN, 24);
	Font smallF = new Font("CourierNew", Font.PLAIN, 12);
	Font tinyF = new Font("CourierNew", Font.PLAIN, 10);
	String inItemsLabel = "";
	
	public HUD(Main mainb){
		main = mainb;
		Broadcaster.addClass(this);
		KeyboardInputHandler.addListenKey(70, "switchItem", this.getClass(), this, false);
	}
	
	public void start(){

		System.out.println("8");
		
		itemImages[0] = LoadImageHandler.loadImage("Medkit.png");
		itemImages[1] = LoadImageHandler.loadImage("CannedFood.png");
		itemImages[2] = LoadImageHandler.loadImage("WaterBottle.png");
		itemImages[3] = LoadImageHandler.loadImage("GPS.png");
		itemImages[4] = LoadImageHandler.loadImage("Map.png");
		itemImages[5] = LoadImageHandler.loadImage("WoodenBarricade.png");
		itemImages[6] = LoadImageHandler.loadImage("MetalBarricade.png");
		itemImages[7] = LoadImageHandler.loadImage("ConcreteBarricade.png");
		itemImages[8] = LoadImageHandler.loadImage("BarbedWire.png");
		itemImages[9] = LoadImageHandler.loadImage("Door.png");
		
		System.out.println("9");
		
		for(int i = 0; i < 5; i ++){
			structureImages[i] = new BufferedImage(250,250,BufferedImage.TYPE_INT_ARGB);
			for (int x=0;x<structureImages[i].getWidth();x++){
	            for (int y=0;y<structureImages[i].getHeight();y++){
	                int rgba = itemImages[i + 5].getRGB(x,y);
	                boolean isTrans = (rgba & 0xff000000) == 0;
	                if(!isTrans)structureImages[i].setRGB(x, y, new Color(0, 250, 0).getRGB());
	            }
	        }
		}
		for(int x = 0; x < 11; x ++)
			wImages[x] = LoadImageHandler.loadImage(Soldier.wNames[x].replaceAll(" ", "") + ".png");
		wGlowGraphics.setPaint(new Color(40, 40, 40));
		wGlowGraphics.fillRect(0, 0, 60, 60);
		paintHUD();
		RenderCallHandler.removeRenderCall("Loading");
	}
	
	public void update(){
		paintHUD();
	}
	
	public void paintHUD(){
		hudGraphics.setBackground(new Color(0, 0, 0, Clock.darkness));
		hudGraphics.clearRect(0, 0, 860, 510);
		hudGraphics.setFont(smallF);
		if(main.game.inMenu){
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "", 250, 250, 250, 0, 0, 0, 0, true, 50, 50, 50, 0, 0, 860, 510);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", icon, "", 250, 250, 250, 5, 15, 70, 70, true, 0, 0, 0, 0, 10, 860, 80);
			hudGraphics.setFont(titleF);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Posdusk", 250, 250, 250, 85, 85, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			hudGraphics.setFont(smallF);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Start Game", 250, 250, 250, 105, 140, 0, 0, true, 0, 0, 0, 100, 126, 250, 19);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Quit", 250, 250, 250, 105, 160, 0, 0, true, 0, 0, 0, 100, 146, 250, 19);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Controls:", 250, 250, 250, 105, 250, 0, 0, true, 0, 0, 0, 100, 236, 650, 200);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Movement: WASD", 250, 250, 250, 105, 280, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Use Weapon: L-Mouse Button", 250, 250, 250, 105, 300, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Reload: R", 250, 250, 250, 105, 320, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Switch Weapon: Q", 250, 250, 250, 105, 340, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Use Item/Structure: 1-5", 250, 250, 250, 105, 360, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Switch Between Items & Structures: F", 250, 250, 250, 105, 380, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Interact: E", 250, 250, 250, 105, 400, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
		}
		else{
			if(Soldier.dead){
				CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "You Are Dead", 250, 250, 250, 50, 40, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
				if(Clock.day > 1)CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "You Survived " + String.valueOf(Clock.day) + " Days", 250, 250, 250, 50, 50, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
				else CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "You Survived 1 Day", 250, 250, 250, 50, 50, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Exit To Menu", 250, 250, 250, 50, 70, 0, 0, true, 0, 0, 0, 45, 56, 200, 19);
			}
			else if(Soldier.overNPC)CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Press E To Trade", 250, 250, 250, 550, 40, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			else if(Soldier.overLoot)CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Press E To Pick Up " + Soldier.loot.type, 250, 250, 250, 550, 40, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			else if(Soldier.overDoor){
				if(Soldier.door.open)CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Press E To Close Door", 250, 250, 250, 550, 40, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
				else CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Press E To Open Door", 250, 250, 250, 550, 40, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			}
			if(Soldier.trading){
				CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "Purchase:", 250, 250, 250, 50, 35, 0, 0, true, 50, 50, 50, 0, 0, 860, 510);
				for(int x = 0; x < 20; x ++)
					CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, (Soldier.tradeNames[x] + " - $" + String.valueOf(Soldier.tradePrices[x])), 250, 250, 250, 75, 50 + 20 * x, 0, 0, true, 0, 0, 0, 70, 36 + 20 * x, 205, 19);
			}
			if(Soldier.mapUp && !Soldier.trading)CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", Map.soldierMap, "", 250, 250, 250, 50, 30, 500, 400, true, 50, 50, 50, 0, 0, 860, 510);
			if(Soldier.gpsUp && !Soldier.trading)CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "x:" + String.valueOf(WorldShifter.soldierX) + " y:" + String.valueOf(WorldShifter.soldierY), 250, 250, 250, 50, 40, 0, 0, Soldier.mapUp, 0, 0, 250, 47 + (int) (WorldShifter.soldierX / 32.76), 27 + (int) ((WorldShifter.soldierY + 2520) / 37.8), 10, 10);
			if(Soldier.buildingStructure && !Soldier.trading)CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", RotateImageHandler.rotateImage(structureImages[Soldier.structureIndex], Soldier.structureRotation, 125, 125), "", 250, 250, 250, main.mouseX - 40, main.mouseY - 40, 80, 80, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", hudL, "", 250, 250, 250, 0, 0, 500, 510, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", hudR, "", 250, 250, 250, 500, 0, 360, 510, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "HEALTH", 250, 250, 250, 5, 15, 0, 0, true, 250, 0, 0, 65, 7, Soldier.health * 4, 10);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, "MONEY: $" + Soldier.money, 250, 250, 250, 550, 15, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("Day " + String.valueOf(Clock.day)), 250, 250, 250, 750, 15, 0, 0, true, 0, 250, 0, 5, 21, 10, Soldier.hunger * 4);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, (String.valueOf(Clock.hour) + ":00"), 250, 250, 250, 800, 15, 0, 0, true, 0, 0, 250, 20, 21, 10, Soldier.thirst * 4);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", wGlowImage, "", 250, 250, 250, Soldier.wGlowX, 445, 50, 50, false, 0, 0, 0, 0, 0, 0, 0);
			for(int x = 0; x < 11; x ++)
				if(Soldier.wOwned[x])CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", wImages[x], "", 250, 250, 250, x * 55 + 15, 455, 50, 30, false, 0, 0, 0, 0, 0, 0, 0);
			if(Soldier.inItems){
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[0], "", 250, 250, 250, 801, 31, 235, 230, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[1], "", 250, 250, 250, 801, 84, 235, 230, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[2], "", 250, 250, 250, 801, 137, 235, 230, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[3], "", 250, 250, 250, 801, 190, 235, 230, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[4], "", 250, 250, 250, 801, 243, 235, 230, false, 0, 0, 0, 0, 0, 0, 0);
				inItemsLabel = "Items";
				Soldier.itemOffset = 0;
			}
			else{
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[5], "", 250, 250, 250, 806, 41, 40, 40, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[6], "", 250, 250, 250, 806, 94, 40, 40, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[7], "", 250, 250, 250, 806, 147, 40, 40, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[8], "", 250, 250, 250, 806, 195, 40, 40, false, 0, 0, 0, 0, 0, 0, 0);
				CanvasUpdateHandler.paintImage(hudGraphics, "drawImage", itemImages[9], "", 250, 250, 250, 806, 253, 40, 40, false, 0, 0, 0, 0, 0, 0, 0);
				inItemsLabel = "Structures";
				Soldier.itemOffset = 5;
			}
			hudGraphics.setFont(tinyF);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("1-" + Soldier.items[0 + Soldier.itemOffset] + "x"), 250, 250, 250, 806, 36, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("2-" + Soldier.items[1 + Soldier.itemOffset] + "x"), 250, 250, 250, 806, 89, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("3-" + Soldier.items[2 + Soldier.itemOffset] + "x"), 250, 250, 250, 806, 142, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("4-" + Soldier.items[3 + Soldier.itemOffset] + "x"), 250, 250, 250, 806, 195, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, ("5-" + Soldier.items[4 + Soldier.itemOffset] + "x"), 250, 250, 250, 806, 248, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, inItemsLabel, 250, 250, 250, 800, 305, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
			hudGraphics.setFont(largeF);
			CanvasUpdateHandler.paintImage(hudGraphics, "drawString", null, (String.valueOf(Soldier.wAmmoInMags[Soldier.w]) + "/" + String.valueOf(Soldier.wAmmos[Soldier.w])), 250, 250, 250, 740, 500, 0, 0, false, 0, 0, 0, 0, 0, 0, 0);
		}
	}
	
	public void switchItem(int k){if(!Soldier.buildingStructure)Soldier.inItems = !Soldier.inItems;}
	
}
