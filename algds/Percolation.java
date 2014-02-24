public class Percolation {
    private int mGridSize; 
    private int[][] mGrid; // 2-D grid of size above
 
/**
 * create N-by-N grid, with all sites blocked
 */
    public Percolation(int N) {
       if (N < 1) {
            StdOut.println("Invalid value of N provided in constructor");
            System.exit(1);
        }
       // Create the grid and store the grid size
       mGridSize = N;
       mGrid = new int[N][N];
        
       for (int i=0; i < N; i++) {
            mGrid[i] = new int[N];
            for (int j=0; j < N; j++) 
                mGrid[i][j] = 0;
        }
    
    }
    
 /**
  * open site (row i, column j) if it is not already
  */
    public void open(int i, int j) {
        if (!check_index(i,j) 
                throw new IndexOutOfBoundsException("invalid index i or j for grid site");
    }
  
    
    
  // is site (row i, column j) open?
  public boolean isOpen(int i, int j) {
      if (mGrid[i-1][j-1] == 1)
          return true;
      else
          return false;
  }
   // is site (row i, column j) full?
   //public boolean isFull(int i, int j);   
   
   // does the system percolate?
   //public boolean percolates();  
    
    // 
    // Private Methods
    //
    private boolean check_index(int row, int col) {
        boolean valid = true;
        int i = row - 1;
        int j = col - 1;
        
        if ((i < 0 && i >= N) || (j < 0 && j >= N))  {
            valid = false;
        }
        return valid;
    }    
        
    // static Main method
    public static void main(String[] args) { 
        Percolation p = new Percolation(5);
        
    }
}