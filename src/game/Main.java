package game;

import iooperations.FileHandler;
import listeners.SnakeKeyListener;

/*
 * A main() f�ggv�ny.
 * L�trehoz egy SNake objektumot, mely a program alapk�ve.
 */

public class Main {

	public static void main(String[] args) {
	    SnakeKeyListener snakeKeyListener = new SnakeKeyListener();
	    FileHandler fileHandler = new FileHandler();
	    
		Snake snake = new Snake(snakeKeyListener, fileHandler);
		snakeKeyListener.setSnake(snake);
	}

}
