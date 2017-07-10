import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ; 

public class Solver {
    private int[][] locBlocks = new int[3][3];
    MinPQ<Board> solutionQueue = new MinPQ<Board>();
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return true;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return 0;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return new Iterable<Board>()
        {
            @Override  
            public Iterator<Board> iterator()
            {
                return new Iterator<Board>()
                {
                    public boolean hasNext()
                    {
                        return false;
                    }
                    public void remove() {}
                    public Board next()
                    {
                        return null;
                    }
                };
            }
        };
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }        
    }
}