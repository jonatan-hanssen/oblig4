class Narkotisk extends Legemiddel {
	private int narkotiskStyrke;
	
	public Narkotisk(String navn, double pris, double virkestoff, int narkotiskStyrke) {
		super(navn, pris, virkestoff);
		this.narkotiskStyrke = narkotiskStyrke;
	}
	public int hentNarkotiskStyrke() {
		return narkotiskStyrke;
	}
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er et narkotisk legemiddel \n"
				+ "Navn: " + navn
				+ "\nPris: " + Double.toString(pris)
				+ "\nVirkestoff: " + Double.toString(virkestoff)
				+ "\nNarkotisk styrke: " + Integer.toString(narkotiskStyrke)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
	
}