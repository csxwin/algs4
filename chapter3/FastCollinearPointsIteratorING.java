import java.util.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints implements Iterable<LineSegment>{
    private LineSegment[] lines = new LineSegment[2];
    private int num;
    private Point[] points = new Point[2];
    private Point[] resize(int size)
    {
        Point[] newPoints = new Point[size];
        return newPoints;
    }
    private LineSegment[] resizeLines(int size)
    {
        LineSegment[] newLines = new LineSegment[size];
        for (int i = 0; i < num; i++)
        {
            newLines[i] = lines[i];
        }
        return newLines;
    }
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) throw new NullPointerException("");
        this.points = resize(points.length);
        for (int i = 0; i < this.points.length; i++)
        {
            if (points[i] == null) throw new NullPointerException("");
            for (int j = 0; j < i; j++)
            {
//                if (points[j] == points[i])
//                {
//                    throw new java.lang.IllegalArgumentException("");
//                }
            }
            this.points[i] = points[i];
        }
        num = 0;
    }
    public           int numberOfSegments()        // the number of line segments
    {
        return num;
    }
    public LineSegment[] segments()                // the line segments
    {
        Point[] ops = new Point[points.length];
//        System.out.println("Original array");
        for (int i = 0; i < points.length; i++)
        {
            ops[i] = points[i];
//            System.out.printf("%s ", ops[i].toString());
        }
//        System.out.println(" ");
        for (int i = 0; i < ops.length; i++)
        {
            Arrays.sort(ops, ops[i].slopeOrder());
//            System.out.printf("already found %d", num);
//            for (int u = 0; u < ops.length; u++)
//            {
//                System.out.printf("%s ", ops[u].toString());
//            }
//            System.out.println(" ");
            int counter = 0;
            int endIndex = 0;
            for (int m = 2; m < ops.length; m++)
            {
                //Find first group of collinear, sort them and assign them to 
                if (ops[0].slopeTo(ops[m]) == ops[0].slopeTo(ops[m - 1]))
                {
                    endIndex = m;
                    counter++;
                }
                if (ops[0].slopeTo(ops[m]) != ops[0].slopeTo(ops[m - 1]) || m == ops.length - 1)
                {
                    if (counter >= 2)
                    {
                        //sort ops[0] with ops[m - counter] ~ ops[m]
                        //form line with start and end points
                        Point[] aux = new Point[counter + 2];
                        aux[0] = ops[0];
                        for (int x = 1; x < counter + 2; x++)
                        {
                            aux[x] = ops[x -1 + endIndex - counter];
                        }
                        Arrays.sort(aux); //could print all members for debugging
//                        for (int h = 0; h < aux.length; h++)
//                        {
//                            System.out.printf("%s ", aux[h].toString());
//                        }
                        //check if lines already exist
                        LineSegment tempLine = new LineSegment(aux[0], aux[counter + 1]);
                        if (num == 0)
                        {
                            lines[num] = tempLine;
//                            System.out.printf("## found first line: %s ##\n", lines[num].toString());
                            num++;   
                        }
                        else
                        {
                            boolean isNew = true;
                            for (int w = 0; w < num; w++)
                            {
                                if (lines[w].toString().equals(tempLine.toString()))
                                {
                                    isNew = false;
                                }
                            }
                            if (isNew)
                            {
                                if (num == lines.length)
                                {
                                    lines = resizeLines(2 * lines.length);
                                }
//                                System.out.printf("## found line: %s ##\n", tempLine.toString());
                                lines[num++] = tempLine;
                            }
                        }
                    }
                    counter = 0; //reset counter and look for new possible lines
                }
            } 
//            System.out.println("Finish one element check");
            for (int j = 0; j < ops.length; j++)
            {
                ops[j] = points[j];
            }
        }
        LineSegment[] retLines = new LineSegment[num];
        for (int i = 0; i < num; i++)
        {
            retLines[i] = lines[i];
        }
        return retLines;
    }
    public Iterator<LineSegment> iterator()         // return an independent iterator over items in random order
    {
        return new LinesIterator();
    }
    private class LinesIterator implements Iterator<LineSegment>
    {
        private int i = num;
        public boolean hasNext() { return (i > 0); }
        public LineSegment next()
        {
            LineSegment tempLine = lines[i];
            i--;
            return tempLine;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove is unsupported");
        }
    }
    
    public static void main(String[] args)
    {
//        Point[] points = new Point[16];
//        points[0] = new Point(4, 6);
//        points[1] = new Point(4, 8);
//        points[2] = new Point(0, 0);
//        points[3] = new Point(-1, 1);
//        points[4] = new Point(1, 1);
//        points[5] = new Point(2, 2);
//        points[6] = new Point(3, 3);
//        points[7] = new Point(4, 1);
//        points[8] = new Point(4, 3);
//        points[9] = new Point(2, -2);
//        points[10] = new Point(1, -1);
//        points[11] = new Point(4, -5);
//        points[12] = new Point(1, 2);
//        points[13] = new Point(10, 20);
//        points[14] = new Point(-10, -20);
//        points[15] = new Point(100, 200);
//        FastCollinearPoints fcp = new FastCollinearPoints(points);
//        LineSegment[] lines = fcp.segments();
//        for (int k = 0; k < fcp.numberOfSegments(); k++)
//        {
//            System.out.printf("## found line: %s ##\n", lines[k].toString());
//        }
            // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

//        LineSegment[] lines = collinear.segments();
//        for (int k = 0; k < collinear.numberOfSegments(); k++)
//        {
//            StdOut.println(lines[k].toString());
//            lines[k].draw();
//        }
        StdDraw.show();
    }
}
