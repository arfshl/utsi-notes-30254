public class TipeBoolean {

    public static void main(String[] args) {

        // Deklarasi
        boolean booleanKondisi, booleanJumlah, booleanStatus;
        int nilai1, nilai2;

        // Isi variabel nilai1 dan nilai2
        nilai1 = 7;
        nilai2 = 10;

        booleanKondisi = nilai1 > nilai2;
        // anda juga dapat memnggunakan operator perbandingan lain seperti:
        // > artinya lebih besar dari
        // < artinya lebih kecil dari
        // == artinya sama dengan
        // != artinya tidak sama dengan
        // anda juga dapat menggabungkan operasi penjumlahan juga seperti:
        booleanJumlah = 10 + 2 == 13;
        // berlaku juga untuk operasi pengurangan (-), perkalian (*), pembagian (/), dan modulus (%)
        booleanStatus = true;

        // Cetak
        System.out.println("Tipe Data Boolean");
        System.out.println("--------------------");
        System.out.println("Nilai BooleanKondisi 7 > 10 = " + booleanKondisi);
        System.out.println("Nilai BooleanJumlah 10 + 2 == 13 = " + booleanJumlah);
        System.out.println("Nilai booleanStatus = " + booleanStatus);

    }
}