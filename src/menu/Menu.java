package menu;

import game.Snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu extends JMenu{

    private Snake snake;
    private List<JMenu> menus = new ArrayList<>();
    
    public Menu(){
        super();
        menus.add(new JMenu("Játék"));
        menus.add(new JMenu("Beállítások"));
        menus.add(new JMenu("Segítség"));
        
        JMenuItem ujjatek = new JMenuItem("�j J�t�k (F2)");
        JMenuItem toplist = new JMenuItem("Toplista");
        JMenuItem kilepes = new JMenuItem("Kil�p�s (ALT+F4)");

        JMenuItem nehez = new JMenuItem("Neh�z");
        JMenuItem normal = new JMenuItem("Norm�l");
        JMenuItem konnyu = new JMenuItem("K�nny�");

        JMenuItem iranyitas = new JMenuItem("Ir�ny�t�s");
        JMenuItem keszito = new JMenuItem("K�sz�t�");

        ujjatek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.resetGame();
            }
        });
        toplist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(snake.getJatekTer(), snake.getScrollPane());
            }
        });
        kilepes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menus.get(0).add(ujjatek);
        menus.get(0).addSeparator();
        menus.get(0).add(toplist);
        menus.get(0).addSeparator();
        menus.get(0).add(kilepes);
        
        nehez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setSebesseg(50);
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setSebesseg(70);
            }
        });
        konnyu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setSebesseg(90);
            }
        });

        menus.get(1).add(nehez);
        menus.get(1).addSeparator();
        menus.get(1).add(normal);
        menus.get(1).addSeparator();
        menus.get(1).add(konnyu);
        
        keszito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(snake.getJatekTer(), "K�sz�t�: K�rlek Refaktor�lj\n" + "Programn�v: Snake\n" + "Verzi�sz�m: v0.7");
            }
        });
        iranyitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(snake.getJatekTer(), "Ir�ny�t�s a kurzor seg�ts�g�vel:\n" + "-Fel ny�l: a k�gy� felfele mozog\n"
                        + "-Le ny�l: a k�gy� lefele mozog\n" + "-Jobbra ny�l: a k�gy� jobbra mozog\n" + "-Balra ny�l: a k�gy� balra mozog\n");
            }
        });

        menus.get(2).add(keszito);
        menus.get(2).addSeparator();
        menus.get(2).add(iranyitas);
    }
    public void setSnake(Snake snake) {
        this.snake = snake;
    }
    public List<JMenu> getMenus() {
        return new ArrayList<>(menus);
    }
}
