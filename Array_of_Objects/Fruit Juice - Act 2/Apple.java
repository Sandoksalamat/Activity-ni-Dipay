class Apple extends Fruit {
    public Apple(String name, String baseFlavor) {
        super(name, baseFlavor);
    }

    @Override
    public void describeFruit() {
        System.out.println("this is very apple-y.");
    }

    @Override
    public int getSweetnessLevel() {
        return 5;
    }
    public void infoApple() {
        System.out.println("Fruit Name      : " + name);
        System.out.println("Fruit Flavor    : " + baseFlavor);
        describeFruit();
    }
}