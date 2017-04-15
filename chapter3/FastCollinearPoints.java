import java.util.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class FastCollinearPoints{
    private LineSegment[] lines = new LineSegment[2];
    private final LineSegment[] lineList;
    private final int num;
    private int currentNumOfLines;
    private final Point[] locPoints;
    private Point[][] auxPoints = new Point[2][2];
    
    private void resizeAuxPoints(int size)
    {
        Point[][] newAuxPoints = new Point[size][2];
        for (int i = 0; i < currentNumOfLines; i++)
        {
            newAuxPoints[i][0] = auxPoints[i][0];
            newAuxPoints[i][1] = auxPoints[i][1];
        }
        auxPoints = newAuxPoints;
    }
    private LineSegment[] resizeLines(int size)
    {
        LineSegment[] newLines = new LineSegment[size];
        for (int i = 0; i < currentNumOfLines; i++)
        {
            newLines[i] = lines[i];
        }
        return newLines;
    }
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more locPoints
    {
        if (points == null) throw new NullPointerException("");
        locPoints = new Point[points.length];
        for (int i = 0; i < locPoints.length; i++)
        {
            if (points[i] == null) throw new NullPointerException("");
            for (int j = 0; j < i; j++)
            {
                if (points[j].equals(points[i]))
                {
                    throw new java.lang.IllegalArgumentException("");
                }
            }
            locPoints[i] = points[i];
        }
        currentNumOfLines = 0;
        Point[] ops = new Point[locPoints.length];
        for (int i = 0; i < locPoints.length; i++)
        {
            ops[i] = locPoints[i];
        }
        for (int i = 0; i < ops.length; i++)
        {
            Arrays.sort(ops, ops[i].slopeOrder());
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
                        Point[] aux = new Point[counter + 2];
                        aux[0] = ops[0];
                        for (int x = 1; x < counter + 2; x++)
                        {
                            aux[x] = ops[x -1 + endIndex - counter];
                        }
                        Arrays.sort(aux); //could print all members for debugging
                        LineSegment tempLine = new LineSegment(aux[0], aux[counter + 1]);
                        if (currentNumOfLines == 0)
                        {
                            lines[currentNumOfLines] = tempLine;
                            auxPoints[currentNumOfLines][0] = aux[0];
                            auxPoints[currentNumOfLines][1] = aux[counter + 1];
                            currentNumOfLines++;   
                        }
                        else
                        {
                            boolean isNew = true;
                            for (int w = 0; w < currentNumOfLines; w++)
                            {
                                if (auxPoints[w][0] == aux[0] && auxPoints[w][1] == aux[counter + 1])
//                                if (lines[w].toString().equals(tempLine.toString()))
                                {
                                    isNew = false;
                                }
                            }
                            if (isNew)
                            {
                                if (currentNumOfLines == lines.length)
                                {
                                    resizeAuxPoints(2 * lines.length);
                                    lines = resizeLines(2 * lines.length);
                                }
                                lines[currentNumOfLines] = tempLine;
                                auxPoints[currentNumOfLines][0] = aux[0];
                                auxPoints[currentNumOfLines++][1] = aux[counter + 1];
                            }
                        }
                    }
                    counter = 0; //reset counter and look for new possible lines
                }
            } 
            for (int j = 0; j < ops.length; j++)
            {
                ops[j] = locPoints[j];
            }
        }
        num = currentNumOfLines;
        lineList = new LineSegment[num];
        for (int i = 0; i < num; i++)
        {
            lineList[i] = lines[i];
        }
    }
    public           int numberOfSegments()        // the number of line segments
    {
        return num;
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] retLineList = new LineSegment[num];
        for (int i = 0; i < num; i++)
        {
            retLineList[i] = lineList[i];
        }
        return retLineList;
    }    
    public static void main(String[] args)
    {
        // read the n locPoints from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] locPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            locPoints[i] = new Point(x, y);
        }
        
        // draw the locPoints
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : locPoints) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(locPoints);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
