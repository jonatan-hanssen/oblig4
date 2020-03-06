abstract class Resept {
	static protected int amountOfSelfCreated = 0;
	protected int id;
	protected Legemiddel legemiddel;
	protected Lege utskrivendeLege;
	protected int pasientId, reit;
	
	
	public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
		this.id = amountOfSelfCreated;
		amountOfSelfCreated++;
		
		this.legemiddel = legemiddel;
		this.utskrivendeLege = utskrivendeLege;
		this.pasientId = pasientId;
		this.reit = reit;
		
		
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
	
	public int hentPasientId() {
		return pasientId;
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
		
		string += "Dette er en resept\n\n"
				+ "Info om legemiddelet:\n" + legemiddel
				+ "\n\nInfo om legen: \n" + utskrivendeLege
				+ "\n\nPasient-ID: " + Integer.toString(pasientId)
				+ "\nReit: " + Integer.toString(reit)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
}