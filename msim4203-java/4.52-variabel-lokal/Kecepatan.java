public class Kecepatan {

    public void tambahKecepatan() {
        // contoh benar
        int cepat = 0;
        // contoh salah
        //int cepat;
        // yang akan memicu error pada intellisense dan javac
        cepat = cepat + 40;
        System.out.println("Penambahan Kecepatan : " + cepat);
    }

    public static void main(String args[]) {
        Kecepatan kecepatan = new Kecepatan();
        kecepatan.tambahKecepatan();
    }
}