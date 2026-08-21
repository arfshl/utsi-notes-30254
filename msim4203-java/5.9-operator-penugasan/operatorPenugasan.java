// Operator Penugasan
public class operatorPenugasan {

    public static void main(String[] args) {

        // Deklarasi dan inisialisasi nilai awal
        int a = 20, b = 10, c, d, e = 10, f = 4;

        // Contoh penugasan sederhana
        c = b;
        System.out.println("Nilai c = " + c);

        // Contoh operasi penambahan, pengurangan,
        // perkalian, dan pembagian dengan cara biasa
        a = a + 1;
        b = b - 1;
        e = e * 2;
        f = f / 2;

        System.out.println("Nilai dengan operand BIASA");
        System.out.println("--------------------");
        System.out.println("Nilai a = " + a);
        System.out.println("Nilai b = " + b);
        System.out.println("Nilai e = " + e);
        System.out.println("Nilai f = " + f);
        System.out.println("");

        // Mengembalikan ke nilai awal seperti pada
        // saat inisialisasi nilai awal
        a = a - 1;
        b = b + 1;
        e = e / 2;
        f = f * 2;

        // Menggunakan operator penugasan singkat
        // yang hasilnya sama dengan operasi biasa
        a += 1;
        b -= 1;
        e *= 2;
        f /= 2;

        System.out.println("Nilai dengan operand SINGKAT");
        System.out.println("--------------------");
        System.out.println("Nilai a = " + a);
        System.out.println("Nilai b = " + b);
        System.out.println("Nilai e = " + e);
        System.out.println("Nilai f = " + f);
        System.out.println("");

        // Menggunakan operator Modulus
        f %= 2;

        System.out.println("Modulus");
        System.out.println("--------------------");
        System.out.println("Nilai f = " + f);

    }
}