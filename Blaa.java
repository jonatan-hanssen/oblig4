package oblig4;

class Blaa extends Resept {
	public Blaa(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
		pris = legemiddel.hentPris()*0.25;
		type = "blaa";
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
