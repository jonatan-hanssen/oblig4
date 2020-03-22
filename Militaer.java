class Militaer extends Hvit {
	static String type = "militaer";
	public Militaer(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
		pris = 0;
	}
}
