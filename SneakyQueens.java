/*
Samuel Voor
3971335
COP 3503 Spring 2023
Dr. Szumlanski
Assignment 1
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SneakyQueens 
{
    public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize) 
    {
        // Create sets to keep track of occupied rows, columns and diagonals
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diag1 = new HashSet<>();
        Set<Integer> diag2 = new HashSet<>();
        
        // Iterate through the list of coordinate strings
        // Use a regular expression to extract the column and row values from the string
        for (String coordinates : coordinateStrings) 
        {
            // grabbing input from the coordinates string that are lowercase letters followed by numbers, once found it stores the
            // matched group into matcher object so it they can be accessed in matcher.group1 and matcher.group2
            // which gets the row and column values 
            Matcher matcher = Pattern.compile("([a-z]+)(\\d+)").matcher(coordinates);
            if (matcher.find()) 
            {
                // Convert the column string to a number
                int col = convertColumnStringToNumber(matcher.group(1)) - 1;
                int row = Integer.parseInt(matcher.group(2)) - 1;

                // Check if the row, column, or any of the diagonals that the queen is on, have already been occupied by another queen.
                if (rows.contains(row) || cols.contains(col) || diag1.contains(row - col) || diag2.contains(row + col)) 
                {
                    return false;
                }

                    // if not occupied, add the row, column, and diagonals to the respective sets to mark them as occupied.
                    rows.add(row);
                    cols.add(col);
                    diag1.add(row - col);
                    diag2.add(row + col);
            }
        }

        // If all queens are safe, return true
        return true;
    }
    // Helper method to convert column string to number
    // Iterate through the string an d for each character, 
    // multiply the current number by 26 and add the numerical value of the character. a = 1, b = 2...etc
    private static int convertColumnStringToNumber(String colString) 
        {
            int result = 0;
            for (int i = 0; i < colString.length(); i++) 
                {
                result = result * 26 + (colString.charAt(i) - 'a' + 1);
                }
            return result;
        }
                    


    // forgot how to program in java, watched a 10 hour youtube tutorial and did dozens of practice problems before starting.
    // rip me, ill get better
   public static double difficultyRating()
   {
    return 4.5;
   }

   // took me a full work week to finish this, although i spent most of that time relearning java.
   public static double hoursSpent()
   {
    return 40.0;
   }


}


