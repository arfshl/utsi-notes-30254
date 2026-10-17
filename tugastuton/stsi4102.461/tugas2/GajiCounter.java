// import library untuk mengambil input stdin dari user (Scanner)
// formattting angka (NumberFormat) dan Locale (untuk format mata uang)

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class GajiCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // variabel untuk menyimpan gaji pokok, persentase bonus lembur, dan jam lembur
        long gaji = 0;
        int persen = 0;
        int jam = 0;

        // ambil input golongan dari user dan validasi input
        while (true) {
            System.out.print("MASUKKAN GOLONGAN (A/B/C): ");
            // hapus spasi di awal dan akhir input, ubah ke huruf besar
            String golongan = scanner.nextLine().trim().toUpperCase();

            switch (golongan) {
                case "A":
                    gaji = 5000000;
                    break;
                case "B":
                    gaji = 6500000;
                    break;
                case "C":
                    gaji = 9500000;
                    break;
                default:
                    System.out.println("GOLONGAN TIDAK VALID (A/B/C)!");
                    continue; 
            }
            break; 
        }

        // ambil input jam lembur dari user dan validasi input
        while (true) {
            System.out.print("MASUKKAN JAM LEMBUR: ");
            if (!scanner.hasNextInt()) {
                System.out.println("JAM LEMBUR TIDAK DALAM FORMAT YANG VALID!");
                scanner.next();
                continue;
            }
            
            jam = scanner.nextInt(); 

            switch (jam) {
                case 0:
                    persen = 0;
                    break;
                case 1:
                    persen = 30;
                    break;
                case 2:
                    persen = 32;
                    break;
                case 3:
                    persen = 34;
                    break;
                case 4:
                    persen = 36;
                    break;
                default:
                    if (jam >= 5) {
                        persen = 38;
                    // angka minus dan selain angka invalid
                    } else {
                        System.out.println("JAM LEMBUR TIDAK DALAM FORMAT YANG VALID!");
                        continue; 
                    }
                    break;
            }
            break; 
        }
        
        // hitung bonus lembur dan total penghasilan
        long lembur = (gaji * persen) / 100;
        long total = gaji + lembur;

        // format output ke dalam format mata uang Rupiah
        NumberFormat rupiah = NumberFormat.getInstance(Locale.of("id", "ID"));

        // output hasil perhitungan
        System.out.println("Gaji Pokok: Rp " + rupiah.format(gaji));
        System.out.println("Bonus Lembur (" + persen + "%): Rp " + rupiah.format(lembur));
        System.out.println("Total Penghasilan: Rp " + rupiah.format(total));
        
        // tutup scanner untuk mencegah memory leak
        scanner.close();
    }
}