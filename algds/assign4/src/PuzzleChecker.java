/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {

    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);

//            StdOut.println("Test 1: Print string representation");
//            StdOut.println(initial.toString());
//            StdOut.println("Test 2: Hamming score: " + initial.hamming());
//            StdOut.println("Test 3: Manhattan score: " + initial.manhattan());
//            StdOut.println("Test 4: Board Dimension: " + initial.dimension());
//            StdOut.println("Test 5: Print twin() representation");
//            StdOut.println(initial.twin());
//            StdOut.println("Twin Hamming Score: " + initial.twin().hamming());
//            StdOut.println("Twin Manhattan Score: " + initial.twin().manhattan());
//            StdOut.println("Test 6: neighbours() call");
//            Iterable<Board> neigbhors = initial.neighbors();
//            for (Board b : neigbhors) {
//                StdOut.println(b);
//            }


            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
            StdOut.println("Solution as follows");
            StdOut.println("-------------------");
            for (Board b:solver.solution()) {
                StdOut.println(b);
            }
        }
    }
}
