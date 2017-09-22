package handlers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadImageHandler {

	public static BufferedImage loadImage(String ImageName){
		try{
			BufferedImage LoadImageReturn = ImageIO.read(LoadImageHandler.class.getResource("/images/" + ImageName));
			return LoadImageReturn;
        }
        catch(IOException LIEx){
            System.out.println("Error <<" + ImageName +">> not found");
            return null;
        }
	}

}
