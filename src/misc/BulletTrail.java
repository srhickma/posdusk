package misc;

import handlers.RenderCallHandler;

import java.awt.image.BufferedImage;

import main.Broadcaster;

public class BulletTrail {
		
	public BufferedImage image;
	long creTime, curTime;
	boolean done = false;
		
	public BulletTrail(BufferedImage imageb){
		image = imageb;
		start();
	}
	
	public void start(){
		creTime = System.currentTimeMillis();
		Broadcaster.addClass(this);
		RenderCallHandler.addRenderCall(image, 0, 0, 860, 510, String.valueOf(creTime));
	}
		
	public void update(){
		if(!done){
			curTime = System.currentTimeMillis();
			if(curTime - creTime > 10){
				Broadcaster.removeClassInside(this);
				RenderCallHandler.removeRenderCall(String.valueOf(creTime));
				done = true;
			}
		}
	}
		
}
