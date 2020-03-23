package oblig4;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Database db = new Database();
		if (args.length > 0) db.lesFraFil(args[0]);
		Startmeny hovedmeny = new Startmeny(db);
	}
}
