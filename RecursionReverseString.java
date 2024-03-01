public class RecursionReverseString {
  public static String reverseString(String input) 
  {
      if (input.length() <= 1) 
      {
          return input;
      }

      return reverseString(input.substring(1)) + input.charAt(0);
  }

  public static void main(String[] args) 
  {
      System.out.println(reverseString("hello")); 
  }
}



