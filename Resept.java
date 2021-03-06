

abstract class Resept {
	static protected int amountOfSelfCreated = 0;
	protected int id;
	protected Legemiddel legemiddel;
	protected Lege utskrivendeLege;
	protected int reit;
	protected Pasient pasient;
	protected double pris;

	String type = "";

	public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
		this.id = amountOfSelfCreated;
		amountOfSelfCreated++;

		this.legemiddel = legemiddel;
		this.utskrivendeLege = utskrivendeLege;
		this.pasient = pasient;
		this.reit = reit;
		
		this.pris  = this.legemiddel.hentPris();
		
		pasient.leggTilResept(this);

	}
	public int hentId() {
		return id;
	}
	public Legemiddel hentLegemiddel() {
		return legemiddel;
	}

	public Lege hentLege() {
		return utskrivendeLege;
	}

	public Pasient hentPasient() {
		return pasient;
	}
	public int hentReit() {
		return reit;
	}

	public boolean bruk() {
		if (reit > 0) {
			reit -= 1;
			return true;
		}
		return false;
	}

	abstract public String farge();
	abstract public double prisAaBetale();

	@Override
	public String toString() {
		String string = "";

		string += id + ": " + type + "\n"
				+ "  Legemiddel: " + legemiddel.hentNavn() + "\n"
				+ "  Utskrivende lege: " + utskrivendeLege.hentNavn() + "\n"
				+ "  Pasient: " + pasient.hentNavn() + "\n"
				+ "  Reit: " + Integer.toString(reit) + "\n";

		return string;
	}
}
