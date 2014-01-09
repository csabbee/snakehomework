package toplist;
import java.io.Serializable;

public class TopListElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nev;
	private int pont;

	/*
	 * A konstruktor l�trehoz egy toplista bejegyz�st a param�terk�nt megadott
	 * n�vvel �s pontsz�mmal
	 */
	public TopListElement(String nev, int pont) {
		this.pont = pont;
		this.nev = nev;
	}

	/*
	 * Visszat�r az adott bejegyz�s nev�vel
	 */
	public String getnev() {
		return nev;
	}

	/*
	 * Visszat�r az adott bejegyz�s pontsz�m�val (int)
	 */
	public int getPont() {
		return pont;
	}

	/*
	 * Visszat�r az adott bejegyz�s pontsz�m�val (String)
	 */
	public String getstrpont() {
		return Integer.toString(pont);
	}
}