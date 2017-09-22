package characters;

import java.util.Arrays;

import map.*;
import misc.Structure;

public class SoldierCollisionDetector {
	
	public static int[] checkCollisions(int shiftX, int shiftY){
		int soldierXOrig = WorldShifter.soldierX, soldierYOrig = WorldShifter.soldierY, soldierX = WorldShifter.soldierX, soldierY = WorldShifter.soldierY;
		int[] shift = new int[]{shiftX, shiftY};
				try{
					int xout = 0, yout = 0;
					xout = (int) (long) Map.loadedIndexX[1][1]*(-1260) + soldierXOrig + 1260;
					if(soldierYOrig < 1260){yout = (int) (long) Math.round(Math.sqrt(Math.pow((Map.loadedIndexY[1][1] - 2)*(-1260), 2.0)) + soldierYOrig) + 1260;}
					else{yout = (int) (long) Math.round((Map.loadedIndexY[1][1] - 2)*(-1260) + soldierYOrig) + 1260;}
					int[] coloursL = Map.loadedMapComposite.getRGB(xout - 10, yout - 10, 1, 20, null, 0, 1);
					Arrays.sort(coloursL);
					if(Arrays.binarySearch(coloursL, -13421773) > 0){
						if(shiftX > 0)shift[0] = 0;
					}
					int[] coloursR = Map.loadedMapComposite.getRGB(xout + 15, yout - 10, 1, 20, null, 0, 1);
					Arrays.sort(coloursR);
					if(Arrays.binarySearch(coloursR, -13421773) > 0){
						if(shiftX < 0)shift[0] = 0;
					}
					int[] coloursU = Map.loadedMapComposite.getRGB(xout - 5, yout - 15, 15, 1, null, 0, 15);
					Arrays.sort(coloursU);
					if(Arrays.binarySearch(coloursU, -13421773) > 0){
						if(shiftY > 0)shift[1] = 0;
					}
					int[] coloursD = Map.loadedMapComposite.getRGB(xout - 5, yout + 15, 15, 1, null, 0, 15);
					Arrays.sort(coloursD);
					if(Arrays.binarySearch(coloursD, -13421773) > 0){
						if(shiftY < 0)shift[1] = 0;
					}
					boolean touchedBarbedWire = false, overDoor = false;
					Structure door = null;
					for(Structure s : Soldier.structures){
						boolean touchingStructure = false;
						if(Math.sqrt(Math.pow((soldierXOrig - s.xPos), 2) + Math.pow((soldierYOrig - s.yPos), 2)) < 125){
							soldierX = soldierXOrig + shift[0];
							soldierY = soldierYOrig;
							if(soldierX - 15 >= s.xPos && soldierX - 15 <= s.xPos + s.width && ((soldierY - 8 < s.yPos + s.height && soldierY - 8 > s.yPos) || (soldierY + 8 < s.yPos + s.height && soldierY + 8 > s.yPos) || (soldierY < s.yPos + s.height && soldierY > s.yPos))){
								if(shiftX > 0 && s.type != 8 && !(s.type == 9 && s.open))shift[0] = 0;
								touchingStructure = true;
							}
							if(soldierX + 20 >= s.xPos && soldierX + 20 <= s.xPos + s.width && ((soldierY - 8 < s.yPos + s.height && soldierY - 8 > s.yPos) || (soldierY + 8 < s.yPos + s.height && soldierY + 8 > s.yPos) || (soldierY < s.yPos + s.height && soldierY > s.yPos))){
								if(shiftX < 0 && s.type != 8 && !(s.type == 9 && s.open))shift[0] = 0;
								touchingStructure = true;
							}
							soldierX = soldierXOrig;
							soldierY = soldierYOrig + shift[1];
							if(soldierY - 15 >= s.yPos && soldierY - 15 <= s.yPos + s.height && ((soldierX - 5 < s.xPos + s.width && soldierX - 5 > s.xPos) || (soldierX + 10 < s.xPos + s.width && soldierX + 10 > s.xPos) || (soldierX < s.xPos + s.width && soldierX > s.xPos))){
								if(shiftY > 0 && s.type != 8 && !(s.type == 9 && s.open))shift[1] = 0;
								touchingStructure = true;
							}
							if(soldierY + 15 >= s.yPos && soldierY + 15 <= s.yPos + s.height && ((soldierX - 5 < s.xPos + s.width && soldierX - 5 > s.xPos) || (soldierX + 10 < s.xPos + s.width && soldierX + 10 > s.xPos) || (soldierX < s.xPos + s.width && soldierX > s.xPos))){
								if(shiftY < 0 && s.type != 8 && !(s.type == 9 && s.open))shift[1] = 0;
								touchingStructure = true;
							}
							if(touchingStructure){
								if(s.type == 8)touchedBarbedWire = true;
								if(s.type == 9){
									overDoor = true;
									door = s;
								}
							}
						}
					}
					if(touchedBarbedWire){
						Soldier.inBarbedWire = true;
						Soldier.health -= 1;
					}
					else Soldier.inBarbedWire = false;
					if(overDoor){
						Soldier.overDoor = true;
						Soldier.door = door;
					}
					else{
						Soldier.overDoor = false;
						Soldier.door = null;
					}
				}
				catch(Exception e){}
		return shift;
	}
	
}
