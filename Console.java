import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.Robot;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.util.Scanner;

public class Console {
	public static String _getch() {
		try {
			BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
			
			Robot enterKey = new Robot();
			TimerTask task = new TimerTask() {
				public void run() {
					enterKey.keyPress(KeyEvent.VK_ENTER);
				}
 			};

 			int timeout = 2000; // ms
 			Timer timer = new Timer();
			timer.schedule(task, timeout);

			int userInputanswer = myReader.read();
			timer.cancel();

			System.out.println(userInputanswer);

			return "";

		} catch (Exception e) {
			System.out.println("Error!");
			return "";
		}
	}
	public static int getInt(int maxvalue, int fallback) {
		Scanner scanner = new Scanner(System.in);
		
		if (scanner.hasNextInt()){
			int value = scanner.nextInt();
			return (value <= maxvalue) ? value : fallback;
		} else {
			return fallback;
		}
	}

	public static int getInt() {
		return getInt(Integer.MAX_VALUE, -1);
	}

	public static int getInt(String s) {
		System.out.println(s);
		return getInt();
	}

	public static int getInt(String s, int maxvalue, int fallback) {
		System.out.println(s);
		return getInt(maxvalue, fallback);
	}

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

	public static char getChar() {
		Scanner scanner = new Scanner(System.in);
		return scanner.next().charAt(0);
	}

	public static String getString() {
		return (new Scanner(System.in)).nextLine();
	}

	public static String getString(String s) {
		System.out.println(s);
		return getString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getString("Potet?"));
	}

	public static void clearScreen() { 
		
		try {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")) {
				Runtime.getRuntime().exec("clear");
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
}
