public class RunLikeHell 
{
    public static int maxGain(int[] blocks) 
    {   
        // if the array is null or empty, there is no knowledge gain, return 0;
        if (blocks == null || blocks.length == 0) 
        {
            return 0;
        }

        // memoization action
        int n = blocks.length;
        int[] memo = new int[n + 1];

        // no knowledge gain when there are no blocks and
        // knowledge gain of the first block
        memo[0] = 0;
        memo[1] = blocks[0];

        // 1. The knowledge gain without including the current block (dp[i - 1]).
        // 2. The knowledge gain from two blocks before (dp[i - 2]) 
        // plus the current block's knowledge gain (blocks[i - 1]). These are the max gain options
        for (int i = 2; i <= n; i++) 
        {
            memo[i] = Math.max(memo[i - 1], memo[i - 2] + blocks[i - 1]);
        }

        return memo[n];
    }

    // pretty good critical thinking action
    public static double difficultyRating() 
    {
        return 3.0;
    }

    public static double hoursSpent() 
    {
        return 4.0;
    }
}
