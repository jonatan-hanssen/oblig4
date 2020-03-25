

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
		return id + ": " + navn + " (pris: " + String.format("%.2f kr",pris) + ", virkestoff: " 
				+ String.format("%.3f mg", virkestoff) + ", narkotisk styrke: " + narkotiskStyrke + ")";
	}
}