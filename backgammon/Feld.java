package spielElement;

import java.util.ArrayList;
import hilfKlasse.Farbe;

public class Feld {
	private int nummer;
	private ArrayList<Stein> steinList = new ArrayList<>();

	public Feld(int nummer) {
		this.nummer = nummer;
	}

	/**
	 * 
	 * Getter
	 */
	public int getNummer() {
		return nummer;
	}

	public ArrayList<Stein> getSteinList() {
		return steinList;
	}

	public void setSteinList(ArrayList<Stein> steinList) {
		this.steinList = steinList;
	}

	/**
	 * set letzte Stein bewegen(false)
	 * einen Stein hinzufügen
	 * @param stein
	 */
	public void steinHinzufuegen(Stein stein) {
		int anzahl = steineAnzahl();
		if (anzahl > 0)
		this.getSteinList().get(anzahl-1).setDarfBewegen(false);
		// für fertigBar Felder
		if(this.nummer ==24 || this.nummer ==25)
			stein.setDarfBewegen(false);
			this.steinList.add(stein);
	}

	/**
	 * mehrere Steine hinzufügen
	 * 
	 * @param SteineAnzahl
	 * @param steinFarbe
	 */
	public void steinHinzufuegen(int SteineAnzahl, Farbe steinFarbe) {
		for (int i = 0; i < SteineAnzahl; i++) {
			Stein stein = new Stein(steinFarbe);
			if(i == SteineAnzahl-1)
				stein.setDarfBewegen(true);
			else
				stein.setDarfBewegen(false);
			this.steinList.add(stein);
		}
	}

	/**
	 * einen Stein abziehen
	 * 
	 * @param stein
	 */
	public Stein einSteinAbziehen() {
		int anzahl = steineAnzahl();
		Stein stein = this.steinList.get(anzahl-1);
		Stein abziehendeStein = new Stein(stein.getFarbe());
		abziehendeStein.setDarfBewegen(true);
		this.steinList.remove(anzahl-1);
		if(anzahl>1)
		this.steinList.get(anzahl-2).setDarfBewegen(true);
		return abziehendeStein;
	}

	/**
	 * Felder leeren
	 */
	public void feldLeeren() {
		this.steinList.clear();
	}

	/**
	 * Ist das Feld leer?
	 * 
	 * @return
	 */
	public boolean istLeer() {
		if (this.steinList.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Hat das Feld nur einen Stein einer anderen Farbe?
	 * 
	 * @return
	 */
	public boolean hatEinSteinVonAndereFarbe(Farbe farbe) {
		int anzahl = steineAnzahl();
		Farbe andereFarbe = (farbe.equals(Farbe.SCHWARZ)) ? Farbe.WEISS : Farbe.SCHWARZ;
		if (anzahl == 1 && steinList.get(0).getFarbe().equals(andereFarbe))
			return true;
		else
			return false;
	}

	/**
	 * Hat das Feld Steine von einer Farbe?
	 * 
	 * @param farbe
	 * @return true wenn das Feld hat Steine, die haben Farben als die
	 *         Parameterfarbe
	 */
	public boolean hatSteineVonFarbe(Farbe farbe) {
		int anzahl = steineAnzahl();
		if (anzahl > 0 && steinList.get(0).getFarbe().equals(farbe))
			return true;
		else
			return false;
	}

	/**
	 * Hat das Feld mehr als einen Stein von der anderen Farbe?
	 * 
	 * @param farbe
	 * @return true wenn das Feld mindestens einen Stein der anderen Parameterfarbe hat 
	 */
	public boolean hatVielSteineVonAndereFarbe(Farbe farbe) {
		int anzahl = steineAnzahl();
		Farbe andereFarbe = (farbe.equals(Farbe.SCHWARZ)) ? Farbe.WEISS : Farbe.SCHWARZ;
		if (anzahl > 1 && steinList.get(0).getFarbe().equals(andereFarbe))
			return true;
		else
			return false;
	}

	/**
	 * Wie viele Steine sind in dem Feld?
	 * 
	 * @return
	 */
	public int steineAnzahl() {
		int anzahl = steinList.size();
		return anzahl;
	}

	@Override
	public String toString() {
		String farbe = "------";
		if (steinList.size() > 0)
			farbe = steinList.get(0).getFarbe().toString();
		return "nummer: " + nummer + " ---Stein Anzahl: " + steineAnzahl() + " ---Farbe: " + farbe;
	}
}
