class Militaer extends Hvit {
	public Militaer(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		super(legemiddel, utskrivendeLege, pasientId, reit);
		legemiddel.settNyPris(0);
	}
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er en militaerresept\n\n"
				+ "Info om legemiddelet:\n\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + Integer.toString(pasientId)
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
	
}