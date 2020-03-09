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
	public static int getInt() {
		Scanner scanner = new Scanner(System.in);
		
		if (scanner.hasNextInt()){
			return scanner.nextInt();
		} else {
			return -1;
		}
	}

	public static char getChar() {
		Scanner scanner = new Scanner(System.in);
		return scanner.next().charAt(0);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getInt());
	}
}