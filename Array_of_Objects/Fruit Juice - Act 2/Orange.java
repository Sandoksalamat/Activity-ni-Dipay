class Orange extends Fruit {
    public Orange(String name, String baseFlavor) {
        super(name, baseFlavor);
    }

    @Override
    public void describeFruit() {
        System.out.println("this is very Orange-y.");
    }

    public void infoOrange() {
        System.out.println("Fruit Name      : " + name);
        System.out.println("Fruit Flavor    : " + baseFlavor);
        describeFruit();
    }
}