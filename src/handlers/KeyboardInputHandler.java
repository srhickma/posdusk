package handlers;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class KeyboardInputHandler  extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	static List<Integer> keysPressed = new ArrayList<Integer>();
	static List<Integer> listenKeys = new ArrayList<Integer>();
	static List<String> listenKeyMethods = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	static List<Class> listenKeyClasses = new ArrayList<Class>();
	static List<Object> listenKeyObjects = new ArrayList<Object>();
	static List<Boolean> listenKeyIsStatic = new ArrayList<Boolean>();
	
	public static void keyTyped(KeyEvent kTE){}
	
	@SuppressWarnings("unchecked")
	public static void keyPressed(KeyEvent kPE){
		if(!keysPressed.contains(kPE.getKeyCode())){
			keysPressed.add(kPE.getKeyCode());
			if(listenKeys.contains(kPE.getKeyCode())){
				Method method;
				int index = listenKeys.indexOf(kPE.getKeyCode());
				try{
					@SuppressWarnings("rawtypes")
					Class[] cArg = new Class[]{int.class};
					if(listenKeyIsStatic.get(index))method = listenKeyClasses.get(index).getMethod(listenKeyMethods.get(index), cArg);
					else method = (listenKeyClasses.get(index)).getMethod(listenKeyMethods.get(index), cArg);
					method.invoke(listenKeyObjects.get(index), kPE.getKeyCode());
				}catch(Exception e){}
			}
		}
	}
	
	public static void keyReleased(KeyEvent kRE){
		if(keysPressed.contains(kRE.getKeyCode()))keysPressed.remove(keysPressed.indexOf(kRE.getKeyCode()));
	}
	
	public static boolean keyDown(int keyDownCode){
		if(keysPressed.contains(keyDownCode))return true;
		else return false;
	}
	
	public static void addListenKey(int key, String method, @SuppressWarnings("rawtypes") Class clazz, Object object, boolean isStatic){
		listenKeys.add(key);
		listenKeyMethods.add(method);
		listenKeyClasses.add(clazz);
		listenKeyObjects.add(object);
		listenKeyIsStatic.add(isStatic);
	}

}
