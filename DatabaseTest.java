import java.io.FileNotFoundException;
class DatabaseTest {
	public static void main(String[] args) throws FileNotFoundException{
		Database db = new Database();
		db.lesFraFil("fil.txt");
	}
}