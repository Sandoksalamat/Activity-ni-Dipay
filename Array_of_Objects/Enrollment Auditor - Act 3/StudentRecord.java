public class StudentRecord {
    int studentId;
    String studentName;
    double examScore;
    double average;

    public StudentRecord(int id, String name, double score) {
        this.studentId = id;
        this.studentName = name;
        this.examScore = score;
    }

    public void computeAverage(double[] quizzes) {
        double totalQuizzes = 0;
        int count = 0;
        for (double q : quizzes) {
            totalQuizzes += q;
            count++;
        }
        double quizAvg = (count > 0) ? totalQuizzes / count : 0;
        this.average = (quizAvg + examScore) / 2;
    }

    public String getRemark() {
        if (average >= 75) return "Passed";
        if (average >= 70) return "Conditional";
        return "Failed";
    }

    public String printSummary() {
        return String.format("ID: %-10d | Name: %-15s | Avg: %-7.2f | Remark: %s", 
                studentId, studentName, average, getRemark());
    }
}