/*************************************************************************
  *  Compilation:  javac Fast.java
  *  Execution:    java Brutter input.txt
  *  Dependencies: Point.java, In.java, StdDraw.java
  *
  *  Takes the name of a file as a command-line argument.
  *  Reads in an integer N followed by N pairs of points (x, y)
  *  with coordinates between 0 and 32,767, and plots them using
  *  standard drawing.
  *
  *************************************************************************/
import java.util.Arrays;
import java.util.List;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;

public class Fast {
    private static int points; // Stores the number of points
    private static Point[] pointsArray;
    // Keep a set data structure to store collinear points
    // found for the first time for a data set
    private static SortedSet<Point> set = new TreeSet<Point>();
    
    /**
     * Method to read input file and initialize the points array
     */
    private static void readInput(String filename) {
        // read in the input
        
        In in = new In(filename);
        points = in.readInt();
        pointsArray = new Point[points];
        
        
        for (int i = 0; i < points; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pointsArray[i] = p;
            p.draw();
        }
    }
    
    /**
     *  Method to create an array copy except the one mentioned as index
     * for the source array
     * 
     */
    private static Point[] copyArray(final Point[] a, int index) {
        
        int len = a.length - 1;
        Point[] ptarray = new Point[len];
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (i == index) continue;
            ptarray[j++] = a[i];
        }
        
        return ptarray;
    }
    
    /**
     *  Method to check if the collinear tuple has already been found
     * 
     */
    private static boolean checkDuplicateSolution(List<Point> l) {
        // For each point in the list l, check if the point is 
        // member in the set
        // if all points are alreay found, found a duplicate
        // if not, add the point to the set
        boolean duplicate = false;
        int count = 0;
        for (Point p: l) {
            if (!set.contains(p)) {
                set.add(p);
            }
            else { 
                count++; // found a duplicate 
            }
        }
        if (count == l.size()) duplicate = true;
        return duplicate;
    }
    
    public static void main(String[] args) {
        
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenColor();
        StdDraw.setPenRadius(.003);
        
        
        readInput(args[0]);
        
        // Sort the Points array
        Arrays.sort(pointsArray);
        
//        for (Point p: pointsArray) 
//            StdOut.println(p);
        
        // Faster algorithm that calculates slope
        // for each point against each other point, sorts them
        // any 3 or more equal slope makes it collinear for each point
        if (points >= 4) {
            Point p;
            Point[] pts;
            for (int i = 0; i < points; i++) {
                p = pointsArray[i];
                pts = copyArray(pointsArray, i);
//                StdOut.println("p:" + p);
                
                /*
                 //            StdOut.println("Size of p: " + p.length);
                 //            StdOut.println("Before sorting");
                 //            for (Point pti: p) 
                 //                     StdOut.println(pti); */
                
                Arrays.sort(pts, p.SLOPE_ORDER);
                //set.clear();
                
//          StdOut.println("After sorting with respect to " + p);
//            for (Point pti2: pts) 
//                     StdOut.println(pti2);
//            StdOut.println("--------------------------");
                
                // Check if there are 3 or more form same 
                // slope with the point designated by i
                double currentSlope = 0.0D, slope = 0.0D;
                int match = 0;
                List<Point> l = new ArrayList<Point>();
                // Store the reference point first
                l.add(p); 
                
                // Start with first point and keep checking until end of sorted array
                slope = p.slopeTo(pts[0]);
                
                //StdOut.println(first + ":" + currentSlope);
//            
                for (int index = 0; index < pts.length; index++) {
                    //StdOut.println("pts[index]: " + pts[index]);
                    //StdOut.println(l);
//                    StdOut.println("slope: " + slope);
                    currentSlope = p.slopeTo(pts[index]);
//                    StdOut.println("currentSlope: " + currentSlope);
                    
                    if (slope == currentSlope) { 
                        match++; 
//                        StdOut.println("slope: " + slope);
//                        StdOut.println("currentSlope: " + currentSlope);
//                        StdOut.println("match: " + match);
                        l.add(pts[index]);
                    }
                    
                    else { 
                        if (match < 3) {
                            slope = currentSlope; 
                            
                            // clear the list
                            l.clear();
                            l.add(p); 
                            l.add(pts[index]);
                            match = 1;
                        }
                        
                        
                    } 
                    
                    
                }
                
                if (match >= 3) {
                    if (!checkDuplicateSolution(l)) { 
                        StringBuilder s = new StringBuilder();
                        Point nextPoint = null;
                        Point firstPoint = null;
                        Point currentPoint = null;
                        
                        if (l.size() > 0) { 
                            firstPoint = l.get(0); 
                            
                            currentPoint = firstPoint;
                            s.append(currentPoint + " -> ");
                            
                            int count = 0;
                            for (Point point: l) {
                                count++;
                                nextPoint = point;
                                if (currentPoint.compareTo(nextPoint) == 0) continue;
                                s.append(nextPoint); 
                                if (count < l.size()) s.append(" -> "); 
                                currentPoint.drawTo(nextPoint);
                                currentPoint = nextPoint;
                                
                            }
                            StdOut.println(s);
                            //slope = currentSlope;
                            l.clear();
                            
                            match = 0;
                            //l.add(pointsArray[i]);
                        }  
                    }  
                }
            }
            
        }
        // display to screen all at once
        StdDraw.show(0);
    }
}
