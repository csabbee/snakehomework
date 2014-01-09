package game;
import interfaces.SnakeInterface;
import iooperations.FileHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import listeners.SnakeKeyListener;
import toplist.Toplist;

import comparator.Comp;

public class Snake extends JFrame implements Runnable, SnakeInterface {
    private static final int EGYSEG = 10;
    private static final int WIDTH = 506; 
    private static final int HEIGHT = 380;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int palyaSzelesseg = 50 * EGYSEG;
	private int palyaMagassag = 30 * EGYSEG;
	private int sebesseg, pontok, hossz, xValt, yValt;
	private boolean fut, mehetBalra, mehetJobbra, mehetFel, mehetLe, evett, magabaMent, gameOver;
	private int[] pozX = new int[125];
	private int[] pozY = new int[125];
	private Point[] points = new Point[125];
	private Random random = new Random();
	
	private FileHandler fileHandler;
	private List<Toplist> lista = new ArrayList<Toplist>();
	
	private JButton[] snake = new JButton[125];
	private JFrame frame;
	private JPanel jatekTer, pontSzam, top;
	private JPanel[] keret = new JPanel[4];
	private JLabel pontKiIras;
	private JScrollPane scrollPane;


	public void init() {
	    frame = new JFrame("Snake v0.7");
	    frame.setSize(WIDTH, HEIGHT);
	    
	    
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
		fileHandler.fajlmegnyitas(lista);
		
	    // Az ablak r�szeinek l�trehoz�sa
        jatekTer = new JPanel();
        pontSzam = new JPanel();
        top = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// A p�lya r�szeinek r�szletes be�ll�t�sa (poz�ci�, sz�less�g,
        // magass�g, sz�n) �s hozz�ad�sa az ablakhoz
        frame.add(jatekTer, BorderLayout.CENTER);
        frame.add(pontSzam, BorderLayout.SOUTH);
        frame.setLayout(null);
        jatekTer.setLayout(null);
        jatekTer.setBounds(0, 0, palyaSzelesseg, palyaMagassag);
        jatekTer.setBackground(Color.LIGHT_GRAY);
        pontSzam.setBounds(0, palyaMagassag, palyaSzelesseg, 30);
        pontSzam.setBackground(Color.GRAY);
        top.setBounds(0, 0, palyaSzelesseg, palyaMagassag);
        top.setBackground(Color.LIGHT_GRAY);

        // Keret megrajzol�sa �s hozz�ad�sa a p�ly�hoz
        keret[0] = new JPanel();
        keret[0].setBounds(0, 0, palyaSzelesseg, EGYSEG);
        keret[1] = new JPanel();
        keret[1].setBounds(0, 0, EGYSEG, palyaMagassag);
        keret[2] = new JPanel();
        keret[2].setBounds(0, palyaMagassag - EGYSEG, palyaSzelesseg, EGYSEG);
        keret[3] = new JPanel();
        keret[3].setBounds(palyaSzelesseg - EGYSEG, 0, EGYSEG, palyaMagassag);
        jatekTer.add(keret[0]);
        jatekTer.add(keret[1]);
        jatekTer.add(keret[2]);
        jatekTer.add(keret[3]);
        

        // A pontsz�m k��r�sa a k�perny�re
        pontKiIras = new JLabel("Pontsz�m: " + pontok);
        pontKiIras.setForeground(Color.BLACK);
        pontSzam.add(pontKiIras);

        // Az ablak be�ll�t�sai
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
	}

	public void start() {
		fut = true;
		(new Thread(this)).start();
	}

	public Snake(SnakeKeyListener sankeKeyListener, FileHandler fileHandler) {
	    this.fileHandler = fileHandler;
	    
		init();
		elsoSnake();
		
		frame.addKeyListener(sankeKeyListener);
	}
	public void setMenuBar(JMenuBar menubar){
	    frame.setJMenuBar(menubar);
	}
	
	public void resetGame() {
		// Az �rt�kek kezdeti helyzetbe �ll�t�sa
		init();

		// A p�lya lepucol�sa
		jatekTer.removeAll();
		scrollPane.removeAll();

		// Ha az el�z� j�t�kban meghalt a k�gy�, akkor a j�t�k v�ge kijelz�
		// t�rl�se az ablakb�l
		if (gameOver == true) {
			frame.remove(top);
		}

		// A keret hozz�ad�sa a p�ly�hoz
		jatekTer.add(keret[0]);
		jatekTer.add(keret[1]);
		jatekTer.add(keret[2]);
		jatekTer.add(keret[3]);

		// Az els� k�gy� l�trehoz�sa, kirajzol�sa
		elsoSnake();

		// A p�lya hozz�ad�sa az ablakhoz, annak �jrarajzol�sa �s a pontsz�m
		// ki�r�sa
		frame.add(jatekTer, BorderLayout.CENTER);
		frame.repaint();
		frame.setVisible(true);
		pontKiIras.setText("Pontsz�m: " + pontok);

		// A mozgat�s elind�t�sa
		start();
	}

	
	void elsoSnake() {
		for (int i = 0; i < hossz; i++) {
			snake[i] = new JButton();
			snake[i].setEnabled(false);
			snake[i].setBounds(pozX[i], pozY[i], EGYSEG, EGYSEG);
			snake[i].setBackground(Color.BLACK);

			jatekTer.add(snake[i]);

			pozX[i + 1] = pozX[i] - EGYSEG;
			pozY[i + 1] = pozY[i];
		}
	}

	/*
	 * Ez a f�ggv�ny l�trehozza az �j �telt a p�ly�n random helyen, �s
	 * kirajzolja azt
	 */
	void novekszik() {
		// L�trehozza az �j �telt, �s hozz�adja a p�ly�hoz
		snake[hossz] = new JButton();
		snake[hossz].setEnabled(false);
		snake[hossz].setBackground(Color.BLACK);
		jatekTer.add(snake[hossz]);

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
		frame.remove(jatekTer);

		// Ha az el�rt eredm�ny jobb az eddigi legkisebb eredm�nyn�l
		if (pontok > lista.get(9).getpont()) {
			// Egy ArrayList l�trehoz�sa, mely a megadott nevet t�rolja
			final ArrayList<String> holder = new ArrayList<String>();

			// A ki�r�sok �s a sz�vegmez� l�trehoz�sa
			JLabel nyert1 = new JLabel("A j�t�knak v�ge!");
			JLabel nyert2 = new JLabel("Gratul�lok! Felker�lt�l a toplist�ra. K�rlek add meg a neved (max 10 bet�):");
			final JTextField newnev = new JTextField(10);

			// Ezek hozz�ad�sa a top panelhez
			top.removeAll();
			top.add(nyert1);
			top.add(nyert2);
			top.add(newnev);

			// A sz�vegmez� tartalm�nak hozz�sad�sa a holderhez
			newnev.addActionListener(new ActionListener() {
				@Override
                public void actionPerformed(ActionEvent e) {
					synchronized (holder) {
						holder.add(newnev.getText());
						holder.notify();
					}
					frame.dispose();
				}
			});

			// A top panel hozz�ad�sa az ablakhoz, �s az ablak �jrarajzol�sa
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();

			// V�rakoz�s a sz�vegez� kit�lt�s�ig
			synchronized (holder) {
				while (holder.isEmpty())
					try {
						holder.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			}

			// A lista utols� elem�nek kicser�l�se az �j listaelemmel �s a lista
			// sorbarendez�se
			Comp comp = new Comp();
			lista.remove(9);
			lista.add(new Toplist(holder.remove(0), pontok));
			Collections.sort(lista, comp);

			// A toplista friss�t�se, �s kirajzol�sa az ablakra
			toplistaFrissites();
			top.removeAll();
			top.add(scrollPane);
			frame.repaint();
			// Ha az eredm�ny nincs bent a legjobb 10-be
		} else {
			JLabel nemnyert1 = new JLabel("A j�t�knak v�ge!");
			JLabel nemnyert2 = new JLabel("Sajnos nem ker�lt be az eredm�nyed a legjobb 10-be. Pr�b�lkozz �jra (F2).");
			nemnyert1.setForeground(Color.BLACK);
			nemnyert2.setForeground(Color.BLACK);
			top.removeAll();
			top.add(nemnyert1);
			top.add(nemnyert2);

			toplistaFrissites();
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();
		}
		fileHandler.fajlbairas(lista);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void toplistaFrissites() {
		// A t�bl�zat fejl�c�nek l�trehoz�sa
		Vector colnames = new Vector();
		colnames.add("N�v");
		colnames.add("Pont");

		// A t�bl�zat l�trehoz�sa egy ScrollPane-ben
		DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
		JTable tablazat = new JTable(tablazatmodell);
		scrollPane = new JScrollPane(tablazat);

		// A t�bl�zat felt�lt�se a lista elemeivel
		for (Toplist i : lista) {
			String[] row = { i.getnev(), i.getstrpont() };
			tablazatmodell.addRow(row);
		}

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
			pontKiIras.setText("Pontsz�m: " + pontok);
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
		jatekTer.repaint();
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
		} else
		if (mehetFel && keyCode == java.awt.event.KeyEvent.VK_UP) {
			xValt = 0;
			yValt = -EGYSEG;
			mehetLe = false;
			mehetJobbra = true;
			mehetBalra = true;
		} else
		if (mehetJobbra && keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
			xValt = +EGYSEG;
			yValt = 0;
			mehetBalra = false;
			mehetFel = true;
			mehetLe = true;
		}else 
		if (mehetLe && keyCode == java.awt.event.KeyEvent.VK_DOWN) {
			xValt = 0;
			yValt = +EGYSEG;
			mehetFel = false;
			mehetJobbra = true;
			mehetBalra = true;
		}else 
		if (keyCode == java.awt.event.KeyEvent.VK_F2) {
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
        return jatekTer;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setSebesseg(int sebesseg) {
        this.sebesseg = sebesseg;
    }
}
