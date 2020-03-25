

class Vanedannende extends Legemiddel {
	private int vanedannendeStyrke;
	public Vanedannende(String navn, double pris, double virkestoff, int vanedannendeStyrke) {
		//bruker legemiddel sin konstruktoer
		super(navn, pris, virkestoff);
		this.vanedannendeStyrke = vanedannendeStyrke;
	}
	public int hentVanedannendeStyrke() {
		return vanedannendeStyrke;
	}
	@Override
	public String toString() {
		return id + ": " + navn + " (pris: " + String.format("%.2f kr",pris) + ", virkestoff: " 
				+ String.format("%.3f mg", virkestoff) + ", vanedannende styrke: " + vanedannendeStyrke + ")";
	}
}