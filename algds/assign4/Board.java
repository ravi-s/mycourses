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
    private int N; // Store the dimension of the board
    private int blankRow;
    private int blankCol;
    
    /* Board a logical N by N grid,
     but stored as N^2 one-dimensional array
     */
    private char[] mBlocks;  
    
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
        N = size;
        mBlocks = new char[N * N];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
            // Store the blank or 0 i, j index for
            // finding neighbours
            if (blocks[i][j] == 0) { 
                blankRow = i; 
                blankCol = j; 
            }
            mBlocks[N * i + j] = (char) blocks[i][j];
        }
        
        
//        // Intialize the goal grid
//        int number = 1; // 0 is blank implicitly
//        _mgrid = new int[N][N];
//        
//        for (int i = 0; i < N; i++) 
//            for (int j = 0; j < N; j++)
//              _mgrid[i][j] = number++;
        
        
    }      
    
    // (where blocks[i][j] = block in row i, column j)
    /**
     * board dimension N
     */
    public int dimension() { return N; }
    
    /**
     *  number of blocks out of place
     */
    public int hamming() { 
        int score = 0;
        //if ((int)mBlocks[mBlocks.length - 1] != 0) score++;
        for (int i = 0; i < mBlocks.length; i++) {
            if (i == (mBlocks.length - 1)) break;
            if ((int) mBlocks[i] != (i+1)) score++;
        }
        
        return score;
    } 
    
    /**
     *  sum of Manhattan distances between blocks and goal
     */
    public int manhattan() { 
        int score = 0; // For this board object
        int colIndex = 0, rowIndex = 0;
        int colGoal = 0, rowGoal = 0;
        
        for (int i = 0; i < mBlocks.length; i++) {
            if ((int) mBlocks[i] == 0) continue; // 0 is blank,skip
            colGoal = ((int) mBlocks[i] - 1) % N;
            colIndex = i % N;
            
            rowGoal = ((int) mBlocks[i] - 1) / N;
            rowIndex = i / N;
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
        char adj1 = 0, adj2 = 0;
        
        boolean found = false;
        Board twinobj = null;
        twinobj = this.copyBoard(); 
        
        // Twinning operation below, 
        //adjacent blocks in same row can be swapped
        // as long as one of the them is not 0 or blank
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((j + 1) == N) continue;
                
                adj1 = twinobj.mBlocks[N * i + j];
                adj2 = twinobj.mBlocks[N * i + j + 1];
                
                if (0 == adj1 || 0 == adj2) continue;
                else { 
                    // found 2 adjacent blocks to swap
                    found = true;
                    twinobj.mBlocks[N * i + j] = adj2;
                    twinobj.mBlocks[N * i + j + 1] = adj1;
                    break;
                }
            }
            if (found) break;
        }
        return twinobj;
    }   
    
    /**
     *  Hashcode 
     * 
     */
//    public int hashCode() { return System.identityHashCode(this); }
    /**
     * Description: does this board equal y?    
     */
    public boolean equals(Object y) {
        
        if (this == y) return true; 
        if (y == null) return false; 
        if (this.getClass() != y.getClass()) return false;
        Board anotherBoard = (Board) y;
        boolean match = true;
        // Compare each element of blocks until all match
        for (int i = 0; i < N * N; i++) {
            if (mBlocks[i] == anotherBoard.mBlocks[i]) continue;
            else {
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
        char temp;
        Board neighbour;
        Queue<Board> queue = new Queue<Board>();
        /* Neighbours are those boards that can whose position is 
         created by exchanges with blank, 
         i.e 0, value with the adjacent index
         */
        // 
        // Check for corners
        //
        
        // for all rows starting first row till last but one row
        if (blankRow >= 0 && blankRow < (N-1)) {
            int i = blankRow + 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[N * blankRow + blankCol];
            neighbour.mBlocks[N * blankRow + blankCol] = 
                neighbour.mBlocks[N * i  + blankCol];
            neighbour.mBlocks[N * i  + blankCol] = temp;
            neighbour.blankRow = i;
            queue.enqueue(neighbour);
        }
        
        // For all cols starting 0 till last but one column
        if (blankCol >= 0 && blankCol < (N-1)) {
            int j = blankCol + 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[N * blankRow + blankCol];
            neighbour.mBlocks[N * blankRow + blankCol] = 
                neighbour.mBlocks[N * blankRow  + j];
            neighbour.mBlocks[N * blankRow  + j] = temp;
            neighbour.blankCol = j;
            queue.enqueue(neighbour);    
            
        }
        
        // for all cols starting second row till last row
        if (blankRow >= 1 && blankRow <= (N-1)) {
            int i = blankRow - 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[N * blankRow + blankCol];
            neighbour.mBlocks[N * blankRow + blankCol] = 
                neighbour.mBlocks[N * i  + blankCol];
            neighbour.mBlocks[N * i  + blankCol] = temp;
            neighbour.blankRow = i;
            queue.enqueue(neighbour);
        }
        
        // For all columns starting column 1 till last column
        if (blankCol >= 1 && blankCol <= (N-1)) {
            int j = blankCol - 1;
            neighbour = this.copyBoard();
            temp = neighbour.mBlocks[N * blankRow + blankCol];
            neighbour.mBlocks[N * blankRow + blankCol] = 
                neighbour.mBlocks[N * blankRow  + j];
            neighbour.mBlocks[N * blankRow  + j] = temp;
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
        s.append(N + "\n");
        for (int i = 0; i < N * N; i++) {
            s.append(String.format("%2d ", (int) mBlocks[i]));
            if ((i+1) % N == 0) s.append("\n");
        }
        return s.toString();
    }              
    
    private Board copyBoard() {
        Board cpy = null;
        int[][] blocks = new int[N][N];
        // Create the board first which is a copy of the
        // board
        
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
            blocks[i][j] = (int) mBlocks[N * i + j];
        }
        cpy = new Board(blocks);
        return cpy;
    }
    
    public static void main(String[] args) { 
//         unit tests (not graded)
//       int[][] i = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//        int i[][] = {{1,0},{2,3}}; // Passed Manhatttan test
//        int i[][] = {{1,3},{0,2}}; // Passed Manhatttan test
//        int i[][] = {{5,1,8},{2,7,3}, {4,0,6}}; // Passed Manhatttan test
//        int i[][] = {{5,8,7},{1,4,6}, {3,0,2}}; // Passed Manhatttan test
        
        // Passed Manhatttan test 4 x 4
//        int i[][] = {{5,11,2,4},{10,3,9,14}, {0,13,12,8}, {7,15,6,1}}; 
        
        // Passed Manhatttan test 5 x 5
//        int i[][] = {
//            {13,4,17,7,23},{3,0,18,2,22}, 
//            {9,20,19,10,15}, {24,5,21,16,12}, 
//            {1,8,11,6,14}};
        
        // Passed Manhatttan test 9 x 9
//          int i[][] = {
//              {29,34,26,28,45,3,35,9,78}, 
//              {42,77,58,7,39,72,60,51,21}, 
//              {50,41,1,38,63,11,55,12,22},
//              {46,5,36,57,65,20,54,31,14}, 
//              {68,32,64,56,52,23,33,47,25}, 
//              {13,8,27,6,80,53,74,66,76},
//              {61,79,40,2,0,44,16,17,69}, 
//              {49,62,59,4,73,15,67,43,10}, 
//              {75,37,30,48,71,19,18,70,24}
//              }; 
//        int i1[][] = {{8,3,1},{4,0,2},{7,6,5}};
//        int i2[][] = {{3,1,8},{4,0,2},{7,6,5}};
//        int i3[][] = {{3,8,1},{4,0,2},{7,6,5}};
//        int i4[][] = {{1,3,8},{4,0,2},{7,6,5}};
//        int i5[][] = {{1,8,3},{4,0,2},{7,6,5}};
//        
//        int icopy[][] = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        //int i[][] = {{4,0,2},{7,6,5},{8,1,3}};
//        int[][] j = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] j = {{1, 0, 2}, {7, 5, 4}, {8, 6, 3}};
        //int k[][] = {{1,3,0},{4,2,5},{7,8,6}};
        //int l[][] = {{4,2,5},{7,8,6},{1,3,0}};
        //int[][] m = {{1, 2, 3}, {5, 7, 6}, {0, 4, 8}};
        //int g[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
        
        StdOut.println("Board:");
        Board b = new Board(j);
        StdOut.println(b);
        //StdOut.println(b.hashCode());
        StdOut.println("Hamming score:" + b.hamming());
        StdOut.println("Manhattan score:" + b.manhattan());
        
//        Board b1 = new Board(i1);
//        StdOut.println(b1);
//        StdOut.println("Hamming score:" + b1.hamming());
//        StdOut.println("Manhattan score:" + b1.manhattan());
        
//        Board bcopy = b;
        
        StdOut.println("Neighbors:\n--------");
        Iterable<Board> iter = b.neighbors();
        
        // Check for each neighbor of the board b,
        // its neighbors as an unit test
        Queue<Board> q = new Queue<>();
        
        for (Board obj: iter) {
            q.enqueue(obj);
            StdOut.println(obj); 
            StdOut.println("Hamming score:" + b.hamming());
            StdOut.println("Manhattan score:" + b.manhattan());
//            StdOut.println("Hamming score:" + obj.hamming());
//            StdOut.println("Manhattan score:" + obj.manhattan());
        }
        
        for (Board obj: q) {
            StdOut.println("Board:");
            StdOut.println(obj);
            StdOut.println("Hamming score:" + b.hamming());
            StdOut.println("Manhattan score:" + b.manhattan());
            StdOut.println("Neighbors:\n--------");
            iter = obj.neighbors();
            for (Board obj1: iter)  {
                StdOut.println(obj1);
                StdOut.println("Hamming score:" + b.hamming());
                StdOut.println("Manhattan score:" + b.manhattan());
            }
        }
//        if (b.isGoal()) StdOut.println("This is goal board!!!");
//        StdOut.println("Twin:\n" + b.twin());
        
//        if (bcopy.equals(b)) StdOut.println("bcopy equals b");
//        if (b.equals(bcopy)) StdOut.println("b equals bcopy");
//        
//        bcopy = new Board(icopy);
//        if (bcopy.equals(b)) StdOut.println("bcopy equals b");
//        if (b.equals(bcopy)) StdOut.println("b equals bcopy");
    } 
}