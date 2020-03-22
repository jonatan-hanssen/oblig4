class Blaa extends Resept {
	static String type = "blaa";
	public Blaa(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
		pris = legemiddel.hentPris()*0.25;
	}
	@Override
	public String farge() {
		return "blaa";
	}
	@Override
	public double prisAaBetale() {
		return pris;
	}
}
