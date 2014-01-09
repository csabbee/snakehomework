package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import toplist.TopListElement;

import comparator.Comp;

public class Frame extends JFrame {

    private static final int HEIGHT = 380;
    private static final int WIDTH = 506;

    private JPanel jatekTer;
    private JPanel pontSzam;
    private JPanel top;
    private JPanel[] keret = new JPanel[4];
    private JLabel pontKiIras;
    private JScrollPane scrollPane;
    
    public Frame(String string) {
        super(string);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        
        jatekTer = new JPanel();
        pontSzam = new JPanel();
        top = new JPanel();
        
        add(jatekTer, BorderLayout.CENTER);
        add(pontSzam, BorderLayout.SOUTH);
        keret[0] = new JPanel();
        keret[1] = new JPanel();
        keret[2] = new JPanel();
        keret[3] = new JPanel();
    }

    public void setupFrame(int palyaSzelesseg, int palyaMagassag, int egyseg) {
        jatekTer.setLayout(null);
        jatekTer.setBounds(0, 0, palyaSzelesseg, palyaMagassag);
        jatekTer.setBackground(Color.LIGHT_GRAY);
        pontSzam.setBounds(0, palyaMagassag, palyaSzelesseg, 30);
        pontSzam.setBackground(Color.GRAY);
        top.setBounds(0, 0, palyaSzelesseg, palyaMagassag);
        top.setBackground(Color.LIGHT_GRAY);
        
        
        keret[0].setBounds(0, 0, palyaSzelesseg, egyseg);
        keret[1].setBounds(0, 0, egyseg, palyaMagassag);
        keret[2].setBounds(0, palyaMagassag - egyseg, palyaSzelesseg, egyseg);
        keret[3].setBounds(palyaSzelesseg - egyseg, 0, egyseg, palyaMagassag);
        jatekTer.add(keret[0]);
        jatekTer.add(keret[1]);
        jatekTer.add(keret[2]);
        jatekTer.add(keret[3]);
        

        pontKiIras = new JLabel("Pontsz�m: " + 0);
        pontKiIras.setForeground(Color.BLACK);
        pontSzam.add(pontKiIras);
    }

    public void resetFrame(boolean gameOver, int pontok) {
        jatekTer.removeAll();
        
        if (gameOver == true) {
            remove(top);
        }
        jatekTer.add(keret[0]);
        jatekTer.add(keret[1]);
        jatekTer.add(keret[2]);
        jatekTer.add(keret[3]);

        add(jatekTer, BorderLayout.CENTER);
        repaint();
        setVisible(true);
        pontKiIras.setText("Pontsz�m: " + pontok);


    }

    public void addComponentToJatekTer(JButton jButton) {
        jatekTer.add(jButton);
    }

    public JPanel getJatekTer() {
        return jatekTer;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    void toplistaFrissites(Snake snake) {
    	// A t�bl�zat fejl�c�nek l�trehoz�sa
    	Vector colnames = new Vector();
    	colnames.add("N�v");
    	colnames.add("Pont");
    
    	// A t�bl�zat l�trehoz�sa egy ScrollPane-ben
    	DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
    	JTable tablazat = new JTable(tablazatmodell);
    	scrollPane = new JScrollPane(tablazat);
    
    	// A t�bl�zat felt�lt�se a lista elemeivel
    	for (TopListElement i : snake.getLista()) {
    		String[] row = { i.getnev(), i.getstrpont() };
    		tablazatmodell.addRow(row);
    	}
    
    }

    public void nyert(final ArrayList<String> holder, List<TopListElement> lista, int pontok) {
            JLabel nyert1 = new JLabel("A játéknak vége!");
            JLabel nyert2 = new JLabel("Gratulálok! Felkeréltél a toplistára. Kérlek add meg a neved (max 10 betű):");
            final JTextField newNev = new JTextField(10);
            
            top.removeAll();
            top.add(nyert1);
            top.add(nyert2);
            top.add(newNev);

            newNev.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized (holder) {
                        holder.add(newNev.getText());
                        holder.notify();
                    }
                    dispose();
                }
            });

            // A top panel hozz�ad�sa az ablakhoz, �s az ablak �jrarajzol�sa
            add(top, BorderLayout.CENTER);
            setVisible(true);
            repaint();

            // V�rakoz�s a sz�vegez� kit�lt�s�ig
            synchronized (holder) {
                while (holder.isEmpty())
                    try {
                        holder.wait();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
            }
            Comp comp = new Comp();
            lista.remove(9);
            lista.add(new TopListElement(holder.remove(0), pontok));
            Collections.sort(lista, comp);

            top.removeAll();
            top.add(scrollPane);
            repaint();
        }

    public void nemNyert() {
        JLabel nemnyert1 = new JLabel("A j�t�knak v�ge!");
        JLabel nemnyert2 = new JLabel("Sajnos nem ker�lt be az eredm�nyed a legjobb 10-be. Pr�b�lkozz �jra (F2).");
        nemnyert1.setForeground(Color.BLACK);
        nemnyert2.setForeground(Color.BLACK);
        top.removeAll();
        top.add(nemnyert1);
        top.add(nemnyert2);

        
        add(top, BorderLayout.CENTER);
        setVisible(true);
        repaint();
    }

    public void removeJatekTer() {
        remove(jatekTer);
    }

    public void setPont(int pontok) {
        pontKiIras.setText("Pontsz�m: " + pontok);
    }
}
