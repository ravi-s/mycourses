public class PercolationStats {
    /**
     * perform T independent computational experiments on an N-by-N grid
     */
   
    
   public PercolationStats(int N, int T) { 
       if (N <= 0 || T <= 0)
           throw new IllegalArgumentException("Invalid constructor argument: N and T should be > 0);
       
        // Set up percolation grid and perform monte-carlo simulation T times
       for (int i = 1; i <= T; i++) {
           Percolation p = new Percolation(N);
           
       }
    }   
   
   /** 
    * sample mean of percolation threshold
    */
   //public double mean() 
   
   /** sample standard deviation of percolation threshold 
   */
   //public double stddev()    
   
   /** returns lower bound of the 95% confidence interval */
   //public double confidenceLo() 
       
   /** returns upper bound of the 95% confidence interval */   
   //public double confidenceHi() 
       
   /** test client, described below  */ 
   public static void main(String[] args)   
}