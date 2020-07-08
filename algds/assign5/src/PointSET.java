import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

/**
 * ***********************************************************************
 * Compilation:  javac PointSET.java
 * <p>
 * PointSet
 * - point set abstraction that represents a set of points in a plane
 *
 * @author Ravi S
 * Created: 18-March-2015
 * Modified: 24-June-2020
 */
public class PointSET {
    private final SET<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new SET<>();
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * Add the point to the set
     * (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (null == p) throw
                new IllegalArgumentException("2D point is null");
        set.add(p);
    }

    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (null == p) throw
                new IllegalArgumentException("2D point is null\n");
        return set.contains(p);
    }


    /**
     * draw all points to standard draw
     */
    public void draw() {
        /*
         * For each point in the set, draw the point to the
         * a window established by StdDraw
         *
         */
        for (Point2D p : set) {
            p.draw();
        }
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        /*
         * Check: for each point in the set, check if
         * the point is contained in the rectangle.
         * If contained, add it to a queue and return the queue
         */
        if (null == rect) throw new IllegalArgumentException("rect is null\n");

        SET<Point2D> point2DSET = new SET<>();

        // Time proportional to number of elements in the set
        for (Point2D p : set) {
            if (rect.contains(p))
                point2DSET.add(p);
        }
        return point2DSET;
    }

    /**
     * @return a nearest neighbor in the set to point p;
     * null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (null == p) {
            throw new IllegalArgumentException("Input parameter Point2D p is null\n");
        }

        if (set.isEmpty()) return null;
        /*
         * For each point in set, calculate distance of p with
         * each point, return the point with least distance
         */
        double minimum = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D point : this.set) {
            double distsqr = point.distanceSquaredTo(p);
            if (distsqr < minimum) {
                minimum = distsqr;
                nearest = point;
            }
        }
        return nearest;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {


        PointSET pointset = new PointSET();
//        Point2D p = new Point2D(0.7, 0.2);
//        pointset.insert(p);
//        p = new Point2D(0.5, 0.4);
//        pointset.insert(p);
//        p = new Point2D(0.2, 0.3);
//        pointset.insert(p);
//        p = new Point2D(0.4, 0.7);
//        pointset.insert(p);
//        p = new Point2D(0.9, 0.6);
//        pointset.insert(p);

        Point2D p = new Point2D(0.821653, 0.300758);
        pointset.insert(p);
        p = new Point2D(0.0, 0.0);
        pointset.insert(p);
        p = new Point2D(0.069324, 0.275381);
        pointset.insert(p);
        p = new Point2D(1.0, 1.0);
        pointset.insert(p);


        Point2D queryPoint = new Point2D(0.412109375, 0.564453125);
        Point2D nearest = pointset.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);
        }

        queryPoint = new Point2D(0.3359375, 0.62890625);
        nearest = pointset.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);
        }

        queryPoint = new Point2D(0.50390625, 0.841796875);
        nearest = pointset.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);
        }

        queryPoint = new Point2D(0.681640625, 0.802734375);
        nearest = pointset.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);
        }
        queryPoint = new Point2D(0.357421875, 0.359375);
        nearest = pointset.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);
        }

//         String filename = args[0];
//        In in = new In(filename);
//        Point2D p;
//        PointSET pointset = new PointSET();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            p = new Point2D(x, y);
//            pointset.insert(p);
//        }
//
//        RectHV r = new RectHV(0.009765625, 0.091796875, 0.255859375, 0.41796875);
//
//        StdOut.println("Points in query Rectangle: " + r);
//
//        for (Point2D pt: pointset.range(r))
//            StdOut.println(pt);
//
//        // Draw the points
//        StdDraw.show(0);
//        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(.01);
//        pointset.draw();
//        StdDraw.show(0);


    }
}
