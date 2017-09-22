package main;

public class Launcher {
	
	public static void main(String[] args) {
		Main main = new Main();
		Thread myThread;
		myThread = new Thread(main);
		myThread.start();
		main.running = true;
	}

}
