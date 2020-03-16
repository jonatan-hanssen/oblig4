class Vanedannende extends Legemiddel {
	private int vanedannendeStyrke;
	
	public Vanedannende(String navn, double pris, double virkestoff, int vanedannendeStyrke) {
		//bruker legemiddel sin konstruktoer
		super(navn, pris, virkestoff);
		this.vanedannendeStyrke = vanedannendeStyrke;
	}
	public int hentVanedannendeStyrke() {
		return vanedannendeStyrke;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er et vanedannende legemiddel \n"
				+ "Navn: " + navn
				+ "\nPris: " + Double.toString(pris)
				+ "\nVirkestoff: " + Double.toString(virkestoff)
				+ "\nVanedannende styrke: " + Integer.toString(vanedannendeStyrke)
				+ "\nID: " + Integer.toString(id)
				+ "\n";
		
		return string;
	}
}