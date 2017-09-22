package main;

import characters.Soldier;
import handlers.*;
import map.*;

public class Game {
	
	static boolean sMWaiting = false;
	public boolean inMenu = true;
	Main main;
	
	public Game(Main main){
		this.main = main;
		HUD hUD = new HUD(main);
		main.hUD = hUD;
		Soldier.hUD = hUD;
	}
	
	public void update(){
		if(!Soldier.dead){
			boolean moved = false;
			if(KeyboardInputHandler.keyDown(83) && !sMWaiting){
				if(KeyboardInputHandler.keyDown(68))WorldShifter.shiftWorld(-4, -4);
				else if(KeyboardInputHandler.keyDown(65))WorldShifter.shiftWorld(4, -4);
				else WorldShifter.shiftWorld(0, -4);
				moved = true;
			}
			else if(KeyboardInputHandler.keyDown(87) && !sMWaiting){
				if(KeyboardInputHandler.keyDown(68))WorldShifter.shiftWorld(-4, 4);
				else if(KeyboardInputHandler.keyDown(65))WorldShifter.shiftWorld(4, 4);
				else WorldShifter.shiftWorld(0, 4);
				moved = true;
			}
			else if(KeyboardInputHandler.keyDown(68) && !sMWaiting){
				if(KeyboardInputHandler.keyDown(83))WorldShifter.shiftWorld(-4, -4);
				else if(KeyboardInputHandler.keyDown(87))WorldShifter.shiftWorld(-4, 4);
				else WorldShifter.shiftWorld(-4, 0);
				moved = true;
			}
			else if(KeyboardInputHandler.keyDown(65) && !sMWaiting){
				if(KeyboardInputHandler.keyDown(83))WorldShifter.shiftWorld(4, -4);
				else if(KeyboardInputHandler.keyDown(87))WorldShifter.shiftWorld(4, 4);
				else WorldShifter.shiftWorld(4, 0);
				moved = true;
			}
			if(moved){
				WaitHandler.addWait(20L, "sMWaiting");
				sMWaiting = true;
				AudioHandler.playAudioClip(Soldier.footstepsSFX, true, -15f);
			}
			if(KeyboardInputHandler.keyDown(82) && !Soldier.wReloading && !Soldier.dead){
				Soldier.wReloading = true;
				WaitHandler.addWait(Soldier.wReloadTime, "wRWaited");
			}
		}
	}
	
	public void pressButton(double x, double y){
		if(x < 350 && x > 100 && y < 145 && y > 126){inMenu = false;}
		else if(x < 350 && x > 100 && y < 165 && y > 146)System.exit(0);
	}
	
	public void refresh(){inMenu = true;}
	
	public static void waitDone(String Output){
		switch(Output){
		case("sMWaiting"):
			sMWaiting = false;
			break;
		case("wWaited"):
			Soldier.wWaited = true;
			break;
		case("wRWaited"):
			Soldier.reload();
			break;
		case("wSSwitch"):
			if(Soldier.w == 2 || Soldier.w == 3){
				Soldier.setWState(Soldier.wState + 1);
				WaitHandler.addWait(100, "wSSwitchD");
			}
			else Soldier.setWState(0);
			break;
		case("wSSwitchD"):
			Soldier.setWState(Soldier.wState - 1);
			if(Soldier.w == 2){
				if((Soldier.wState > 0 && !Soldier.leftFistLast) || (Soldier.wState > 2 && Soldier.leftFistLast))WaitHandler.addWait(100, "wSSwitchD");
				else if(Soldier.wState == 2)Soldier.setWState(0);
				if(Soldier.wState == 0)Soldier.leftFistLast = !Soldier.leftFistLast;
			}
			else if(Soldier.wState > 0)WaitHandler.addWait(100, "wSSwitchD");
			break;
		}
	}
	
}
