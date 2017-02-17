import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int openSite;
    private int[] openNode;
    private double threshold;
    private int numOfGrid;
    private WeightedQuickUnionUF union;

    public Percolation(int n)               // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
        {
            throw new java.lang.IllegalArgumentException("n less than 1");
        }
        numOfGrid = n;
        openNode = new int[numOfGrid * numOfGrid + 2];
        for (int i = 1; i <= numOfGrid * numOfGrid; i++)
        {
            openNode[i] = 0;                  // all blocked
        }
        openNode[0] = 1;
        openNode[numOfGrid * numOfGrid + 1] = 1;
        union = new WeightedQuickUnionUF(numOfGrid * numOfGrid + 2);  // including top root and bottom root
    }
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
       if (i < 1 || i > numOfGrid || j < 1 || j > numOfGrid)
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
       }
       if (!isOpen(i, j))
       {
           openSite++;
           openNode[(i - 1) * numOfGrid + j] = 1;
           if (i == 1)
           {
               union.union(0, j);
           }
           else if (i == numOfGrid)
           {
               union.union(numOfGrid * numOfGrid + 1, (i - 1) * numOfGrid + j);
           }
           if ((i > 1) && isOpen(i - 1, j))
           {
               union.union((i - 1) * numOfGrid + j, (i - 2) * numOfGrid + j);
           }
           if ((i < numOfGrid) && isOpen(i + 1, j))
           {
               union.union((i - 1) * numOfGrid + j, i * numOfGrid + j);
           }
           if ((j > 1) && isOpen(i, j - 1))
           {
               union.union((i - 1) * numOfGrid + j, (i - 1) * numOfGrid + j - 1);
           }
           if ((j < numOfGrid) && isOpen(i, j + 1))
           {
               union.union((i - 1) * numOfGrid + j, (i - 1) * numOfGrid + j + 1);
           }
           
       }
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
       if (i < 1 || i > numOfGrid || j < 1 || j > numOfGrid)
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
       }
       if (openNode[(i - 1) * numOfGrid + j] == 1)
       {
           return true;
       }
       return false;
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
       if (i < 1 || i > numOfGrid || j < 1 || j > numOfGrid)
       {
           String exce = "i: " + i + " j: " + j + " out of boundary\n";
           throw new IndexOutOfBoundsException(exce);
       }
       return union.connected(0, (i - 1) * numOfGrid + j);   // use 0 to represent top root       
   }

   public int numberOfOpenSites()
   {
       return openSite;    
   }
   
   public boolean percolates()             // does the system percolate?
   {
       return union.connected(0, numOfGrid * numOfGrid + 1);     
   } 
   public static void main(String[] args)  // test client (optional)
   {
       Percolation playground = new Percolation(10);
       // StdOut.printf("\n%d\n", playground.numberOfOpenSites());
       int randomNumOfGrid;   
       int i, j;
       while (!playground.percolates())
       {
           if (playground.numberOfOpenSites() > playground.numOfGrid * playground.numOfGrid) { break; }
           do {
               randomNumOfGrid = StdRandom.uniform(0, playground.numOfGrid * 
                                                    playground.numOfGrid);
           } while (randomNumOfGrid == 0);
           if (randomNumOfGrid % playground.numOfGrid == 0) {
               i = randomNumOfGrid / playground.numOfGrid;
               j = playground.numOfGrid;
           }
           else {
               i = randomNumOfGrid / playground.numOfGrid + 1;
               j = randomNumOfGrid % playground.numOfGrid;
           }    
           playground.open(i, j);
       }
       playground.threshold = playground.numberOfOpenSites() / (double) (playground.numOfGrid * playground.numOfGrid);
       StdOut.printf("\n%f\n", playground.threshold);
       // StdOut.printf("\n%d\n", playground.numberOfOpenSites());
   }
}
