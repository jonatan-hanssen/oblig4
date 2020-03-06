class Spesialist extends Lege implements Godkjenningsfritak {
	private int kontrollID;
	
	public Spesialist(String navn, int kontrollID) {
		super(navn);
		this.kontrollID = kontrollID;
	}
	
	
	public int hentKontrollID() {
		return kontrollID;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er en spesialist \n"
				+ "Navn: " + navn
				+ "\nKontroll-ID: " + Integer.toString(kontrollID)
				+ "\n";
		
		return string;
	}
	
}