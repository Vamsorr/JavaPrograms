// Samuel Voor
// UCF ID: 3971335
// NID: sa716864
// Professor Szumlanski
// COP 3503 Spring 2023
// Programming assignment 5, TopoPaths


import java.util.*;
import java.io.*;

public class TopoPaths {

    public static int countTopoPaths(String filename) throws IOException {
        // Read graph from the input file
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(reader.readLine());
        boolean[][] matrix = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ");
            int k = Integer.parseInt(line[0]);
            for (int j = 1; j <= k; j++) {
                int destination = Integer.parseInt(line[j]) - 1;
                matrix[i][destination] = true;
            }
        }
        reader.close();

        // Find topopaths
        int[] visited = new int[n];
        int[] incoming = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                incoming[j] += (matrix[i][j] ? 1 : 0);
            }
        }

        return countTopoPathsUtil(matrix, visited, incoming, 0);
    }

    private static int countTopoPathsUtil(boolean[][] matrix, int[] visited, int[] incoming, int current) {
        if (current == matrix.length) {
            return 1;
        }

        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (!matrix[current][i] || visited[i] == 1 || incoming[i] != 0) {
                continue;
            }

            visited[i] = 1;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]) {
                    incoming[j]--;
                }
            }

            count += countTopoPathsUtil(matrix, visited, incoming, i);

            visited[i] = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]) {
                    incoming[j]++;
                }
            }
        }

        return count;
    }

    public static double difficultyRating() {
        return 4.0;
    }

    public static double hoursSpent() {
        return 4.0;
    }

    public static void main(String[] args) throws IOException {
        String filename = "g1.txt";
        System.out.println("Topopaths in " + filename + ": " + countTopoPaths(filename));
    }
}



