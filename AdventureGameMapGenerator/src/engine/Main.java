package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends JFrame implements KeyListener {

	//Display
	public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Tiles
	public static int tileRes = 32;
	
	// Gui bar size
	public static int guiBarSize = 1;
	
	// Local map size
	public static Dimension localMapSize = new Dimension(16, 12);
	
	// Frame variables
	public static int width = tileRes * localMapSize.width;
	public static int height = tileRes * localMapSize.height + guiBarSize * tileRes;

	// Run variables
	private static boolean isRunning;
	private double FPS;
	private boolean debugMode = false;

	// GameState
	public static GameStateManager gsm = new GameStateManager();
	
	
	public Main() {
		// Listener setup
		addKeyListener(this);

		// Window setup
		setSize(screen.width, screen.height);
		setLocation(0, 0);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// GameState
		gsm.currentState = new Level(this);
		
		// Run setup
		isRunning = true;
		FPS = 40;
		run();
	}
	
	public void run() {
		// Time between each frame in nanoseconds
		double delta = 1000000000 / FPS;

		// Variable setup for recording elapsed time
		long lastTime = System.nanoTime();
		long now;
		long elapsedTime = 0;

		// Render variable
		boolean canRender;

		while (isRunning) {
			// Identifies the elapsed time since last update
			now = System.nanoTime();
			elapsedTime += (now - lastTime);
			lastTime = now;
			canRender = false;
			// Makes updates happen every 'delta' amount of time has passed
			if (elapsedTime >= delta) {
				elapsedTime -= delta;
				canRender = true;
				// Update
				update();
			}
			// Render
			if (canRender) {
				render();
			}
		}
	}

	public void update() {
		// GameState
		gsm.update();
	}

	public void render() {
		// Creates 2-buffer buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (getBufferStrategy() == null) {
			createBufferStrategy(2);
			return;
		}

		// Draws to hidden buffer, clears previous image
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setBackground(Color.getHSBColor(0.0f, 0.2f, 0.4f));
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.clearRect(0, 0, screen.width, screen.height);
		
		// GameState
		gsm.render(g);
		
		// Disposes graphics object and shows hidden buffer
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		// Main
		new Main();
		System.exit(0);
	}

	//// KeyListener
	@Override
	public void keyPressed(KeyEvent e) {
		// GameState
		gsm.keyPressed(e);

		// Entire Game key commands
		switch (e.getKeyCode()) {
		case KeyEvent.VK_BACK_SPACE:
			System.out.print("Exit message!");
			System.exit(1);
			break;
			
		case KeyEvent.VK_BACK_QUOTE:
			debugMode = !debugMode;
			if (debugMode) {
				System.out.println("[Debugmode on]");
			} else {
				System.out.println("[Debugmode off]");
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// GameState
		gsm.keyReleased(e);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}





}
