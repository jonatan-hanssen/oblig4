public class Test {
	public static void main(String[] args) {
		Pasient p = new Pasient("fdf", "fhu32i");

		//Legemidler
		Narkotisk nark0 = new Narkotisk("straight up heroin", 100, 12,3);//boer gi id0
		Vanedannende vane0 = new Vanedannende("viagra", 123.1234, 123,12);//id1
		Vanlig van0 = new Vanlig("flourtabletter", 312, 312);//id2
		Narkotisk nark1 = new Narkotisk("memes", 1000, 12,3);//id3
		Vanlig van1 = new Vanlig("oppladede krystaller", 107, 0);//id4

		//Leger
		Lege lege0 = new Lege("Dr. Skronkus");
		Spesialist spes0 = new Spesialist("Dr. Lonkus", 100110);
		Lege lege1 = new Lege("Dr. Rupert Sr.");
		Spesialist spes1 = new Spesialist("Dr. Rupert Jr.", 123);


		//Resepter
		Resept[] reseptArray = new Resept[5];

		reseptArray[0] = new Hvit(nark0, lege0, p, 3);
		reseptArray[1] = new Blaa(nark1, lege1, p, 4);
		reseptArray[2] = new PResept(vane0, spes0, p);
		reseptArray[3] = new Militaer(van0, spes1, p, 3);
		reseptArray[4] = new PResept(van1, lege0, p);

		for (Resept resept : reseptArray) {
			System.out.println(resept.farge());
			resept.bruk();
			System.out.println(resept);
		}
	}

}
