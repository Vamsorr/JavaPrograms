
// Samuel Voor
// 3971335
// COP 3503 Spring 2023
// Dr. Szumlanski
// Assignment 3


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SneakyKnights 
{
    public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize) 
    {
        // Create sets to keep track of occupied rows and columns
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        
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
    
                // checking a L shape attack pattern for all  8 possible attacks
                // a knight may have
                if (rows.contains(row + 2) && cols.contains(col + 1) || 
                    rows.contains(row + 2) && cols.contains(col - 1) || 
                    rows.contains(row - 2) && cols.contains(col + 1) || 
                    rows.contains(row - 2) && cols.contains(col - 1) ||
                    rows.contains(row + 1) && cols.contains(col + 2) ||
                    rows.contains(row + 1) && cols.contains(col - 2) ||
                    rows.contains(row - 1) && cols.contains(col + 2) ||
                    rows.contains(row - 1) && cols.contains(col - 2))
                {
                    return false;
                }
    
                // if not occupied, add the row and column
                // to the respective sets to mark them as occupied.
                rows.add(row);
                cols.add(col);
            }
        }
    
        // If all knights are safe, return true
        return true;
    }


    // Helper method to convert column string to number
    // Iterate through the string an d for each character, 
    // multiply the current number by 26 and add the numerical value of the character.
    // a = 2, b = 2, z = 26....etc
    private static int convertColumnStringToNumber(String colString) 
        {
            int result = 0;
            for (int i = 0; i < colString.length(); i++) 
                {
                result = result * 26 + (colString.charAt(i) - 'a' + 1);
                }
            return result;
        }

        // just used my exact code from SneakyQueens but checked attack patterns in an L
        // shape instead of vertical, horizontal and diagonal directions
        public static double difficultyRating()
        {
         return 2.0;
        }
        // didn't take long, recycled previous code with slight change
        public static double hoursSpent()
        {
         return 4.0;
        }
    


}