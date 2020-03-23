package oblig4;

class Pasient {
    private String navn;
    private String fnr;
    private int id;
    static private int teller = 0;
    private Stabel<Resept> resepter;

    public Pasient(String nv, String nr){
        navn = nv;
        fnr = nr;
        id = teller;
        teller++;

		resepter = new Stabel<Resept>();
    }

    public void leggTilResept(Resept r){
        resepter.leggPaa(r);
    }

    public Stabel<Resept> hentResepter(){
        return resepter;
    }

	public int hentId() {
		return id;
	}
	
	public String hentFnr() {
		return fnr;
	}

    public String hentNavn(){
		return navn;
    }

    public String toString(){
        return id + ": Pasient: " + navn + " (" + fnr + ")";
    }

}
