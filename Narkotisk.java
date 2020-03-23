package oblig4;

class Narkotisk extends Legemiddel {
	private int narkotiskStyrke;
	
	public Narkotisk(String navn, double pris, double virkestoff, int narkotiskStyrke) {
		super(navn, pris, virkestoff);
		this.narkotiskStyrke = narkotiskStyrke;
	}
	public int hentNarkotiskStyrke() {
		return narkotiskStyrke;
	}
	
}