
public interface ConsoleInterface {
	public static void main(String[] args) throws Exception;

	public static int getInt(int maxvalue, int fallback);
	public static int getInt();
	public static int getInt(String s);
	public static int getInt(String s, int maxvalue, int fallback);

	public static double getDouble(double maxvalue, double fallback);
	public static double getDouble();
	public static double getDouble(String s);
	public static double getDouble(String s, double maxvalue, double fallback);

	public static char getChar();

	public static String getString();
	public static String getString(String s);

	public static void clearScreen(); 
	public static void waitForEnter();
}