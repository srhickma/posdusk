package main;

import characters.Soldier;

public class Clock {
	
	public static Game game;
	public static int day = 1, hour = 12;
	public static float darkness = 0f;
	public static long lasTime = System.currentTimeMillis(), lasHTime = System.currentTimeMillis();
	public static boolean zombiesCanSpawn = false;
	static int ticksPerHour = 15000;
	
	public static void update(){
		if(!Soldier.dead && !game.inMenu){
			long curTime = System.currentTimeMillis(), curHTime = System.currentTimeMillis();
			long pasTime = curTime - lasTime;
			if(pasTime > ticksPerHour){
				lasTime = curTime;
				pasTime = 0;
				hour ++;
				Soldier.thirst --;
				if(hour > 23){
					hour = 0;
					day ++;
				}
				if(hour > 19 || hour < 8){
					zombiesCanSpawn = true;
					darkness = 0.5f;
				}
				else zombiesCanSpawn = false;
				if(hour > 8 && hour < 19)darkness = 0f;
			}
			if(hour == 19)darkness = 0 + (pasTime / (float) ticksPerHour * 0.5f);
			if(hour == 8)darkness = 0.5f - (pasTime / (float) ticksPerHour * 0.5f);
			if(curHTime - lasHTime > ticksPerHour * 1.8){
				Soldier.hunger --;
				lasHTime = curHTime;
			}
		}
	}
	
	public static void refresh(){
		day = 1;hour = 12;
		darkness = 0f;
		zombiesCanSpawn = false;
	}
	
}
