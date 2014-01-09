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
        
        JMenuItem ujjatek = new JMenuItem("Új Játék (F2)");
        JMenuItem toplist = new JMenuItem("Toplista");
        JMenuItem kilepes = new JMenuItem("Kilépés (ALT+F4)");

        JMenuItem nehez = new JMenuItem("Nehéz");
        JMenuItem normal = new JMenuItem("Normál");
        JMenuItem konnyu = new JMenuItem("Könnyű");

        JMenuItem iranyitas = new JMenuItem("Irányítás");
        JMenuItem keszito = new JMenuItem("Készítő");

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
                JOptionPane.showMessageDialog(snake.getJatekTer(), "Készítő: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.7");
            }
        });
        iranyitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(snake.getJatekTer(), "Irányítás a kurzor segítségével:\n" + "-Fel nyíl: a kígyó felfele mozog\n"
                        + "-Le nyíl: a kígyó lefele mozog\n" + "-Jobbra nyíl: a kígyó jobbra mozog\n" + "-Balra nyíl: a kígyó balra mozog\n");
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
