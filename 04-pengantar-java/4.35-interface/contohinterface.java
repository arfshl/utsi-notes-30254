interface Kendaraan {
    void bergerak();
    void berhenti();
}

class Sepeda implements Kendaraan {

    @Override
    public void bergerak() {
        System.out.println("Sepeda bergerak dengan dikayuh");
    }

    @Override
    public void berhenti() {
        System.out.println("Sepeda berhenti dengan rem");
    }
}

class Mobil implements Kendaraan {

    @Override
    public void bergerak() {
        System.out.println("Mobil bergerak dengan mesin");
    }

    @Override
    public void berhenti() {
        System.out.println("Mobil berhenti dengan rem");
    }
}

public class contohinterface {
    public static void main(String[] args) {

        Kendaraan kendaraan;

        kendaraan = new Sepeda();
        kendaraan.bergerak();
        kendaraan.berhenti();

        System.out.println();

        kendaraan = new Mobil();
        kendaraan.bergerak();
        kendaraan.berhenti();
    }
}