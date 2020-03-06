class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {
	
	@Override
	public void leggTil(T x) {
		//hvis stoerrelsen er null kan vi bare legge inn elementet
		if (stoerrelse == 0) {
			super.leggTil(x);
		}
		//hvis ikke maa vi sjekke
		else {
			//vi vil at det siste elementet skal vaere stoerst
			
			
			int i = 0;
			//vi stopper å iterere dersom det elementet er mindre eller 
			//lik det vi sammenligner med, eller dersom vi er kommet til
			//slutten av lista. 
			
			//et element som er stoerst vil dermed havne sist i lista.
			while (x.compareTo(super.nodeVedPos(i).data) > 0) {
				i++;
				if (i >= stoerrelse) break;
			}
			super.leggTil(i, x);
			
		}
	}
	
	
	@Override
	public void leggTil(int pos, T x) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void sett(int pos, T x) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public T fjern() {
		return fjern(stoerrelse - 1);
	}
}