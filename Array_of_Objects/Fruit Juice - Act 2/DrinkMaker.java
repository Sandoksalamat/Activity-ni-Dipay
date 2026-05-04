class DrinkMaker {
    static void makeFruitJuice(Fruit f) {

        if (f == null) {
            System.out.println("No Fruit Found.");
            return;
        }
        
        System.out.println("Here's your " + f.name + " Juice that's " + f.baseFlavor);
        f.describeFruit();
    }
}