import java.util.Scanner;

public class GeometryAnalyzer {
    private static final int MAX_CIRCLES = 5;
    private static Circle[] circles = new Circle[MAX_CIRCLES];
    private static int recordCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String choice;

        do {
            showMenu();
            System.out.print("Enter choice: ");
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    addCircle(input);
                    break;
                case "2":
                    printCircleCenters(circles);
                    break;
                case "3":
                    generateFullReport();
                    break;
                case "4":
                    runNullSlotAudit(circles);
                    break;
                case "5": searchByIndex(input); 
                    break;
                case "6": System.out.println("Exiting..."); 
                    break;
                default: System.out.println("Invalid choice.");
            }
        } while (!choice.equals("6"));
    }

    public static void showMenu() {
        System.out.println("\n--- Geometry Analyzer Menu ---");
        System.out.println("1. Add Circle Record");
        System.out.println("2. Print All Centers");
        System.out.println("3. View Full Geometry Report");
        System.out.println("4. View Null-Slot Audit");
        System.out.println("5. Search by Index");
        System.out.println("6. Exit");
    }

    public static void addCircle(Scanner input) {
        if (recordCount >= MAX_CIRCLES) {
            System.out.println("Error: Array is full!");
            return;
        }

        System.out.print("Enter x-center: ");
        String sx = input.nextLine();
        System.out.print("Enter y-center: ");
        String sy = input.nextLine();
        System.out.print("Enter radius: ");
        String sr = input.nextLine();

        try {
            double x = Double.valueOf(sx);
            double y = Double.valueOf(sy);
            double r = Double.valueOf(sr);

            if (r <= 0) {
                System.out.println("Error: Radius must be greater than 0.");
            } else {
                circles[recordCount] = new Circle(x, y, r);
                recordCount++;
                System.out.println("Record added at index " + (recordCount - 1));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numeric strings.");
        }
    }

    public static void printCircleCenters(Circle[] circleArray) {
        System.out.println("\n--- Center List ---");
        for (int i = 0; i < circleArray.length; i++) {
            if (circleArray[i] != null) {
                System.out.println("Index " + i + ": (" + circleArray[i].getXCenter() + ", " + circleArray[i].getYCenter() + ")");
            } else {
                System.out.println("Index " + i + ": [Empty Slot]");
            }
        }
    }

    public static void generateFullReport() {
        System.out.println("\n--- Full Geometry Report ---");
        for (int i = 0; i < circles.length; i++) {
            if (circles[i] != null) {
                System.out.printf("Index %d | Area: %.2f | Diameter: %.2f\n", 
                                  i, circles[i].getArea(), circles[i].getDiameter());
            }
        }
    }

    public static void runNullSlotAudit(Circle[] array) {
        System.out.println("\n--- Null-Slot Audit ---");
        for (int i = 0; i < array.length; i++) {
            String status = (array[i] == null) ? "NULL (Empty)" : "FILLED";
            System.out.println("Slot " + i + ": " + status);
        }
    }

    public static boolean findValidIndex(Object[] array, int index) {
        if (index < 0 || index >= array.length) {
            System.out.println("Error: Index " + index + " is out of bounds.");
            System.out.println("Valid range is 0 to " + (array.length - 1));
            return false;
        }

        if (array[index] == null) {
            System.out.println("Error: Slot " + index + " is empty (null).");
            return false;
        }
        
        return true;
    }

    public static void searchByIndex(Scanner input) {
        System.out.print("Enter index to search: ");
        try {
            int idx = Integer.parseInt(input.nextLine());
            
            if (findValidIndex(circles, idx)) {
                Circle c = circles[idx];
                System.out.println("Found Circle at " + idx + ": Radius=" + c.getRadius());
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a whole number for the index.");
        }
    }

} 