package engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class GameStateManager {

	public GameState currentState;
	
	public GameStateManager() {

	}
	
	public void update() {
		if (currentState != null) {
			currentState.update();
		}
	}
	
	public void render(Graphics2D g) {
		if (currentState != null) {
			currentState.render(g);
		}
	}
	
	// KeyListener
	public void keyPressed(KeyEvent e) {
		if (currentState != null) {
			currentState.keyPressed(e);
		}
	}
		
	public void keyReleased(KeyEvent e) {
		if (currentState != null) {
			currentState.keyReleased(e);
		}
	}
	
	// MouseListener
	public void mousePressed(MouseEvent e) {
		if (currentState != null) {
			currentState.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (currentState != null) {
			currentState.mouseReleased(e);
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		if (currentState != null) {
			currentState.mouseEntered(e);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (currentState != null) {
			currentState.mouseExited(e);
		}
	}
	
	// MouseWheelListener
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (currentState != null) {
			currentState.mouseWheelMoved(e);
		}
	}
	
}