import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int loops;
    private int numOfGrid;
    private double[] arrayOfPercolation;
    public PercolationStats(int n, int trials)
    {
        if ((n <= 0) || (trials <= 0))
        {
            throw new java.lang.IllegalArgumentException("n and trails have to be larger than zero");
        }
        loops = trials;
        numOfGrid = n;
        arrayOfPercolation = new double[loops];
        for (int loop = 0; loop < loops; loop++)
        {
            Percolation playground = new Percolation(numOfGrid);
            int openSite = 1;
            int randomNumOfGrid;    
            int[] existSite = new int[numOfGrid * numOfGrid + 1];
            int i, j;
            while (!playground.percolates())
            {
                if (openSite > numOfGrid * numOfGrid) { break; }
                do {
                    randomNumOfGrid = StdRandom.uniform(0, numOfGrid * numOfGrid);
                } while (randomNumOfGrid == 0);
                boolean isExist = false;
                for (int arrayIndex = 1; arrayIndex <= openSite;   arrayIndex++)
                {
                    if (randomNumOfGrid == existSite[arrayIndex])
                    {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    openSite++;
                    existSite[openSite] = randomNumOfGrid;
                }
                if (randomNumOfGrid % numOfGrid == 0) {
                    i = randomNumOfGrid / numOfGrid;
                    j = numOfGrid;
                }
                else {
                    i = randomNumOfGrid / numOfGrid + 1;
                    j = randomNumOfGrid % numOfGrid;
                }      
                playground.open(i, j);
            }
            arrayOfPercolation[loop] = openSite / (double) (numOfGrid * numOfGrid);  
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(arrayOfPercolation);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(arrayOfPercolation);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return (mean() - 1.96 * stddev() / Math.sqrt(loops));        
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return (mean() + 1.96 * stddev() / Math.sqrt(loops));   
    }

    public static void main(String[] args)    // test client (described below)    
    {
        int n, trials;
        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);
        PercolationStats PS = new PercolationStats(n, trials);
        StdOut.printf("%-23s %s %f\n", "mean", "=", PS.mean());
        StdOut.printf("%-23s %s %f\n", "stddev", "=", PS.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", PS.confidenceLo(), PS.confidenceHi());
    }    
}
