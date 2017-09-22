package pathfinding;

import java.util.ArrayList;
import java.util.List;

import map.Map;

public class Pathfind{
	
	public List<Node> search(int sX, int sY, int gX, int gY){
		int cp = (gX / 252) * 252;
		int cs = cp - 252, cf = cp + 504;
		int cpb = (gY / 252) * 252;
		int csb = cpb - 252, cfb = cpb + 504;
		if(cs < 0)cs = 0;
		if(cf > 3275)cf = 3275;
		if(csb < 0)csb = 0;
		if(cfb > 3023)cfb = 3023;
		int newX, newY;
		boolean found = false;
		List<Node> open = new ArrayList<Node>();
		Node cN = new Node(sX, sY, 0, Math.abs(sX - gX) + Math.abs(sY - gY), null);
		open.add(cN);
		do{
			cN = null;
			for(Node n : open){
				if(cN == null)cN = n;
				if(n.f < cN.f)cN = n;
			}
			open.remove(open.indexOf(cN));
			if(cN.x == gX && cN.y == gY)found = true;
			else{
				for(int x = 0; x < 3; x ++){
					newX = cN.x + x - 1;
					for(int y = 0; y < 3; y ++){
						try{
							newY = cN.y + y - 1;
							if(Map.navMap[newX][newY] == 0){
								open.add(new Node(newX, newY, cN.g + 1, Math.abs(newX - gX) + Math.abs(newY - gY), cN));
								Map.navMap[newX][newY] = 8;
							}	
						}
						catch(Exception e){}
					}
				}
			}
		}while(!found);
		for(int x = cs; x < cf + 1; x ++){
			for(int y = csb; y < cfb + 1; y ++){
				if(Map.navMap[x][y] == 8)Map.navMap[x][y] = 0;
			}
		}
		List<Node> path = new ArrayList<Node>();
		do{
			path.add(cN);
			cN = cN.p;
		}while(cN != null);
		return path;
	}
	
	public boolean checkSpawn(int sX, int sY, int gX, int gY){
		long sTime = System.nanoTime();
		int cp = (gX / 252) * 252;
		int cs = cp - 252, cf = cp + 504;
		int cpb = (gY / 252) * 252;
		int csb = cpb - 252, cfb = cpb + 504;
		if(cs < 0)cs = 0;
		if(cf > 3275)cf = 3275;
		if(csb < 0)csb = 0;
		if(cfb > 3023)cfb = 3023;
		int newX, newY;
		boolean found = false;
		List<Node> open = new ArrayList<Node>();
		Node cN = new Node(sX, sY, 0, Math.abs(sX - gX) + Math.abs(sY - gY), null);
		open.add(cN);
		do{
			if(System.nanoTime() - sTime > 10000000 || open.size() < 1)return false;
			cN = null;
			for(Node n : open){
				if(cN == null)cN = n;
				if(n.f < cN.f)cN = n;
			}
			open.remove(open.indexOf(cN));
			if(cN.x == gX && cN.y == gY)found = true;
			else{
				for(int x = 0; x < 3; x ++){
					newX = cN.x + x - 1;
					for(int y = 0; y < 3; y ++){
						newY = cN.y + y - 1;
						try{
							if(Map.navMap[newX][newY] == 0){
								open.add(new Node(newX, newY, cN.g + 1, Math.abs(newX - gX) + Math.abs(newY - gY), cN));
								Map.navMap[newX][newY] = 8;
							}
						}catch(Exception e){}
					}
				}
			}
		}while(!found);
		for(int x = cs; x < cf + 1; x ++){
			for(int y = csb; y < cfb + 1; y ++){
				if(Map.navMap[x][y] == 8)Map.navMap[x][y] = 0;
			}
		}
		return true;
	}
	
	public class Node{
		
		public int x = 0, y = 0, f = 0, g = 0;
		public Node p;
		
		public Node(int xb, int yb, int gb, int h, Node pb){
			x = xb;
			y = yb;
			g = gb;
			f = g + h;
			p = pb;
		}
		
	}
	
}
