import java.io.FileNotFoundException;
import java.io.IOException;


public class Startmeny implements StartmenyInterface {
	static boolean consoleLogging = false;

	static String startmelding = "Velkommen til Legesystemet!\n";
	static String tutorial = "Hva vil du gjoere? Velg ett av de mulige menyelementene med tallet.";
	static String menyEntriesStr = "1. Printe.\n" + 
							"2. Legge til i databasen.\n" +
							"3. Bruke en resept.\n" +
							"4. Se statistikk.\n" +
							"5. Skrive til fil\n" +
							"6. Lese fra fil\n" +
							"0. Avslutt\n"; // av denne karantena
	static String exitStr = "Ha en fin dag!";


	private Database db;

	enum MenyValg {
		RAGEQUIT, // 0
		PRINTE, // 1
		OPPRETTE, // 2
		BRUKERESEPT, // 3
		STATISTIKK, // 4
		FILSKRIVING, // 5
		FILLESNING, //6
		FAIL, // kan ikke naas
		ROOT // kan ikke naas
	}
	private MenyValg brukerValg = MenyValg.ROOT;

	public Startmeny() {
		this.db = new Database(); // creates an empty Database unit and assigns it to self
		this.start(); // runs the startmeny with the assigned db
	}

	public Startmeny(Database db) {
		this.db = db;
		this.startmelding = "Velkommen til Legesystemet!\nDatabasen har blitt lastet fra fil.\n"; // we want to inform the user the file has loaded succesfully
		this.start();
	}

	private void start() {
		while (this.brukerValg != MenyValg.RAGEQUIT) {
			switch (this.brukerValg) {
				/*
					Each function inside each case changes the this.brukerValg, so that in the next while-loop it knows where to continue.
					Ususally, this will be ROOT, as this means we've returned to the main start page.

					Each case is a use-case for the user.
				*/

				case ROOT:
					Console.clearScreen();
					System.out.print(startmelding + "\n" + menyEntriesStr);
					int brukerTall = Console.getInt(tutorial, 6, -1); // 6 as the maxValue, because of MenyValg, -1 as the fallback value
					if (brukerTall == -1) this.brukerValg = MenyValg.FAIL;
					else this.brukerValg = MenyValg.values()[brukerTall];
					this.startmelding = "Hovedmeny:";  // override startmelding in the first run of this function.
					break;
				
				case PRINTE:
					printeMeny();
					break;
				
				case OPPRETTE:
					opretteMeny();
					break;

				case BRUKERESEPT:
					reseptMeny();
					break;

				case STATISTIKK:
					statistikkMeny();
					break;

				case FILSKRIVING:
					filskrivingMeny();
					break;

				
				case FILLESNING:
					fillesningMeny();
					break;

				case FAIL:
					this.startmelding = "Dette var visst feil, proev paa nytt.\n";
					this.brukerValg = MenyValg.ROOT; // error message and back to start
					break;
					
				case RAGEQUIT:
					break;
			}
		}
		System.out.println(exitStr);

	}

	private void printeMeny(){
		Console.clearScreen(); // we simulate a new page

		System.out.print("Hva vil du printe?\n" +
						 "1. Pasienter\n" +
						 "2. Leger\n" +
						 "3. Legemidler\n" +
						 "4. Resepter\n" +
						 "5. ALT!!!\n" +
						 "0. Jeg angrer!\n"
		);
		int printeValg = -1;
		do {
			printeValg = Console.getInt(5, -1);
			if (printeValg == -1) System.out.println("Dette er ikke gyldig, venligst skriv et gyldig tall");
		} while (printeValg == -1);

		switch (printeValg) {
			case 0:
				this.brukerValg = MenyValg.ROOT;
				return;
			case 1:
				db.printPasient();
				Console.waitForEnter();
				System.out.println("s");
				break;
			case 2:
				db.printLege();
				Console.waitForEnter();
				break;
			case 3:
				db.printLegemiddel();
				Console.waitForEnter();
				break;
			case 4:
				db.printResept();
				Console.waitForEnter();
				break;
			case 5:
				db.printAlt();
				Console.waitForEnter();
				break;
		}
		this.brukerValg = MenyValg.PRINTE;

	}
	private void opretteMeny(){
		Console.clearScreen(); // we simulate a new page

		System.out.print("Hva vil du oprette?\n" +
						 "1. Pasient\n" +
						 "2. Lege\n" +
						 "3. Legemiddel\n" +
						 "4. Resept\n" +
						 "0. Jeg angrer!\n"
		);
		int opretteValg = -1;
		do {
			opretteValg = Console.getInt(4, -1);
			if (opretteValg == -1) System.out.println("Dette var ikke gyldig, vennligst skriv et gyldig tall");
		} while (opretteValg == -1);

		switch (opretteValg) {
			case 0:
				this.brukerValg = MenyValg.ROOT;
				return;
			case 1:
				String navn = Console.getString("Hva skal pasienten hete?");
				String personnr = Console.getString("Hva er person-nummeret til pasienten?");

				db.lagPasient(navn, personnr);

				break;
			case 2:
				String legenavn = Console.getString("Hva skal legen hete?");
				int kontrollid = Console.getInt("Hva er kontroll-nummeret til legen? (0 hvis ingen)");

				if (kontrollid  < 0) {
					System.out.println("KontrollID maa vaere et positivt heltall eller 0");
					break;
				}
				db.lagLege(legenavn, kontrollid);

				break;
			case 3:
				String legemiddelNavn = Console.getString("Hva heter legemiddelet?");
				double pris = Console.getDouble("Hva koster legemiddelet (kr)?");
				if (pris == -1) {
					System.out.println("Bruk et positivt tall.");
					break;
				}

				double virkestoff = Console.getDouble("Hvor mye virkestoff har legemiddelet (mg)?");
				if (virkestoff == -1) {
					System.out.println("Bruk et tall i N.");
					break;
				}

				int styrke = 0;
				String legemiddelType = Console.getString("Av hvilken type er legemiddelet (vanlig, vannedannende, narkotisk)?");
				if (legemiddelType.equals("vannedannende") || legemiddelType.equals("narkotisk")) {
					styrke = Console.getInt("Hvor sterkt er legemiddelet?");
					if (styrke == -1) {
						System.out.println("Styrke må være en positiv tallverdi.");
						break;
					}
				}

				try {
					db.lagLegemiddel(legemiddelNavn, pris, virkestoff, styrke, legemiddelType);
				} catch (TypeNotFoundException e) {
					System.out.println("Type maa vaere vanlig, narkotisk eller vanedannende");
				}

				break;
			case 4:
				boolean error = false; // we assume there is no error, but check for each, printing error messages on the way for each possible requirement
				if (db.hentLeger().stoerrelse() == 0) {
					System.out.println("Fant ingen leger. Legg til en doktor for aa lage en resept.");
					error = true;
				}
				if (db.hentLegemidler().stoerrelse() == 0) {
					System.out.println("Fant ingen legemidler. Legg til some drugs for aa lage en resept.");
					error = true;
				}
				if (db.hentPasienter().stoerrelse() == 0) {
					System.out.println("Fant ingen pasienter. Ikke lek med legemidler.");
					error = true;
				}
				if (error) { // if an error is found user will not be able to create a Resept
					this.brukerValg = MenyValg.OPPRETTE;
					break;
				}

				db.printLege();
				String utskrivendeLegenavn = Console.getString("Hva heter legen som skriver ut resepten");
				if (db.finnLege(utskrivendeLegenavn) == null) {
					System.out.println("Denne legen finnes ikke");
					break;
				}
				db.printLegemiddel();
				int legemiddelId = Console.getInt("Hva er id-nummeret til legemiddelet?");
				if (db.finnLegemiddel(legemiddelId) == null) {
					System.out.println("Intet legemiddel med denne ID");
					break;
				}
				db.printPasient();
				int pasientId = Console.getInt("Hva er id-nummeret til pasienten?");
				if (db.finnPasient(pasientId) == null) {
					System.out.println("Ingen pasient med denne ID");
					break;
				}

				String reseptType = Console.getString("Hvilken type resept oensker du aa opprette (blaa, hvit, militaer, presept)?");
				try {
					if (!reseptType.equals("p") && !reseptType.equals("presept")){
						int reit = Console.getInt("Hva er reiten for resepten?");
						db.lagResept(utskrivendeLegenavn, legemiddelId, pasientId, reit, reseptType);
					} else {
						db.lagResept(utskrivendeLegenavn, legemiddelId, pasientId, 3, reseptType);
					}
				} catch (TypeNotFoundException e) {
					System.out.println("Resept maa vaere hvit, blaa, presept eller militaer");
				}
				break;
		}
		this.brukerValg = MenyValg.OPPRETTE;
		Console.waitForEnter();

	}
	private void reseptMeny(){
		boolean fantPasientMedResept = false;
		for (Pasient pasient : db.hentPasienter()) {
			if (pasient.hentResepter().stoerrelse() > 0) {
				System.out.println(pasient);
				fantPasientMedResept = true;
			}
			
		}
		if (!fantPasientMedResept) {
			System.out.println("Fant ingen pasienter med resepter.");
            this.brukerValg = MenyValg.ROOT;
            Console.waitForEnter();
			return;
		}
		System.out.println("");

		int pasientId = Console.getInt("Bruk tallet til aa velge pasienten du vil bruke reseptene til,\n" +
									"eller skriv noe annet enn et tall for aa gaa tilbake",-1); // any string works because Console returns -1 on error
		if (pasientId == -1) {
			System.out.println("Gaar tilbake...");
			this.brukerValg = MenyValg.ROOT;
		}
		else if (db.finnPasient(pasientId) == null) {
			System.out.println("Denne pasienten finnes ikke");
			this.brukerValg = MenyValg.BRUKERESEPT;
		}
		else if (db.finnPasient(pasientId).hentResepter().stoerrelse() == 0) {
			System.out.println("Denne pasienten har ingen resepter");
			this.brukerValg = MenyValg.BRUKERESEPT;
		}
		else {
			Pasient pasient = db.finnPasient(pasientId);

			for (Resept resept : pasient.hentResepter()) {
				System.out.println(resept);
			}
			System.out.println("");
			int reseptId = -1;

			reseptId = Console.getInt("Skriv id-en til resepten du vil bruke, eller skriv noe annet enn et tall for aa gaa tilbake",-1);
			if (reseptId == -1) {
				System.out.println("Gaar tilbake...");
				this.brukerValg = MenyValg.ROOT;
			}
			else if (db.finnResept(reseptId) == null) {
				System.out.println("Dette er en ugyldig ID");
				this.brukerValg = MenyValg.BRUKERESEPT;
			}
			else {
				boolean gyldigResept = false;
				Resept resept = db.finnResept(reseptId);
				for (Resept pasientSinResept : pasient.hentResepter()) {
					if (resept.equals(pasientSinResept)) gyldigResept = true;
				}

				if (gyldigResept) {
					if (!resept.bruk()) {
						System.out.println("Denne resepten er tom");
						this.brukerValg = MenyValg.BRUKERESEPT;
					}
					else {
						System.out.println("Brukte resept paa " + resept.hentLegemiddel().hentNavn() + ", " + resept.hentReit() + " bruk igjen.");
						this.brukerValg = MenyValg.ROOT;
					}
				}
				else {
					System.out.println("Denne pasienten har ikke en resept med id " + reseptId + ".");
					this.brukerValg = MenyValg.BRUKERESEPT;
				}
			}
		}
		Console.waitForEnter();
	}
	private void statistikkMeny(){
		Console.clearScreen();

		System.out.print("Hva slags statistikk vil du printe?\n" +
						 "1. Antall resepter paa narkotiske legemidler\n" +
						 "2. Antall resepter paa vanedannende legemidler\n" +
						 "3. Antall narkotiske resepter per lege\n" +
						 "4. Antall narkotiske resepter per pasient\n" +
						 "0. Jeg angrer!\n"
		);
		int printeValg = -1;
		do {
			printeValg = Console.getInt(4, -1);
			if (printeValg == -1) System.out.println("Dette er ikke gyldig, skriv et gyldig tall");
		} while (printeValg == -1);

		String pressEnterString = "\n\nFerdig med aa printe, trykk paa enter \nfor aa gaa tilbake til startmenyen";

		switch (printeValg) {
			case 0:
				this.brukerValg = MenyValg.ROOT;
				return;
			case 1:
				db.printResepterAvType(Narkotisk.class);
				Console.waitForEnter();
				break;
			case 2:
				db.printResepterAvType(Vanedannende.class);
				Console.waitForEnter();
				break;
			case 3:
				db.printPersonMedMisbruk(Vanedannende.class, "lege");
				Console.waitForEnter();
				break;
			case 4:
				db.printPersonMedMisbruk(Narkotisk.class, "pasient");
				Console.waitForEnter();
				break;
		}
		this.brukerValg = MenyValg.STATISTIKK;
	}
	private void filskrivingMeny(){
		String filnavn = Console.getString("Skriv filnavn du vil skrive til, eller 0 for aa gaa tilbake.");
		try {
			if (!filnavn.equals("0")) db.skrivTilFil(filnavn);
		}
		catch (IOException e) {
			Console.clearScreen();
			System.out.println("Det er ikke mulig aa skrive til denne filen");
			this.brukerValg = MenyValg.FILSKRIVING;
		}
		this.brukerValg = MenyValg.ROOT;
	}
	private void fillesningMeny() {
		this.brukerValg = MenyValg.ROOT;
		String filnavn = Console.getString("Skriv filnavn du vil lese fra, eller 0 for aa gaa tilbake.");
		if (!filnavn.equals("0")) {
			try {
				db.lesFraFil(filnavn);
			}
			catch (FileNotFoundException e) {
				Console.clearScreen();
				System.out.println("Denne filen eksisterer ikke");
				this.brukerValg = MenyValg.FILLESNING;
				System.out.println(this.brukerValg);
			}
			Console.waitForEnter();
		}
	}
}
