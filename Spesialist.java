

class Spesialist extends Lege implements Godkjenningsfritak {
	private int kontrollID;
	
	public Spesialist(String navn, int kontrollID) {
		super(navn);
		//this.utskrevedeResepter = new Lenkeliste<Resept>();
		this.kontrollID = kontrollID;
	}
	
	
	public int hentKontrollID() {
		return kontrollID;
	}
	
	@Override
	public Hvit skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) {
		Hvit resept = new Hvit(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	@Override
	public Militaer skrivMilitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) {
		Militaer resept = new Militaer(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	@Override
	public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) {
		PResept resept = new PResept(legemiddel, this, pasient);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	@Override
	public Blaa skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) {
		Blaa resept = new Blaa(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);
		
		return resept;
	}
	
	@Override
	public String toString() {
		return navn + " (Kontroll-ID: " + kontrollID + ")";
	}
	
}