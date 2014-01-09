package game;

import interfaces.SnakeInterface;
import iooperations.FileHandler;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import listener.SnakeKeyListener;
import toplist.TopListElement;

public class Snake implements Runnable, SnakeInterface {
    private static final int EGYSEG = 10;
    /**
     * 
     */
    private int palyaSzelesseg = 50 * EGYSEG;
    private int palyaMagassag = 30 * EGYSEG;
    private int sebesseg, pontok, hossz, xValt, yValt;
    private boolean fut, mehetBalra, mehetJobbra, mehetFel, mehetLe, evett, magabaMent, gameOver;
    private int[] pozX = new int[125];
    private int[] pozY = new int[125];
    private Point[] points = new Point[125];
    private Random random = new Random();

    private FileHandler fileHandler;
    private List<TopListElement> lista = new ArrayList<TopListElement>();

    private JButton[] snake = new JButton[125];
    private Frame frame;

    public Snake(SnakeKeyListener sankeKeyListener, FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        fileHandler.fajlmegnyitas(lista);
        frame = new Frame("Snake v0.7");
        init();

        elsoSnake();
        frame.setupFrame(palyaSzelesseg, palyaMagassag, EGYSEG);

        frame.addKeyListener(sankeKeyListener);
    }

    public void init() {
        pozX[0] = 24 * EGYSEG;
        pozY[0] = 14 * EGYSEG;
        sebesseg = 70;
        pontok = 0;
        hossz = 3;
        xValt = +EGYSEG;
        yValt = 0;
        fut = false;
        magabaMent = false;
        mehetBalra = false;
        mehetJobbra = true;
        mehetFel = true;
        mehetLe = true;
        evett = true;
        gameOver = false;
    }

    public void start() {
        fut = true;
        (new Thread(this)).start();
    }

    public void setMenuBar(JMenuBar menubar) {
        frame.setJMenuBar(menubar);
    }

    public void resetGame() {
        init();
        frame.resetFrame(gameOver, pontok);
        elsoSnake();
        start();
    }

    void elsoSnake() {
        for (int i = 0; i < hossz; i++) {
            snake[i] = new JButton();
            snake[i].setEnabled(false);
            snake[i].setBounds(pozX[i], pozY[i], EGYSEG, EGYSEG);
            snake[i].setBackground(Color.BLACK);

            frame.addComponentToJatekTer(snake[i]);

            pozX[i + 1] = pozX[i] - EGYSEG;
            pozY[i + 1] = pozY[i];
        }
    }
    
    void novekszik() {
        snake[hossz] = new JButton();
        snake[hossz].setEnabled(false);
        snake[hossz].setBackground(Color.BLACK);

        frame.addComponentToJatekTer(snake[hossz]);

        int kajax = 20 + (EGYSEG * random.nextInt(46));
        int kajay = 20 + (EGYSEG * random.nextInt(26));

        pozX[hossz] = kajax;
        pozY[hossz] = kajay;
        snake[hossz].setBounds(pozX[hossz], pozY[hossz], EGYSEG, EGYSEG);

        hossz++;
    }

    /*
     * Ez a f�ggv�ny a j�t�k v�g�t vizsg�lja. Megn�zi, hogy a k�gy� hal�la ut�n
     * felker�l-e a toplist�ra a j�t�kos az el�rt eredm�ny�vel. Ha igen akkor
     * bek�ri a nev�t, �s friss�ti a toplist�t. Ha nem akkor egy j�t�k v�ge
     * k�perny�t rajzol ki. A v�g�n pedig szerializ�l.
     */
    void toplistabaTesz() {
        frame.removeJatekTer();

        // Ha az el�rt eredm�ny jobb az eddigi legkisebb eredm�nyn�l
        if (pontok > lista.get(9).getPont()) {
            // Egy ArrayList l�trehoz�sa, mely a megadott nevet t�rolja
            final ArrayList<String> holder = new ArrayList<String>();

            frame.nyert(holder, lista, pontok);

            frame.toplistaFrissites(this);
            // Ha az eredm�ny nincs bent a legjobb 10-be
        } else {
            frame.nemNyert();
            frame.toplistaFrissites(this);
        }
        fileHandler.fajlbairas(lista);
    }

    /*
     * A mozgat� f�ggv�ny megv�ltoztatja a k�gy� poz�ci�j�t a megadott ir�nyba,
     * �s k�zben vizsg�lja, hogy a k�gy� nem �tk�z�tt-e falnak vagy mag�nak,
     * illetve azt, hogy evett-e
     */
    void mozgat() {
        // Lek�ri a k�gy� �sszes elem�nek poz�ci�j�t a p�ly�n
        for (int i = 0; i < hossz; i++) {
            points[i] = snake[i].getLocation();
        }

        // Megv�ltoztatja az els� elemnek a poz�ci�j�t a megadott ir�nyba
        pozX[0] = pozX[0] + xValt;
        pozY[0] = pozY[0] + yValt;
        snake[0].setBounds(pozX[0], pozY[0], EGYSEG, EGYSEG);

        // Megv�ltoztatja a t�bbi elem helyzet�t az el�tt l�v� elem�re
        for (int i = 1; i < hossz; i++) {
            snake[i].setLocation(points[i - 1]);
        }

        // Ellen�rzi, hogy a k�gy� nem-e ment �nmag�ba
        for (int i = 1; i < hossz - 1; i++) {
            if (points[0].equals(points[i])) {
                magabaMent = true;
            }
        }

        // Ellen�rzi, hogy a k�gy� nem-e ment �nmag�ba vagy falnak. Ha igen
        // akkor a j�t�knak v�ge proced�ra zajlik le, illetve le�ll a mozgat�s
        if ((pozX[0] + 10 == palyaSzelesseg) || (pozX[0] == 0) || (pozY[0] == 0) || (pozY[0] + 10 == palyaMagassag) || (magabaMent == true)) {
            fut = false;
            gameOver = true;
            toplistabaTesz();
        }

        // Ellen�rzi, hogy a k�gy� nem �rte-e el az �telt. Ha igen akkor n�veli
        // a pontsz�mot
        if (pozX[0] == pozX[hossz - 1] && pozY[0] == pozY[hossz - 1]) {
            evett = true;
            pontok = pontok + 5;
            frame.setPont(pontok);
        }

        // Ha evett, akkor l�trehozza az �j �telt �s n�veli a k�gy�t, k�l�nben
        // az �tel ott marad ahol volt
        if (evett == true) {
            novekszik();
            evett = false;
        } else {
            snake[hossz - 1].setBounds(pozX[hossz - 1], pozY[hossz - 1], EGYSEG, EGYSEG);
        }

        // A p�lya friss�t�se
        frame.repaint();
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(int keyCode) {
        if (mehetBalra && keyCode == java.awt.event.KeyEvent.VK_LEFT) {
            xValt = -EGYSEG;
            yValt = 0;
            mehetJobbra = false;
            mehetFel = true;
            mehetLe = true;
        } else if (mehetFel && keyCode == java.awt.event.KeyEvent.VK_UP) {
            xValt = 0;
            yValt = -EGYSEG;
            mehetLe = false;
            mehetJobbra = true;
            mehetBalra = true;
        } else if (mehetJobbra && keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
            xValt = +EGYSEG;
            yValt = 0;
            mehetBalra = false;
            mehetFel = true;
            mehetLe = true;
        } else if (mehetLe && keyCode == java.awt.event.KeyEvent.VK_DOWN) {
            xValt = 0;
            yValt = +EGYSEG;
            mehetFel = false;
            mehetJobbra = true;
            mehetBalra = true;
        } else if (keyCode == java.awt.event.KeyEvent.VK_F2) {
            resetGame();
        }
    }

    @Override
    public void run() {
        while (fut) {
            mozgat();
            try {
                Thread.sleep(sebesseg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public JPanel getJatekTer() {
        return frame.getJatekTer();
    }

    public JScrollPane getScrollPane() {
        return frame.getScrollPane();
    }

    public void setSebesseg(int sebesseg) {
        this.sebesseg = sebesseg;
    }

    public List<TopListElement> getLista() {
        return new ArrayList<>(lista);
    }
}
