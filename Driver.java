import java.io.FileNotFoundException;

public class Driver {
    public static void main(String[] args) {
        JobAssignmentFinder jf = new JobAssignmentFinder();
        String[] files = {
            "data0.txt", "data1.txt", "data2.txt", "data3.txt", "data4.txt", "data5.txt"
        };
        try {
            for (String f : files) {
                jf.readDataFile(f);
                System.out.println("Size of the benefit matrix: " + jf.getInputSize());
                System.out.println(jf.benefitMatrixToString());
                System.out.println("Maximum assignment permutation: " + jf.getMaxAssignment());
                System.out.println("Maximum assignment value: " + jf.getMaxAssignmentTotalValue());
                System.out.println("\n");
            }
        } catch (FileNotFoundException e) {
        }
    }
}