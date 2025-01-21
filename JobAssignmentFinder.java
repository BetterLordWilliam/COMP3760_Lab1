import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class JobAssignmentFinder {
    private int[][] benefitMatrix;
    private int benefitMatrixSize;
    private ArrayList<Integer> jobAssignment;
    
    /**
     * Recursive decrease-and-conquer algorithm to generate a list of all
     * permutations of the numbers 0..N-1. This follows the "decrease by 1" pattern
     * of decrease and conquer algorithms.
     * 
     * This method returns an ArrayList of ArrayLists. One permutation is an
     * ArrayList containing 0,1,2,...,N-1 in some order. The final result is an
     * ArrayList containing N! of those permutations.
     * 
     * @param N
     * @return
     */
    private ArrayList<ArrayList<Integer>> getPermutations(int N) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();

        /**
         * This isn't a "base case", it's a "null case". This function does not call
         * itself with an argument of zero, but we can't prevent another caller from
         * doing so. It's a weird result, though. The list of permutations has one
         * permutation, but the one permutation is empty (0 elements).
         */
        if (N == 0) {
            ArrayList<Integer> emptyList = new ArrayList<Integer>();
            results.add(emptyList);
        } else if (N == 1) {
            /**
             * Now THIS is the base case. Create an ArrayList with a single integer, and add
             * it to the results list.
             */
            ArrayList<Integer> singleton = new ArrayList<Integer>();
            singleton.add(0);
            results.add(singleton);
        } else {
            /**
             * And: the main part. First a recursive call (this is a decrease and conquer
             * algorithm) to get all the permutations of length N-1.
             */
            ArrayList<ArrayList<Integer>> smallList = getPermutations(N - 1);
            /**
             * Iterate over the list of smaller permutations and insert the value 'N-1' into
             * every permutation in every possible position, adding each new permutation to 
             * the big list of permutations.
             */
            for (ArrayList<Integer> perm : smallList) {
                /**
                 * Add 'N-1' -- the biggest number in the new permutation -- at each of the
                 * positions from 0..N-1.
                 */
                for (int i = 0; i < perm.size(); i++) {
                    ArrayList<Integer> newPerm = (ArrayList<Integer>) perm.clone();
                    newPerm.add(i, N - 1);
                    results.add(newPerm);
                }
                /**
                 * Add 'N-1' at the end (i.e. at position "size").
                 */
                ArrayList<Integer> newPerm = (ArrayList<Integer>) perm.clone();
                newPerm.add(N - 1);
                results.add(newPerm);
            }
        }

        /**
         * Nothing left to do except:
         */
        return results;
    }

    /**
     * readDataFile:                reads matrix from a data file.
     * 
     * @param dataFile
     * @throws FileNotFoundException
     */
    public void readDataFile(String dataFile) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(dataFile));
        int n = -1;
        // Begin parsing the data file
        try {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Determine the size of the matrix and initialize it
                if (n == -1) {
                    benefitMatrixSize = Integer.parseInt(line);
                    benefitMatrix = new int[benefitMatrixSize][benefitMatrixSize];
                // Parse the row of the matrix
                } else {
                    int m = 0;
                    String[] literals = line.split(" ");
                    for(String l : literals) {
                        benefitMatrix[n][m] = Integer.parseInt(l);
                        m++;
                    }
                }
                n++;
            }
        } catch (Exception e) {
            System.out.println("Error parsing data file: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    /**
     * getInputSize:                returns N the size of the N x N benefit matrix.
     * 
     * @return benefitMatrixSize
     */
    public int getInputSize() {
        return benefitMatrixSize;
    }

    /**
     * getBenefitMatrix:            returns the currently loaded benefit matrtix.
     * 
     * @return benefitMatrix
     */
    public int[][] getBenefitMatrix() {
        return benefitMatrix;
    }

    /**
     * benefitMatrixToString:       returns the benefit matrix as a string.
     * 
     * @return
     */
    public String benefitMatrixToString() {
        String str = "";
        for (int i = 0; i < benefitMatrixSize; i++) {
            String row = "[";
            for (int j = 0; j < benefitMatrixSize; j++) {
                if (j == 0)
                    row += benefitMatrix[i][j];
                else
                    row += ", " + benefitMatrix[i][j];
            }
            row += "]\n";
            str += row;
        }
        return str;
    }

    /**
     * getBenefit:                  Benefit for assigning a person to a job.
     * 
     * @param n     number of a person
     * @param m     number of a job
     * @return
     */
    public int getBenefit(int n, int m) {
        return benefitMatrix[n][m];
    }

    /**
     * getMaxAssignmentTotalValue:  returns the value of a job assignment.
     * 
     * @return
     */
    public int getMaxAssignmentTotalValue() {
        return 0;
    }

    /**
     * getMaxAssignment:            returns the permutation which represents\
     * 
     * the maximum possible job value.
     * 
     * @return
     */
    public ArrayList<Integer> getMaxAssignment() {
        return null;
    }

    public static void main(String[] args) {
        JobAssignmentFinder jf = new JobAssignmentFinder();
        try {
            jf.readDataFile("data0.txt");
            System.out.println(jf.getInputSize());
            System.out.println(jf.benefitMatrixToString());
            // jf.readDataFile("data1.txt");
            // System.out.println(jf.getInputSize());
            // System.out.println(jf.benefitMatrixToString());
            // jf.readDataFile("data2.txt");
            // System.out.println(jf.getInputSize());
            // System.out.println(jf.benefitMatrixToString());
            // jf.readDataFile("data3.txt");
            // System.out.println(jf.getInputSize());
            // System.out.println(jf.benefitMatrixToString());
            // jf.readDataFile("data4.txt");
            // System.out.println(jf.getInputSize());
            // System.out.println(jf.benefitMatrixToString());
            // jf.readDataFile("data5.txt");
            // System.out.println(jf.getInputSize());
            // System.out.println(jf.benefitMatrixToString());
        } catch (FileNotFoundException e) {
        }
    }
}
