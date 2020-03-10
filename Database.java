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
		//vi hopper over den foerste linja fordi det er en hashtag
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String naavaerendeLinje = scanner.nextLine();

			//dersom vi kommer til en # vet vi at vi maa hoppe over linjen
			//og at vi maa bytte til neste liste
			if (naavaerendeLinje.contains("#")) {
				naavaerendeLinje = scanner.nextLine();
				listeIndeks++;
			}
			try {
			leggTilIListe(naavaerendeLinje, listeIndeks);
			}
			catch (UgyldigListeIndeks e) {
				System.out.println("feil paa denne linje: " + naavaerendeLinje);
			}
		}
	}
	private void leggTilIListe(String linje, int nummerPaaListe) throws UgyldigListeIndeks {
		String[] linjeArray = linje
			.trim() //fjerner whitespace
			.replaceAll(",$","") //fjerner komma uten noen string
			.split(","); //gjoer til array

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
				}
			break;
			case 2:
			//Lege = (navn,kontrollid)
				if (Integer.parseInt(linjeArray[1]) == 0) {
					System.out.println(linjeArray[0]);
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
					System.out.println("ADVARSEL: Ingen lege ved navn " + linjeArray[1]);
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



	public void printDatabase() {
		for (Resept resept : reseptListe) System.out.println(resept);
		for (Lege lege : legeListe) System.out.println(lege);
		for (Legemiddel legemiddel : legemiddelListe) System.out.println(legemiddel);
		for (Pasient pasient : pasientListe) System.out.println(pasient);
	}

}
