

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			Database db = new Database();
			db.lesFraFil(args[0], false);
			Startmeny hovedmeny = new Startmeny(db);
		}
		else {
			Startmeny hovedmeny = new Startmeny();
		}
	}
}