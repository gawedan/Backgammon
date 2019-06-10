package spielElement;

public class Wuerfel {
	private int ergebnis = 1;

	public int getErgebnis() {
		return ergebnis;
	}

	public void WuerfelWerfen() {
		this.ergebnis = (int) ((Math.random() * 6) + 1);
	}
}
