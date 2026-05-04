import java.util.Scanner;
    
public class FruitJuice {
    static final Scanner input = new Scanner(System.in);
    static int[] quantities;
    static int recordCount;
    static int storageSize;
    static boolean[] processedFruit;
    static Fruit[] f;
    static int pIndex;
    
    static void countdown(int seconds) {
        System.out.println("*Clanker Noise*");
        for (int i = seconds; i > 0; i--) {
            System.out.println("Time Remaining: " + i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Timer Interrupted");
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\nWelcome to Juice Maker 3000!");
            System.out.println("[1] Preparation     | [2] Add Fruits    | [3] Convert Quantity ");
            System.out.println("[4] Process One     | [5] Process All   | [6] Search Fruit     ");
            System.out.println("[7] View Report     | [8] Exit ");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("== Fruit Preparation ==");

                    do {
                        System.out.print("Enter number of Fruits to store: ");
                        storageSize = input.nextInt();
                        if (storageSize <= 0) {
                            System.out.println("Fruit Storage must be greater than zero.");
                        }
                    } while (storageSize <= 0);

                    f = new Fruit[storageSize];
                    quantities = new int[storageSize];
                    processedFruit = new boolean[storageSize];
                    recordCount = 0;

                    System.out.println("Fruit Storage created with " + storageSize + " slots.");
                    break;

                case 2:
                    System.out.println("== Fruit Adding ==");

                    if (f == null) {
                        System.out.println("Please run Preparation (Option 1) first.");
                        break;
                    }

                    if (recordCount >= f.length) {
                        System.out.println("Fruit Storage is full.");
                        break;
                    }

                    System.out.println("Select Fruit to Add:");
                    System.out.println("[1] Apple  | [2] Orange | [3] Fuji ");
                    System.out.print("Enter choice: ");
                    int fruitChoice = input.nextInt();

                    Fruit newFruit = null;

                    switch (fruitChoice) {
                        case 1:
                            newFruit = new Apple("Apple", "Sweet");
                            break;
                        case 2:
                            newFruit = new Orange("Orange", "Citrus");
                            break;
                        case 3:
                            newFruit = new Fuji("Fuji", "Very Sweet");
                            break;
                        default:
                            System.out.println("Invalid fruit choice.");
                    }

                    if (newFruit != null) {
                        f[recordCount] = newFruit;
                        quantities[recordCount] = 0;
                        recordCount++;
                        System.out.println(newFruit.name + " added at index " + (recordCount - 1) + ".");
                    }
                    break;

                case 3:
                    System.out.println("== Convert Quantity ==");

                    if (f == null || recordCount == 0) {
                        System.out.println("No fruits available.");
                        break;
                    }

                    System.out.print("Enter Index (0 to " + (recordCount - 1) + "): ");
                    int qIndex = input.nextInt();

                    if (qIndex < 0 || qIndex >= recordCount) {
                        System.out.println("Invalid index.");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    String quantityInput = input.next();

                    try {
                        int quantity = Integer.parseInt(quantityInput);
                        if (quantity <= 0) {
                            System.out.println("Quantity must be greater than zero.");
                            break;
                        }
                        quantities[qIndex] = quantity;
                        System.out.println("Quantity " + quantity + " recorded for index " + qIndex + ".");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Whole numbers only.");
                    }
                    break;

                case 4:
                    System.out.println("== Process Fruit (One) ==");

                    if (f == null || recordCount == 0) {
                        System.out.println("No fruits available.");
                        break;
                    }

                    System.out.print("Enter Index (0 to " + (recordCount - 1) + "): ");
                    pIndex = input.nextInt();

                    if (pIndex < 0 || pIndex >= recordCount) {
                        System.out.println("Invalid index.");
                        break;
                    }

                    if (f[pIndex] == null) {
                        System.out.println("No fruit stored at this index.");
                        break;
                    }

                    if (processedFruit[pIndex]) {
                        System.out.println("Fruit at slot " + pIndex + " already processed.");
                        break;
                    } else {
                        countdown(3);
                        DrinkMaker.makeFruitJuice(f[pIndex]);
                        processedFruit[pIndex] = true;
                        break;
                    }

                case 5:
                    System.out.println("== Process Fruit (All) ==");

                    if (f == null || recordCount == 0) {
                        System.out.println("No fruits available.");
                        break;
                    }

                    for (int i = 0; i < recordCount; i++) {
                        if (f[i] == null) {
                            System.out.println("Empty slot at index " + i + ".");
                            continue;
                        }
                        
                        if (processedFruit[i]) {
                            System.out.println("Fruit at slot " + pIndex + " already processed.");
                            continue;
                        } else {
                            countdown(3);
                            DrinkMaker.makeFruitJuice(f[i]);
                            processedFruit[i] = true;
                        }
                    }
                    break;

                case 6:
                    System.out.println("== Search Fruit ==");

                    if (f == null || recordCount == 0) {
                        System.out.println("No fruits available.");
                        break;
                    }

                    System.out.print("Enter Fruit Type: ");
                    String search = input.next();
                    boolean found = false;

                    for (int i = 0; i < recordCount; i++) {
                        if (f[i] == null) continue;

                        if (search.equalsIgnoreCase("Apple") && f[i] instanceof Apple && !(f[i] instanceof Fuji)) {
                            System.out.println("Index " + i + ": " + f[i].name);
                            found = true;
                        } else if (search.equalsIgnoreCase("Orange") && f[i] instanceof Orange) {
                            System.out.println("Index " + i + ": " + f[i].name);
                            found = true;
                        } else if (search.equalsIgnoreCase("Fuji") && f[i] instanceof Fuji) {
                            System.out.println("Index " + i + ": " + f[i].name);
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("No matching fruit found.");
                    }
                    break;

                case 7:
                    System.out.println("== View Report ==");

                    if (f == null || recordCount == 0) {
                        System.out.println("No fruits available.");
                        break;
                    }

                    int processed = 0;
                    int unprocessed = 0;
                    int appleCount = 0;
                    int orangeCount = 0;
                    int fujiCount = 0;

                    for (int i = 0; i < recordCount; i++) {
                        if (f[i] == null) continue;

                        if (quantities[i] > 0) {
                            processed++;
                        } else {
                            unprocessed++;
                        }

                        if (f[i] instanceof Fuji) {
                            fujiCount++;
                        } else if (f[i] instanceof Apple) {
                            appleCount++;
                        } else if (f[i] instanceof Orange) {
                            orangeCount++;
                        }
                    }

                    System.out.println("\n--- SUMMARY REPORT ---");
                    System.out.println("Total Records      : " + recordCount);
                    System.out.println("Processed Orders   : " + processed);
                    System.out.println("Unprocessed Orders : " + unprocessed);
                    System.out.println("\nFruit Type Counts:");
                    System.out.println("Apple      : " + appleCount);
                    System.out.println("Orange     : " + orangeCount);
                    System.out.println("Fuji       : " + fujiCount);
                    break;

                case 8:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1-8 only.");
                    break;
            }
        } while (choice != 8);
    }
}