import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		Database db = new Database();
		try {
			db.lesFraFil("storfil.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Filen ble ikke funnet");
		}
		Startmeny hovedmeny = new Startmeny(db);
	}
}
