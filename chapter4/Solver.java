import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ; 
import edu.princeton.cs.algs4.Queue; 
import java.util.Comparator;

public class Solver {

    private MinPQ<Board> pQueue = new MinPQ<Board>(boardOrder());
    private Queue<Board> solutionQueue = new Queue<Board>();
    private Board locBoard;
    private int moves;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        //dequeue node with minimum priority
        //process it: add children to game tree and priority queue
        locBoard = initial;
        pQueue.insert(locBoard);
        StdOut.println("start");
        Board currentBoard;
        Board PreviousBoard = null;
        int bugGuard = 10;
        do {
            StdOut.println("dequeue");
            currentBoard = pQueue.delMin();
            StdOut.println(currentBoard.toString());
            
            solutionQueue.enqueue(currentBoard);
            
            for (Board childrenBoard : currentBoard.neighbors())
            { 
                //check against previous board
                if (!childrenBoard.equals(PreviousBoard))
                {
                    //enqueue
                    StdOut.println("enqueue");
                    pQueue.insert(childrenBoard);
                    StdOut.println(childrenBoard.toString());
                }
            }
        } while (!currentBoard.isGoal() && (bugGuard-- > 0));
        StdOut.println("finish");
    }
    
    private Comparator<Board> boardOrder()
    {
        return new Comparator<Board>()
        {
            @Override
            public int compare(Board X, Board Y)
            {
                if (X.manhattan() < Y.manhattan())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
        };
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        //set flag if 
        Solver twinSolver = new Solver(locBoard.twin());
        return true;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return this.moves;
    }
    
    // use queue to store nodes dequeued from priority queue
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
                        return !solutionQueue.isEmpty();
                    }
                    public void remove() {}
                    public Board next()
                    {
                        return solutionQueue.dequeue();
                    }
                };
            }
        };
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 0;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 6;
                
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
////            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                StdOut.println("path:");
                StdOut.println(board.toString());
            }
//        }        
    }
}