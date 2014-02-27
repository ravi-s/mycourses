public class PercolationStats {
    /**
     * perform T independent computational experiments on an N-by-N grid
     */
   
   private int mExperiments;
   private int mGridSize;
   private double[] thresholds;
   
   public PercolationStats(int N, int T) { 
       if (N <= 0 || T <= 0)
           throw new IllegalArgumentException("Invalid constructor argument: N and T should be > 0");
       
      mGridSize = N;
      mExperiments = T;                                        
       thresholds = new double[mExperiments];
                                              
        // Set up percolation grid and perform monte-carlo simulation T times
       for (int i = 0; i < mExperiments; i++) {
          Percolation p = new Percolation(mGridSize);
          thresholds[i] = performExperiment(p, mGridSize);
       }
    }   
   
   /** 
    * sample mean of percolation threshold
    */
   public double mean() {
       return StdStats.mean(thresholds);
   }
   
   /** sample standard deviation of percolation threshold 
   */
   public double stddev()   {
       return StdStats.stddev(thresholds);
   }
   
   /** returns lower bound of the 95% confidence interval */
   public double confidenceLo() {
       return mean() - ((1.96*stddev()) / (Math.sqrt(mExperiments)));
   }
       
   /** returns upper bound of the 95% confidence interval */   
   public double confidenceHi() {
       return mean() + ((1.96*stddev()) / (Math.sqrt(mExperiments)));
       
   }
   
    // Private methods
   
    /**
     * perform Monte Carlo experiment on the percolation object of certain size
     */
    private double performExperiment(Percolation p, int size) {
     int i; // site row index
     int j; // site col index
     
     double fractionOpened = 0.0D;
     int opened = 0; // Variable for opened site 
                     // when it is either full site or open
     
     // repeate until system percolates
     // Choose site randomly
     // open the site
     // 
     while (!p.percolates()) {
      // added 1 because the api returns the left 
      // inclusively but the right one is exclusive
        i = StdRandom.uniform(1, size + 1);
        j = StdRandom.uniform(1, size + 1);
        
        if (!p.isOpen(i, j)) {
            p.open(i, j); 
            opened++;
        }
      }
     
     
     //StdOut.println(opened);
     fractionOpened = ((double) (opened))/(double) (size * size);
     //StdOut.println(fraction_opened);

     return fractionOpened;
    }
   /** test client, described below  */ 
    public static void main(String[] args)  {
        int gridsize;
        int experiments;
        
        gridsize = Integer.parseInt(args[0]);   
        experiments = Integer.parseInt(args[1]);
        
        PercolationStats p = new PercolationStats(gridsize, experiments);
        /*
         * Print results
         */
        StdOut.printf("%s%f%s", "mean\t = ", p.mean(), "\n");
        StdOut.printf("%s%f%s", "stddev\t = ",  p.stddev(), "\n");
        StdOut.printf("%s%f%s%f%s", 
                      "95% confidence interval\t = ", 
                      p.confidenceLo(), "," , 
                      p.confidenceHi(), "\n");
        
    
    } 
}