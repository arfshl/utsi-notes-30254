// Super Class
class Sepeda {
    public void jenisSepeda() {
        System.out.println("Karakteristik Sepeda belum didefinisikan");
    }
}

// Sub Class Sepeda Gunung
class sepedaGunung extends Sepeda {
    // Melakukan override terhadap method jenisSepeda()
    @Override
    public void jenisSepeda() {
        System.out.println("Jenis Sepeda Gunung");
        System.out.println("Jenis Sepeda dengan Ban Kasar");
    }
}

// Sub Class Sepeda Santai
class sepedaSantai extends Sepeda {
    @Override
    public void jenisSepeda() {
        System.out.println("Jenis Sepeda Santai");
        System.out.println("Jenis Sepeda dengan Ban Halus");
    }
}

// Main Class
public class ContohPolimorfisme {
    public static void main(String[] args) {
        Sepeda Jenis;

        sepedaGunung bankasar = new sepedaGunung();
        sepedaSantai banHalus = new sepedaSantai();

        // Jenis mengacu pada objek sepedaGunung
        Jenis = bankasar;
        // Akan memanggil method pada kelas sepedaGunung
        Jenis.jenisSepeda();

        System.out.println("");

        // Jenis mengacu pada objek sepedaSantai
        Jenis = banHalus;
        // Akan memanggil method pada kelas sepedaSantai
        Jenis.jenisSepeda();
    }
}