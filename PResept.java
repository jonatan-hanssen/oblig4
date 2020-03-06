class PResept extends Hvit{
	public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
		super(legemiddel, utskrivendeLege, pasient, 3);

		if (legemiddel.hentPris() <= 108) {
			legemiddel.settNyPris(0);
		}
		else {
			legemiddel.settNyPris(legemiddel.hentPris() - 108);
		}
	}
	@Override
	public String toString() {
		String string = "";

		string += "Dette er en P-resept\n\n"
				+ "Info om legemiddelet:\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + pasient.toString()
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";

		return string;
	}
}
