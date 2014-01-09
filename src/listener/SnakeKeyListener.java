package listener;
import interfaces.SnakeInterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeKeyListener implements KeyListener{

    private SnakeInterface snake;
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        snake.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setSnake(SnakeInterface snake) {
        this.snake = snake;
    }

}
