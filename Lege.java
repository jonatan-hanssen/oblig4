class Lege implements Comparable<Lege>{
	protected String navn; // i was here

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
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel, pasient.hentId());
		Hvit resept = new Hvit(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);

		return resept;
	}
	public Militaer skrivMilitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel, pasient.hentId());
		Militaer resept = new Militaer(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);

		return resept;
	}
	public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel, pasient.hentId());
		PResept resept = new PResept(legemiddel, this, pasient);
		utskrevedeResepter.leggTil(resept);

		return resept;
	}
	public Blaa skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws  UlovligUtskrift {
		if (legemiddel instanceof Narkotisk) throw new UlovligUtskrift(this, legemiddel, pasient.hentId());
		Blaa resept = new Blaa(legemiddel, this, pasient, reit);
		utskrevedeResepter.leggTil(resept);

		return resept;
	}

	@Override
	public String toString() {
		return "Lege: " + navn;
	}
}
