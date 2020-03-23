

import java.util.Scanner;

public class Console implements ConsoleInterface {
	public static void main(String[] args) throws Exception {
		// easy testing of functions
		System.out.println(getString("Potet?"));
	}


	/* 
		Integers
		Generic function to get integers from the console easily
	*/
	public static int getInt(int maxvalue, int fallback) {
		// waits for the user to input a number and press enter, and then returns it
		// must be a value between 0 and maxvalue included, or else it will return 
		// fallback value instead
		Scanner scanner = new Scanner(System.in);
		
		if (scanner.hasNextInt()){
			int value = scanner.nextInt();
			return (value <= maxvalue && value >= 0) ? value : fallback;
		} else {
			return fallback;
		}
	}

	public static int getInt() {
		// easiest way to use the function, allows any positive integer
		// up to what java allows, anyway. (2**31 - 1)
		return getInt(Integer.MAX_VALUE, -1);
	}

	public static int getInt(String s) {
		// same as getInt() but with the optional question to user
		System.out.println(s);
		return getInt();
	}

	public static int getInt(String s, int maxvalue, int fallback) {
		// same as getInt(maxvalue, fallback) but with the optional question to user
		System.out.println(s);
		return getInt(maxvalue, fallback);
	}


	/* 
		Doubles:
		These are analogous to getInt, but with a floating value instead.
	*/
	public static double getDouble(double maxvalue, double fallback) {
		Scanner scanner = new Scanner(System.in);
		
		if (scanner.hasNextInt()){
			double value = scanner.nextInt();
			return (value <= maxvalue) ? value : fallback;
		} else {
			return fallback;
		}
	}

	public static double getDouble() {
		return getDouble(Double.MAX_VALUE, -1);
	}

	public static double getDouble(String s) {
		System.out.print(s);
		return getDouble();
	}

	public static double getDouble(String s, double maxvalue, double fallback) {
		System.out.println(s);
		return getDouble(maxvalue, fallback);
	}


	/*
		Characters:
		Gets a single character from the user
	*/

	public static char getChar() {
		// gets the first letter from the string the user inputs after pressing enter
		Scanner scanner = new Scanner(System.in);
		return scanner.next().charAt(0);
	}

	/* 
		Strings
		Gets a string from the user
	*/

	public static String getString() {
		// gets input from user in terminal, like input() in Python3
		return (new Scanner(System.in)).nextLine();
	}

	public static String getString(String s) {
		// same as getString() but with the optional user message
		System.out.println(s);
		return getString();
	}

	/*
		Handy tools
	*/

	public static void clearScreen() { 
		// clears the terminal from any previous text		
		try {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")) {
				Runtime.getRuntime().exec("cls");
			} else {
				System.out.println();
				System.out.print("\033[H\033[2J");  
				System.out.flush();
			}
		} catch (Exception e) {
			String manyLines = new String(new char[20]).replace("\0", "\n");
			System.out.println(manyLines);
		}
	}

	public static void waitForEnter() {
		getString("Trykk <Enter> for aa fortsette.");
		clearScreen();
	}
}
