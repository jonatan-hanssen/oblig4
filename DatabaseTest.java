import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.IOException;

class DatabaseTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Database db = new Database();
		db.lesFraFil("storfil.txt");
		//db.printDatabase();
		//db.printTypeMisbruk(Vanlig.class, "pasient");
		db.skrivTilFil("skrivefil.txt");

	}
}
