import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * a client program Permutation.java that takes a command-line integer k;
 * reads in a sequence of N strings from standard input
 * and prints out exactly k of them, uniformly at random.
 * @author Ravishankar
 */


public class Permutation {
    public static void main(String[] args) {
        // k is the number of random values to be printed
        int k = Integer.parseInt(args[0]);

        // Store the N strings in a randomized queue
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String s = null;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            q.enqueue(s);
        }
        // Get K items from the queue
        for (int i = 1; i <= k; i++)
            StdOut.println(q.dequeue());
    }
}
