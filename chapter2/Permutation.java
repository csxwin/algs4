import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {   
    public static void main(String[] args)
    {
        String[] inputStrArray = new String[100];
        int m;
        int n = 0;
        int elemNum = 0;
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            inputStrArray[elemNum] = StdIn.readString();
            elemNum++;
            if (elemNum == inputStrArray.length)
            {
                String[] temp = new String[elemNum * 2];
                for (int i = 0; i < elemNum; i++)
                {
                    temp[i] = inputStrArray[i];
                }
                inputStrArray = temp;
            }
        }
        int[] isPrinted = new int[elemNum];
        n = 0;
        while (n++ < k) {
            do {
                m = StdRandom.uniform(elemNum);
            } while (isPrinted[m] == 1);
            isPrinted[m] = 1;
            StdOut.println(inputStrArray[m]);
        }
    }
}
