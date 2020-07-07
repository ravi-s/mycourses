import edu.princeton.cs.algs4.Queue;

/**
 * ***********************************************************************
 *  Compilation:  javac Board.java
 *
 * A puzzle board abstraction, typically 8-puzzle problem
 *  @author Ravi S
 *  Date: 24-February-2015
 *
 *
 */

public class Board  {
    private int nDimension; // Store the dimension of the board
    private int blankRow;
    private int blankCol;

    /* Board a logical N by N grid,
     but stored as N^2 one-dimensional array
     */
    private int[] mBlocks;

//    private int _mgrid[][]; // Stores the goal grid

    /**
     *  construct a board from an N-by-N array of blocks
     */
    public Board(int[][] blocks) {
        if (null == blocks) {
            throw new NullPointerException("blocks array is null");
        }
        int size = blocks.length;
        assert (blocks[0].length == size);
        nDimension = size;
        mBlocks = new int[nDimension * nDimension];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
            // Store the blank or 0 i, j index for
            // finding neighbours
            if (blocks[i][j] == 0) {
                blankRow = i;
                blankCol = j;
            }
            mBlocks[nDimension * i + j] = blocks[i][j];
        }


//        // Intialize the goal grid
//        int number = 1; // 0 is blank implicitly
//        _mgrid = new int[N][N];
//
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++)
//              _mgrid[i][j] = number++;


    }

    /**
     * Private constructor for state copy
     * used in neighbors only
     */
    private Board(Board b) {

        nDimension = b.nDimension;
        mBlocks = new int[nDimension * nDimension];

        // Create the board first which is a copy of the
        // board

        System.arraycopy(b.mBlocks, 0, mBlocks, 0, nDimension * nDimension);



        this.blankRow = b.blankRow;
        this.blankCol = b.blankCol;
    }

    // (where blocks[i][j] = block in row i, column j)
    /**
     * board dimension N
     */
    public int dimension() { return nDimension; }

    /**
     *  number of blocks out of place
     */
    public int hamming() {
        int score = 0;
        // if ((int)mBlocks[mBlocks.length - 1] != 0) score++;
        for (int i = 0; i < mBlocks.length; i++) {
            if (i == (mBlocks.length - 1)) break;
            if (mBlocks[i] != (i+1)) score++;
        }

        return score;
    }

    /**
     *  sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int score = 0; // For this board object
        int colIndex, rowIndex;
        int colGoal, rowGoal;

        for (int i = 0; i < mBlocks.length; i++) {
            if (mBlocks[i] == 0) continue; // 0 is blank,skip
            colGoal = (mBlocks[i] - 1) % nDimension;
            colIndex = i % nDimension;

            rowGoal = (mBlocks[i] - 1) / nDimension;
            rowIndex = i / nDimension;
            score += Math.abs(colGoal - colIndex) + Math.abs(rowGoal - rowIndex);
        }
        return score;
    }

//    public int manhattan() {
//        int score = 0;
//
//        for (int i = 0; i < mBlocks.length; i++) {
//            int diff = 0;
//            if ((int) mBlocks[i] == 0) continue; // 0 is blank,skip
////            StdOut.println((int)mBlocks[i]);
//
//            if ((int) mBlocks[i] < (i+1)) { diff = (i+1) - (int) mBlocks[i]; }
//            else diff = (int) mBlocks[i] - (i + 1);
//            if (diff < N) { score += diff; }
//            else {
//
//                while (diff > N) {
//                    score++;
//                    diff -= N;
//                }
//                score++;
//            }
//            // StdOut.println("Score:" + score);
//        }
//        return score;
//
//    }

    /** Name: isGoal
      * Description: is this board the goal board?
      * Returns true if this board is goal board, false otherwise.
      */
    public boolean isGoal() {
        return ((0 == this.hamming()) && (0 == this.manhattan()));

    }
    /** Name: twin
      * Description: A boardthat is obtained by exchanging two adjacent blocks
      * in the same row, either block should not be 0 or blank
      * Returns twin board
      */

    public Board twin() {
        int adj1, adj2;

        boolean found = false;
        Board twinobj;
        twinobj = this.copyBoard();

        // Twinning operation below,
        // adjacent blocks in same row can be swapped
        // as long as one of the them is not 0 or blank
        /* for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((j + 1) == N) continue;

                adj1 = twinobj.mBlocks[N * i + j];
                adj2 = twinobj.mBlocks[N * i + j + 1];

                if (!(0 == adj1 || 0 == adj2)) {
                    found = true;
                    twinobj.mBlocks[N * i + j] = adj2;
                    twinobj.mBlocks[N * i + j + 1] = adj1;
                    break;
                }
                //else {
                    // found 2 adjacent blocks to swap

                //}
            }
            if (found) break;
        }
*/

        int i = 0;
        int row = 0;
        while (i < nDimension) {
//        for (int i = 0; i < N; i++) {
//            StdOut.println("row:" + row);
//            StdOut.println("i:" + i);
            adj1 = twinobj.mBlocks[nDimension * row + i];
            adj2 = twinobj.mBlocks[nDimension * row + (i +1)];
            if (!(0 == adj1 || 0 == adj2)) {
                found = true;
                twinobj.mBlocks[nDimension * row + i] = adj2;
                twinobj.mBlocks[nDimension * row + (i + 1)] = adj1;
                break;
            }
            i++;
            if (i == nDimension && !found) {
                i = 0;
                row += 1;
                if (row == nDimension) break;
            }
        }
        return twinobj;
    }


    /**
     * Description: does this board equal y?
     */
    public boolean equals(Object y) {

        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        // Different boards are not equal. Corner case.
        Board anotherBoard = (Board) y;
        if (nDimension != (anotherBoard.nDimension)) return false;
        boolean match = true;
        // Compare each element of blocks until all match
        for (int i = 0; i < nDimension * nDimension; i++) {
            if (!(mBlocks[i] == anotherBoard.mBlocks[i])) {
                match = false;
                break;
            }
        }
        return match;
    }

    /**
     * Description: all neighboring boards
     * Returns a datastructure that implements Iterable interface
     */
    public Iterable<Board> neighbors() {
        int temp;
        Board neighbour;
        Queue<Board> queue = new Queue<>();
        /* Neighbours are those boards that can whose position is
         created by exchanges with blank,
         i.e 0, value with the adjacent index
         */
        //
        // Check for corners
        //

        // for all rows starting first row till last but one row
        if (blankRow >= 0 && blankRow < (nDimension -1)) {
            int i = blankRow + 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[nDimension * blankRow + blankCol];
            neighbour.mBlocks[nDimension * blankRow + blankCol] =
                neighbour.mBlocks[nDimension * i  + blankCol];
            neighbour.mBlocks[nDimension * i  + blankCol] = temp;
            neighbour.blankRow = i;
            queue.enqueue(neighbour);
        }

        // For all cols starting 0 till last but one column
        if (blankCol >= 0 && blankCol < (nDimension -1)) {
            int j = blankCol + 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[nDimension * blankRow + blankCol];
            neighbour.mBlocks[nDimension * blankRow + blankCol] =
                neighbour.mBlocks[nDimension * blankRow  + j];
            neighbour.mBlocks[nDimension * blankRow  + j] = temp;
            neighbour.blankCol = j;
            queue.enqueue(neighbour);

        }

        // for all cols starting second row till last row
        if (blankRow >= 1 && blankRow <= (nDimension -1)) {
            int i = blankRow - 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[nDimension * blankRow + blankCol];
            neighbour.mBlocks[nDimension * blankRow + blankCol] =
                neighbour.mBlocks[nDimension * i  + blankCol];
            neighbour.mBlocks[nDimension * i  + blankCol] = temp;
            neighbour.blankRow = i;
            queue.enqueue(neighbour);
        }

        // For all columns starting column 1 till last column
        if (blankCol >= 1 && blankCol <= (nDimension -1)) {
            int j = blankCol - 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[nDimension * blankRow + blankCol];
            neighbour.mBlocks[nDimension * blankRow + blankCol] =
                neighbour.mBlocks[nDimension * blankRow  + j];
            neighbour.mBlocks[nDimension * blankRow  + j] = temp;
            neighbour.blankCol = j;
            queue.enqueue(neighbour);
        }

        return queue;
    }


    /**
     * string representation of this board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(nDimension).append("\n");
        for (int i = 0; i < nDimension * nDimension; i++) {
            s.append(String.format("%2d ",  mBlocks[i]));
            if ((i+1) % nDimension == 0) s.append("\n");
        }
        return s.toString();
    }

//    private Board copyBoard() {
//        Board cpy = null;
//        int[][] blocks = new int[N][N];
//        // Create the board first which is a copy of the
//        // board
//
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++) {
//            blocks[i][j] = (int) mBlocks[N * i + j];
//        }
//        cpy = new Board(blocks);
//        return cpy;
//    }


    private Board copyBoard() {
        Board cpy;
        cpy = new Board(this);
        return cpy;
    }
    public static void main(String[] args) {
//

//        Board b1 = new Board(i1);
//        StdOut.println(b1);
//        StdOut.println("Hamming score:" + b1.hamming());
//        StdOut.println("Manhattan score:" + b1.manhattan());

//        Board bcopy = b;

//        StdOut.println("Neighbors:\n--------");
//        Iterable<Board> iter = b.neighbors();
//
//        // Check for each neighbor of the board b,
//        // its neighbors as an unit test
//        Queue<Board> q = new Queue<>();
//
//        for (Board obj: iter) {
//            q.enqueue(obj);
////            StdOut.println(obj);
////            StdOut.println("Hamming score:" + obj.hamming());
////            StdOut.println("Manhattan score:" + obj.manhattan());
////            StdOut.println("Hamming score:" + obj.hamming());
////            StdOut.println("Manhattan score:" + obj.manhattan());
//        }
//
//        for (Board obj: q) {
//            StdOut.println("Board:");
//            StdOut.println(obj);
//            StdOut.println("Hamming score:" + obj.hamming());
//            StdOut.println("Manhattan score:" + obj.manhattan());
////            StdOut.println("Neighbors:\n--------");
////            iter = obj.neighbors();
////            for (Board obj1: iter)  {
////                StdOut.println(obj1);
////                StdOut.println("Hamming score:" + obj1.hamming());
////                StdOut.println("Manhattan score:" + obj1.manhattan());
////            }
//        }
////        if (b.isGoal()) StdOut.println("This is goal board!!!");
////        StdOut.println("Twin:\n" + b.twin());
//
////        if (bcopy.equals(b)) StdOut.println("bcopy equals b");
////        if (b.equals(bcopy)) StdOut.println("b equals bcopy");
////
////        bcopy = new Board(icopy);
////        if (bcopy.equals(b)) StdOut.println("bcopy equals b");
////        if (b.equals(bcopy)) StdOut.println("b equals bcopy");
    }
}
