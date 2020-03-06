class Pasient {
    private String navn;
    private String foedselsnummer;
    private int id;
    static private int teller;
    private Stabel<Resept> resepter;

    public Pasient(String nv, String nr){
        navn = nv;
        foedselsnummer = nr;
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
            + "\nFødselsnummer: " + foedselsnummer
            + "\nID: " + id
            + "\nAntall resepter: " + resepter.stoerrelse();
    }

}
