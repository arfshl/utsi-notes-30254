// import libs
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class GajiCounter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

       // Pada sebuah perusahaan dengan tiga golongan Karyawan, yaitu:
       // Jika Golongan A: Gaji Rp. 5.000.000
       // Jika Golongan B: Gaji Rp. 6.500.000
       // Jika Golongan C: Gaji Rp. 9.500.000

        int indexGaji;
        int[] gaji = {5000000, 6500000, 9500000};

        // Buat Persen Array lembur 30, 32, 34,36,38
        int indexLembur;
        int[] lembur = {30, 32, 34, 36, 38};

        // input golongan karyawan
        System.out.print("Masukkan Golongan (A/B/C): ");
        String golongan = input.next().toUpperCase();

        // Gaji setiap golongan disimpan pada Array Gaji,
        // Jika di Input Goloangan “A” maka akan mengambil Gaji pada Array 0
        // Jika di Input Goloangan “B” maka akan mengambil Gaji pada Array 1
        // Jika di Input Goloangan “C” maka akan mengambil Gaji pada Array 2

        if (golongan.equals("A")) {
            indexGaji = 0;
        } else if (golongan.equals("B")) {
            indexGaji = 1;
        } else if (golongan.equals("C")) {
            indexGaji = 2;
        } else {
            System.out.println("Golongan tidak valid!");
            input.close();
            return;
        }

        // ambil value gaji pokok dari array gaji
        int gajiPokok = gaji[indexGaji];

        // input jumlah jam lembur
        System.out.print("Masukkan jumlah jam lembur: ");
        int jamLembur = input.nextInt();

        // Jika Karyawan lembur 1 Jam maka gaji lemburnya 30% (array lembur) dari Gaji Pokok dari Array Gaji sesuai Golongan
        // Jika Karyawan lembur 2 Jam maka gaji lemburnya 32% (array lembur) dari Gaji Pokok dari Array Gaji sesuai Golongan
        // Jika Karyawan lembur 3 Jam maka gaji lemburnya 34% (array lembur) dari Gaji Pokok dari Array Gaji sesuai Golongan
        // Jika Karyawan lembur 4 Jam maka gaji lemburnya 36% (array lembur) dari Gaji Pokok dari Array Gaji sesuai Golongan
        // Jika Karyawan lembur >= 5 Jam maka gaji lemburnya 38% (array lembur) dari Gaji Pokok dari Array Gaji sesuai Golongan
        
        if (jamLembur == 1) {
            indexLembur = 0;
        } else if (jamLembur == 2) {
            indexLembur = 1;
        } else if (jamLembur == 3) {
            indexLembur = 2;
        } else if (jamLembur == 4) {
            indexLembur = 3;
        } else if (jamLembur >= 5) {
            indexLembur = 4;
        } else {
            System.out.println("Jam lembur tidak valid!");
            input.close();
            return;
        }

        // hitung gaji lembur
        int gajiLembur = gajiPokok * lembur[indexLembur] / 100;

        // hitung total gaji
        int totalGaji = gajiPokok + gajiLembur;

        // jadikan format rupiah dengan locale, fromm gajiPokok to rupiah.format(gajiPokok)
        NumberFormat rupiah = NumberFormat.getInstance(Locale.of("id", "ID"));

        // output
        System.out.println("Golongan       : " + golongan);
        System.out.println("Gaji Pokok     : Rp" + rupiah.format(gajiPokok));
        System.out.println("Jam Lembur     : " + jamLembur);
        System.out.println("Persentase     : " + lembur[indexLembur] + "%");
        System.out.println("Gaji Lembur    : Rp" + rupiah.format(gajiLembur));
        System.out.println("Total Gaji     : Rp" + rupiah.format(totalGaji));

        input.close();
    }
}
