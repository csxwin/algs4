import java.util.*;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;

public class PointSET {
   private int numOfPoints = 0;
   private SET<Point2D> pointSET;
   private Point2D[] pArray;
   private int numOfPointsInside = 0;
   public         PointSET()                               // construct an empty set of points
   {
       pointSET = new SET<Point2D>();
   }
   public           boolean isEmpty()                      // is the set empty?
   {
      return numOfPoints == 0;
   }
   public               int size()                         // number of points in the set
   {
      return numOfPoints;
   }
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
      if (p == null)
      {
         throw new java.lang.IllegalArgumentException("");
      }
      numOfPoints++;
      pointSET.add(p);
   }
   public           boolean contains(Point2D p)            // does the set contain point p?
   {
      if (p == null)
      {
         throw new java.lang.IllegalArgumentException("");
      }
      return pointSET.contains(p);
   }
   public              void draw()                         // draw all points to standard draw !!!!!!!!!!!
   {
      for (Point2D p : pointSET)
      {
         p.draw();
      }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
   {
      if (rect == null)
      {
         throw new java.lang.IllegalArgumentException("");
      }
      pArray = new Point2D[size()];
      for (Point2D p : pointSET)
      {
         if (rect.contains(p))
         {
            pArray[numOfPointsInside++] = p;
         }
      }
      return new Iterable<Point2D>()
      {
         @Override
         public Iterator<Point2D> iterator()
         {
             return new Iterator<Point2D>()
             {
                private int i = numOfPointsInside;
                public boolean hasNext()
                {
                   return i > 0;
                }
                public void remove() {}
                public Point2D next()
                {
                    return pArray[--i];
                }
             };
         }
      };
   }
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
   {
      if (p == null)
      {
         throw new java.lang.IllegalArgumentException("");
      }
      Point2D nearestP = new Point2D(0, 0);
      boolean init = false;
      for (Point2D localP : pointSET)
      {
         if (!init)
         {
            // init nearestP to first point from iteration
            nearestP = localP;
            init = true;
         }
         if (p.distanceTo(localP) < p.distanceTo(nearestP))
         {
            nearestP = localP;
         }
      }
      return nearestP;
   }
   public static void main(String[] args)                  // unit testing of the methods (optional)
   {
      Point2D A = new Point2D(0, 0);
      Point2D B = new Point2D(2, 2);
      Point2D C = new Point2D(3, 2);
      Point2D D = new Point2D(4, 2);
      Point2D E = new Point2D(2, 3);
      PointSET ps = new PointSET();
      ps.insert(A);
      ps.insert(B);
      ps.insert(C);
      ps.insert(D);
      ps.insert(E);
      ps.draw();
      RectHV R = new RectHV(1, 1, 4, 4);
      for (Point2D p : ps.range(R))
      {
        System.out.printf("Here is : %s", p.toString());
      }

      Point2D F = new Point2D(-1, -1);
      System.out.printf("nearest Point to (5, 5) is : %s", ps.nearest(F).toString());
   }
}