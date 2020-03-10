import java.io.FileNotFoundException;
import java.util.HashMap;

class DatabaseTest {
	public static void main(String[] args) throws FileNotFoundException {
		Database db = new Database();
		db.lesFraFil("fil.txt");
<<<<<<< HEAD
		//db.printDatabase();
=======

		db.printAntallType(Narkotisk.class);
		db.printLegeType(Vanedannende.class);
>>>>>>> dca2a8a0ea7a56a738952935c6dd00faac4aff03
	}
}
