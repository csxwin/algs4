public class Board {
    private int[][] locBlocks = new int[3][3];
    private int dimension;
    private int blankX;
    private int blankY;
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
                if (locBlocks[i][j] = 0)
                {
                    //init location of blank
                    blankX = i;
                    blankY = j;
                }
            }
        }
        dimension = blocks[0].length;
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return this.dimension;
    }
    public int hamming()                   // number of blocks out of place
    {
        return 0;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return 0;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        boolean isGoal = true;
        for (int i = 0; i < blocks[0].length; i++)
        {
            for (int j = 0; j < blocks[0].length; j++)
            {
                if (locBlocks[i][j] != i * dimension + j + 1)
                {
                    isGoal = false;
                }
            }
        }
        return isGoal;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        Board newBoard;
        return newBoard;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        return true;
    }
//    public Iterable<Board> neighbors()     // all neighboring boards
//    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] initBoard = new int[3][3];
        initBoard[0][0] = 0;
        initBoard[0][1] = 1;
        initBoard[0][2] = 3;
        initBoard[1][0] = 4;
        initBoard[1][1] = 2;
        initBoard[1][2] = 5;
        initBoard[2][0] = 7;
        initBoard[2][1] = 8;
        initBoard[2][2] = 6;
        
        
    }
}