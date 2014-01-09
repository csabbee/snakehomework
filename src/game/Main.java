package game;

import iooperations.FileHandler;
import listeners.SnakeKeyListener;

public class Main {

	public static void main(String[] args) {
	    SnakeKeyListener snakeKeyListener = new SnakeKeyListener();
	    FileHandler fileHandler = new FileHandler();
	    
		Snake snake = new Snake(snakeKeyListener, fileHandler);
		snakeKeyListener.setSnake(snake);
	}

}
