import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;


class Database {
	public Lenkeliste<Pasient> pasientListe = new Lenkeliste<Pasient>();//nr 0
	public Lenkeliste<Legemiddel> legemiddelListe = new Lenkeliste<Legemiddel>();//nr 1
	public SortertLenkeliste<Lege> legeListe = new SortertLenkeliste<Lege>();//nr 2
	public Lenkeliste<Resept> reseptListe = new Lenkeliste<Resept>();//nr 3

	public Database() {}

	public void lesFraFil(String filnavn) throws FileNotFoundException {
		File fil = new File(filnavn);
		Scanner scanner = new Scanner(fil);

		//denne indeksen holder styr paa hvilke objekter og hvilken liste vi skal jobbe med
		//den oppdateres ved hver # fordi vi da gaar til en ny del av tekstfila
		int listeIndeks = 0;
		//scannerpos brukes til aa gi bruker informasjon om hvilken linje som er feil.
		int scannerPos = 0;
		String naavaerendeLinje;
		//vi hopper over den foerste linja fordi det er en hashtag. vi oeker ogsaa scannerpos.
		if (scanner.hasNextLine()) {
			scanner.nextLine(); scannerPos++;
		}
		while (scanner.hasNextLine()) {
			naavaerendeLinje = scanner.nextLine(); scannerPos++;

			//dersom vi kommer til en # vet vi at vi maa hoppe over linjen
			//og at vi maa bytte til neste liste
			if (naavaerendeLinje.contains("#")) {
				naavaerendeLinje = scanner.nextLine(); scannerPos++;
				listeIndeks++;
			}
			leggTilIListe(naavaerendeLinje, listeIndeks, scannerPos);
		}
		System.out.println("***   Naa er databasen ferdig konstruert   ***\n");
		System.out.println("	Antall pasienter: " + pasientListe.stoerrelse());
		System.out.println("	Antall leger: " + legeListe.stoerrelse());
		System.out.println("	Antall legemidler: " + legemiddelListe.stoerrelse());
		System.out.println("	Antall resepter: " + reseptListe.stoerrelse());
		System.out.println("	Linjer gaatt gjennom: " + scannerPos);
	}
	private void leggTilIListe(String linje, int nummerPaaListe, int linjePos) throws UgyldigListeIndeks, ArrayIndexOutOfBoundsException, NumberFormatException {
		String[] linjeArray = linje
			.trim() //fjerner whitespace
			.replaceAll(",$","") //fjerner komma uten noen string
			.split(","); //gjoer til array
		//vi bruker denne til aa returnere riktig info til bruker om en feil skjer
		String objektType;
			switch (nummerPaaListe) {
				case 0: objektType = "pasient"; break;
				case 1: objektType = "legemiddel"; break;	
				case 2: objektType = "lege"; break;
				case 3: objektType = "resept"; break;
				default: objektType = "";
			}
		try {
			switch (nummerPaaListe) {
			
				case 0:
				//Pasient = (navn, fnr)
					pasientListe.leggTil(new Pasient(linjeArray[0],linjeArray[1]));
				break;
				case 1:
				
				//Legemiddel = (navn, type, pris, virkestoff [,styrke])
					switch (linjeArray[1]) {
						case "vanlig":
							legemiddelListe.leggTil(new Vanlig(linjeArray[0], Float.valueOf(linjeArray[2]), Float.valueOf(linjeArray[3])));
							break;
						case "narkotisk":
							legemiddelListe.leggTil(new Narkotisk(linjeArray[0], Float.valueOf(linjeArray[2]), Float.valueOf(linjeArray[3]),Integer.parseInt(linjeArray[4])));
							break;
						case "vanedannende":
							legemiddelListe.leggTil(new Vanedannende(linjeArray[0], Float.valueOf(linjeArray[2]), Float.valueOf(linjeArray[3]),Integer.parseInt(linjeArray[4])));
							break;
						default:
							/*ser at det er mange feil med navn som er kombinasjoner av flere legemidler 
							* separert med komma. Jeg velger aa ikke haandtere disse ettersom test-filen
							* spesifiserer at hver parameter skal vaere separert med komma, som gjoer at 
							* disse filene er feil oppgitt.
							*/
							System.out.println("FEIL TYPE\nLinje " + linjePos + ":" + linjeArray[1] + " er ikke et type legemiddel.\n");
					}
				break;
				case 2:
				//Lege = (navn,kontrollid)
					if (Integer.parseInt(linjeArray[1]) == 0) {
						legeListe.leggTil(new Lege(linjeArray[0]));
					}
					else {
						legeListe.leggTil(new Spesialist(linjeArray[0], Integer.parseInt(linjeArray[1])));
					}
				break;
				case 3:
				//Resept (legemiddelNummer, legeNavn, pasientID, type, [reit])
					//finner legemiddel
					Legemiddel legemiddel = legemiddelListe.hent(Integer.parseInt(linjeArray[0]));

					//finner lege
					Lege lege = null;
					for (Lege muligLege : legeListe) {
						if (muligLege.navn.equals(linjeArray[1]))
						lege = muligLege;
					}
					if (lege == null) {
						System.out.println("FEIL VED NAVN\nLinje " + linjePos + ": Ingen lege ved navn " + linjeArray[1] + "\n");
					}
					
					//finner pasient
					Pasient pasient = pasientListe.hent(Integer.parseInt(linjeArray[2]));

					String type = linjeArray[3];
					switch (type) {
						case "blaa":
							reseptListe.leggTil(new Blaa(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4])));
							break;
						case "hvit":
							reseptListe.leggTil(new Hvit(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4])));
							break;
						case "militaer":
							reseptListe.leggTil(new Militaer(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4])));
							break;
						case "p":
							reseptListe.leggTil(new PResept(legemiddel, lege, pasient));
							break;
					}
				break;
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
			"UGYLDIG ANTALL PARAMETRE\nLinje " 
			+ linjePos + ": For faa parametre for aa lage et " 
			+ objektType 
			+ "-objekt.\n");
		}
		catch (NumberFormatException e) {
			System.out.println(
			"PARAMETERFEIL\nLinje " 
			+ linjePos 
			+ ": Feil i parameterverdier for objekttype " 
			+ objektType + ".\n");
		}
		catch (UgyldigListeIndeks e) {
			System.out.println(
			"UEKSISTERENDE OBJEKT\nLinje " 
			+ linjePos 
			+ ": Intet " 
			+ objektType + "objekt paa denne indeksen:");
			System.out.println("		" + e.getMessage() + "\n");

		}
	}

	public void printA(){
		for (Legemiddel lm: legemiddelListe) {
			System.out.println(lm.toString());
		}
		System.out.println("---------------------------------------------------------");
		for (Lege l: legeListe) {
			System.out.println(l.toString());
		}
		System.out.println("---------------------------------------------------------");
		for (Resept r: reseptListe) {
			System.out.println(r.toString());
		}
		System.out.println("---------------------------------------------------------");
		for (Pasient p: pasientListe) {
			System.out.println(p.toString());
		}
	}

	public void printAntallType(Class<?> cls){
		int ant = 0;
		for (Resept r: reseptListe) {
			if (cls.isInstance(r.hentLegemiddel())){
				ant++;
			}
		}
		System.out.println(ant);
	}

	public void printTypeMisbruk(Class<?> cls, String type){
		HashMap<String, Integer> personer = new HashMap<String, Integer>();
		for (Resept r: reseptListe) {
			if (cls.isInstance(r.hentLegemiddel())) {

				if (type == "pasient" && r.hentReit()>0) {
					if (personer.get(r.hentPasient().hentNavn()) == null) {
						personer.put(r.hentPasient().hentNavn(),1);
					}
					else{
						personer.put(r.hentPasient().hentNavn(),personer.get(r.hentPasient().hentNavn())+1);
					}
				}
				else{
					if (personer.get(r.hentLege().hentNavn()) == null) {
						personer.put(r.hentLege().hentNavn(),1);
					}
					else{
						personer.put(r.hentLege().hentNavn(),personer.get(r.hentLege().hentNavn())+1);
					}
				}
			}
		}
		for (String person: personer.keySet()) {
			System.out.println(person + ": " + personer.get(person));
		}
	}

	public void skrivResept(String legenavn, int lmId, int pasientId, int reit, String type){
		Lege lege = null;
		for (Lege l: legeListe) {
			if (l.hentNavn().toLowerCase() == legenavn.toLowerCase()) {
				lege = l;
			}
		}

		Legemiddel legemiddel = null;
		for (Legemiddel lm: legemiddelListe) {
			if (lmId == lm.hentId()) {
				legemiddel = lm;
			}
		}

		Pasient pasient = null;
		for (Pasient p: pasientListe) {
			if (p.hentId() == pasientId) {
				pasient = p;
			}
		}

		type = type.toLowerCase();

		Boolean gyldig = true;
		if (lege == null) {
			System.out.println("Legenavnet er feil");
			gyldig = false;
		}
		if (legemiddel == null) {
			System.out.println("Det finnes ikke et legemiddel med id-en som ble oppgitt");
			gyldig = false;
		}
		if (pasient == null) {
			System.out.println("Det finnes ikke en pasient med id-en som ble oppgitt");
			gyldig = false;
		}
		if (reit <= 0 && type != "p") {
			System.out.println("Resepten må ha et heltall større enn 0 reit, eller være p-resept");
			gyldig = false;
		}
		if (type != "p" && type != "militær" && type != "militaer" && type != "blaa" && type != "blå" && type != "hvit") {
			System.out.println("Militær, hvit, blå eller p ble ikke oppgitt som type");
			gyldig = false;
		}

		Resept resept = null;
		if (gyldig == true) {
			try{
				if (type == "militaer" || type == "militær") {
					resept = lege.skrivMilitaerResept(legemiddel, pasient, reit);
				} else if (type == "hvit") {
					resept = lege.skrivHvitResept(legemiddel, pasient, reit);
				} else if (type == "blaa" || type == "blå") {
					resept = lege.skrivBlaaResept(legemiddel, pasient, reit);
				} else if (type == "p") {
					resept = lege.skrivPResept(legemiddel, pasient);
				}
				reseptListe.leggTil(resept);
			} catch (UlovligUtskrift e){
				System.out.println("Vanlig lege kan ikke skrive ut resept på narkotiske midler");
			}
		}

	}

	public void lagLege(String navn, int kontrollid){
		Lege l;
		if (kontrollid != 0) {
			l = new Spesialist(navn, kontrollid);
		} else {
			l = new Lege(navn);
		}
		legeListe.leggTil(l);
	}

	public void lagPasient(String navn, String fnr){
		Pasient p = new Pasient(navn, fnr);
		pasientListe.leggTil(p);
	}

	public void lagLegemiddel(String navn, double pris, double virkestoff, int styrke, String type){
		Legemiddel legemiddel;
		type = type.toLowerCase();
		if (type == "narkotisk") {
			legemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
		} else if (type == "vanedannende") {
			legemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
		} else {
			legemiddel = new Vanlig(navn, pris, virkestoff);
		}
		legemiddelListe.leggTil(legemiddel);
	}

	public void printDatabase() {
		for (Resept resept : reseptListe) System.out.println(resept);
		for (Lege lege : legeListe) System.out.println(lege);
		for (Legemiddel legemiddel : legemiddelListe) System.out.println(legemiddel);
		for (Pasient pasient : pasientListe) System.out.println(pasient);
	}

}
