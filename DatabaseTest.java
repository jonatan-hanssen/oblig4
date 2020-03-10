import java.io.FileNotFoundException;
import java.util.HashMap;

class DatabaseTest {
	public static void main(String[] args) throws FileNotFoundException {
		Database db = new Database();
		db.lesFraFil("fil.txt");
		//db.printDatabase();
		db.printTypeMisbruk(Vanlig.class, "pasient");

	}
}
