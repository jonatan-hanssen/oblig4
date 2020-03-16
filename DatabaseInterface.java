import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

interface DatabaseInterface {
	public void lesFraFil(String filnavn) throws FileNotFoundException;
	public void skrivTilFil(String filnavn) throws IOException;
	public void printAlt();
	public void printAntallTypeResept(Class<?> cls);
	public void printTypeMisbruk(HashMap<String, Integer> liste);
	public void lagResept(String legenavn, int lmId, int pasientId, int reit, String type);
	public void lagLege(String navn, int kontrollid);
	public void lagPasient(String navn, String fnr);
	public void lagLegemiddel(String navn, double pris, double virkestoff, int styrke, String type);

	public void printPasient(int detaljLevel);
	public void printResept(int detaljLevel);
	public void printLege(int detaljLevel);
	public void printLegemiddel(int detaljLevel);

	public SortertLenkeliste<Lege> hentLeger();
	public Lenkeliste<Legemiddel> hentLegemidler();
	public Lenkeliste<Resept> hentResepter();
	public Lenkeliste<Pasient> hentPasienter();
}