package comparator;

import java.util.Comparator;

import toplist.TopListElement;

public class Comp implements Comparator<TopListElement> {
	/*
	 * A Comparator bels� f�ggv�nye, melynek seg�ts�g�vel sorba rendezi a
	 * Toplist�t
	 */
	@Override
    public int compare(TopListElement pont1, TopListElement pont2) {
		int p1 = pont1.getPont();
		int p2 = pont2.getPont();

		if (p1 > p2) {
			return -1;
		} else if (p1 < p2) {
			return +1;
		} else {
			return 0;
		}
	}

}
