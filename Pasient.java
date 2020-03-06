class Pasient {
    private String navn;
    private String fnr;
    private int id;
    static private int teller;
    private Stabel<Resept> resepter;

    public Pasient(String nv, String nr){
        navn = nv;
        fnr = nr;
        id = teller;
        teller++;
    }

    public void leggTilResept(Resept r){
        resepter.leggPaa(r);
    }

    public Stabel hentResepter(){
        return resepter;
    }

    public String toString(){
        return "\nPasient: " + navn
            + "\nFødselsnummer: " + fnr
            + "\nID: " + id
            + "\nAntall resepter: " + resepter.stoerrelse();
    }

}
