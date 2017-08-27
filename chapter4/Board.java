import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
public class Board {
    private int[][] initBlocks; //initial blocks
    private int[][] neighborBlock1;  //neigbor blocks
    private int[][] neighborBlock2;  //neigbor blocks
    private int[][] neighborBlock3;  //neigbor blocks
    private int[][] neighborBlock4;  //neigbor blocks
    private Board[] neighborBoard = new Board[4];

    private int dimension;
    private int numOfBlocksOutOfSpace = 0;
    private int sumOfManhattanDistance = 0;
    private int blankX = 0;
    private int blankY = 0;

    private int numOfNeighbor = 0;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        initBlocks = new int[blocks[0].length][blocks[0].length];
        neighborBlock1 = new int[blocks[0].length][blocks[0].length];
        neighborBlock2 = new int[blocks[0].length][blocks[0].length];
        neighborBlock3 = new int[blocks[0].length][blocks[0].length];
        neighborBlock4 = new int[blocks[0].length][blocks[0].length];
        for (int i = 0; i < blocks[0].length; i++)
        {
            for (int j = 0; j < blocks[0].length; j++)
            {
                initBlocks[i][j] = blocks[i][j];
                // create neighbor blocks
                neighborBlock1[i][j] = initBlocks[i][j];
                neighborBlock2[i][j] = initBlocks[i][j];
                neighborBlock3[i][j] = initBlocks[i][j];
                neighborBlock4[i][j] = initBlocks[i][j];
                if (initBlocks[i][j] == 0)
                {
                    //init location of blank
                    blankX = i;
                    blankY = j;
                }
            }
        }
        dimension = blocks[0].length;

        /*
         * compute sum of distance between blocks and goal
         */
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (initBlocks[i][j] != 0)
                {
                    if (initBlocks[i][j] != i * dimension + j + 1)
                    {
                        numOfBlocksOutOfSpace++;
                        sumOfManhattanDistance += (i >= (initBlocks[i][j] - 1) / dimension ?
                                                        i - (initBlocks[i][j] - 1) / dimension : (initBlocks[i][j] - 1) / dimension - i)
                                                       + (j >= (initBlocks[i][j] - 1) % dimension ?
                                                              j - (initBlocks[i][j] - 1) % dimension : (initBlocks[i][j] - 1) % dimension - j);

                    }
                }
            }
        }
    }

    private void findAllNeighbor()
    {
        numOfNeighbor = 0;
        if (blankX > 0)
        {
            int temp = neighborBlock1[blankX - 1][blankY];
            neighborBlock1[blankX - 1][blankY] = neighborBlock1[blankX][blankY];
            neighborBlock1[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighborBlock1);
        }
        if (blankX < this.dimension - 1)
        {
            int temp = neighborBlock2[blankX + 1][blankY];
            neighborBlock2[blankX + 1][blankY] = neighborBlock2[blankX][blankY];
            neighborBlock2[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighborBlock2);
        }
        if (blankY > 0)
        {
            int temp = neighborBlock3[blankX][blankY - 1];
            neighborBlock3[blankX][blankY - 1] = neighborBlock3[blankX][blankY];
            neighborBlock3[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighborBlock3);
        }
        if (blankY < this.dimension - 1)
        {
            int temp = neighborBlock4[blankX][blankY + 1];
            neighborBlock4[blankX][blankY + 1] = neighborBlock4[blankX][blankY];
            neighborBlock4[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighborBlock4);
        }
    }
    public int dimension()                 // board dimension n
    {
        return this.dimension;
    }
    public int hamming()                   // number of blocks out of place
    {
        return numOfBlocksOutOfSpace;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return sumOfManhattanDistance;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        boolean isGoal = true;
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (!(i == (this.dimension - 1) & j == (this.dimension - 1)))
                {
                    if (initBlocks[i][j] != i * dimension + j + 1)
                    {
                        isGoal = false;
                    }
                }
            }
        }
        return isGoal;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int swapX1;
        int swapY1;
        int swapX2;
        int swapY2;
        do {
            swapX1 = StdRandom.uniform(this.dimension);
            swapY1 = StdRandom.uniform(this.dimension);
        } while (swapX1 == blankX && swapY1 == blankY);
        do {
            swapX2 = StdRandom.uniform(this.dimension);
            swapY2 = StdRandom.uniform(this.dimension);
        } while (swapX2 == blankX && swapY2 == blankY);

        int[][] twinBlock = new int[dimension][dimension];
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                twinBlock[i][j] = initBlocks[i][j];
            }
        }
        int temp = twinBlock[swapX1][swapY1];
        twinBlock[swapX1][swapY1] = twinBlock[swapX2][swapY2];
        twinBlock[swapX2][swapY2] = temp;

        Board newBoard = new Board(twinBlock);
        return newBoard;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y != null && this.toString().equals(((Board)y).toString()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        findAllNeighbor();
        return new Iterable<Board>()
        {
            @Override
            public Iterator<Board> iterator()
            {
                return new Iterator<Board>()
                {
                    private int i = numOfNeighbor;
                    public boolean hasNext()
                    {
                        return (i > 0);
                    }
                    public void remove() {}
                    public Board next()
                    {
                        return neighborBoard[--i];
                    }
                };
            }
        };
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", initBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
//        int[][] initBoard = new int[2][2];
//        int[][] initBoard2 = new int[3][3];
//        //
//        initBoard[0][0] = 1;
//        initBoard[0][1] = 2;
//        // initBoard[0][2] = 7;
//        initBoard[1][0] = 3;
//        initBoard[1][1] = 0;
//        // initBoard[1][2] = 0;
//        // initBoard[2][0] = 3;
//        // initBoard[2][1] = 2;
//        // initBoard[2][2] = 4;
//        //
//        initBoard2[0][0] = 1;
//        initBoard2[0][1] = 2;
//        initBoard2[0][2] = 3;
//        initBoard2[1][0] = 4;
//        initBoard2[1][1] = 5;
//        initBoard2[1][2] = 6;
//        initBoard2[2][0] = 7;
//        initBoard2[2][1] = 8;
//        initBoard2[2][2] = 0;
//
//        Board testBoard = new Board(initBoard);
//        Board ReferenceBoard = new Board(initBoard2);
//
//        System.out.println(testBoard.toString());
//        if (testBoard.isGoal())
//        {
//            System.out.println("Is Goal!");
//        }
//        else
//        {
//            System.out.println("Is not Goal!");
//        }
//
//        if (testBoard.equals(ReferenceBoard))
//        {
//            System.out.println("Equal");
//        }
//        else
//        {
//            System.out.println("Not Equal");
//        }
//        System.out.println("twin of reference Board: " + (ReferenceBoard.twin()).toString());
//        for (Board neighborBoard : testBoard.neighbors())
//        {
//            System.out.println(neighborBoard.toString());
//        }
//        System.out.printf("\n hamming: %d", testBoard.hamming());
//        System.out.printf("\n manhattan: %d", testBoard.manhattan());
        
        
        
              // create initial board from file
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
          for (int j = 0; j < n; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      
              if (initial.isGoal())
        {
            System.out.println("Is Goal!");
        }
    }
}
