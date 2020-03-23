package oblig4;

class Stabel<T> extends Lenkeliste<T> {
	public void leggPaa(T x) throws UgyldigListeIndeks {
		//amazing!
		leggTil(x);
	}
	public T taAv() {
		//incredible!!!
		return fjern(stoerrelse - 1);
	}
}