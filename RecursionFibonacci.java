import java.util.*;
import java.util.Arrays;

public class RecursionFibonacci
{
    private static final int UNINITIALIZED = -1;

    // naive recursive solution
    public static int fibonacci(int n)
    {
        if (n < 2)
            return n;

        return fibonacci(n - 1) + fibonacci(n -2);
    }

    private static int fibMemo(int n)
    {
        int [] memo = new int[n + 1];
        Arrays.fill(memo, UNINITIALIZED);

        return fibMemo(memo, n);
    }

    public static int fibMemo(int [] memo, int n)
    {
        if (n < 2)
            return n;

        if (memo[n] != UNINITIALIZED)
            return memo[n];

        memo[n] = fibMemo(memo, n - 1) + fibMemo(memo, n - 2);

        return memo[n];

    }

    public static void main(String [] args)
    {
        int n = 5;
        System.out.println("F(" + n + ") = " + fibMemo(n));
    }
}