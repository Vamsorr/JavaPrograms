/*
    Problem: Climbing Stairs
    You are climbing a staircase. It takes n steps to reach the top. 
    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?    
*/
import java.util.*;
import java.util.Arrays;
import java.util.HashMap;

public class Stairs 
{
    public static int numWays(int n)
    {
        if (n <= 0)
            return n;

        if (n == 1)
            return n;
        
        if (n == 2)
            return n;
        
    
        return numWays(n - 1) + numWays(n - 2);
        
    }

    /*
        I want to store the amount of combinations a stairwell of n steps can be stepped up.
        key = n number of steps. Value = possible number of combinations
    */

    public static int ways(int n, Map<Integer, Integer> memo)
{
    if (n <= 0)
        return 0;

    if (n == 1)
        return 1;
    
    if (n == 2)
        return 2;

    if (memo.containsKey(n))
        return memo.get(n);

    int num = ways(n - 1, memo) + ways(n - 2, memo);
    memo.put(n, num);

    return num;
}

            
    
  
    
    

    public static void main(String [] args)
    {
        System.out.println(ways(4, null));
    }
}
