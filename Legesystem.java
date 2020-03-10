import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;

public class Legesystem {
	public static void main(String[] args) throws
	FileNotFoundException, UgyldigListeIndeks, UlovligUtskrift
	{
		legesystem("fil.txt");
	}

	public static void legesystem(String filnavn) throws FileNotFoundException {
		File fil = new File(filnavn);
		Scanner scanner = new Scanner(fil);

		Lenkeliste<Pasient> pasientListe = new Lenkeliste<Pasient>();//nr 0
		Lenkeliste<Legemiddel> legemiddelListe = new Lenkeliste<Legemiddel>();//nr 1
		Lenkeliste<Lege> LegeListe = new Lenkeliste<Lege>();//nr 2
		Lenkeliste<Resept> reseptListe = new Lenkeliste<Resept>();//nr 3

		int listeIndeks = 0;
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String naavaerendeLinje = scanner.nextLine();

			//dersom vi kommer til en # vet vi at vi maa hoppe over linjen
			//og at vi maa bytte til neste liste
			if (naavaerendeLinje.contains("#")) {
				scanner.nextLine();
				listeIndeks++;
			}

			leggTilIListe(naavaerendeLinje, listeIndeks);
		}


	}
	public static void leggTilIListe(String linje, int nummerPaaListe) {
		String[] linjeArray = linje
			.trim() //fjerner whitespace
			.replaceAll(",$","") //fjerner komma uten noen string
			.split(","); //gjoer til array
		switch (nummerPaaListe) {
			case 0:
				pasientListe.leggTil(new Pasient(linjeArray[0],linjeArray[1]));
				break;
			case 1:
				switch (linjeArray[1]):
					case "vanlig":

						break;
					case "narkotisk":

						break;
					case "vanedannende":
						
						break;
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}
}
