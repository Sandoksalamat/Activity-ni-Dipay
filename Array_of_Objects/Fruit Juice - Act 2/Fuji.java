class Fuji extends Apple {
    public Fuji(String name, String baseFlavor) {
        super(name, baseFlavor);
    }

    @Override
    public void describeFruit() {
        System.out.println("this is a descendant of Apple. I do not know what it tastes like.");
    }

    public void infoFuji() {
        System.out.println("Fruit Name      : " + name);
        System.out.println("Fruit Flavor    : " + baseFlavor);
        describeFruit();
    }
}