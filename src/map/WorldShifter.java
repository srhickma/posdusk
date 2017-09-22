package map;

import main.Broadcaster;
import characters.Soldier;
import characters.SoldierCollisionDetector;

public class WorldShifter {
	
	public static int worldX = -125, worldY = -250, soldierX = 975, soldierY = 750;
	
	public static void shiftWorld(int shiftX, int shiftY){
		int[] shift = new int[2];
		shift = SoldierCollisionDetector.checkCollisions(shiftX, shiftY);
		worldX += shift[0];
		soldierX -= shift[0];
		worldY += shift[1];
		soldierY -= shift[1];
		Soldier.overLoot = false;
		Broadcaster.broadcastVoid("shift", shift[0], shift[1]);
		Map.shift(shift[0], shift[1]);
	}
	
	public static void refresh(){worldX = -125;worldY = -250;soldierX = 975;soldierY = 750;}
	
}
