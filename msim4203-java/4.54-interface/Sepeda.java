public class Sepeda {

    // Variabel instance ini dapat digunakan oleh semua subkelas.
    public String namaSepeda;
    public int hargaSepeda;

    // Variabel namaSepeda dan hargaSepeda diinisialisasikan dalam konstruktor.
    public Sepeda(String NamaSepeda, int HargaSepeda) {
        namaSepeda = NamaSepeda;
        hargaSepeda = HargaSepeda;
    }

    // Method ini menampilkan informasi sepeda.
    public void tampilSepeda() {
        System.out.println("Nama sepeda : " + namaSepeda);
        System.out.println("Harga sepeda : " + hargaSepeda);
    }

    public static void main(String[] args) {
        Sepeda namasepeda = new Sepeda("Sepeda Gunung", 100000);
        namasepeda.tampilSepeda();
    }
}