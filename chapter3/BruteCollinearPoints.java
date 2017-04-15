import java.util.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lines = new LineSegment[2];
    private final LineSegment[] lineList;
    private final int num;
    private int currentNumOfLines;
    private final Point[] locPoints;
    
    private LineSegment[] resizeLines(int size)
    {
        LineSegment[] newLines = new LineSegment[size];
        for (int i = 0; i < currentNumOfLines; i++)
        {
            newLines[i] = lines[i];
        }
        return newLines;
    }
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 locPoints
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
            if (points[i] == null) throw new NullPointerException("");
            locPoints[i] = points[i];
        }
        currentNumOfLines = 0;
        Arrays.sort(locPoints);      
        for (int i = 0; i < locPoints.length; i++)
        {
            for (int j = i + 1; j < locPoints.length; j++)
            {
                for (int m = j + 1; m < locPoints.length; m++)
                {
                    for (int n = m + 1; n < locPoints.length; n++)
                    {
                        if (locPoints[i].slopeTo(locPoints[j]) == locPoints[j].slopeTo(locPoints[m]) 
                                && locPoints[j].slopeTo(locPoints[m]) == locPoints[m].slopeTo(locPoints[n]))
                        {
                            if (currentNumOfLines == lines.length)
                            {
                                lines = resizeLines(2 * lines.length);
                            }
                            lines[currentNumOfLines] = new LineSegment(locPoints[i], locPoints[n]);
                            currentNumOfLines++;
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(locPoints);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
