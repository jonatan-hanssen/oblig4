class Hvit extends Resept {
	public Hvit(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		super(legemiddel, utskrivendeLege, pasientId, reit);
	}
	@Override
	public String farge() {
		return "hvit";
	}
	@Override
	public double prisAaBetale() {
		return legemiddel.hentPris();
		
	}
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er en hvit resept\n\n"
				+ "Info om legemiddelet:\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + Integer.toString(pasientId)
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
}