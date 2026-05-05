import java.util.Scanner;

public class EnrollmentAuditor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        StudentRecord[] records = null;
        String[] rawIds = null, rawExamScores = null, studentNames = null;
        double[][] quizScores = null;
        String[] conversionStatus = null;
        int size = 0;

        int choice;
        do {
            System.out.println("\n===== ENROLLMENT OBJECT ARRAY AUDITOR =====");
            System.out.println("1. Create record array");
            System.out.println("2. Encode raw student data");
            System.out.println("3. Convert and create object");
            System.out.println("4. Encode quiz scores");
            System.out.println("5. Compute averages");
            System.out.println("6. Search by index or ID");
            System.out.println("7. View reports");
            System.out.println("8. Reset one slot");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter size: ");
                    size = sc.nextInt();
                    if (size > 0) {
                        records = new StudentRecord[size];
                        rawIds = new String[size];
                        rawExamScores = new String[size];
                        studentNames = new String[size];
                        conversionStatus = new String[size];
                        quizScores = new double[size][3]; 
                        for(int i=0; i<size; i++) conversionStatus[i] = "Empty Slot";
                        System.out.println("Arrays initialized.");
                    }
                    break;

                case 2:
                    if (records == null) break;
                    for (int i = 0; i < size; i++) {
                        System.out.println("\nIndex [" + i + "]");
                        System.out.print("Raw ID: "); rawIds[i] = sc.nextLine();
                        System.out.print("Name: "); studentNames[i] = sc.nextLine();
                        System.out.print("Raw Exam: "); rawExamScores[i] = sc.nextLine();
                    }
                    break;

                case 3:
                    if (rawIds == null) break;
                    for (int i = 0; i < size; i++) {
                        try {
                            int id = Integer.parseInt(rawIds[i]);
                            double exam = Double.parseDouble(rawExamScores[i]);
                            if (exam < 0 || exam > 100) throw new Exception();
                            records[i] = new StudentRecord(id, studentNames[i], exam);
                            conversionStatus[i] = "Valid";
                        } catch (Exception e) {
                            records[i] = null;
                            conversionStatus[i] = "Invalid Input";
                        }
                    }
                    System.out.println("Conversion done.");
                    break;

                case 4:
                    if (records == null) break;
                    for (int i = 0; i < size; i++) {
                        if (records[i] != null) {
                            System.out.println("Quizzes for " + records[i].studentName);
                            for (int j = 0; j < 3; j++) {
                                System.out.print(" Q" + (j+1) + ": ");
                                quizScores[i][j] = sc.nextDouble();
                            }
                        }
                    }
                    break;

                case 5:
                    if (records == null) break;
                    for (int i = 0; i < size; i++) {
                        if (records[i] != null) records[i].computeAverage(quizScores[i]);
                    }
                    break;

                case 7:
                    if (records == null) break;
                    System.out.println("\n--- Audit Report ---");
                    for (int i = 0; i < size; i++) 
                        System.out.println("["+i+"] " + conversionStatus[i]);

                    System.out.println("\n--- Student List ---");
                    for (int i = 0; i < size; i++) {
                        if (records[i] != null) System.out.println(records[i].printSummary());
                    }
                    break;

                case 8:
                    System.out.print("Index to reset: ");
                    int rIndex = sc.nextInt();
                    if (rIndex >= 0 && rIndex < size) {
                        records[rIndex] = null;
                        conversionStatus[rIndex] = "Empty Slot";
                    }
                    break;
            }
        } while (choice != 9);
    }
}