import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

interface DatabaseInterface {
	// filer
	public void lesFraFil(String filnavn) throws FileNotFoundException;
	public void skrivTilFil(String filnavn) throws IOException;

	// opprette	
	public Resept lagResept(String legenavn, int legemiddelId, int pasientId, int reit, String type) throws TypeNotFoundException;
	public Lege lagLege(String navn, int kontrollid);
	public Pasient lagPasient(String navn, String fnr);
	public Legemiddel lagLegemiddel(String navn, double pris, double virkestoff, int styrke, String type) throws TypeNotFoundException;

	// vise
	public void printPasient();
	public void printResept();
	public void printLege();
	public void printLegemiddel();
	public void printAlt();

	// stats
	public void printAntallTypeResept(Class<?> cls);
	public void printTypeMisbruk(HashMap<String, Integer> liste);

	// data
	public SortertLenkeliste<Lege> hentLeger();
	public Lenkeliste<Legemiddel> hentLegemidler();
	public Lenkeliste<Resept> hentResepter();
	public Lenkeliste<Pasient> hentPasienter();

	// soeke
	public Lege finnLege(String legenavn);
	public Legemiddel finnLegemiddel(int id);
	public Pasient finnPasient(int id);
}