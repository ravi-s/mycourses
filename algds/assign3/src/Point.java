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

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    // compare points by slope
    // YOUR DEFINITION HERE
    public  Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            if (p1 == null || p2 == null) {
                throw new NullPointerException("Cannot compare with null point");
            }

            double slopeWithp1 = this.slopeTo(p1);
            double slopeWithp2 = this.slopeTo(p2);
            int val = Integer.MIN_VALUE;
            int compareResult = Double.compare(slopeWithp1, slopeWithp2);

            if (0 == compareResult) {
                val = 0;
            } else if (-1 == compareResult) {
                val = -1;
            } else if (1 == compareResult) {
                val = 1;
            }
            return val;
        };
    }



    // unit test
    public static void main(String[] args) {

        Point p;
        StdOut.println("Test Case 1 - print a slope of point it should be 0");
        p = new Point(16000, 16000);
        StdOut.println(p);
        StdOut.println(p.slopeTo(p));
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

        if (this.pointEquals(that)) {
            return Double.NEGATIVE_INFINITY;
        }
        /* Horizontal Line is 0 slope */
        else if (0 == Double.compare(ycoord, thatY)) {
            return 0.0D;
        }

        /* Vertical line is Positive Infinity */
        else if (0 == Double.compare(xcoord, thatX)) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (thatY - ycoord)
                    / (thatX - xcoord);
        }

    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException("Compare to point is null");
        }
        if (this.pointEquals(that)) {
            return 0;
        }
        if ((y < that.y) || ((y == that.y) && (x < that.x))) {
            return -1;
        }
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private boolean pointEquals(Point p) {
        return (this.x == p.x) && (this.y == p.y);
    }
}
