import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] Open_node;
    public double Threshold;
    public int N;
    private WeightedQuickUnionUF UF;

    public Percolation(int n)               // create n-by-n grid, with all sites blocked
    {
        N = n;
        
        Open_node = new int[N * N + 2];
        for(int i = 1; i <= N * N; i++)
        {
            Open_node[i] = 0;                  // all blocked
        }
        Open_node[0] = 1;
        Open_node[N * N + 1] = 1;
        UF = new WeightedQuickUnionUF(N * N + 2);  // including top root and bottom root
    }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
       if(isOpen(i, j) == false)
       {
           Open_node[(i - 1) * N + j] = 1;
           if(i == 1)
           {
               UF.union(0, j);
           }
           else if(i == N)
           {
               UF.union(N * N + 1, (i - 1) * N + j);
           }
           if(isOpen(i - 1, j) == true)
           {
               UF.union((i - 1) * N + j, (i - 2) * N + j);
           }
           if(isOpen(i + 1, j) == true)
           {
               UF.union((i - 1) * N + j, i * N + j);
           }
           if(isOpen(i, j - 1) == true)
           {
               UF.union((i - 1) * N + j, (i - 1) * N + j - 1);
           }
           if(isOpen(i, j + 1) == true)
           {
               UF.union((i - 1) * N + j, (i - 1) * N + j + 1);
           }
           
       }
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
       if((i == 0)||(i == N + 1)||(j == 0)||(j == N + 1))
       {
           return false;
       }
       else if(Open_node[(i - 1) * N + j] == 1)
       {
           return true;
       }
       else{
           return false;
       }
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
       return UF.connected(0, (i - 1) * N + j);   // use 0 to represent top root       
   }

   public boolean percolates()             // does the system percolate?
   {
       return isFull(N, N+1);     
   } 
   public static void main(String[] args)  // test client (optional)
   {
       Percolation P = new Percolation(20);
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
       System.out.format("\n%f\n", P.Threshold);
   }
}
