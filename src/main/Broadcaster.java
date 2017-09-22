package main;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Broadcaster {
	
	public static List<Object> classes = new ArrayList<Object>();
	public static List<Object> cTRemove = new ArrayList<Object>();
	
	public static void addClass(Object o){classes.add(o);}
	
	public static void removeClass(Object o){classes.remove(classes.indexOf(o));}
	
	public static void removeClassInside(Object o){cTRemove.add(o);}
	
	public static void u(){
		for(Object o : cTRemove)
			classes.remove(classes.indexOf(o));
		cTRemove = new ArrayList<Object>();
	}
	
	public static void broadcastVoid(String name, int shiftX, int shiftY){
		Method method;
			for(Object o : classes){
				try{
					if(name.equals("shift")){
						@SuppressWarnings("rawtypes")
						Class[] cArg = new Class[]{int.class, int.class};
						method = (o.getClass()).getMethod(name, cArg);
						method.invoke(o, shiftX, shiftY);
					}
					else{
						method = (o.getClass()).getMethod(name);
						method.invoke(o);
					}
				}
				catch(Exception e){}
			}
	}
	
	public static void broadcastNavMap(int[][] navMap, int xMult, int yMult){
		Method method;
			for(Object o : classes){
				try{
					@SuppressWarnings("rawtypes")
					Class[] cArg = new Class[]{int[][].class, int.class, int.class};
					method = (o.getClass()).getMethod("setNavMap", cArg);
					method.invoke(o, (Object) navMap, xMult, yMult);
				}
				catch(Exception e){}
			}
	}
	
}
