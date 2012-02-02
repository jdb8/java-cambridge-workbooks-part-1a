package uk.ac.cam.jdb75.tick3;

public class FibonacciCache {

    public static void main(String[] args) {
        store();
        System.out.println(get(0));
        System.out.println(get(1));
        System.out.println(get(5));
        System.out.println(get(-1));
        System.out.println(get(19));
        System.out.println(get(21));      
    }

    //TODO: Test your program with values other than 20 as given here
    public static long[] fib = new long[20];

    public static void store() {
        //TODO: using a for loop, fill "fib" with the Fibonacci numbers 
        //      e.g. if length of "fib" is zero, store nothing; and
        //           if length is six, store 1,1,2,3,5,8 in "fib"
        for (int i = 0; i < fib.length; i++) {
            fib[i] = (i == 0 || i == 1) ? 1L : fib[i-1] + fib[i-2];
        }
    }

    public static void reset() {
        //TODO: using a for loop, set all the elements of fib to zero
        for (int i = 0; i < fib.length; i++) {
            fib[i] = 0L;
        }
    }
 
    public static long get(int i) {
        //TODO: return the value of the element in fib found at index i
        //      e.g. "get(3)" should return the fourth element of fib
        //
        //Note: your code should check that i is within the bounds of fib
        //      and if it is outside this range return the literal "-1L"
        return (i >= fib.length || i < 0) ? -1L : fib[i];
    }
}