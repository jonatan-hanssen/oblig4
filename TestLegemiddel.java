public class TestLegemiddel {
	public static void main(String[] args) {
		Narkotisk nark = new Narkotisk("hey", 100, 12,3);
		Vanedannende vane = new Vanedannende("viagra", 123.1234, 123,12);
		Vanlig van = new Vanlig("gregus", 312, 312);
		
		System.out.println(vane.toString());
		System.out.println("");
		System.out.println(nark.toString());
		System.out.println("");
		System.out.println(van.toString());
	}

}