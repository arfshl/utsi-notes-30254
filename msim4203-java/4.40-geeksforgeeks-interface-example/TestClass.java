// Java program to demonstrate working of interface.
interface In1 {
    // public, static and final
    // can also be declared as:
    //public static final int a = 10;
    final int a = 10;

    // public and abstract
    // can also be declared as:
    //public abstract void display();
    void display();
}

// A class that implements the interface.
class TestClass implements In1 {
    // Implementing the capabilities of interface.
    public void display() {
        System.out.println("Geek");
    }

    // Driver Code
    public static void main(String[] args) {
        TestClass t = new TestClass();
        t.display();
        System.out.println(a);
    }
}
