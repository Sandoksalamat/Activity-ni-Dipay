class Apple extends Fruit {
    public Apple(String name, String baseFlavor) {
        super(name, baseFlavor);
    }

    @Override
    public void describeFruit() {
        System.out.println("this is very apple-y.");
    }

    public void infoApple() {
        System.out.println("Fruit Name      : " + name);
        System.out.println("Fruit Flavor    : " + baseFlavor);
        describeFruit();
    }
}