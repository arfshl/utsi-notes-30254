import java.math.BigInteger;
public class BilanganBulat {

    public static void main(String[] args) {

        // Deklarasi
        byte tipeByte = 50;
        short tipeShort = 0x7e24;
        int tipeInt = 21466666;
        // long tipeLong = 922000000;
        // secara teknis 922000000 masih bisa ditampung di tipe int
        // karena masih dalam range tipe int, namun jika ingin menampung bilangan yang lebih besar dari range tipe int, 
        // maka harus menggunakan tipe long seperti contoh berikut:
        long tipeLong = 9223372036854775807L;
        // 9223372036854775807 sendiri adalah angka terbesar yang bisa ditampung oleh tipe long
        // jika ingin menampung angka yang lebih besar lagi, maka harus menggunakan tipe data BigInteger sebagai contoh:
        BigInteger tipeBigInteger = new BigInteger("9223372036854775808");

        // Cetak
        System.out.println("Bilangan Bulat ");
        System.out.println("--------------------");
        System.out.println("Tipe Byte tipeByte = " + tipeByte);
        System.out.println("Tipe Short tipeShort = " + tipeShort);
        System.out.println("Tipe Int tipeInt = " + tipeInt);
        System.out.println("Tipe Long tipeLong = " + tipeLong);
        System.out.println("Tipe BigInteger tipeBigInteger = " + tipeBigInteger);
    }
}