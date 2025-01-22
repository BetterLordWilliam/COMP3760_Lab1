import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// Will Otterbein
// A01372608
// Set D

/**
 * JobAssignmentFinder: Computes maximum job assignment value from a job benefit matrix.
 * 
 * Brute force approach, generates all possible job assignment permutations and then calculates which one
 * of the permutations yields the maximum value.
 * 
 * @author Will Otterbein, A01372608, Set D
 * @version 2025-1
 */
public class JobAssignmentFinder {
    private int[][] benefitMatrix;
    private int benefitMatrixSize;
    private ArrayList<ArrayList<Integer>> jobAssignmentPerms;
    // private ArrayList<Integer> maxJobAssignmentPermutation = null;
    // private Integer maxJobAsssignmentValue = null;

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
     * readDataFile:                Reads a matrix from a data file.
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
                // Parse the rows of the matrix
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
            // Calculate permutations
            jobAssignmentPerms = getPermutations(benefitMatrixSize);
            // maxJobAssignmentPermutation = null;
            // maxJobAsssignmentValue = null;
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

    // Please ignore this, I kept this here for my own testing purposes
    // /**
    //  * determineResult:             determines the highest value permutation and value simultaneously.
    //  */
    // private void determineResult() {
    //     int maxVal = 0;
    //     ArrayList<Integer> solPerm = new ArrayList<>();
    //     for (ArrayList<Integer> permutation : jobAssignmentPerms) {
    //         int temp = 0;
    //         for (int i = 0; i < permutation.size(); i++) {
    //             temp += benefitMatrix[i][permutation.get(i)];
    //         }
    //         if (temp > maxVal) {
    //             maxVal = temp;
    //             solPerm = permutation;
    //         }

    //     }
    //     maxJobAsssignmentValue = maxVal;
    //     maxJobAssignmentPermutation = solPerm;
    // }

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
     * getBenefit:                  Benefit for assigning a person n to a job m.
     * 
     * @param n     number of a person
     * @param m     number of a job
     * @return
     */
    public int getBenefit(int n, int m) {
        return benefitMatrix[n][m];
    }

    /**
     * getMaxAssignmentTotalValue:  returns the max value from all the possible job assignments.
     * 
     * @return
     */
    public int getMaxAssignmentTotalValue() {
        // if (maxJobAsssignmentValue == null || maxJobAssignmentPermutation == null)
        //     determineResult();
        // return maxJobAsssignmentValue;
        int maxVal = 0;
        // ArrayList<Integer> solPerm = new ArrayList<>();
        for (ArrayList<Integer> permutation : jobAssignmentPerms) {
            int temp = 0;
            for (int i = 0; i < permutation.size(); i++) {
                temp += benefitMatrix[i][permutation.get(i)];
            }
            if (temp > maxVal) {
                maxVal = temp;
                // solPerm = permutation;
            }
        }
        return maxVal;
    }

    /**
     * getMaxAssignment:            returns the permutation which represents the maximum possible job value.
     * 
     * the maximum possible job value.
     * 
     * @return
     */
    public ArrayList<Integer> getMaxAssignment() {
        // if (maxJobAsssignmentValue == null || maxJobAssignmentPermutation == null)
        //     determineResult();
        // return maxJobAssignmentPermutation;
        int maxVal = 0;
        ArrayList<Integer> solPerm = new ArrayList<>();
        for (ArrayList<Integer> permutation : jobAssignmentPerms) {
            int temp = 0;
            for (int i = 0; i < permutation.size(); i++) {
                temp += benefitMatrix[i][permutation.get(i)];
            }
            if (temp > maxVal) {
                maxVal = temp;
                solPerm = permutation;
            }
        }
        return solPerm;
    }
}