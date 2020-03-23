

class Hvit extends Resept {
	public Hvit(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
		type = "hvit";
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
