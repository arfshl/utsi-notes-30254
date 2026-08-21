public class hargaSepeda {

    /**
     * Variabel hargaSepeda adalah modifier private dan variabel static
     **/
    private static int hargaSepeda;

    // Variabel jenisSepeda konstan
    public static final String JENISSEPEDA = "Sepeda Gunung";

    public static void main(String args[]) {
        hargaSepeda = 100000;

        System.out.println(JENISSEPEDA + " harganya : Rp. " + hargaSepeda);
    }
}