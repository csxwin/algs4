import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] locBlocks = new int[3][3];
    
    private int[][] neighbor1 = new int[3][3];
    private int[][] neighbor2 = new int[3][3];
    private int[][] neighbor3 = new int[3][3];
    private int[][] neighbor4 = new int[3][3];
    private Board[] neighborBoard = new Board[4];
                
    private int dimension;
    private int numOfBlocksOutOfSpace = 0;
    private int sumOfManhattanDistance = 0;
    private int blankX = 0;
    private int blankY = 0;
    
    private int numOfNeighbor = 0;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        //for now, allocate a big two dimension array
        //todo: dynamic creation
//        lockBlocks = new int[3][3];
        //copy value from blocks to locBlocks
        for (int i = 0; i < blocks[0].length; i++)
        {
            for (int j = 0; j < blocks[0].length; j++)
            {
                locBlocks[i][j] = blocks[i][j];
                if (locBlocks[i][j] == 0)
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
                neighbor1[i][j] = locBlocks[i][j];
                neighbor2[i][j] = locBlocks[i][j];                
                neighbor3[i][j] = locBlocks[i][j];
                neighbor4[i][j] = locBlocks[i][j];
                
                
                if (locBlocks[i][j] != 0)
                {
                    if (locBlocks[i][j] != i * dimension + j + 1)
                    {
                        numOfBlocksOutOfSpace++;
                        sumOfManhattanDistance += (i >= (locBlocks[i][j] - 1) / dimension ? 
                                                        i - (locBlocks[i][j] - 1) / dimension : (locBlocks[i][j] - 1) / dimension - i)
                                                       + (j >= (locBlocks[i][j] - 1) % dimension ? 
                                                              j - (locBlocks[i][j] - 1) % dimension : (locBlocks[i][j] - 1) % dimension - j);
                        
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
            int temp = neighbor1[blankX - 1][blankY];
            neighbor1[blankX - 1][blankY] = neighbor1[blankX][blankY];
            neighbor1[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighbor1);
        }
        if (blankX < 2)
        {
            int temp = neighbor2[blankX + 1][blankY];
            neighbor2[blankX + 1][blankY] = neighbor2[blankX][blankY];
            neighbor2[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighbor2);
        }
        if (blankY > 0)
        {
            int temp = neighbor3[blankX][blankY - 1];
            neighbor3[blankX][blankY - 1] = neighbor3[blankX][blankY];
            neighbor3[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighbor3);
        }
        if (blankY < 2)
        {
            int temp = neighbor4[blankX][blankY + 1];
            neighbor4[blankX][blankY + 1] = neighbor4[blankX][blankY];
            neighbor4[blankX][blankY] = temp;
            neighborBoard[numOfNeighbor++] = new Board(neighbor4);
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
                if (!(i == 2 & j == 2))
                {
                    if (locBlocks[i][j] != i * dimension + j + 1)
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
            swapX1 = StdRandom.uniform(3);
            swapY1 = StdRandom.uniform(3);
        } while (swapX1 == blankX && swapY1 == blankY);
        do {
            swapX2 = StdRandom.uniform(3);
            swapY2 = StdRandom.uniform(3);
        } while (swapX2 == blankX && swapY2 == blankY);
        
        int[][] twinBlock = new int[3][3];
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                twinBlock[i][j] = locBlocks[i][j];
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
                s.append(String.format("%2d ", locBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] initBoard = new int[3][3];
        int[][] initBoard2 = new int[3][3];
        
        initBoard[0][0] = 5;
        initBoard[0][1] = 1;
        initBoard[0][2] = 7;
        initBoard[1][0] = 6;
        initBoard[1][1] = 8;
        initBoard[1][2] = 0;
        initBoard[2][0] = 3;
        initBoard[2][1] = 2;
        initBoard[2][2] = 4;
        
        initBoard2[0][0] = 1;
        initBoard2[0][1] = 2;
        initBoard2[0][2] = 3;
        initBoard2[1][0] = 4;
        initBoard2[1][1] = 5;
        initBoard2[1][2] = 6;
        initBoard2[2][0] = 7;
        initBoard2[2][1] = 8;
        initBoard2[2][2] = 0;
        
        Board testBoard = new Board(initBoard);
        Board ReferenceBoard = new Board(initBoard2);
        
        System.out.println(testBoard.toString());
        if (testBoard.isGoal())
        {
            System.out.println("Is Goal!");
        }
        else
        {
            System.out.println("Is not Goal!");          
        }
        
        if (testBoard.equals(ReferenceBoard))
        {
            System.out.println("Equal");
        }
        else
        {
            System.out.println("Not Equal");          
        }
        System.out.println("twin of reference Board: " + (ReferenceBoard.twin()).toString());
        for (Board neighborBoard : testBoard.neighbors())
        {
            System.out.println(neighborBoard.toString());
        }
        System.out.printf("\n hamming: %d", testBoard.hamming());
        System.out.printf("\n manhattan: %d", testBoard.manhattan());
    }
}
