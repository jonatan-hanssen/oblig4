class PResept extends Hvit {
	static String type = "p-resept";
	public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
		super(legemiddel, utskrivendeLege, pasient, 3);

		if (legemiddel.hentPris() <= 108) {
			pris = 0;
		}
		else {
			pris = legemiddel.hentPris() - 108;
		}
	}
}
