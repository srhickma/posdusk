package handlers;

import java.awt.image.BufferedImage;

import map.Map;
import map.WorldShifter;
import characters.Soldier;
import characters.Zombie;
import characters.ZombieSpawner;

public class RaycastHandler {
	
	public static BufferedImage raycast(double xb, double yb, int wDamage, int wRange, int w){
		BufferedImage image = new BufferedImage(860,510,BufferedImage.TYPE_INT_ARGB);
		double soldierX = WorldShifter.soldierX, soldierY = WorldShifter.soldierY;
		double cx = Math.round(xb) - 425 + soldierX, cy = Math.round(yb) - 250 + soldierY;
		boolean done = false;
		if(w != 2){
			soldierX += Soldier.rotX;
			soldierY -= Soldier.rotY;
		}
		double oSX = soldierX, oSY = soldierY;
		do{
			double slope = (cy - soldierY) / (cx - soldierX);
			double factor = Math.round(Math.abs(slope));
			double distanceTravelled = Math.sqrt(Math.pow(soldierX -  oSX, 2) + Math.pow(soldierY - oSY, 2));
			if(slope == Double.POSITIVE_INFINITY)soldierY ++;
			else if(slope == Double.NEGATIVE_INFINITY)soldierY --;
			else if(cx > oSX){
				if(slope < 1 && slope > -1){
					soldierX ++;
					soldierY += slope;
				}
				else{
					soldierX += 1 / (double) factor;
					soldierY += slope / (double) factor;
				}
			}
			else{
				if(slope < 1 && slope > -1){
					soldierX --;
					soldierY -= slope;
				}
				else{
					soldierX -= 1 / (double) factor;
					soldierY -= slope / (double) factor;
				}
			}
			if(Math.round(soldierX - cx) == 0 && Math.round(soldierY - cy) == 0)done = true;
			if(distanceTravelled > wRange)done = true;
			for(Zombie z : ZombieSpawner.zombies){
				if((z.xPos * 5 - 10  < soldierX && z.xPos * 5 + 10  > soldierX) && (z.yPos * 5 - 10 < soldierY + 2520 && z.yPos * 5 + 10 > soldierY + 2520)){
					done = true;
					if(w == 6){
						z.health -= wDamage - 5 * distanceTravelled;
						System.out.println(wDamage - 5 * distanceTravelled);
					}
					else z.health -= wDamage;
				}
			}
	    	for(int x = 0; x < 3; x ++){
				for(int y = 0; y < 3; y ++){
					try{
						int xout = 0, yout = 0;
						xout = (int) ((int) (long) Map.loadedIndexX[x][y]*(-1260) + soldierX);
						if(soldierY < 1260)yout = (int) (long) Math.round(Math.sqrt(Math.pow((Map.loadedIndexY[x][y] - 2)*(-1260), 2.0)) + soldierY);
						else yout = (int) (long) Math.round((Map.loadedIndexY[x][y] - 2)*(-1260) + soldierY);
						int colour = Map.loadedMap[x][y].getRGB(xout, yout);
						if(colour == -13421773)done = true;
					}catch(Exception e){}
				}
			}
	    	if(w != 2 && w != 3){
	    		int trailStart = 30;
	    		if(w == 10)trailStart = 40;
	    		try{
	    			if(distanceTravelled > trailStart)image.setRGB((int) Math.round(soldierX - WorldShifter.soldierX + 425), (int) Math.round(soldierY - WorldShifter.soldierY + 250), -00000001);
	    		}catch(Exception e){}
	    	}
		}while(!done);
		return image;
	}
	
}
