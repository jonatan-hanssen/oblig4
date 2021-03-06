

public abstract class Legemiddel {
	static protected int amountOfSelfCreated = 0;
	protected int id;
	protected String navn;
	protected double pris, virkestoff;
	
	
	public Legemiddel(String navn, double pris, double virkestoff) {
		//denne vil oeke hver gang vi lager et nytt objekt
		id = amountOfSelfCreated;
		amountOfSelfCreated++;
		
		this.navn = navn;
		this.pris = pris;
		this.virkestoff = virkestoff;
		
	}
	public int hentId() {
		return id;
	}
	public String hentNavn() {
		return navn;
	}
	
	public double hentPris() {
		return pris;
	}
	
	public double hentVirkestoff() {
		return virkestoff;
	}
	
	public void settNyPris(double nyPris) {
		pris = nyPris;
	}
	@Override
	public String toString() {
		return id + ": " + navn + " (pris: " + String.format("%.2f kr",pris) + ", virkestoff: " 
				+ String.format("%.3f mg", virkestoff) + ")";
	}
}