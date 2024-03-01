public class ArraySum 
{

    // returns sum of elements in array up to a certain index
    public static int arraySum(int [] arr, int index)
    {
        if (arr.length == 0 || arr == null)
            return 0;
        if (index == 0)
            return arr[0];
        
        return arr[index] + arraySum(arr, index - 1);
    }    
    
    public static void main (String [] args)
    {
        int [] arr = {1, 2, 4, 8, 11};
        System.out.println(arraySum(arr, 3));
    }
}
