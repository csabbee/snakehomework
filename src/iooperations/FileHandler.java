package iooperations;

import game.Snake;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import toplist.Toplist;

public class FileHandler {

    @SuppressWarnings("unchecked")
    public
    void fajlmegnyitas(Snake snake) {
    	// A f�jl megnyit�sa
    	try {
    		InputStream file = new FileInputStream("toplista.ser");
    		InputStream buffer = new BufferedInputStream(file);
    		ObjectInput in;
    		in = new ObjectInputStream(buffer);
    
    		// A f�jl tartalm�nak bem�sol�sa a lista ArrayListbe
    		snake.lista = (ArrayList<Toplist>) in.readObject();
    
    		// A f�jl bez�r�sa
    		in.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void fajlbairas(Snake snake) {
    	// A f�jl megnyit�sa
    	try {
    		OutputStream file = new FileOutputStream("toplista.ser");
    
    		OutputStream buffer = new BufferedOutputStream(file);
    		ObjectOutput out;
    		out = new ObjectOutputStream(buffer);
    
    		// A lista ArrayList f�jlba �r�sa
    		out.writeObject(snake.lista);
    
    		// A f�jl bez�r�sa
    		out.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
