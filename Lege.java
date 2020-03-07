class Lege implements Comparable<Lege>{
	protected String navn;
	
	protected Lenkeliste<Resept> utskrevedeResepter;
	
	public Lege(String navn) {
		this.navn = navn;
	}
	
	@Override
	public int compareTo(Lege lege) {
		return navn.compareTo(lege.navn);
	}
	
	public String hentNavn() {
		return navn;
	}
	
	public Lenkeliste<Resept> hentReseptListe() {
		return utskrevedeResepter;
	}
	
	public Hvit skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw UlovligUtskrift(this, legemiddel);
		Hvit resept = new Hvit(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	public MilitaerResept skrivMilitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw UlovligUtskrift(this, legemiddel);
		MilitaerResept resept = new MilitaerResept(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw UlovligUtskrift(this, legemiddel);
		PResept resept = new PResept(legemiddel, this, pasient);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	public Blaa skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw UlovligUtskrift(this, legemiddel);
		Blaa resept = new Blaa(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
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