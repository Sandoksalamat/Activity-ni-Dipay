abstract class Fruit {
    String name;
    String baseFlavor;

    public Fruit(String name, String baseFlavor) {  
        this.name = name;
        this.baseFlavor = baseFlavor;
    }

    public void describeFruit() {
        System.out.println("this a fruit.");
    }

    public int getSweetnessLevel() {
        return 1;
    }
}
