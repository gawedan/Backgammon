package spielElement;

import hilfKlasse.Farbe;

public class Spieler {
	private String name;
	private Farbe steinFarbe;
	private boolean Geschlagen = false;


	public Spieler(String name, Farbe steinFarbe) {
		this.setName(name);
		this.setSteinFarbe(steinFarbe);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!name.equals(null) && !name.equals(" "))
			this.name = name;
	}

	public Farbe getSteinFarbe() {
		return steinFarbe;
	}

	public void setSteinFarbe(Farbe steinFarbe) {
		this.steinFarbe = steinFarbe;
	}

	public boolean istGeschlagen() {
		return Geschlagen;
	}

	public void setGeschlagen(boolean geschlagen) {
		Geschlagen = geschlagen;
	}
	
	@Override
	public String toString() {
		StringBuilder spieler = new StringBuilder();
		spieler.append("Name: " + name + "\n" + "SteinFarbe: " + steinFarbe + "\n");
		spieler.append("Geschlagen: " + Geschlagen + "\n");
		spieler.append("------------------------------------");
		return spieler.toString();
	}
}
