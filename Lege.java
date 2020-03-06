class Lege {
	protected String navn;
	
	public Lege(String navn) {
		this.navn = navn;
	}
	
	public String hentNavn() {
		return navn;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		string += "Dette er en lege \n"
				+ "Navn: " + navn
				+ "\n";
		
		return string;
	}
}