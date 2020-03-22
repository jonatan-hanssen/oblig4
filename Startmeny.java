import java.io.FileNotFoundException;
import java.io.IOException;

public class Startmeny {
	static boolean consoleLogging = true;

	static String velkommenStr = "Velkommen til Legesystemet!\n";
	static String tutorial = "Velg ett av de mulige menyelementene med tallet.";
	static String menyEntriesStr = "1. Jeg vil printe.\n" + 
							"2. Jeg vil legge til i databasen.\n" +
							"3. Jeg vil bruke en resept.\n" +
							"4. Jeg vil se statistikk.\n" +
							"5. Jeg vil skrive til fil.\n" +
							"6. Jeg vil skrive til databasen fra fil\n" +
							"0. Jeg vil doe.\n";
	static String exitStr = "Ha en fin dag!";
	static String donaldDuckStr = "Oopsie! Det smakte daarlig.";

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

	public Startmeny(Database db) {
		this.db = db;

		Console.clearScreen();

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
				int brukerTall = Console.getInt(6, -1); // 5 as the maxValue, because of MenyValg, -1 as the fallback value
				if (brukerTall == -1) this.brukerValg = MenyValg.FAIL;
				else if (brukerTall == 420) this.brukerValg = MenyValg.INIT; // never reachable atm
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
					System.out.println(donaldDuckStr);
					break;

				case INIT:
					Console.clearScreen();
					System.out.print(velkommenStr + menyEntriesStr);
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
						 "2. Resepter\n" + 
						 "3. Leger\n" + 
						 "4. Legemidler\n" +
						 "5. ALT!!!\n" +
						 "0. Jeg angrer!\n"
		);
		int printeValg = -1;
		do {
			printeValg = Console.getInt(5, -1);
			if (printeValg == -1) System.out.println(donaldDuckStr);
		} while (printeValg == -1);		

		
		/*System.out.println("Hvor detaljert vil du printe? (0-2)");

		int detaljLevel = -1;
		do {
			detaljLevel = Console.getInt(2, -1);
			if (detaljLevel == -1) System.out.println(donaldDuckStr);
		} while (detaljLevel == -1);
		*/
		String pressEnterString = "\n\nFerdig med aa printe, trykk paa enter \nfor aa gaa tilbake til startmenyen";

		switch (printeValg) {
			case 0:
				return;
			case 1:
				db.printPasient();
				Console.getString(pressEnterString);
				break;
			case 2: 
				db.printResept();
				Console.getString(pressEnterString);
				break;
			case 3:
				db.printLege();
				Console.getString(pressEnterString);
				break;
			case 4:
				db.printLegemiddel();
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
						 "2. Resept\n" + 
						 "3. Lege\n" + 
						 "4. Legemiddel\n" +
						 "0. Jeg angrer!\n"
		);
		int opretteValg = -1;
		do {
			opretteValg = Console.getInt(3, -1);
			if (opretteValg == -1) System.out.println(donaldDuckStr);
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
				String legenavn = Console.getString("Hva heter legen som skriver ut resepten");
				int legemiddelId = Console.getInt("Hva er id-nummeret til legemiddelet?");
				int pasientId = Console.getInt("Hva er id-nummeret til pasienten?");
				String type = Console.getString("Hvilken type resept oensker du aa opprette?");
				String reit = Console.getInt("Hva er reiten for resepten?");

				db.lagResept(legenavn, legemiddelId, pasientId, reit, type);

				break;
			case 3:
				String navn = Console.getString("Hva skal legen hete?");
				int kontrollid = Console.getInt("Hva er kontroll-nummeret til legen? (0 hvis ingen)");
				db.lagLege(navn, kontrollid);

				break;
			case 4:
				String navn = Console.getString("Hva heter legemiddelet?");
				double pris = Console.getDouble("Hva koster legemiddelet?");
				double virkestoff = Console.getDouble("Hvor mye virkestoff har legemiddelet?");
				int styrke = Console.getInt("Hvor sterkt er legemiddelet?");
				int type = Console.getString("Av hvilken type er legemiddelet?");
				db.lagLegemiddel(navn, pris, virkestoff, styrke, type);

				break;
		}
		Console.getString("Trykk Enter for å fortsette.");

	}
	private void reseptMeny(){
		db.printPasient();
		System.out.println("Skriv id-en paa pasienten du vil bruke reseptene til");
	}
	private void statistikkMeny(){
		System.out.println("Jeg driver med statistikk.");
	}
	private void filskrivingMeny(){
		String filnavn = Console.getString("Skriv filnavn du vil skrive til, eller 0 for aa gaa tilbake.");
		try {
			if (filnavn != "0") db.skrivTilFil(filnavn);
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
	}
}