public class LetterFrequency 
{
    /*
        Problem: Given a string, write a recursive function 
        to count the number of occurrences of a specific character 
        in the string.
     */    

     public static int countOccurrences(String input, char target, int index)
     {
        if (input == null || input.length() == 0)
            return 0;

        if(index == input.length())
            return 0;

        if (input.charAt(index) == target)
            return 1 + countOccurrences(input, target, index + 1);
        
        else 
        {
            return countOccurrences(input, target, index + 1);
        }
     }


     public static void main(String [] args)
     {
        System.out.println(countOccurrences("Napoleeeaonneee", 'e', 0));
     }
}


