abstract class Sepeda //contoh superclass
{
    private int kecepatan = 1000;
    public void info()
    {
        System.out.println("Kecepatan =     "+kecepatan);
        System.out.println("Panggil dari subclass "+ this.getClass().getName());
    }
}
//subclass
public class sepedaGunung extends Sepeda
{
    public static void main(String[] args)
    {
        //membuat objek
        sepedaGunung sepedaGunung = new sepedaGunung();
        //memanggil objek
        sepedaGunung.info();
    }
}