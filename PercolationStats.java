import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats{
    private int T;
    private int N;
    private double[] X;
    public PercolationStats(int n, int trials)
    {
        T = trials;
        N = n;
        X = new double[T];
        for(int i = 0; i < T; i++)
        {
            Percolation P = new Percolation(N);
            int open_site = 0;
            
            int random_N;       
            while(P.percolates() == false)
            {
                open_site++;
                if(open_site > P.N * P.N){break;} 
                do{
                    random_N = (int)(StdRandom.random() * P.N * P.N);
                    //random_N = 4;
                }while(random_N == 0);
                //random_N = (open_site - 1) * P.N + 1; //vertical drill
                //System.out.format("%d\ni:%d j:%d   counter:%d\n", random_N, (int)(random_N / P.N) + 1, (int)(random_N % P.N), open_site);
                P.open((int)(random_N / P.N) + 1, (int)(random_N % P.N));
            }
            P.Threshold = open_site / (double)(P.N * P.N);  
            System.out.format("%f\n", P.Threshold);
            X[i] = P.Threshold;
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(X);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(X);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return (mean() - 1.96 * stddev() / Math.sqrt(T));        
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));   
    }

    public static void main(String[] args)    // test client (described below)    
    {
        PercolationStats PS = new PercolationStats(10, 50);
        System.out.format("%-23s %s %f\n", "mean", "=", PS.mean());
        System.out.format("%-23s %s %f\n", "stddev", "=", PS.stddev());
        System.out.format("95%% confidence interval = %f, %f\n", PS.confidenceLo(), PS.confidenceHi());
    }    
}
