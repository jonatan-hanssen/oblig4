class Hvit extends Resept {
	static String type = "hvit";
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
}
