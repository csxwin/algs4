import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ; 
import edu.princeton.cs.algs4.Queue; 
import java.util.Comparator;

public class Solver {

    private MinPQ<SearchNode> pQueue = new MinPQ<SearchNode>(SearchNodeOrder());
    private MinPQ<SearchNode> pQueueTwin = new MinPQ<SearchNode>(SearchNodeOrder());
    private Queue<Board> solutionQueue = new Queue<Board>();
    private int moves = -1;
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        //dequeue node with minimum priority
        //process it: add children to game tree and priority queue
        SearchNode initNode = new SearchNode(initial, 0);
        SearchNode initNodeTwin = new SearchNode(initial.twin(), 0);
        
        pQueue.insert(initNode);
        pQueueTwin.insert(initNodeTwin);
        
        StdOut.println("start");
        
//        StdOut.println(locBoard.toString());
//        StdOut.println(locBoard.twin().toString());
            
        SearchNode currentNode;
        SearchNode PreviousNode = null;
        
        SearchNode currentNodeTwin;
        SearchNode PreviousNodeTwin = null;
        
        int bugGuard = 2000;
        boolean isGoal = false;
        boolean isGoalTwin = false;
        
        do {
            StdOut.println("PQ is as following: ");
            for (SearchNode sn : pQueue)
            {
                StdOut.printf("priority: %d moves: %d manhattan: %d\n", sn.Priority, sn.Moves, sn.SearchBoard.manhattan());
            }
            
            currentNode = pQueue.delMin();
            currentNodeTwin = pQueueTwin.delMin();
            
            StdOut.println("dequeue");
            StdOut.printf("priority = %d\n", currentNode.Priority);
            StdOut.printf("moves = %d\n", currentNode.Moves);
            StdOut.printf("manhattan = %d\n", currentNode.SearchBoard.manhattan());
            StdOut.println(currentNode.SearchBoard.toString());
                    
            solutionQueue.enqueue(currentNode.SearchBoard);
            moves++;

            for (Board childrenBoard : currentNode.SearchBoard.neighbors())
            { 
                if (PreviousNode == null || !childrenBoard.equals(PreviousNode.SearchBoard))
                {
                    StdOut.println("enqueue");
                    SearchNode childrenNode = new SearchNode(childrenBoard, currentNode.Moves + 1);
                    pQueue.insert(childrenNode);
                    StdOut.printf("priority = %d\n", childrenNode.Priority);
                    StdOut.printf("moves = %d\n", childrenNode.Moves);
                    StdOut.printf("manhattan = %d\n", childrenNode.SearchBoard.manhattan());
                    StdOut.println(childrenNode.SearchBoard.toString());
                }
            }
            
            for (Board childrenBoardTwin : currentNodeTwin.SearchBoard.neighbors())
            { 
                //check against previous board
                if (PreviousNodeTwin == null || !childrenBoardTwin.equals(PreviousNodeTwin.SearchBoard))
                {
                    SearchNode childrenNodeTwin = new SearchNode(childrenBoardTwin, currentNodeTwin.Moves + 1);
                    pQueueTwin.insert(childrenNodeTwin);
//                    StdOut.println(childrenBoardTwin.toString());
                }
            }
                                
            PreviousNode = currentNode;
            PreviousNodeTwin = currentNodeTwin;
            
            if (currentNode.SearchBoard.isGoal())
            {
                isGoal = true;
            }
            if (currentNodeTwin.SearchBoard.isGoal())
            {
                StdOut.println("twin is solvable");
                isGoalTwin = true;
                moves = -1;
            }
            
        } while (!isGoal && !isGoalTwin && (bugGuard-- > 0));
    }
    
    private class SearchNode
    {
        public int Priority;
        public int Moves = 0;
        public Board SearchBoard;
        public SearchNode(Board searchBoard, int moves)
        {
            this.SearchBoard = searchBoard;
            this.Moves = moves;
            this.Priority = (this.SearchBoard).manhattan() + this.Moves;
        }
    }
    
    private Comparator<SearchNode> SearchNodeOrder()
    {
        return new Comparator<SearchNode>()
        {
            @Override
            public int compare(SearchNode X, SearchNode Y)
            {
                if (X.Priority < Y.Priority)
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
        return (this.moves != -1);
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
//        int[][] blocks = new int[3][3];
//        blocks[0][0] = 0;
//        blocks[0][1] = 1;
//        blocks[0][2] = 3;
//        blocks[1][0] = 4;
//        blocks[1][1] = 2;
//        blocks[1][2] = 5;
//        blocks[2][0] = 7;
//        blocks[2][1] = 8;
//        blocks[2][2] = 6;
              
//        blocks[0][0] = 1;
//        blocks[0][1] = 2;
//        blocks[0][2] = 3;
//        blocks[1][0] = 0;
//        blocks[1][1] = 4;
//        blocks[1][2] = 5;
//        blocks[2][0] = 7;
//        blocks[2][1] = 8;
//        blocks[2][2] = 6;
        
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
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            StdOut.println("Solution Path:");
//            for (Board board : solver.solution())
//            {
//                StdOut.println(board.toString());
//            }
//        }        
    }
}