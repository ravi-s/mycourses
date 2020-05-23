import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Ravi S
 * @version 1.0
 * @code Percolation
 */
public class Percolation {
    private int mGridSize;
    private boolean[][] mGrid; // 2-D grid of size above
    private WeightedQuickUnionUF mUnionFind;
    private int mOpenSites;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException("Size n cannot be 0 or less than 0");
        // Create the grid and store the grid size
        mGridSize = n;
        mGrid = new boolean[n][n];

        mOpenSites = 0;

        for (int i = 0; i < n; i++) {
            mGrid[i] = new boolean[n];
            for (int j = 0; j < n; j++)
                mGrid[i][j] = false;
        }

        // Initialize the WeightedQuickUnionFind
        // 2 for extra virtual nodes - one at the top
        // and one at the bottom (index 0 and index n^2 + 1 respectively)
        mUnionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    /**
     * open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        if (!checkIndex(i, j))
            throw new IndexOutOfBoundsException("invalid index i or j for grid site");

        if (isOpen(i, j)) return;

        mGrid[i - 1][j - 1] = true;
        ++mOpenSites;

        // Neither Topmost or bottom most rows

        int idxFrom = xyTo1D(i, j);

        if (checkIndex(i - 1, j)) {
            if (isOpen(i - 1, j)) mUnionFind.union(idxFrom, xyTo1D(i - 1, j));
        }
        if (checkIndex(i + 1, j) && (i != mGridSize)) {
            if (isOpen(i + 1, j)) mUnionFind.union(idxFrom, xyTo1D(i + 1, j));
        }
        if (checkIndex(i, j + 1)) {
            if (isOpen(i, j + 1)) mUnionFind.union(idxFrom, xyTo1D(i, j + 1));
        }
        if (checkIndex(i, j - 1)) {
            if (isOpen(i, j - 1)) mUnionFind.union(idxFrom, xyTo1D(i, j - 1));
        }
        // 1: top row
        if (i == 1) {
            int idx = xyTo1D(i, j);
            mUnionFind.union(0, idx);

        }
        // 2: bottom row
        if (i == mGridSize && (!percolates())) {
            int idx = xyTo1D(i, j);
            mUnionFind.union(idx, mGridSize * mGridSize + 1);
        }


    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        boolean status = false;
        if (!checkIndex(i, j))
            throw new IndexOutOfBoundsException("invalid index i or j for grid site");
        if (mGrid[i - 1][j - 1]) {
            status = true;
        }
        return status;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (!checkIndex(i, j))
            throw new IndexOutOfBoundsException("invalid index i or j for grid site");
        int idx = xyTo1D(i, j);
        // is virtual top site connected to this site
        //return mUnionFind.connected(0, idx);
        return mUnionFind.find(0) == mUnionFind.find(idx);

    }

    // does the system percolate?
    public boolean percolates() {
        if (mGridSize == 1 && !isOpen(1, 1))
            return false;
        //return mUnionFind.connected(0, mGridSize * mGridSize + 1);
        return mUnionFind.find(0) == mUnionFind.find(mGridSize * mGridSize + 1);

    }

    // Calculate the number of opensites
    public int numberOfOpenSites() {
        return mOpenSites;
    }

    //
    // Private Methods
    //
    private boolean checkIndex(int row, int col) {
        boolean valid = true;

        // Check for invalid inputs
        if (0 == row || 0 == col) {
            valid = false;
        }
        int i = row - 1;
        int j = col - 1;

        if ((i < 0 || i >= mGridSize) || (j < 0 || j >= mGridSize)) {
            valid = false;
        }
        return valid;
    }

    // Translate 2D co-ordinates of grid to 1-D co-ordinates of the UnionFind
    private int xyTo1D(int i, int j) {
        return (mGridSize * (i - 1) + j);
    }


    //
    // End private methods
    //
    // static Main method
    public static void main(String[] args) {
        // Percolation p = new Percolation(5);

    }
}
