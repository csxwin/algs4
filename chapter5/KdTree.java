import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;

public class KdTree {
   private int numOfPoints = 0;
   private int numOfPointsInside = 0;
   private static class Node
   {
      public Point2D point;
      public RectHV rect;
      public Node left;
      public Node right;
      public boolean IsVertical;
      public Node()
      {
         point = new Point2D(0, 0);
         left = null;
         right = null;
      }
      public Node(Point2D pointVal)
      {
         point = pointVal;
         left = null;
         right = null;
      }
      public void insert(Point2D newPoint)
      {
         if (IsVertical && (newPoint.x() >= this.point.x())
         || !IsVertical && (newPoint.y() >= this.point.y()))
         {
            // insert onto right node
            if (this.right == null)
            {
               this.right = new Node(newPoint);
               // reverse IsVertical property at next level
               this.right.IsVertical = !this.IsVertical;
               if (this.IsVertical)
               {
                   this.right.rect = new RectHV(this.point.x(), this.rect.ymin(),
                                                 this.rect.xmax(), this.rect.ymax());
               }  
               else
               {
                  this.right.rect = new RectHV(this.rect.xmin(), this.point.y(),
                                                 this.rect.xmax(), this.rect.ymax());
               }
            }
            else
            {
               this.right.insert(newPoint);
            }
         }
         else
         {
            if (this.left == null)
            {
               this.left = new Node(newPoint);
               // reverse IsVertical property at next level
               this.left.IsVertical = !this.IsVertical;
               if (this.IsVertical)
               {
                  this.left.rect = new RectHV(this.rect.xmin(), this.rect.ymin(),
                                                this.point.x(), this.rect.ymax());
               }
               else
               {
                  this.left.rect = new RectHV(this.rect.xmin(), this.rect.ymin(),
                                                this.rect.xmax(), this.point.y());
               }
            }
            else
            {
               this.left.insert(newPoint);
            }
         }
      }
      public boolean contains(Point2D targetPoint)
      {
         if (this.point == targetPoint)
         {
            return true;
         }
         else if (this.IsVertical && (targetPoint.x() >= this.point.x())
         || !this.IsVertical && (targetPoint.y() >= this.point.y()))
         {
            if (this.right != null)
            {
               // continue searching at right subtrees
               return this.right.contains(targetPoint);
            }
            else
            {
               return false;
            }
         }
         else
         {
            if (this.left != null)
            {
               // continue searching at left subtrees
               return this.left.contains(targetPoint);
            }
            else
            {
               return false;
            }
         }
      }
      public void draw()
      {
         StdDraw.setPenColor(StdDraw.BLACK);
         StdDraw.setPenRadius(0.01);
         StdDraw.point(this.point.x(), this.point.y());
         if (this.IsVertical)
         {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
         }
         else
         {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
         }
         if (this.left != null)
         {
            System.out.printf("%s\n", this.left.point.toString());
            this.left.draw();
         }
         if (this.right != null)
         {
            System.out.printf("%s\n", this.right.point.toString());
            this.right.draw();
         }
      }
   }
   private Node root;
   public         KdTree()                               // construct an empty set of points
   {
      root = null;
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
      // if KdTree is empty, insert
      if (root == null)
      {
         root = new Node(p);
         root.IsVertical = true;
         root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      }
      else
      {
         // insert p onto subtrees
         root.insert(p);
      }
   }
   public           boolean contains(Point2D p)            // does the set contain point p?
   {
      if (p == null)
      {
         throw new java.lang.IllegalArgumentException("");
      }
      if (root == null)
      {
         return false;
      }
      else
      {
         return root.contains(p);
      }
   }
   public              void draw()                         // draw all points to standard draw !!!!!!!!!!!
   {
      root.draw();
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
   {
      if (rect == null)
      {
         throw new java.lang.IllegalArgumentException("");
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
                   return false;
                }
                public void remove() {}
                public Point2D next()
                {
                   return null;
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
      return nearestP;
   }
   public static void main(String[] args)                  // unit testing of the methods (optional)
   {
      KdTree kdTree = new KdTree();
      String filename = "/kdtree-testing/kdtree/circle10.txt";
      In in = new In(filename);
      while(!in.isEmpty())
      {
         double x = in.readDouble();
         double y = in.readDouble();
         Point2D p = new Point2D(x, y);
         kdTree.insert(p);
      }
      System.out.printf("size of tree is : %d", kdTree.size());
      kdTree.draw();

      // if (kdTree.contains(D))
      // {
      //     System.out.println("contain D");
      // }

      // RectHV R = new RectHV(1, 1, 4, 4);
      // for (Point2D p : kdTree.range(R))
      // {
      //   System.out.printf("Here is : %s", p.toString());
      // }
      //
      // Point2D F = new Point2D(-1, -1);
      // System.out.printf("nearest Point to (5, 5) is : %s", kdTree.nearest(F).toString());
   }
}