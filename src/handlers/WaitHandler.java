package handlers;

import main.Game;

public class WaitHandler{
	
	public static long[] LASTs = new long[100];
	public static long[] CURTs = new long[100];
	public static long[] PASTs = new long[100];
	public static long[] WAITs = new long[100];
	public static String[] Outputs = new String[100];
	
	public WaitHandler(){	
	}
	
	public static void updateWaits(){
		for(int x = 0;x<100;x ++){
			if(LASTs[x] != 0L){
				CURTs[x] = System.nanoTime();
				PASTs[x] = CURTs[x] - LASTs[x];
				if(PASTs[x] > (WAITs[x] * 1000000)){
					Game.waitDone(Outputs[x]);
					LASTs[x] = 0L;
					CURTs[x] = 0L;
					PASTs[x] = 0L;
					WAITs[x] = 0L;
					Outputs[x] = "";
				}
			}
		}
	}
	
	public static void addWait(long WAIT, String Output){
		boolean ATDone = false;
		int i = 0;
		while(!ATDone){
			if(LASTs[i] == 0L){
				LASTs[i] = System.nanoTime();
				CURTs[i] = System.nanoTime();
				PASTs[i] = 0L;
				WAITs[i] = WAIT;
				Outputs[i] = Output;
				ATDone = true;
			}
			i ++;
		}
	}
	
	public static void removeWait(String name){
		int i = 0;
		for(String x : Outputs){
			try{
				if(x.equals(name)){
					Outputs[i] = "";
					LASTs[i] = 0L;
					CURTs[i] = 0L;
					PASTs[i] = 0L;
					WAITs[i] = 0L;
				}
			}catch(Exception e){}
			i ++;
		}
	}
	
}
