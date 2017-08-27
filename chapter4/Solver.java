import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;

public class Solver {

    private MinPQ<SearchNode> pQueue = new MinPQ<SearchNode>(SearchNodeOrder());
    private MinPQ<SearchNode> pQueueTwin = new MinPQ<SearchNode>(SearchNodeOrder());
    private Stack<SearchNode> solutionStack = new Stack<SearchNode>();
    private int moves = -1;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        //dequeue node with minimum priority
        //process it: add children to game tree and priority queue
        SearchNode initNode = new SearchNode(null, initial, 0);
        SearchNode initNodeTwin = new SearchNode(null, initial.twin(), 0);

        pQueue.insert(initNode);
        pQueueTwin.insert(initNodeTwin);

      //  StdOut.println("start");

        // StdOut.println(initial.toString());
        // StdOut.println(initial.twin().toString());

        SearchNode currentNode;// = pQueue.delMin();
        SearchNode currentNodeTwin;// = pQueueTwin.delMin();

//        int bugGuard = 200000;
        boolean isGoal = false;
        boolean isGoalTwin = false;

        do {
//            StdOut.println("PQ is as following: ");
//            for (SearchNode sn : pQueue)
//            {
//                StdOut.printf("priority: %d moves: %d manhattan: %d\n", sn.Priority, sn.Moves, sn.SearchBoard.manhattan());
//            }

            currentNode = pQueue.delMin();
            currentNodeTwin = pQueueTwin.delMin();

            if (currentNode.SearchBoard.isGoal())
            {
                isGoal = true;
                SearchNode node = currentNode;
                while (node != null)
                {
                  this.moves++;
                  solutionStack.push(node);
                  node = node.PreviousNode;
                }
                // StdOut.println("Found");
                break;
            }
            if (currentNodeTwin.SearchBoard.isGoal())
            {
                // StdOut.println("twin is solvable");
                isGoalTwin = true;
                moves = -1;
                break;
            }

            // StdOut.println("dequeue");
            // StdOut.printf("priority = %d\n", currentNode.Priority);
            // StdOut.printf("moves = %d\n", currentNode.Moves);
            // StdOut.printf("manhattan = %d\n", currentNode.SearchBoard.manhattan());
            // StdOut.println(currentNode.SearchBoard.toString());

            for (Board childrenBoardTwin : currentNodeTwin.SearchBoard.neighbors())
            {
                  if (currentNodeTwin.PreviousNode == null || !childrenBoardTwin.equals(currentNodeTwin.PreviousNode.SearchBoard))
                  {
                       SearchNode childrenNodeTwin = new SearchNode(currentNodeTwin, childrenBoardTwin, currentNodeTwin.Moves + 1);
//                      SearchNode childrenNodeTwin = new SearchNode(childrenBoardTwin, currentNodeTwin.Moves + 1);
                      pQueueTwin.insert(childrenNodeTwin);
  //                    StdOut.println(childrenBoardTwin.toString());
                  }
            }

            for (Board childrenBoard : currentNode.SearchBoard.neighbors())
            {
                if (currentNode.PreviousNode == null || !childrenBoard.equals(currentNode.PreviousNode.SearchBoard))
                {
//                    StdOut.println("enqueue");
                     SearchNode childrenNode = new SearchNode(currentNode, childrenBoard, currentNode.Moves + 1);
//                    SearchNode childrenNode = new SearchNode(childrenBoard, currentNode.Moves + 1);
                    pQueue.insert(childrenNode);
//                    StdOut.printf("priority = %d\n", childrenNode.Priority);
//                    StdOut.printf("moves = %d\n", childrenNode.Moves);
//                    StdOut.printf("manhattan = %d\n", childrenNode.SearchBoard.manhattan());
//                    StdOut.println(childrenNode.SearchBoard.toString());
                }
            }
        } while (!isGoal && !isGoalTwin); // && (bugGuard-- > 0));
        // StdOut.printf("iteration: %d", 200000 - bugGuard);
        // push boards to Stack
    }

    private class SearchNode
    {
        public int Priority;
        public int Moves = 0;
        public Board SearchBoard;
        public SearchNode PreviousNode;

//        public SearchNode(Board searchBoard, int moves)
//        {
//            this.SearchBoard = searchBoard;
//            this.Moves = moves;
//            this.Priority = (this.SearchBoard).manhattan() + this.Moves;
//        }

         public SearchNode(SearchNode previousNode, Board searchBoard, int moves)
         {
             this.PreviousNode = previousNode;
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
                        return !solutionStack.isEmpty();
                    }
                    public void remove() {}
                    public Board next()
                    {
                        return solutionStack.pop().SearchBoard;
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
