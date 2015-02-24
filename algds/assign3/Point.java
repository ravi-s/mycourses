/*************************************************************************
  * Name: Ravi S
  * Email:
  *
  * Compilation:  javac Point.java
  * Execution:
  * Dependencies: StdDraw.java
  *
  * Description: An immutable data type for points in the plane.
  *
  *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    // compare points by slope
    // YOUR DEFINITION HERE
    public  final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            // Compare this with p1
            Point p = new Point(x, y);
            double slopeWithp1 = p.slopeTo(p1);
            double slopeWithp2 = p.slopeTo(p2);
            
            int val;
            
            if (slopeWithp1 == slopeWithp2) { val = 0; }
            else if (slopeWithp1 < slopeWithp2) { 
                val = -1; 
            }
            else { val = 1; }
            return val;
        }
        
    };       
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }
    
    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    /**
     * slope between this point and that point
     */
    public double slopeTo(Point that) {
        // Do double arithmetic
        Number xcoord = new Double(x * 1.0D);
        Number ycoord = new Double(y * 1.0D);
        
        Number thatX = new Double(that.x * 1.0D);
        Number thatY = new Double(that.y * 1.0D);
        
        if (this.pointEquals(that)) {
            return Double.NEGATIVE_INFINITY;
        }   
        
        
        /* Horizontal Line is 0 slope */
        else if (0 == ((Double) ycoord).compareTo((Double) thatY)) {
            return 0.0D;
        }
        
        /* Vertical line is Positive Infinity */
        else if (0 == ((Double) xcoord).compareTo((Double) thatX)) {
            return Double.POSITIVE_INFINITY;
        }
        
        
       else  return ((Double) thatY - (Double) ycoord) 
            / ((Double) thatX - (Double) xcoord);
        
    }
    
    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.pointEquals(that)) {
            return 0;
        }
        if ((y < that.y) || ((y == that.y) && (x < that.x)))  { return  -1; }
        //else if (y > that.y) { return 1; }
//            else if ((y == that.y) && (x - that.x) < 0) {
//                return -1;
//            }
//            else
//                return 1;
        
        return 1;
    }
    
    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    
    private boolean pointEquals(Object p) {
        if (p instanceof Point) {
            Point that = (Point) p;
            if ((this.x == that.x) && (this.y == that.y))
                return true;
        }
        
        return false;
        
    }
    
    // unit test
    public static void main(String[] args) {
        
        Point p;
        StdOut.println("Test Case 1 - print a slope of point it should be 0");
        p = new Point(16000, 16000);
        StdOut.println(p);
        StdOut.println(p.slopeTo(p));
    }
}
