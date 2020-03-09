public class Startmeny {
	static String velkommenStr = "Velkommen til Legesystemet!\n";
	static String tutorial = "Velg ett av de mulige menyelementene med tallet.";
	static String menyEntriesStr = "1. Jeg vil printe.\n" + 
							"2. Jeg vil opprette.\n" +
							"3. Jeg vil bruke en resept.\n" +
							"4. Jeg vil se statistikk.\n" +
							"5. Jeg vil skrive til fil.\n" +
							"6. Jeg vil se dette paa nytt.\n" +
							"0. Jeg vil doe.\n";
	static String exitStr = "Ha en fin dag!";
	static String donaldDuckStr = "Oopsie! Det smakte daarlig.";

	enum MenyValg {
		RAGEQUIT, // 0
		PRINTE, // 1
		OPRETTE, // 2
		BRUKERESEPT, // 3
		STATISTIKK, // 4
		FILSKRIVING, // 5
		INIT // 6
	}
	MenyValg valgNum = MenyValg.INIT;

	public Startmeny() {
		while (valgNum != MenyValg.RAGEQUIT) {
			System.out.print(velkommenStr + menyEntriesStr);

			switch (valgNum) {
				case INIT: case REPEAT
					System.out.println(tutorial);
					break;

				case PRINTE:
					printeMeny();
					break;
				
				case OPRETTE:
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
				
				case RAGEQUIT:
					System.out.print(exitStr);
					break;

				default:
					System.out.println(donaldDuckStr);
				
			}

			int keyvalue = Console.getInt();
			valgNum = MenyValg.values()[keyvalue];
			System.out.println("Du valgte " + keyvalue);
			
		}

	}

	private void printeMeny(){
		System.out.println("Jeg driver med printe.");

	}
	private void opretteMeny(){
		System.out.println("Jeg driver med oprette.");
	}
	private void reseptMeny(){
		System.out.println("Jeg driver med resept.");
	}
	private void statistikkMeny(){
		System.out.println("Jeg driver med statistikk.");
	}
	private void filskrivingMeny(){
		System.out.println("Jeg driver med filskriving.");
	}
}