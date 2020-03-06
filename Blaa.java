class Blaa extends Resept {
	public Blaa(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		super(legemiddel, utskrivendeLege, pasientId, reit);
		legemiddel.settNyPris(legemiddel.hentPris()*0.25);
	}
	@Override
	public String farge() {
		return "blaa";
	}
	@Override
	public double prisAaBetale() {
		return legemiddel.hentPris();	
	}
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er en blaa resept\n\n"
				+ "Info om legemiddelet:\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + Integer.toString(pasientId)
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
}