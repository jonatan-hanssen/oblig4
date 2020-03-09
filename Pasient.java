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

    public Stabel hentResepter(){
        return resepter;
    }
	
	public int hentID() {
		return id;
	}

    public String toString(){
        return "\nPasient: " + navn
            + "\nFoedselsnummer: " + fnr
            + "\nID: " + id
            + "\nAntall resepter: " + resepter.stoerrelse();
    }

}
