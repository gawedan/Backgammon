package spielElement;

import hilfKlasse.Farbe;

public class Stein {
	private Farbe farbe;

	private boolean darfBewegen;
	public Stein(Farbe farbe) {
		this.farbe = farbe;
	}

	public boolean isDarfBewegen() {
		return darfBewegen;
	}

	public void setDarfBewegen(boolean darfBewegen) {
		this.darfBewegen = darfBewegen;
	}

	public Farbe getFarbe() {
		return farbe;
	}

	@Override
	public String toString() {
		return farbe.toString() + "Darf bewegen?" + darfBewegen;
	}
}
