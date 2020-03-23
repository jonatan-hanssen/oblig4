

class PResept extends Hvit {
	public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
		super(legemiddel, utskrivendeLege, pasient, 3);
		this.type = "p-resept";
		if (legemiddel.hentPris() <= 108) {
			pris = 0;
		}
		else {
			pris = legemiddel.hentPris() - 108;
		}
	}
}
