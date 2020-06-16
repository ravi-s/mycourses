import edu.princeton.cs.algs4.QuickX;

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

        // For tracking sub segments of points
        Point[] startArray = new Point[size];
        Point[] endArray = new Point[size];

        /*
         *  Fast collinear algorithm
         *
         *  */
        Comparator<Point> ctor;

        ctor = pointsCopy[0].slopeOrder();
        Arrays.sort(pointsCopy, ctor);

//        StdOut.println("After Custom comparator");
//        for (Point p : pointsCopy) {
//            StdOut.println(p);
//        }
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
        for (int i = 0; i < size; i++) {
            maxIndex = -1;
            totalCollinearPoints = 0;
            int j = i + 1;
//            StdOut.println("i:" + i);
            // StdOut.println("i:" + i);
            // for (int j = i + 1; j < size - 2; j++) {
            while (j < size - 2) {
//                StdOut.println("j:" + j);
                double s1 = pointsCopy[i].slopeTo(pointsCopy[j]);
                double s2 = pointsCopy[i].slopeTo(pointsCopy[j + 1]);
                if (Double.compare(s1, s2) == 0) {

                    if ((j + 2) > (size - 1)) break;
                    double s3 = pointsCopy[j].slopeTo(pointsCopy[j + 2]);
                    // Check for minimum next 1 points
                    if (Double.compare(s1, s3) == 0) {
                        // Found 3, see if more points are collinear
                        maxIndex = j + 2;
                        totalCollinearPoints = 4;
//                        StdOut.println("> 3 points: " + points[j + 2]);
//                        StdOut.println("Found minimum 4");
//                        StdOut.println("s1:" + s1);
//                        StdOut.println("s2:" + s2);
//                        StdOut.println("s3:" + s3);

                        for (int k = maxIndex + 1; k < size; k++) {
                            double s = pointsCopy[i].slopeTo(pointsCopy[k]);
//                            StdOut.println("s:" + s);
                            if (Double.compare(s1, s) == 0) {
                                maxIndex = k;
                                totalCollinearPoints++;
                            }
                        }
//                        StdOut.println("totalCollinearPoints: " + totalCollinearPoints);
//                        StdOut.println("maxIndex: " + maxIndex);
                        if (totalCollinearPoints >= 4 && startArray[maxIndex] == null) {
                            startArray[maxIndex] = pointsCopy[i];
//                            StdOut.println("Start Array");
//                            for (Point pt : startArray) {
//                                StdOut.println(pt);
//                            }
                        }
                        if (maxIndex != -1) {
                            // This index is used to compare in start array point for minimum.
                            if (endArray[maxIndex] != null && endArray[maxIndex] == pointsCopy[maxIndex]) {
                                // Compare distance between potential points of line segment
                                if (startArray[maxIndex].compareTo(pointsCopy[i]) < 0) {
                                    startArray[maxIndex] = pointsCopy[i];
                                    // ls.add(new LineSegment(p[0], pointsCopy[maxIndex]));
                                }
                            } else {
                                endArray[maxIndex] = pointsCopy[maxIndex];
                            }
//                            StdOut.println("<<" + startArray[maxIndex] + ">>");
//                            StdOut.println("<<" + endArray[maxIndex] + ">>");
                        }
                    }
                }
                j++;
            }


//            StdOut.println("Start Array");
//            for (Point p : startArray) {
//                if (p != null)  StdOut.println(p);
//            }
//            StdOut.println("------");
//            StdOut.println("End Array");
//            for (Point p : endArray) {
//                if (p != null)  StdOut.println(p);
//            }
//            StdOut.println("------");
        }

        // For each index in start array and end array that is not null
        // create line segments
        for (int index = 0; index < startArray.length; index++) {
            if (startArray[index] != null && endArray[index] != null) {
                ls.add(new LineSegment(startArray[index], endArray[index]));
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
