package oblig4;

class UgyldigListeIndeks extends RuntimeException {
    UgyldigListeIndeks(int indeks){
        super("Ugyldig indeks:" + indeks);
    }
}
