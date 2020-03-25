

//exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.File;

//lesing av fil
import java.util.Scanner;

//skriving til fil
import java.io.OutputStream;//abstrakt klasse for outputStreamWriter
import java.io.OutputStreamWriter;//klasse som skriver karakterer til et output fra byte
import java.io.BufferedWriter;//klasse som lager buffer for aa redusere konvertering av byte
import java.io.FileOutputStream;//klasse som tar outputStream og skriver til fil

import java.util.HashMap;
import java.util.TreeMap;


class Database implements DatabaseInterface {
	public Lenkeliste<Pasient> pasientListe = new Lenkeliste<Pasient>();//nr 0
	public Lenkeliste<Legemiddel> legemiddelListe = new Lenkeliste<Legemiddel>();//nr 1
	public SortertLenkeliste<Lege> legeListe = new SortertLenkeliste<Lege>();//nr 2
	public Lenkeliste<Resept> reseptListe = new Lenkeliste<Resept>();//nr 3

	public Database() {}
	public Database(String filnavn) throws FileNotFoundException {
		lesFraFil(filnavn);
	}

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
			if (naavaerendeLinje.contains("#") && scanner.hasNextLine()) {
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
		System.out.println("	Linjer gaatt gjennom: " + scannerPos + "\n");
	}
	private void leggTilIListe(String linje, int nummerPaaListe, int linjePos) {
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
							legemiddelListe.leggTil(new Vanlig(
														linjeArray[0],
														Double.valueOf(linjeArray[2]),
														Double.valueOf(linjeArray[3])));
							break;
						case "narkotisk":
							legemiddelListe.leggTil(new Narkotisk(
														linjeArray[0],
														Double.valueOf(linjeArray[2]),
														Double.valueOf(linjeArray[3]),
														Integer.parseInt(linjeArray[4])));
							break;
						case "vanedannende":
							legemiddelListe.leggTil(new Vanedannende(
														linjeArray[0],
														Double.valueOf(linjeArray[2]),
														Double.valueOf(linjeArray[3]),
														Integer.parseInt(linjeArray[4])));
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

					//skal naa lage resepten
					Resept resept;
					//reseptlagd vil vaere true med mindre vi kommer til default, hvor den ikke vil bli lagd
					boolean reseptLagd = true;
					String type = linjeArray[3];
					switch (type) {
						case "blaa":
							resept = new Blaa(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4]));
							break;
						case "hvit":
							resept = new Hvit(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4]));
							break;
						case "militaer":
							resept = new Militaer(legemiddel, lege, pasient, Integer.parseInt(linjeArray[4]));
							break;
						case "p":
							resept = new PResept(legemiddel, lege, pasient);
							break;
						default:
							resept = null;
							reseptLagd = false;

							System.out.println(
							"FEIL TYPE RESEPT\nLinje "
							+ linjePos
							+ ": Ingen resepttype ved navn "
							+ linjeArray[3]
							+ "\n");
							break;
					}
					if (reseptLagd) {
						reseptListe.leggTil(resept);
						pasient.leggTilResept(resept);
					}

					break;
				default:
					System.out.println(
					"UEKSISTERENDE KATEGORI\nLinje "
					+ linjePos
					+ ": Det er bare fire kategorier. Denne linjen er i kategori "
					+ (nummerPaaListe + 1)
					+ ".\n");
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
			//dette skjer bare i case 3, altsaa i den siste kategorien
			//da maa vi sjekke hva det var som skapte feilen: legemiddel eller
			//pasient, ettersom de er det eneste som bruker indeksen paa lenkeliste
			int ID = -1;
			if (Integer.parseInt(linjeArray[0]) >= reseptListe.stoerrelse()) {
				ID = Integer.parseInt(linjeArray[0]);
				objektType = "legemiddel";
			}
			//dersom det ikke var feil ved reseptListe vet vi at det maa vaere
			//feil ved pasientListe ettersom disse er de eneste som kan gi UgyldigListeIndeks
			else {
				ID = Integer.parseInt(linjeArray[2]);
				objektType = "pasient";
			}

			System.out.println(
			"FEIL ID\nLinje "
			+ linjePos
			+ ": Intet "
			+ objektType + "objekt med ID "
			+ ID
			+ ".\n");

		}
	}

	public void skrivTilFil(String filnavn) throws IOException {
		//lager fil
		File fil = new File(filnavn);

		if (fil.createNewFile()) {
			System.out.println("Ny fil skapt med navn " + filnavn + "\n");
		}
		else {
			System.out.println("Skriver til fil " + filnavn + "...\n");
		}

		//lager skriver til fil
		OutputStream output = new FileOutputStream(fil, false);
		OutputStreamWriter outputWriter = new OutputStreamWriter(output, "UTF-8");//vi enkoder i UTF-8
		BufferedWriter skriver = new BufferedWriter(outputWriter);//vi bruker bufferedWriter fordi vi skal ha mange skriveoperasjoner

		//her skjer skrivinga
		//liste 0

		skriver.write("# Pasienter (navn,fnr)" + "\n");
		for (Pasient pasient : pasientListe) {
			skriver.write(
						pasient.hentNavn()
						+ ","
						+ pasient.hentFnr()
						+ "\n");
		}

		//liste 1
		skriver.write("# Legemidler (navn,type,pris,virkestoff[,styrke])" + "\n");

		for (Legemiddel legemiddel : legemiddelListe) {
			if (legemiddel instanceof Vanlig) {
				skriver.write(
							legemiddel.hentNavn()
							+ ",vanlig,"
							+ legemiddel.hentPris()
							+ ","
							+ legemiddel.hentVirkestoff()
							+ ",\n");
			}
			if (legemiddel instanceof Narkotisk) {
				//vi maa downcaste for aa faa tilgang til hentNarkotiskStyrke
				Narkotisk narkotiskLegemiddel;
				narkotiskLegemiddel = (Narkotisk) legemiddel;
				skriver.write(
							narkotiskLegemiddel.hentNavn()
							+ ",narkotisk,"
							+ narkotiskLegemiddel.hentPris()
							+ ","
							+ narkotiskLegemiddel.hentVirkestoff()
							+ ","
							+ narkotiskLegemiddel.hentNarkotiskStyrke()
							+ "\n");
			}
			if (legemiddel instanceof Vanedannende) {
				Vanedannende vanedannendeLegemiddel;
				vanedannendeLegemiddel = (Vanedannende) legemiddel;
				skriver.write(
							legemiddel.hentNavn()
							+ ",vanedannende,"
							+ vanedannendeLegemiddel.hentPris()
							+ ","
							+ vanedannendeLegemiddel.hentVirkestoff()
							+ ","
							+ vanedannendeLegemiddel.hentVanedannendeStyrke()
							+ "\n");
			}
		}

		//liste 2
		skriver.write("# Leger (navn, kontrollid / 0 hvis vanlig lege)" + "\n");

		for (Lege lege : legeListe) {
			if (lege instanceof Spesialist) {
				Spesialist spesialist;
				spesialist = (Spesialist) lege;

				skriver.write(
							spesialist.hentNavn()
							+ ","
							+ spesialist.hentKontrollID()
							+ "\n");
			}
			else {
				skriver.write(
							lege.hentNavn()
							+ ","
							+ "0"
							+ "\n");
			}

		}

		//liste 3
		skriver.write("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])" + "\n");

		for (Resept resept : reseptListe) {
			String type = "";
			String reit = Integer.toString(resept.hentReit());
			if (resept instanceof Hvit) type = "hvit";
			if (resept instanceof Blaa) type = "blaa";
			if (resept instanceof Militaer) type = "militaer";
			if (resept instanceof PResept) {
				type = "p";
				reit = "";
			}

			skriver.write(
						resept.hentLegemiddel().hentId()
						+ ","
						+ resept.hentLege().hentNavn()
						+ ","
						+ resept.hentPasient().hentId()
						+ ","
						+ type
						+ ","
						+ reit);
			//jeg bruker denne testen for aa soerge for at siste linje ikke er tom
			if (resept.hentId() < (reseptListe.stoerrelse() - 1)) skriver.write("\n");
		}
		//ferdig
		skriver.flush();
		skriver.close();

		System.out.println("Ferdig skrevet!");
	}

	public void printAlt(){
		printPasient();
		printResept();
		printLege();
		printLegemiddel();
	}

	public int tellResepterAvType(Class<?> legemiddelSubclass){
		int ant = 0;
		for (Resept r: reseptListe) {
			if (legemiddelSubclass.isInstance(r.hentLegemiddel())){
				ant++;
			}
		}
		return ant;
	}

	public void printResepterAvType(Class<?> legemiddelSubclass) {
		System.out.println("Antall resepter:");
		System.out.println(tellResepterAvType(legemiddelSubclass));
	}

	public HashMap<String, Integer> tellMisbruk(Class<?> legemiddelSubclass, String personType){
		HashMap<String, Integer> personer = new HashMap<String, Integer>();
		for (Resept r: reseptListe) {
			if (legemiddelSubclass.isInstance(r.hentLegemiddel())) {

				if (personType.equals("pasient") && r.hentReit()>0) {
					if (personer.get(r.hentPasient().hentNavn()) == null) {
						personer.put(r.hentPasient().hentNavn(), 1);
					}
					else{
						personer.put(r.hentPasient().hentNavn(), personer.get(r.hentPasient().hentNavn())+1);
					}
				}
				else if (personType.equals("lege")) {
					if (personer.get(r.hentLege().hentNavn()) == null) {
						personer.put(r.hentLege().hentNavn(), 1);
					}
					else{
						personer.put(r.hentLege().hentNavn(), personer.get(r.hentLege().hentNavn())+1);
					}
				}
			}
		}
		return personer;
	}
	public void printPersonMedMisbruk(Class<?> legemiddelSubclass, String personType) { // T instead of ? results in "cannot find symbol Class<T>"
		System.out.println("Her er misbruket");
		TreeMap<String, Integer> personer = new TreeMap<String,Integer>(tellMisbruk(legemiddelSubclass, personType));
		for (String person : personer.keySet()) {
			System.out.println(person + ": " + personer.get(person));
		}
	}

	public Lege finnLege(String legenavn) {
		for (Lege l : legeListe) {
			if (l.hentNavn().toLowerCase().equals(legenavn.toLowerCase())) {
				return l;
			}
		}
		return null;
	}

	public Legemiddel finnLegemiddel(int id) {
		for (Legemiddel legemiddel : legemiddelListe) {
			if (id == legemiddel.hentId()) {
				return legemiddel;
			}
		}
		return null;
	}

	public Pasient finnPasient(int id) {
		for (Pasient pasient : pasientListe) {
			if (pasient.hentId() == id) {
				return pasient;
			}
		}
		return null;
	}

	public Resept finnResept(int id) {
		for (Resept resept : reseptListe) {
			if (resept.hentId() == id) {
				return resept;
			}
		}
		return null;
	}

	public Resept lagResept(String legenavn, int legemiddelId, int pasientId, int reit, String type) throws TypeNotFoundException {
		Lege lege = finnLege(legenavn);
		Legemiddel legemiddel = finnLegemiddel(legemiddelId);
		Pasient pasient = finnPasient(pasientId);
		return lagResept(lege, legemiddel, pasient, reit, type);
	}

	public Resept lagResept(Lege lege, Legemiddel legemiddel, Pasient pasient, int reit, String type) throws TypeNotFoundException {
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
			System.out.println("Resepten maa ha et heltall stoerre enn 0 reit, eller vaere p-resept");
			gyldig = false;
		}
		if (!type.equals("p") && !type.equals("militær") && !type.equals("militaer") && !type.equals("blå") && !type.equals("blaa") && !type.equals("hvit")) {
			gyldig = false;
			throw new TypeNotFoundException();
		}

		Resept resept = null;
		if (gyldig == true) {
			try{
				if (type.equals("militaer")) {
					resept = lege.skrivMilitaerResept(legemiddel, pasient, reit);
				} else if (type.equals("hvit")) {
					resept = lege.skrivHvitResept(legemiddel, pasient, reit);
				} else if (type.equals("blaa")) {
					resept = lege.skrivBlaaResept(legemiddel, pasient, reit);
				} else if (type.equals("p")) {
					resept = lege.skrivPResept(legemiddel, pasient);
				}
				reseptListe.leggTil(resept);
			} catch (UlovligUtskrift e){
				System.out.println("Vanlig lege kan ikke skrive ut resept paa narkotiske midler");
			}
		}
		else {
			//throw new Exception("Ikke gyldig resept");
			System.out.println("Ugyldig");
			return null;
		}
		return resept;
	}

	public Lege lagLege(String navn, int kontrollid){
		Lege l;
		if (kontrollid != 0) {
			l = new Spesialist(navn, kontrollid);
		} else {
			l = new Lege(navn);
		}
		legeListe.leggTil(l);
		return l;
	}

	public Pasient lagPasient(String navn, String fnr){
		Pasient p = new Pasient(navn, fnr);
		pasientListe.leggTil(p);
		return p;
	}

	public Legemiddel lagLegemiddel(String navn, double pris, double virkestoff, int styrke, String type) throws TypeNotFoundException {
		Legemiddel legemiddel;
		type = type.toLowerCase();
		if (type.equals("narkotisk")) {
			legemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
		} else if (type.equals("vanedannende")) {
			legemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
		} else if (type.equals("vanlig")) {
			legemiddel = new Vanlig(navn, pris, virkestoff);
		} else {
			throw new TypeNotFoundException();
		}
		legemiddelListe.leggTil(legemiddel);
		return legemiddel;
	}

	public SortertLenkeliste<Lege> hentLeger() {
		return legeListe;
	}
	public Lenkeliste<Legemiddel> hentLegemidler() {
		return legemiddelListe;
	}
	public Lenkeliste<Resept> hentResepter() {
		return reseptListe;
	}
	public Lenkeliste<Pasient> hentPasienter() {
		return pasientListe;
	}
	public void printPasient() {
		System.out.println("Pasienter: ");
		for (Pasient p: pasientListe) {
			System.out.println(p.toString());
		}
		System.out.println();
	}
	public void printResept() {
		System.out.println("Resepter: ");
		for (Resept r: reseptListe) {
			System.out.println(r.toString());
		}
		System.out.println();
	}
	public void printLege() {
		System.out.println("Leger: ");
		for (Lege l: legeListe) {
			System.out.println(l.toString());
		}
		System.out.println();
	}
	public void printLegemiddel() {
		System.out.println("Legemidler: ");
		for (Legemiddel lm: legemiddelListe) {
			System.out.println(lm.toString());
		}
		System.out.println();
	}
}
