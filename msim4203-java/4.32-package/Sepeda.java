import paketSepeda.sepedaGunung;
import paketSepeda.sepedaSantai;

class Sepeda {
    public static void main(String[] args) {
        System.out.println("Jenis Sepeda");
        System.out.println("--------------------");

        // Memanggil kelas dari paket
        sepedaGunung sepeda1 = new sepedaGunung();
        sepeda1.sepedaGunung();

        sepedaSantai sepeda2 = new sepedaSantai();
        sepeda2.sepedaSantai();
    }
}