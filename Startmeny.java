

import java.io.FileNotFoundException;
import java.io.IOException;

public class Startmeny implements StartmenyInterface {
	static boolean consoleLogging = false;

	static String velkommenStr = "Velkommen til Legesystemet!\n";
	static String tutorial = "Velg ett av de mulige menyelementene med tallet.";
	static String menyEntriesStr = "1. Jeg vil printe.\n" + 
							"2. Jeg vil legge til i databasen.\n" +
							"3. Jeg vil bruke en resept.\n" +
							"4. Jeg vil se statistikk.\n" +
							"5. Jeg vil skrive til fil.\n" +
							"6. Jeg vil skrive til databasen fra fil\n" +
							"0. Jeg vil ut (exit).\n"; // av denne karantena
	static String exitStr = "Ha en fin dag!";
	static String feilmelding = "Oopsie! Det var visst ugyldig. Skriv et gyldig tall";

	private Database db;

	enum MenyValg {
		RAGEQUIT, // 0
		PRINTE, // 1
		OPRETTE, // 2
		BRUKERESEPT, // 3
		STATISTIKK, // 4
		FILSKRIVING, // 5
		FILLESNING, //6
		FAIL, // 7
		INIT, // 8
	}
	private MenyValg brukerValg = MenyValg.INIT;

	public Startmeny() {
		this.db = new Database();
		this.start();
	}

	public Startmeny(Database db) {
		this.db = db;
		this.start();
	}

	public start() {
		while (this.brukerValg != MenyValg.RAGEQUIT) {
			/*
			*	if last selection was to show main menu, don't ask the user for a value
			*	and instead show the given settings. showing the main menu resets the 
			*	user option to null, so it doesn't stuck in an infinite loop.

			*	this allows us to precisely choose where to land next from each command.
			*	the currently implemented functions all return to main menu after being
			*	done, but it is easy to override what a function goes to, either by the 
			*	return value of a function, or through the current implementation.

			*	currently, if a user fails to provide valid input, the command line will
			*	complain to the user.
			*/
			if (this.brukerValg != MenyValg.INIT) {
				int brukerTall = Console.getInt(6, -1); // 6 as the maxValue, because of MenyValg, -1 as the fallback value
				if (brukerTall == -1) this.brukerValg = MenyValg.FAIL;
				else this.brukerValg = MenyValg.values()[brukerTall];
				
				if (consoleLogging)
					System.out.println("Du valgte " + this.brukerValg);
			}

			switch (this.brukerValg) {
				case PRINTE:
					printeMeny();
					this.brukerValg = MenyValg.INIT;
					break;
				
				case OPRETTE:
					opretteMeny();
					this.brukerValg = MenyValg.INIT;
					break;
				
				case BRUKERESEPT:
					reseptMeny();
					this.brukerValg = MenyValg.INIT;
					break;
				
				case STATISTIKK:
					statistikkMeny();
					this.brukerValg = MenyValg.INIT;
					break;
				
				case FILSKRIVING:
					filskrivingMeny();
					this.brukerValg = MenyValg.INIT;
					break;
				
				case RAGEQUIT:
					System.out.println(exitStr); // doesn't return to init, so dies
					break;

				case FAIL:
					System.out.println(feilmelding);
					break;

				case INIT:
					Console.clearScreen();
					System.out.print(velkommenStr + menyEntriesStr);
					velkommenStr = "Hovedmeny: \n";
					System.out.println(tutorial);
					this.brukerValg = null;
					break;
				
				case FILLESNING:
					fillesningMeny();
					this.brukerValg = MenyValg.INIT;
					break;
			}
		}

	}

	private void printeMeny(){
		Console.clearScreen();

		System.out.print("Hva vil du printe?\n" + 
						 "1. Pasienter\n" + 
						 "3. Leger\n" + 
						 "4. Legemidler\n" +
						 "2. Resepter\n" + 
						 "5. ALT!!!\n" +
						 "0. Jeg angrer!\n"
		);
		int printeValg = -1;
		do {
			printeValg = Console.getInt(5, -1);
			if (printeValg == -1) System.out.println(feilmelding);
		} while (printeValg == -1);		

		String pressEnterString = "\n\nFerdig med aa printe, trykk paa enter \nfor aa gaa tilbake til hovedmenyen";

		switch (printeValg) {
			case 0:
				return;
			case 1:
				db.printPasient();
				Console.getString(pressEnterString);
				break;
			case 2:
				db.printLege();
				Console.getString(pressEnterString);
				break;
			case 3:
				db.printLegemiddel();
				Console.getString(pressEnterString);
				break;
			case 4: 
				db.printResept();
				Console.getString(pressEnterString);
				break;
			case 5:
				db.printAlt();
				Console.getString(pressEnterString);
				break;
		}

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
			opretteValg = Console.getInt(3, -1);
			if (opretteValg == -1) System.out.println(feilmelding);
		} while (opretteValg == -1);		

		switch (opretteValg) {
			case 0:
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
					System.out.println(feilmelding);
					break;
				}
				db.lagLege(legenavn, kontrollid);

				break;
			case 3:
				String legemiddelNavn = Console.getString("Hva heter legemiddelet?");
				double pris = Console.getDouble("Hva koster legemiddelet?");
				double virkestoff = Console.getDouble("Hvor mye virkestoff har legemiddelet?");
				int styrke = Console.getInt("Hvor sterkt er legemiddelet?");
				String legemiddelType = Console.getString("Av hvilken type er legemiddelet?");

				try {
					db.lagLegemiddel(legemiddelNavn, pris, virkestoff, styrke, legemiddelType);
				} catch (TypeNotFoundException e) {
					System.out.println(feilmelding);
				}


				break;
			case 4: 
				String utskrivendeLegenavn = Console.getString("Hva heter legen som skriver ut resepten");
				if (db.finnLege(utskrivendeLegenavn) == null){
					System.out.println(feilmelding);
					break;
				}

				int legemiddelId = Console.getInt("Hva er id-nummeret til legemiddelet?");
				if (db.finnLegemiddel(legemiddelId) == null) {
					System.out.println(feilmelding);
					break;
				}

				int pasientId = Console.getInt("Hva er id-nummeret til pasienten?");
				if (db.finnPasient(pasientId) == null) {
					System.out.println(feilmelding);
					break;
				}

				String reseptType = Console.getString("Hvilken type resept oensker du aa opprette?");
				try {
					if (reseptType != "p" && reseptType != "presept"){
						int reit = Console.getInt("Hva er reiten for resepten?");
						db.lagResept(utskrivendeLegenavn, legemiddelId, pasientId, reit, reseptType);
					} else {
						db.lagResept(utskrivendeLegenavn, legemiddelId, pasientId, 3, reseptType);
					}
				} catch (TypeNotFoundException e) {
					System.out.println(feilmelding);
				}
				break;
		}
		Console.getString("Trykk Enter for å fortsette.");

	}
	private void reseptMeny(){
		for (Pasient pasient : db.hentPasienter()) {
			if (pasient.hentResepter().stoerrelse() > 0) {
				System.out.println(pasient);
			}
		}
		System.out.println("");
		
		int pasientId = Console.getInt("Bruk tallet til aa velge pasienten du vil bruke reseptene til,\n" +
									"eller skriv -1 for aa gaa tilbake",(db.hentPasienter().stoerrelse()-1),-2);
		if (pasientId == -1) {
			this.brukerValg = MenyValg.INIT;
		}
		else if (pasientId == -2) {
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
		Console.getString("Trykk enter for aa fortsette");
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
			if (printeValg == -1) System.out.println(feilmelding);
		} while (printeValg == -1);		

		String pressEnterString = "\n\nFerdig med aa printe, trykk paa enter \nfor aa gaa tilbake til startmenyen";

		switch (printeValg) {
			case 0:
				return;
			case 1:
				db.printResepterAvType(Narkotisk.class);
				Console.getString(pressEnterString);
				break;
			case 2:
				db.printResepterAvType(Vanedannende.class);
				Console.getString(pressEnterString);
				break;
			case 3:
				db.printPersonMedMisbruk(Vanedannende.class, "lege");
				Console.getString(pressEnterString);
				break;
			case 4:
				db.printPersonMedMisbruk(Narkotisk.class, "pasient");
				Console.getString(pressEnterString);
				break;
		}
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