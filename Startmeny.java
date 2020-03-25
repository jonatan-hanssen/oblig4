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
							"5. Skrive til fil fra databasen\n" +
							"6. Skrive til databasen fra fil\n" +
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
		this.db = new Database();
		this.start();
	}

	public Startmeny(Database db) {
		this.db = db;
		this.startmelding = "Velkommen til Legesystemet!\nDatabasen har blitt lastet fra fil.\n";
		this.start();
	}

	private void start() {
		while (this.brukerValg != MenyValg.RAGEQUIT) {
			switch (this.brukerValg) {
				case ROOT:
					Console.clearScreen();
					System.out.print(startmelding + "\n" + menyEntriesStr);
					int brukerTall = Console.getInt(tutorial, 6, -1); // 6 as the maxValue, because of MenyValg, -1 as the fallback value
					if (brukerTall == -1) this.brukerValg = MenyValg.FAIL;
					else this.brukerValg = MenyValg.values()[brukerTall];
					this.startmelding = "Hovedmeny:";
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
					this.brukerValg = MenyValg.ROOT;
					break;

				case FAIL:
					this.startmelding = "Dette var visst feil, proev paa nytt.\n";
					this.brukerValg = MenyValg.ROOT;
					break;
					
				case RAGEQUIT:
					System.out.println(exitStr); // doesn't return to init, so dies
					break;
			}
		}

	}

	private void printeMeny(){
		Console.clearScreen();

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
		Console.clearScreen();

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

				int styrke = Console.getInt("Hvor sterkt er legemiddelet?");
				if (styrke == -1) {
					System.out.println("Styrke må være en positiv tallverdi.");
					break;
				}

				String legemiddelType = Console.getString("Av hvilken type er legemiddelet (vanlig, vannedannende, narkotisk)?");

				try {
					db.lagLegemiddel(legemiddelNavn, pris, virkestoff, styrke, legemiddelType);
				} catch (TypeNotFoundException e) {
					System.out.println("Type maa vaere vanlig, narkotisk eller vanedannende");
				}


				break;
			case 4:
				db.printLege();
				String utskrivendeLegenavn = Console.getString("Hva heter legen som skriver ut resepten");
				if (db.finnLege(utskrivendeLegenavn) == null){
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
					if (reseptType != "p" && reseptType != "presept"){
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
		for (Pasient pasient : db.hentPasienter()) {
			if (pasient.hentResepter().stoerrelse() > 0) {
				System.out.println(pasient);
			}
		}
		System.out.println("");

		int pasientId = Console.getInt("Bruk tallet til aa velge pasienten du vil bruke reseptene til,\n" +
									"eller skriv \"beklager at jeg valgte feil, jeg vil naa gjerne gaa tilbake om dette er mulig\" for aa gaa tilbake",-1);
		if (pasientId == -1) {
			System.out.println("Takk for din oppmerksomhet, vi vil naa gaa tilbake. Paa gjensyn!");
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
			int reseptId = -2;
			while (reseptId == -2) {
				reseptId = Console.getInt("Skriv id-en til resepten du vil bruke, eller -1 for aa gaa tilbake",(db.hentResepter().stoerrelse()-1),-2);
				if (reseptId == -1) {
					this.brukerValg = MenyValg.BRUKERESEPT;
				}
				else if (reseptId == -2) {
					System.out.println("Dette er ikke en gyldig id");
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
							reseptId = -2;
						}
						else {
							System.out.println("Brukte resept paa " + resept.hentLegemiddel().hentNavn() + ", " + resept.hentReit() + " bruk igjen.");
						}
					}
					else {
						System.out.println("Denne pasienten har ikke en resept med id " + reseptId + ".");
						reseptId = -2;
					}
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
	}
	private void fillesningMeny() {
		String filnavn = Console.getString("Skriv filnavn du vil lese fra, eller 0 for aa gaa tilbake.");
		try {
			if (filnavn != "0") db.lesFraFil(filnavn);
		}
		catch (FileNotFoundException e) {
			Console.clearScreen();
			System.out.println("Denne filen eksisterer ikke");
			this.brukerValg = MenyValg.FILLESNING;
		}
		Console.waitForEnter();
	}
}
