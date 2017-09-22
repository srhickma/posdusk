package handlers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class CanvasUpdateHandler {
	
	public static Graphics2D paintImage(Graphics2D PaintedImage, String DrawAction, BufferedImage DrawImage, String DrawString, int DrawCR, int DrawCG, int DrawCB, int DrawXPOS, int DrawYPOS, int DrawWidth, int DrawHeight, boolean Fill, int FillCR, int FillCG, int FillCB, int FillXPOS, int FillYPOS, int FillWidth, int FillHeight){
		if(Fill){
			PaintedImage.setPaint(new Color(FillCR, FillCG, FillCB));
			PaintedImage.fillRect(FillXPOS, FillYPOS, FillWidth, FillHeight);
		}
		PaintedImage.setPaint(new Color(DrawCR, DrawCG, DrawCB));
		switch(DrawAction){
			case "drawImage":
				ImageObserver ImOb = null;
				PaintedImage.drawImage(DrawImage, DrawXPOS, DrawYPOS, DrawWidth, DrawHeight, ImOb);
				break;
			case "drawString":
				PaintedImage.drawString(DrawString, DrawXPOS, DrawYPOS);
				break;
		}
		return PaintedImage;
	}
	
}
