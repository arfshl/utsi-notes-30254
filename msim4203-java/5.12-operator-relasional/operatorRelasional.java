// Operator Relasional
public class operatorRelasional {

    public static void main(String[] args) {

        int a = 2, b = 3;
        String x = "Relasional", y = "relasional";

        int ar[] = {1, 5, 3};
        int br[] = {1, 5, 3};

        boolean kondisi = true;

        // Penggunaan operator relasional
        System.out.println("Penggunaan operator relasional");
        System.out.println("--------------------");

        System.out.println("a == a : " + (a == a));
        System.out.println("a == b : " + (a == b));
        System.out.println("a < b  : " + (a < b));
        System.out.println("a <= b : " + (a <= b));
        System.out.println("a > b  : " + (a > b));
        System.out.println("a >= b : " + (a >= b));
        System.out.println("a != b : " + (a != b));

        // Untuk Array tidak bisa dibandingkan
        // menggunakan operator relasional
        // nilai akan menghasilkan false
        System.out.println("ar == br : " + (ar == br));
        System.out.println("kondisi == true : " + (kondisi == true));

    }
}