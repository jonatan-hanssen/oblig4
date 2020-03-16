import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.IOException;

class DatabaseTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Database db = new Database();
		db.lesFraFil("storfil.txt");
		db.printAlt();
		//db.printTypeMisbruk(Vanlig.class, "pasient");
		db.skrivTilFil(args[0]);
		System.out.println(System.getProperty("os.name"));

	}
}
