package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import characters.NPCSpawner;
import characters.Soldier;
import characters.ZombieSpawner;
import handlers.*;
import map.*;
import misc.LootSpawner;

public class Main extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	JFrame window = new JFrame("Posdusk");
	JTextField typingArea = new JTextField(1);
	boolean running = false, mouseDown = false, refreshNeeded = false;
	public Game game  = new Game(this);
	HUD hUD;
	public BufferedImage icon = LoadImageHandler.loadImage("Posdusk Icon.png");
	public BufferedImage renderImage = new BufferedImage(860,510,BufferedImage.TYPE_INT_RGB);
	Graphics2D renderGraphics = renderImage.createGraphics();
	public int mouseX = 0, mouseY = 0, mouseWheelTurns = 0;
	
	public Main(){
		Broadcaster.addClass(game);
        setSize(new Dimension(850,500));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setIconImage(icon);
        window.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        window.add(typingArea);
        window.add(this,BorderLayout.CENTER);
        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}
	
	public void paint(Graphics g){
		ImageObserver I = null;
		g.drawImage(renderImage, 0, 0, 860, 510, I);
	}
	
	public void update(Graphics g){paint(g);}
	
	public void render(){
		CanvasUpdateHandler.paintImage(renderGraphics, "", null, "", 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, 860, 510);
		int xxx = 0;
		for(BufferedImage BI : RenderCallHandler.RenderCallImage){
			CanvasUpdateHandler.paintImage(renderGraphics, "drawImage", BI, "", 250, 250, 250, RenderCallHandler.RenderCallX.get(xxx), RenderCallHandler.RenderCallY.get(xxx), RenderCallHandler.RenderCallWidth.get(xxx), RenderCallHandler.RenderCallHeight.get(xxx), false, 0, 0, 0, 0, 0, 0, 0);
			xxx ++;
		}
		CanvasUpdateHandler.paintImage(renderGraphics, "drawImage", hUD.hud, "", 250, 250, 250, 0, 0, 860, 510, false, 0, 0, 0, 0, 0, 0, 0);
		repaint();
	}
	
	public void keyTyped(KeyEvent KTE){KeyboardInputHandler.keyTyped(KTE);}

	public void keyPressed(KeyEvent KPE){KeyboardInputHandler.keyPressed(KPE);}
	
	public void keyReleased(KeyEvent KRE){KeyboardInputHandler.keyReleased(KRE);}
	
	public void run(){
		Clock.game = game;
		BufferedImage logo = LoadImageHandler.loadImage("KJS4BLock Screen.png");
		RenderCallHandler.addRenderCall(logo, 0, 0, 860, 510, "Loading");
		render();
		typingArea.addKeyListener(this);
		this.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				PointerInfo a = MouseInfo.getPointerInfo();
				Point point = new Point(a.getLocation());
				SwingUtilities.convertPointFromScreen(point, e.getComponent());
				mouseX = (int) point.getX();
				mouseY = (int) point.getY();	
			}
			public void mouseDragged(MouseEvent e){
				PointerInfo a = MouseInfo.getPointerInfo();
				Point point = new Point(a.getLocation());
				SwingUtilities.convertPointFromScreen(point, e.getComponent());
				mouseX = (int) point.getX();
				mouseY = (int) point.getY();	
			}
		});
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if(e.getButton() == 1){
					PointerInfo a = MouseInfo.getPointerInfo();
					Point point = new Point(a.getLocation());
					SwingUtilities.convertPointFromScreen(point, e.getComponent());
					if(game.inMenu)game.pressButton(point.getX(), point.getY());
					else if(Soldier.dead && point.getX() < 245 && point.getX() > 45 && point.getY() < 75 && point.getY() > 56)refreshNeeded = true;
					else if(Soldier.trading)Soldier.trade(point.getX(), point.getY());
					else if(Soldier.buildingStructure)Soldier.queueStructure();
					else Soldier.shootSemi(point.getX(), point.getY());
					mouseDown = true;
				}
			}
			public void mouseReleased(MouseEvent e){mouseDown = false;}
		});
		this.addMouseWheelListener(new MouseAdapter(){
			public void mouseWheelMoved(MouseWheelEvent e){
				mouseWheelTurns ++;
				if(e.getWheelRotation() > 0 && mouseWheelTurns > 5){
					Soldier.structureRotation += 90;
					mouseWheelTurns = 0;
				}
				else if(mouseWheelTurns > 5){
					Soldier.structureRotation -=90;
					mouseWheelTurns = 0;
				}
			}
			public void mouseReleased(MouseEvent e){mouseDown = false;}
		});

		System.out.println("3");
		
		Soldier.configureWeapons();

		System.out.println("4");
		
		Map.start();

		System.out.println("5");
		
		Soldier.start(this);

		System.out.println("6");
		
		ZombieSpawner zombieSpawner = new ZombieSpawner(this);
		LootSpawner lootSpawner = new LootSpawner();
		NPCSpawner nPCSpawner = new NPCSpawner();

		System.out.println("7");
		
		Broadcaster.broadcastVoid("start", 0, 0);
		long LASUTime = System.nanoTime();
		long LASRTime = System.nanoTime();
		long LASWTime = System.nanoTime();
		while (running){
			typingArea.requestFocusInWindow();
			long CURUTime = System.nanoTime();
			long CURRTime = System.nanoTime();
			long CURWTime = System.nanoTime();
			long PASUTime = CURUTime - LASUTime;
			long PASRTime = CURRTime - LASRTime;
			long PASWTime = CURWTime - LASWTime;
			if(refreshNeeded){
				WorldShifter.refresh();
				Broadcaster.broadcastVoid("refresh", 0, 0);
				Map.refresh();
				Soldier.refresh();
				Clock.refresh();
				refreshNeeded = false;
			}
			if(PASUTime > 10000000){
				if(!game.inMenu){
					zombieSpawner.spawn();
					lootSpawner.spawn();
					nPCSpawner.spawn();
					Clock.update();
					Soldier.update();
					Broadcaster.broadcastVoid("update", 0, 0);
				}
				else hUD.paintHUD();
				Broadcaster.u();
				PASUTime = 0L;
				LASUTime = CURUTime;
				if(mouseDown)Soldier.shootAuto(mouseX, mouseY);
			}
			if(PASRTime > 10000000){
				render();
				PASRTime = 0L;
				LASRTime = CURRTime;
			}
			if(PASWTime > 100000){
				WaitHandler.updateWaits();
				PASWTime = 0L;
				LASWTime = CURWTime;
			}
		}
	}
	
}
