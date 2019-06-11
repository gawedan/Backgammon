package spielElement;

import hilfKlasse.Farbe;

public class Brett {

	private Feld[] brettFeldArray = new Feld[28];

	/**
	 * Konstruktor
	 */
	public Brett() {
		this.newspiel();
	}

	/**
	 * Getter und Setter
	 * 
	 */
	public Feld[] getBrettFeldArray() {
		return brettFeldArray;
	}

	/**
	 * Start neues Spiel
	 */
	public void newspiel() {
		this.felderLeeren();
		this.neuSetzen();
	}

	/**
	 * Löschen aller Steine aus dem Feld ( brettFeldArray, schlagenBar,FertigBar)
	 */
	private void felderLeeren() {
		for (int i = 0; i < brettFeldArray.length; i++) {
			if (brettFeldArray[i] != null) {
				brettFeldArray[i].feldLeeren();
			}
		}
	}

	private void neuSetzen() {
		int[] positionNummer = { 0, 11, 16, 18 };
		int[] SteineAnzahl = { 2, 5, 3, 5 };
		for (int i = 0; i < positionNummer.length; i++) {
			steineInEinFeldHinzufuegen(positionNummer[i], SteineAnzahl[i], Farbe.WEISS);
		}
		positionNummer = new int[] { 5, 7, 12, 23 };
		SteineAnzahl = new int[] { 5, 3, 5, 2 };
		for (int i = 0; i < positionNummer.length; i++) {
			steineInEinFeldHinzufuegen(positionNummer[i], SteineAnzahl[i], Farbe.SCHWARZ);
		}
		// Leeren Felder nummerierten
		int[] anderePositionNummer = { 1, 2, 3, 4, 6, 8, 9, 10, 13, 14, 15, 17, 19, 20, 21, 22,24,25,26,27 };
		for (int i = 0; i < anderePositionNummer.length; i++) {
			Feld brettFeld = new Feld(anderePositionNummer[i]);
			brettFeldArray[anderePositionNummer[i]] = brettFeld;
		}
	}

	/**
	 * Füge Steine in ein Feld hinzu. 
	 * 
	 * @param feldPosition
	 * @param steineAnzahl
	 * @param steinFarbe
	 */
	private void steineInEinFeldHinzufuegen(int feldPosition, int steineAnzahl, Farbe steinFarbe) {
		brettFeldArray[feldPosition] = new Feld(feldPosition);
		for (int i = 0; i < steineAnzahl; i++) {
			Stein stein = new Stein(steinFarbe);
			//set Darf Bewegen für letzte Stein
			if (i < steineAnzahl - 1)
				stein.setDarfBewegen(false);
			else
				stein.setDarfBewegen(true);
				brettFeldArray[feldPosition].steinHinzufuegen(stein);
		}
	}

	public void steinVonEinemFeldzumAnderenBewegen(int quelleFeldPosition, int ZielFeldPosition) {
		Stein stein = brettFeldArray[quelleFeldPosition].einSteinAbziehen();
		brettFeldArray[ZielFeldPosition].steinHinzufuegen(stein);
	}

	@Override
	public String toString() {
		StringBuilder info = new StringBuilder();
		for (int i = 0; i < 28; i++) {
			if(i==27)
			info.append("\n spieler schwartz schlagbar \n");

			if(i==26)
			info.append("\n spieler weis schlagbar \n");

			if(i==25)
			info.append("\n spieler schwartz firtig \n");

			if(i==24)
			info.append("\n spieler weis firtig \n");
			info.append(brettFeldArray[i].toString()+" : ");
			for (int j = 0; j < brettFeldArray[i].steineAnzahl(); j++) {
				info.append(brettFeldArray[i].getSteinList().get(j).isDarfBewegen()+" : ");		
			}
			info.append("\n");
		}
		
		return info.toString();
	}
}
