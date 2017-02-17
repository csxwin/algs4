import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats{
    private int T;
    private int N;
    private double[] X;
    public PercolationStats(int n, int trials)
    {
        if((n <= 0) || (trials <= 0))
        {
            throw new java.lang.IllegalArgumentException("n and trails have to be larger than zero");
        }
        T = trials;
        N = n;
        X = new double[T];
        for(int loop = 0; loop < T; loop++)
        {
            Percolation P = new Percolation(N);
            int open_site = 1;
            int random_N;    
            int[] exist_Site = new int[P.N * P.N + 1];
            int i, j;
            while(P.percolates() == false)
            {
                if(open_site > P.N * P.N){break;}
                do{
                    random_N = StdRandom.uniform(0, P.N * P.N);
                }while(random_N == 0);
                boolean IsExist = false;
                for(int array_Index = 1; array_Index <= open_site;   array_Index++)
                {
                    if(random_N == exist_Site[array_Index])
                    {
                        IsExist = true;
                    }
                }
                if(!IsExist){
                    open_site++;
                    exist_Site[open_site] = random_N;
                }
                if((int)(random_N % P.N) == 0){
                    i = random_N / P.N;
                    j = P.N;
                }
                else{
                    i = random_N / P.N + 1;
                    j = random_N % P.N;
                }      
                P.open(i, j);
            }
            P.Threshold = open_site / (double)(P.N * P.N);  
            X[loop] = P.Threshold;
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
        int n, trials;
        //n = StdIn.readInt();
        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);
        PercolationStats PS = new PercolationStats(n, trials);
        StdOut.printf("%-23s %s %f\n", "mean", "=", PS.mean());
        StdOut.printf("%-23s %s %f\n", "stddev", "=", PS.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", PS.confidenceLo(), PS.confidenceHi());
    }    
}
