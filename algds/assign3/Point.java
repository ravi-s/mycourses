/*************************************************************************
 * Name:
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
            double slopeOfp1 = p.slopeTo(p1);
            double slopeOfp2 = p.slopeTo(p2);
            int val = 0;
            if (slopeOfp1 == slopeOfp2) { return val; }
            if (slopeOfp1 < slopeOfp2) { 
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
        double xcoord = x * 1.0D;
        double ycoord = y * 1.0D;
        
        double thatX = that.x * 1.0D;
        double thatY = that.y * 1.0D;
        
        /* Horizontal Line is 0 slope */
        if (0.0D == (thatY - ycoord)) {
            return 0.0D;
        }
        
        /* Vertical line is Positive Infinity */
        if (0.0D == (thatX - xcoord)) {
            return Double.POSITIVE_INFINITY;
        }
        
        if (this.equals(that)) {
            return Double.NEGATIVE_INFINITY;
        }   
       
        return (thatY - ycoord) / (thatX - xcoord);
        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.equals(that)) {
            return 0;
        }
        else {
            if ((y - that.y) < 0 || (x - that.x) < 0)
                return -1;
            else
                return 1;
        }

    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object p) {
        if (p instanceof Point) {
            Point that = (Point) p;
            if ((this.x == that.x) && (this.y == that.y))
                return true;
        }
        
        return false;
        
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
