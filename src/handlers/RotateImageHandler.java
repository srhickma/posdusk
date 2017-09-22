package handlers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RotateImageHandler {
	
	public static BufferedImage rotateImage(BufferedImage image, Double angle, int originX, int originY){
		BufferedImage newImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.rotate(Math.toRadians(angle), originX, originY);
		graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		return newImage;
	}
	
}
