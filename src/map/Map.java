package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import handlers.*;

public class Map {
	
	public static BufferedImage[][] map = new BufferedImage[13][12];
	public static BufferedImage[][] loadedMap = new BufferedImage[3][3];
	public static BufferedImage loadedMapComposite = new BufferedImage(3780,3780,BufferedImage.TYPE_INT_RGB);
	public static int[][] navMap = new int[3276][3024];
	public static int [][] loadedIndexX = new int[3][3];
	public static int [][] loadedIndexY = new int[3][3];
	public static int xPos = -550, yPos = -500, oldIndexX = 0, oldIndexY = 0;
	public static BufferedImage soldierMap = new BufferedImage(3276,3024,BufferedImage.TYPE_INT_RGB);
	
	public static void shift(int shiftX, int shiftY){
		xPos += shiftX;
		yPos += shiftY;
		update();
	}
	
	public static void start(){
		map[0][2] = LoadImageHandler.loadImage("A_A.png");map[0][3] = LoadImageHandler.loadImage("A_B.png");map[0][4] = LoadImageHandler.loadImage("A_C.png");map[0][5] = LoadImageHandler.loadImage("A_D.png");map[0][6] = LoadImageHandler.loadImage("A_E.png");map[0][7] = LoadImageHandler.loadImage("A_F.png");map[0][8] = LoadImageHandler.loadImage("A_G.png");map[0][9] = LoadImageHandler.loadImage("A_H.png");
		map[1][2] = LoadImageHandler.loadImage("B_A.png");map[1][5] = LoadImageHandler.loadImage("B_D.png");map[1][7] = LoadImageHandler.loadImage("B_F.png");map[1][9] = LoadImageHandler.loadImage("B_H.png");
		map[2][2] = LoadImageHandler.loadImage("C_A.png");map[2][3] = LoadImageHandler.loadImage("C_B.png");map[2][4] = LoadImageHandler.loadImage("C_C.png");map[2][5] = LoadImageHandler.loadImage("C_D.png");map[2][6] = LoadImageHandler.loadImage("C_E.png");map[2][7] = LoadImageHandler.loadImage("C_F.png");map[2][9] = LoadImageHandler.loadImage("C_H.png");
		map[3][1] = LoadImageHandler.loadImage("D_1.png");map[3][2] = LoadImageHandler.loadImage("D_A.png");map[3][3] = LoadImageHandler.loadImage("D_B.png");map[3][4] = LoadImageHandler.loadImage("D_C.png");map[3][5] = LoadImageHandler.loadImage("D_D.png");map[3][6] = LoadImageHandler.loadImage("D_E.png");map[3][7] = LoadImageHandler.loadImage("D_F.png");map[3][8] = LoadImageHandler.loadImage("D_G.png");map[3][9] = LoadImageHandler.loadImage("D_H.png");
		map[4][1] = LoadImageHandler.loadImage("E_1.png");map[4][2] = LoadImageHandler.loadImage("E_A.png");map[4][3] = LoadImageHandler.loadImage("E_B.png");map[4][4] = LoadImageHandler.loadImage("E_C.png");map[4][5] = LoadImageHandler.loadImage("E_D.png");map[4][6] = LoadImageHandler.loadImage("E_E.png");map[4][7] = LoadImageHandler.loadImage("E_F.png");map[4][8] = LoadImageHandler.loadImage("E_G.png");map[4][9] = LoadImageHandler.loadImage("E_H.png");map[4][10] = LoadImageHandler.loadImage("E_I.png");
		map[5][0] = LoadImageHandler.loadImage("Road _.png");map[5][1] = LoadImageHandler.loadImage("F_1.png");map[5][2] = LoadImageHandler.loadImage("F_A.png");map[5][3] = LoadImageHandler.loadImage("F_B.png");map[5][4] = LoadImageHandler.loadImage("F_C.png");map[5][5] = LoadImageHandler.loadImage("F_D.png");map[5][6] = LoadImageHandler.loadImage("F_E.png");map[5][7] = LoadImageHandler.loadImage("F_F.png");map[5][8] = LoadImageHandler.loadImage("F_G.png");map[5][9] = LoadImageHandler.loadImage("F_H.png");map[5][10] = LoadImageHandler.loadImage("F_I.png");map[5][11] = LoadImageHandler.loadImage("Road _.png");
		map[6][1] = LoadImageHandler.loadImage("G_1.png");map[6][2] = LoadImageHandler.loadImage("G_A.png");map[6][3] = LoadImageHandler.loadImage("G_B.png");map[6][4] = LoadImageHandler.loadImage("G_C.png");map[6][5] = LoadImageHandler.loadImage("G_D.png");map[6][6] = LoadImageHandler.loadImage("G_E.png");map[6][7] = LoadImageHandler.loadImage("G_F.png");map[6][8] = LoadImageHandler.loadImage("G_G.png");map[6][9] = LoadImageHandler.loadImage("G_H.png");map[6][10] = LoadImageHandler.loadImage("G_I.png");
		map[7][2] = LoadImageHandler.loadImage("H_A.png");map[7][3] = LoadImageHandler.loadImage("H_B.png");map[7][4] = LoadImageHandler.loadImage("H_C.png");map[7][5] = LoadImageHandler.loadImage("H_D.png");map[7][6] = LoadImageHandler.loadImage("H_E.png");map[7][7] = LoadImageHandler.loadImage("H_F.png");map[7][8] = LoadImageHandler.loadImage("H_G.png");map[7][9] = LoadImageHandler.loadImage("H_H.png");map[7][10] = LoadImageHandler.loadImage("H_I.png");map[7][11] = LoadImageHandler.loadImage("Road _.png");
		map[8][0] = LoadImageHandler.loadImage("Road _.png");map[8][1] = LoadImageHandler.loadImage("I_1.png");map[8][2] = LoadImageHandler.loadImage("I_A.png");map[8][3] = LoadImageHandler.loadImage("I_B.png");map[8][4] = LoadImageHandler.loadImage("I_C.png");map[8][5] = LoadImageHandler.loadImage("I_D.png");map[8][6] = LoadImageHandler.loadImage("I_E.png");map[8][7] = LoadImageHandler.loadImage("I_F.png");map[8][8] = LoadImageHandler.loadImage("I_G.png");map[8][9] = LoadImageHandler.loadImage("I_H.png");map[8][10] = LoadImageHandler.loadImage("I_I.png");
		map[9][1] = LoadImageHandler.loadImage("J_1.png");map[9][2] = LoadImageHandler.loadImage("J_A.png");map[9][3] = LoadImageHandler.loadImage("J_B.png");map[9][4] = LoadImageHandler.loadImage("J_C.png");map[9][5] = LoadImageHandler.loadImage("J_D.png");map[9][6] = LoadImageHandler.loadImage("J_E.png");map[9][7] = LoadImageHandler.loadImage("J_F.png");map[9][8] = LoadImageHandler.loadImage("J_G.png");map[9][9] = LoadImageHandler.loadImage("J_H.png");map[9][10] = LoadImageHandler.loadImage("J_I.png");
		map[10][2] = LoadImageHandler.loadImage("K_A.png");map[10][4] = LoadImageHandler.loadImage("K_C.png");map[10][5] = LoadImageHandler.loadImage("K_D.png");map[10][6] = LoadImageHandler.loadImage("K_E.png");map[10][7] = LoadImageHandler.loadImage("K_F.png");map[10][8] = LoadImageHandler.loadImage("K_G.png");map[10][10] = LoadImageHandler.loadImage("Road -.png");
		map[11][2] = LoadImageHandler.loadImage("L_A.png");map[11][4] = LoadImageHandler.loadImage("L_C.png");map[11][7] = LoadImageHandler.loadImage("L_F.png");
		map[12][2] = LoadImageHandler.loadImage("Road -.png");map[12][4] = LoadImageHandler.loadImage("Road -.png");map[12][7] = LoadImageHandler.loadImage("Road -.png");
		for(int x = 0; x < 13; x ++){
			for(int y = 0; y < 12; y ++){
				if(map[x][y] == null)map[x][y] = LoadImageHandler.loadImage("Grass.png");
			}
		}
		for(int yb = 0; yb < 3024; yb ++){
			for(int xb = 0; xb < 3276; xb ++){
				if(map[(xb * 5)/1260][(yb * 5)/1260].getRGB((xb * 5)%1260, (yb * 5)%1260) == -13421773)navMap[xb][yb] = 1;
				else navMap[xb][yb] = 0;
				soldierMap.setRGB(xb, yb, map[(xb * 5)/1260][(yb * 5)/1260].getRGB((xb * 5)%1260, (yb * 5)%1260));
			}
		}
		RenderCallHandler.addRenderCall(map[0][2], 0, 0, 0, 0, "t");
		RenderCallHandler.addRenderCall(loadedMap[0][0], -1810, -1760, 1260, 1260, "00");
		RenderCallHandler.addRenderCall(loadedMap[0][1], -1810, -500, 1260, 1260, "01");
		RenderCallHandler.addRenderCall(loadedMap[0][2], -1810, 760, 1260, 1260, "02");
		RenderCallHandler.addRenderCall(loadedMap[1][0], -550, -1760, 1260, 1260, "10");
		RenderCallHandler.addRenderCall(loadedMap[1][1], -550, -500, 1260, 1260, "11");
		RenderCallHandler.addRenderCall(loadedMap[1][2], -550, 760, 1260, 1260, "12");
		RenderCallHandler.addRenderCall(loadedMap[2][0], 710, -1760, 1260, 1260, "20");
		RenderCallHandler.addRenderCall(loadedMap[2][1], 710, -500, 1260, 1260, "21");
		RenderCallHandler.addRenderCall(loadedMap[2][2], 710, 760, 1260, 1260, "22");
		update();
	}
	
	public static void update(){
		int indexX = WorldShifter.soldierX / 1260;
		int indexY = (WorldShifter.soldierY + 2520) / 1260;
		if(indexY < oldIndexY){yPos -= 1260;}
		if(indexX < oldIndexX){xPos -= 1260;}
		for(int x = 0; x < 3; x ++){
			for(int y = 0; y < 3; y ++){
				try{loadedMap[x][y] = map[indexX+x-1][indexY+y-1];}
				catch(Exception e){loadedMap[x][y] = map[0][0];}
				loadedIndexX[x][y] = indexX+x-1;
				loadedIndexY[x][y] = indexY+y-1;
			}
		}
		if(oldIndexX != indexX || oldIndexY != indexY){
			Graphics g = loadedMapComposite.createGraphics();
			ImageObserver iObs = null;
			for(int x = 0; x < 3; x ++){
				for(int y = 0; y < 3; y ++){
					try{
						g.drawImage(loadedMap[x][y], x * 1260, y * 1260, 1260, 1260, iObs);
					}catch(Exception e){}
				}
			}
		}
		oldIndexX = indexX;
		oldIndexY = indexY;
		if((xPos - 425)/ (-1260) > 0){xPos += 1260;}
		if((yPos - 250)/ (-1260) > 0){yPos += 1260;}
		RenderCallHandler.updateRenderCall(loadedMap[0][0], xPos - 1260, yPos - 1260, 1260, 1260, "00");
		RenderCallHandler.updateRenderCall(loadedMap[0][1], xPos - 1260, yPos, 1260, 1260, "01");
		RenderCallHandler.updateRenderCall(loadedMap[0][2], xPos - 1260, yPos + 1260, 1260, 1260, "02");
		RenderCallHandler.updateRenderCall(loadedMap[1][0], xPos, yPos - 1260, 1260, 1260, "10");
		RenderCallHandler.updateRenderCall(loadedMap[1][1], xPos, yPos, 1260, 1260, "11");
		RenderCallHandler.updateRenderCall(loadedMap[1][2], xPos, yPos + 1260, 1260, 1260, "12");
		RenderCallHandler.updateRenderCall(loadedMap[2][0], xPos + 1260, yPos - 1260, 1260, 1260, "20");
		RenderCallHandler.updateRenderCall(loadedMap[2][1], xPos + 1260, yPos, 1260, 1260, "21");
		RenderCallHandler.updateRenderCall(loadedMap[2][2], xPos + 1260, yPos + 1260, 1260, 1260, "22");
	}
	
	public static void refresh(){
		xPos = -550;yPos = -500;oldIndexX = 0;oldIndexY = 0;
		update();
		}
	
}
