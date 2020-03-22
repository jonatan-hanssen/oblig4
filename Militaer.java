class Militaer extends Hvit {
	public Militaer(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		super(legemiddel, utskrivendeLege, pasient, reit);
		pris = 0;
		this.type = "militaer";
	}
}
