package game;

import javax.swing.JFrame;

public class Frame extends JFrame {

    private static final int HEIGHT = 380;
    private static final int WIDTH = 506;

    public Frame(String string) {
        super(string);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
}
