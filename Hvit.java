class Hvit extends Resept {
	public Hvit(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
	}
	@Override
	public String farge() {
		return "hvit";
	}
	@Override
	public double prisAaBetale() {
		return pris;

	}
	@Override
	public String toString() {
		String string = "";

		string += "Dette er en hvit resept\n\n"
				+ "Info om legemiddelet:\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + pasient.toString()
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";

		return string;
	}
}
