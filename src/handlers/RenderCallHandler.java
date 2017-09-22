package handlers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RenderCallHandler {
	
	public static List<BufferedImage> RenderCallImage = new ArrayList<BufferedImage>();
	public static List<Integer> RenderCallX = new ArrayList<Integer>();
	public static List<Integer> RenderCallY = new ArrayList<Integer>();
	public static List<Integer> RenderCallWidth = new ArrayList<Integer>();
	public static List<Integer> RenderCallHeight = new ArrayList<Integer>();
	public static List<String> RenderCallIndex = new ArrayList<String>();
	
	public static void addRenderCall(BufferedImage RCImage, int XPOS, int YPOS, int Width, int Height, String Index){
		RenderCallImage.add(RCImage);
		RenderCallX.add(XPOS);
		RenderCallY.add(YPOS);
		RenderCallWidth.add(Width);
		RenderCallHeight.add(Height);
		RenderCallIndex.add(Index);
	}
	
	public static void updateRenderCall(BufferedImage RCImage, int XPOS, int YPOS, int Width, int Height, String Index){
		int xxx = 0;
		for(String S : RenderCallIndex){
			if(S.equals(Index)){
				RenderCallImage.remove(xxx);
				RenderCallX.remove(xxx);
				RenderCallY.remove(xxx);
				RenderCallWidth.remove(xxx);
				RenderCallHeight.remove(xxx);
				RenderCallImage.add(xxx, RCImage);
				RenderCallX.add(xxx, XPOS);
				RenderCallY.add(xxx, YPOS);
				RenderCallWidth.add(xxx, Width);
				RenderCallHeight.add(xxx, Height);
			}
			xxx ++;
		}
	}
	
	public static void removeRenderCall(String Index){
		int xxx = 0;
		for(String S : RenderCallIndex){
			if(S.equals(Index)){
				RenderCallImage.remove(xxx);
				RenderCallX.remove(xxx);
				RenderCallY.remove(xxx);
				RenderCallWidth.remove(xxx);
				RenderCallHeight.remove(xxx);
		        RenderCallIndex.remove(xxx);
		        break;
			}
			xxx ++;
		}
	}
	
	public static boolean searchRenderCall(String Index){
		for(String S : RenderCallIndex){
			if(S.equals(Index)){
				return true;
			}
		}
		return false;
	}
	
}
