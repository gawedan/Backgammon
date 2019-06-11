package spielElement;

import hilfKlasse.Farbe;

public class SpielVerwalter {
	private Brett brett = new Brett();
	private Wuerfel wurfel1 = new Wuerfel();
	private Wuerfel wurfel2 = new Wuerfel();
	private Spieler spieler1 = new Spieler("Saad", Farbe.WEISS);
	private Spieler spieler2 = new Spieler("Khaled", Farbe.SCHWARZ);

	public SpielVerwalter() {
		brett.newspiel();
	}
	
	public SpielVerwalter(Brett brett) {
		setBrett(brett);
		this.brett.newspiel();
	}

	public Brett getBrett() {
		return brett;
	}

	// Getter und Setter
	public void setBrett(Brett brett) {
		this.brett = brett;
	}

	public Wuerfel getWurfel1() {
		return wurfel1;
	}

	public void setWurfel1(Wuerfel wurfel1) {
		this.wurfel1 = wurfel1;
	}

	public Wuerfel getWurfel2() {
		return wurfel2;
	}

	public void setWurfel2(Wuerfel wurfel2) {
		this.wurfel2 = wurfel2;
	}

	public Spieler getSpieler1() {
		return spieler1;
	}

	public void setSpieler1(Spieler spieler1) {
		this.spieler1 = spieler1;
	}

	public Spieler getSpieler2() {
		return spieler2;
	}

	public void setSpieler2(Spieler spieler2) {
		this.spieler2 = spieler2;
	}

	/**
	 * Darf ein Stein bewegt werden?
	 * 
	 * @param quelleFeldNummer
	 * @param positionFeldNummer
	 * @param wuerfelErgebnis
	 * @param spieler
	 * @return
	 */
	public boolean darfEinSteinBewegtWerden(int quelleFeldNummer, int positionFeldNummer, int wuerfelErgebnis,
			Spieler spieler) {
		if ((quelleFeldNummer <= 23 && quelleFeldNummer >= 0)
				&& (positionFeldNummer >= 0 && positionFeldNummer <= 23)) {
			if (darfEinSteinInnerhalbDesBrettsBewegtWerden(quelleFeldNummer, positionFeldNummer, wuerfelErgebnis,
					spieler))
				return true;
		}
		if ((quelleFeldNummer == 26 || quelleFeldNummer == 27)
				&& (positionFeldNummer >= 0 && positionFeldNummer <= 23)) {
			if (darfEinSteinVonDerBarBewegtWerden(positionFeldNummer, wuerfelErgebnis, spieler))
				return true;
		}
		if (positionFeldNummer == 24 || positionFeldNummer == 25) {
			if (darfEinSteinInsAusBewegtWerden(positionFeldNummer, quelleFeldNummer, wuerfelErgebnis, spieler))
				return true;
		}
		return false;
	}

	/**
	 * Darf ein Stein innerhalb des Bretts bewegt werden?
	 * 
	 * @param quelleFeldNummer
	 * @param positionFeldNummer
	 * @param wuerfelErgebnis
	 * @param spieler
	 * @return
	 */
	private boolean darfEinSteinInnerhalbDesBrettsBewegtWerden(int quelleFeldNummer, int positionFeldNummer,
			int wuerfelErgebnis, Spieler spieler) {
		Farbe steinFarbe = spieler.getSteinFarbe();
		int zielFeldNummer = zielFeldNummer(quelleFeldNummer, wuerfelErgebnis, steinFarbe);
		if (positionFeldNummer != zielFeldNummer)
			return false;
		boolean HatDasZielfeldMehrAlsEinenSteinDerAnderenFarbe = brett.getBrettFeldArray()[positionFeldNummer]
				.hatVielSteineVonAndereFarbe(steinFarbe);
		if (HatDasZielfeldMehrAlsEinenSteinDerAnderenFarbe)
			return false;
		if (spieler.istGeschlagen())
			return false;
		return true;
	}

	/**
	 * Darf ein Stein von der Bar bewegt werden?
	 * 
	 * @param quelleFeldNummer
	 * @param positionFeldNummer
	 * @param wuerfelErgebnis
	 * @param spieler
	 * @return
	 */
	private boolean darfEinSteinVonDerBarBewegtWerden(int positionFeldNummer, int wuerfelErgebnis, Spieler spieler) {
		Farbe steinFarbe = spieler.getSteinFarbe();
		int quelleFeldNummer = (steinFarbe.equals(Farbe.WEISS)) ? 26 : 27;
		int zielFeldNummer = zielFeldNummer(quelleFeldNummer, wuerfelErgebnis, steinFarbe);
		if (positionFeldNummer != zielFeldNummer)
			return false;
		boolean HatDasZielfeldMehrAlsEinenSteinDerAnderenFarbe = brett.getBrettFeldArray()[zielFeldNummer]
				.hatVielSteineVonAndereFarbe(steinFarbe);
		if (HatDasZielfeldMehrAlsEinenSteinDerAnderenFarbe)
			return false;
		return true;
	}

	/**
	 * Darf ein Stein ins Aus bewegt werden?
	 * 
	 * @param quelleFeldNummer
	 * @param positionFeldNummer
	 * @param wuerfelErgebniss
	 * @param spieler
	 * @return
	 */
	private boolean darfEinSteinInsAusBewegtWerden(int fertigBarNummer, int quelleFeldNummer, int wuerfelErgebniss,
			Spieler spieler) {
		// Bewegt der Spieler einen Stein von der Bar?
		if (quelleFeldNummer == 26 || quelleFeldNummer == 27)
			return false;
		// Bewegt der Spieler einen Stein in das falsche Aus?
		int spielerFertigBarNummer = (spieler.getSteinFarbe() == Farbe.WEISS) ? 24 : 25;
		if (spielerFertigBarNummer != fertigBarNummer)
			return false;
		// Anzahl der Steine in Home + Anzahl der Steine in Aus = 15?
		if (this.AnzahlSteineInHome(spieler) + brett.getBrettFeldArray()[spielerFertigBarNummer].steineAnzahl() != 15)
			return false;
		// Der Stein darf bewegt werden falls WuerfelErgebnis ist gleich
		// QuelleFeldNummer
		if (istWuerfelErgebnisGleichQuelleFeldNummer(quelleFeldNummer, wuerfelErgebniss, spieler))
			return true;
		// Es dürfen keine Steine VorQuelleFeld stehen
		if (gibtEsSteineVorQuelleFeld(quelleFeldNummer, spieler))
			return false;
		return true;
	}

	/**
	 * Wird ein Stein regelgerecht bewegt?
	 * 
	 * @param quelleFeldNummer
	 * @param positionFeldNummer
	 * @param wuerfelErgebniss
	 * @param spieler
	 * @return
	 */
	public void unterSpielregelnBewegt(int quelleFeldNummer, int positionFeldNummer, int wuerfelErgebniss,
			Spieler spieler) {
		// Innerhalb des Bretts
		if ((quelleFeldNummer >= 0 && quelleFeldNummer <= 23)
				&& (positionFeldNummer >= 0 && positionFeldNummer <= 23)) {
			// Ist ein Stein des Gegenspielers schlagbar?
			if (brett.getBrettFeldArray()[positionFeldNummer].hatEinSteinVonAndereFarbe(spieler.getSteinFarbe()))
				andereSpielerSchlagen(positionFeldNummer);
			einenSteinInnerhalbDesBrettsBewegen(quelleFeldNummer, positionFeldNummer);
		}
		// Einen Stein aus der Bar bewegen
		if ((quelleFeldNummer == 26 || quelleFeldNummer == 27)
				&& (positionFeldNummer >= 0 && positionFeldNummer <= 23)) {
			if (brett.getBrettFeldArray()[positionFeldNummer].hatEinSteinVonAndereFarbe(spieler.getSteinFarbe()))
				andereSpielerSchlagen(positionFeldNummer);

			einenSteinAusDerBarBewegen(positionFeldNummer, spieler);
		}
		// Einen Stein ins Aus bewegen
		if (positionFeldNummer == 24 || positionFeldNummer == 25) {
			einenSteinInDasAusBewegen(quelleFeldNummer, spieler);
		}
	}

	/**
	 * Einen Stein innerhalb des Bretts bewegen
	 * 
	 * @param quelleFeldNummer
	 * @param wuerfelErgebniss
	 * @param steinFarbe
	 */

	private void einenSteinInnerhalbDesBrettsBewegen(int quelleFeldNummer, int zielFeldNummer) {
		brett.steinVonEinemFeldzumAnderenBewegen(quelleFeldNummer, zielFeldNummer);
	}

	/**
	 * Einen Stein aus der Bar in ein Feld bewegen Methode prüft, ob ein Stein
	 * des Gegenspielers geschlagen werden kann (oder nicht) und schlägt ihn
	 * 
	 * @param quelleFeldNummer
	 * @param wuerfelErgebniss
	 * @param steinFarbe
	 */

	private void einenSteinAusDerBarBewegen(int zielFeldNummer, Spieler spieler) {
		int spielerSchlagenBarNummer = (spieler.getSteinFarbe() == Farbe.WEISS) ? 26 : 27;
		Stein stein = brett.getBrettFeldArray()[spielerSchlagenBarNummer].einSteinAbziehen();
		if (brett.getBrettFeldArray()[spielerSchlagenBarNummer].steineAnzahl() == 0)
			spieler.setGeschlagen(false);
		brett.getBrettFeldArray()[zielFeldNummer].steinHinzufuegen(stein);
	}

	/**
	 * Einen Stein in das Aus bewegen 
	 * 
	 * @param quelleFeldNummer
	 * @param spieler
	 */

	private void einenSteinInDasAusBewegen(int quelleFeldNummer, Spieler spieler) {
		Stein stein = brett.getBrettFeldArray()[quelleFeldNummer].einSteinAbziehen();
		int spielerFertigBarNummer = (spieler.getSteinFarbe() == Farbe.WEISS) ? 24 : 25;
		brett.getBrettFeldArray()[spielerFertigBarNummer].steinHinzufuegen(stein);
	}

	/**
	 * Einen Stein des Gegenspielers schlagen und zur Bar bewegen 
	 * 
	 * @param quelleFeldNummer
	 */
	private void andereSpielerSchlagen(int quelleFeldNummer) {
		Stein stein = brett.getBrettFeldArray()[quelleFeldNummer].einSteinAbziehen();
		if ((stein.getFarbe()).equals(Farbe.WEISS)) {
			brett.getBrettFeldArray()[26].steinHinzufuegen(stein);
			spieler1.setGeschlagen(true);
		} else {
			brett.getBrettFeldArray()[27].steinHinzufuegen(stein);
			spieler2.setGeschlagen(true);
		}
	}

	/**
	 * Anzahl Steine In Home
	 * 
	 * @param spieler
	 * @return steinAnzahl
	 */
	private int AnzahlSteineInHome(Spieler spieler) {
		int steinAnzahl = 0;
		int start = 18;
		int ende = 23;
		Farbe farbe = Farbe.WEISS;
		if (spieler.getSteinFarbe().equals(Farbe.SCHWARZ)) {
			start = 0;
			ende = 5;
			farbe = Farbe.SCHWARZ;
		}
		for (int i = start; i <= ende; i++) {
			Feld feld = brett.getBrettFeldArray()[i];
			if (feld.hatSteineVonFarbe(farbe))
				steinAnzahl += feld.steineAnzahl();
		}
		return steinAnzahl;
	}

	/**
	 * Gibt es Steine in VorQuelleFeld?
	 * 
	 * @param quelleFeldNummer
	 * @param spieler
	 * @return
	 */
	private boolean gibtEsSteineVorQuelleFeld(int quelleFeldNummer, Spieler spieler) {
		Farbe steinFarbe = spieler.getSteinFarbe();
		int schritt = (steinFarbe.equals(Farbe.SCHWARZ)) ? 5 - quelleFeldNummer : 22 - quelleFeldNummer;
		int anfangFeldNummer = (steinFarbe.equals(Farbe.SCHWARZ)) ? quelleFeldNummer + 1 : 18;
		int steinAnzahl = 0;
		for (int i = 0; i < schritt; i++) {
			Feld feld = brett.getBrettFeldArray()[anfangFeldNummer + i];
			if (feld.hatSteineVonFarbe(steinFarbe))
				steinAnzahl += feld.steineAnzahl();
		}
		if (steinAnzahl != 0)
			return true;
		return false;
	}

	/**
	 * zielFeldNummer finden
	 * 
	 * @param quelleFeldNummer
	 * @param wuerfelErgebniss
	 * @param steinFarbe
	 * @return
	 */
	private int zielFeldNummer(int quelleFeldNummer, int wuerfelErgebniss, Farbe steinFarbe) {
		int zielFeldNummer;
		if (steinFarbe.equals(Farbe.WEISS))
			zielFeldNummer = quelleFeldNummer + wuerfelErgebniss;
		else
			zielFeldNummer = quelleFeldNummer - wuerfelErgebniss;
		return zielFeldNummer;
	}

	/**
	 * Ist WuerfelErgebnis gleich QuelleFeldNummer?
	 * 
	 * @param quelleFeldNummer
	 * @param wuerfelErgebnis
	 * @param spieler
	 * @return
	 */
	private boolean istWuerfelErgebnisGleichQuelleFeldNummer(int quelleFeldNummer, int wuerfelErgebnis,
			Spieler spieler) {
		boolean farbeSchwarz = spieler.getSteinFarbe().equals(Farbe.SCHWARZ);
		if (farbeSchwarz && (quelleFeldNummer - wuerfelErgebnis == -1))
			return true;
		if (!farbeSchwarz && (quelleFeldNummer + wuerfelErgebnis == 24))
			return true;
		return false;
	}

	public void newspiel() {
		brett.newspiel();
	}

	/**
	 * Spieler hat gewonnen.
	 * 
	 * @return
	 */
	public boolean istGewonnen(Spieler spieler) {
		int spielerFertigBarNummer = (spieler.getSteinFarbe() == Farbe.WEISS) ? 24 : 25;
		return this.brett.getBrettFeldArray()[spielerFertigBarNummer].steineAnzahl() == 15;
	}

	@Override
	public String toString() {
		StringBuilder verwaltung = new StringBuilder();
		verwaltung.append(brett.toString());
		verwaltung.append("---------------------");
		verwaltung.append("\n" + spieler1.toString());
		verwaltung.append("\n" + spieler2.toString());
		return verwaltung.toString();
	}
}
