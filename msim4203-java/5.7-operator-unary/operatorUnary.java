// Operator Unary
public class operatorUnary {

    public static void main(String[] args) {

        int a = 10, b = 5, c, d = 15, e = 12;
        boolean kondisi = true;

        // Operator pre-increment
        // a = a + 1 dan kemudian c = a;
        c = ++a;
        System.out.println("Nilai c (++a) = " + c);

        // Operator post-increment
        // c = b kemudian b = b + 1
        c = b++;
        System.out.println("Nilai c (b++) = " + c);

        // Operator pre-decrement
        // d = d - 1 kemudian c = d
        c = --d;
        System.out.println("Nilai c (--d) = " + c);

        // Operator post-decrement
        // c = e kemudian e = e - 1
        c = e--;
        System.out.println("Nilai c (e--) = " + c);

        // Membalik kondisi (Logical NOT)
        System.out.println("Nilai !kondisi = " + !kondisi);

    }
}