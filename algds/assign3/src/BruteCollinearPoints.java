
// import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Arrays;


/*************************************************************************
 *  Compilation:  javac Brute.java
 *  Execution:    java Brutter input.txt
 *  Dependencies: Point.java, In.java, StdDraw.java, LineSegment.java
 *
 *  Takes the name of a file as a command-line argument.
 *  Reads in an integer N followed by N pairs of points (x, y)
 *  with coordinates between 0 and 32,767, and plots them using
 *  standard drawing.
 *
 *************************************************************************/
public class BruteCollinearPoints {
    private int size; // Stores the number of points
    // private  Point[] arrayOfPoints;
    // private LineSegment[] segments;
    private ArrayList<LineSegment> ls;

    public BruteCollinearPoints(Point[] points) {
        int matchIdx;
        int lastMatchIdx = -1;
        boolean b;
//        int j, k = 0, idx = 0;
        double slope = 0;
        Point collinearFourth;
        if (points == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        /* Check if any of the input points is null */
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Input array has null points");
            }
        }

        ls = new ArrayList<>();
        size = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, size);
//        QuickX.sort(pointsCopy);
//        for (Point p :pointsCopy ) {
//            StdOut.println(p);
//        }

        for (int i = 0; i < size - 1; i++) {
            // for (int j = i+1; j < size; j++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Input point array has duplicates");
            }
            // }
        }

        /* Brute Force algorithm to check if 4 points at any time are collinear */
        for (int i = 0; i < size; i++) {
            matchIdx = -1;
            b = false; // state of slope match for 4 points
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    for (int idx = k + 1; idx < size; idx++) {
                        slope = pointsCopy[i].slopeTo(pointsCopy[j]);
                        b = Double.compare(slope, pointsCopy[j].slopeTo(pointsCopy[k])) == 0;
                        b = b && (Double.compare(slope, pointsCopy[k].slopeTo(pointsCopy[idx])) == 0);
                        // if match
                        if (b) {
//                            StdOut.println("Found a match");
//                            StdOut.println("i:" + i);
//                            StdOut.println("j:" + j);
//                            StdOut.println("k:" + k);
//                            StdOut.println("idx:" + idx);
//                            if (idx != lastMatchIdx) {
                            matchIdx = idx;
//                                StdOut.println("matchIdx: " + matchIdx);
//                            }
//                            StdOut.println("matchIdx: " + matchIdx);

                            ls.add(new LineSegment(pointsCopy[i], pointsCopy[matchIdx]));
                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments() {
        return ls.size();
    }

    public LineSegment[] segments() {
        // return (LineSegment[]) ls.toArray();
        LineSegment[] lineSegments = new LineSegment[ls.size()];
        return ls.toArray(lineSegments);
    }

}
