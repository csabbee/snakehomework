package iooperations;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
import java.util.List;

import toplist.TopListElement;

public class FileHandler {

    @SuppressWarnings("unchecked")
    public void fajlmegnyitas(List<TopListElement> list) {
        File file = new File("toplista.ser");
        if(!file.exists()){
            List<TopListElement> lista = new ArrayList<>(); 
            for(int i=0; i<10; i++){
                lista.add(new TopListElement("",0));
            }
            fajlbairas(lista);
        }
    	try {
    		InputStream is = new FileInputStream("toplista.ser");
    		InputStream buffer = new BufferedInputStream(is);
    		ObjectInput in = new ObjectInputStream(buffer);
    		
    		List<TopListElement> fromFile = (List<TopListElement>) in.readObject();
    		for (TopListElement toplist : fromFile) {
                list.add(toplist);
            }
    		in.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void fajlbairas(List<TopListElement> lista) {
    	try {
    		OutputStream os = new FileOutputStream("toplista.ser");
    
    		OutputStream buffer = new BufferedOutputStream(os);
    		ObjectOutput out = new ObjectOutputStream(buffer);

    		out.writeObject(lista);
    
    		out.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
