
public class Percolation {
    private int mGridSize; 
    private boolean[][] mGrid; // 2-D grid of size above
    private WeightedQuickUnionUF mUnionFind;
 
/**
 * create N-by-N grid, with all sites blocked
 */
    public Percolation(int N) {
       
       // Create the grid and store the grid size
       mGridSize = N;
       mGrid = new boolean[N][N];
        
       for (int i = 0; i < N; i++) {
            mGrid[i] = new boolean[N];
            for (int j = 0; j < N; j++) 
                mGrid[i][j] = false;
        }
    
       // Initialize the WeightedQuickUnionFind
       // 2 for extra virtual nodes - one at the top 
       // and one at the bottom (index 0 and index N^2 -1)
       mUnionFind = new WeightedQuickUnionUF(N*N + 2);
    }
    
 /**
  * open site (row i, column j) if it is not already
  */
    public void open(int i, int j) {
        if (!checkIndex(i, j))
           throw new IndexOutOfBoundsException("invalid index i or j for grid site");
       if (isOpen(i, j)) return;
       else {
              mGrid[i-1][j-1] = true;
              

              // 1: top row
              if (i == 1) {
                     int idx = xyTo1D(i, j);
                     mUnionFind.union(0, idx);
                     
              }
              // 2: bottom row
              if (i == mGridSize) {
                  int idx = xyTo1D(i, j);
                  mUnionFind.union(mGridSize*mGridSize-1, idx);
              }
              
              // 3: other cases
              int idxFrom = xyTo1D(i, j);
              
              if (checkIndex(i-1, j)) {
                  if (isOpen(i-1, j)) mUnionFind.union(idxFrom, xyTo1D(i-1, j));
              }
              if (checkIndex(i+1, j)) {
                  if (isOpen(i+1, j)) mUnionFind.union(idxFrom, xyTo1D(i+1, j));
              }
              if (checkIndex(i, j+1)) {
                  if (isOpen(i, j+1)) mUnionFind.union(idxFrom, xyTo1D(i, j+1));
              }
              if (checkIndex(i, j-1)) {
                  if (isOpen(i, j-1)) mUnionFind.union(idxFrom, xyTo1D(i, j-1));
              }
              
       } 
       
     } 
  
    
    
  // is site (row i, column j) open?
  public boolean isOpen(int i, int j) {
      if (checkIndex(i, j)) {
         if (mGrid[i-1][j-1])
             return true;
      }
      
          return false;
     
  }
   // is site (row i, column j) full?
  public boolean isFull(int i, int j) {
       if (!checkIndex(i, j))
          throw new IndexOutOfBoundsException("invalid index i or j for grid site");
      int idx = xyTo1D(i, j);
      // is virtual top site connected to this site
       return mUnionFind.connected(idx, 0);
           
  }
   
   // does the system percolate?
   public boolean percolates() {
       if (mGridSize == 1 && !isOpen(1, 1))
          return false;
       return mUnionFind.connected(0,mGridSize*mGridSize-1);
       
   }
    
    // 
    // Private Methods
    //
    private boolean checkIndex(int row, int col) {
        boolean valid = true;
        int i = row - 1;
        int j = col - 1;
        
        if ((i < 0 || i >= mGridSize) || (j < 0 || j >= mGridSize))  {
            valid = false;
        }
        return valid;
    }    
    
    // Translate 2D co-ordinates of grid to 1-D co-ordinates of the UnionFind
    private int xyTo1D(int i, int j) {
        return (mGridSize * (i-1) + j);
    }
    
    
    //
    // End private methods
    // 
    // static Main method
    public static void main(String[] args) { 
       // Percolation p = new Percolation(5);
        
    }
}