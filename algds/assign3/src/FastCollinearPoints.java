import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    // Keep a set data structure to store collinear points
    // found for the first time for a data set

    private int size; // Stores the number of points
    private ArrayList<LineSegment> ls;


    public FastCollinearPoints(Point[] points) {

        /* Check corner cases */
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
        QuickX.sort(pointsCopy);
        // StdOut.println("Quicksort for duplicate checks");

        /*
         *  Fast collinear algorithm
         *
         *  */
        Comparator<Point> ctor;


        /* StdOut.println("After Custom comparator");
        for (Point p : pointsCopy) {
            StdOut.println(p);
        } */
//        for (int i = 0; i < size - 1; i++) {
//            StdOut.println(pointsCopy[i].slopeTo(pointsCopy[i + 1]));
//        }

        for (int i = 0; i < (size - 1); i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Input point array has duplicates");
            }
        }

        int maxIndex;
        int totalCollinearPoints;
        int previousMax = -1;
        int length = 0;
        for (int i = 0; i < size; i++) {
            maxIndex = -1;
            totalCollinearPoints = 0;
            //previousMax = -1;
            ctor = pointsCopy[i].slopeOrder();
            Arrays.sort(pointsCopy, ctor);

            for (Point p : pointsCopy) {
                StdOut.println(p);
            }
            // StdOut.println("i:" + i);
            for (int j = 0; j < size - 1; j++) {
//                StdOut.println("i:" + i);
//                StdOut.println("j:" + j);
//                if (match) break;
                double s1 = pointsCopy[j].slopeTo(pointsCopy[j + 1]);
                double s2 = pointsCopy[j].slopeTo(pointsCopy[j + 2]);

                StdOut.println("<<<");
                StdOut.println(pointsCopy[j]);
                StdOut.println(pointsCopy[j+1]);
                StdOut.println(pointsCopy[j + 2]);
                StdOut.println(">>>");

                if (Double.compare(s1, s2) != 0) {
                    StdOut.println(s1);
                    StdOut.println(s2);
                    StdOut.println("No match");
                } else {
                    if ((j + 3) > (size - 1)) break;
                    double s3 = pointsCopy[j].slopeTo(pointsCopy[j + 3]);

//                    lastMatch = j + 1;
                    // maxIndex = lastMatch;
                    // Check for minimum next 1 points
                    if (Double.compare(s1, s3) == 0) {
                        // Found 3, see if more points are collinear
                        maxIndex = j + 2;
                        totalCollinearPoints = 4;
//                        StdOut.println("> 3 points: " + points[j + 2]);
                        StdOut.println("Found minimum 4");
                        StdOut.println("s1:" + s1);
                        StdOut.println("s2:" + s2);
                        StdOut.println("s3:" + s3);

                        for (int k = maxIndex + 1; k < size; k++) {
                            double s = pointsCopy[j].slopeTo(pointsCopy[k]);
                            StdOut.println("s:" + s);
                            if (Double.compare(s1, s) == 0) {
                                maxIndex = k;
                                totalCollinearPoints++;
                            }
                        }
                        // ls.add(new LineSegment(points[i], points[lastMatch]));
                    }
                }
                if (totalCollinearPoints >= 4) break;
            }
            StdOut.println("totalCollinearPoints: " + totalCollinearPoints);


            if (totalCollinearPoints > 0) {

                StdOut.println("<<< " + pointsCopy[i]);
                StdOut.println(pointsCopy[maxIndex] + " >>>");
                ls.add(new LineSegment(pointsCopy[i], pointsCopy[maxIndex]));
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
