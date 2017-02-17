import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int[] Open_node;
    public double Threshold;
    public int N;
    private WeightedQuickUnionUF UF;

    public Percolation(int n)               // create n-by-n grid, with all sites blocked
    {
        if(n <= 0)
        {
            throw new java.lang.IllegalArgumentException("n less than 1");
        }
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
       if((i < 1) || (i > N ) || (j < 1) || (j > N))
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
       }
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
           if((i > 1) && isOpen(i - 1, j))
           {
               UF.union((i - 1) * N + j, (i - 2) * N + j);
           }
           if((i < N) && isOpen(i + 1, j))
           {
               UF.union((i - 1) * N + j, i * N + j);
           }
           if((j > 1) && isOpen(i, j - 1))
           {
               UF.union((i - 1) * N + j, (i - 1) * N + j - 1);
           }
           if((j < N) && isOpen(i, j + 1))
           {
               UF.union((i - 1) * N + j, (i - 1) * N + j + 1);
           }
           
       }
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
       if((i < 1) || (i > N ) || (j < 1) || (j > N))
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
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
       if((i < 1) || (i > N ) || (j < 1) || (j > N))
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
       }
       return UF.connected(0, (i - 1) * N + j);   // use 0 to represent top root       
   }

   public boolean percolates()             // does the system percolate?
   {
       return UF.connected(0, N * N + 1);     
   } 
   public static void main(String[] args)  // test client (optional)
   {
       Percolation P = new Percolation(100);
       int open_site = 0;
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
       StdOut.printf("\n%f\n", P.Threshold);
   }
}
