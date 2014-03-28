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
import java.util.ArrayList;

public class Fast {
    private static int points; // Stores the number of points
    private static Point[] pointsArray;
    
    
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
            if ( i == index) continue;
            ptarray[j++] = a[i];
        }
        
        return ptarray;
    }
    
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenColor();
        StdDraw.setPenRadius(.005);
        
        
        readInput(args[0]);
        
         // Sort the Points array
         Arrays.sort(pointsArray);
        
//        for (Point p: pointsArray) 
//            StdOut.println(p);
         
        // Faster algorithm that calculates slope
        // for each point against each other point, sorts them
        // any 3 or more equal slope makes it collinear for each point
         if (points >= 4) {
             
           for (int i = 0; i < points; i++) {

            Point[]  p = copyArray(pointsArray, i);
            //StdOut.println("Size of p: " + p.length);
//            StdOut.println("Before sorting");
//            for (Point pti: pointsArray) 
//                     StdOut.println(pti);
            
            Arrays.sort(p, pointsArray[i].SLOPE_ORDER);
//            StdOut.println("After sorting");
//            for (Point pti2: pointsArray) 
//                     StdOut.println(pti2);
            
            
            
            // Check if there are 3 or more form same 
            // slope with the point designated by i
            double currentSlope, slope;
            int match = 0;
            List<Point> l = new ArrayList<Point>();
            
            // Start with first point and keep checking until end of sorted array
            slope = pointsArray[i].slopeTo(p[0]);
           
            // StdOut.println("slope: " + currentSlope);
            
            l.add(pointsArray[i]);

            for (int j =  1; j < p.length; j++) {
                currentSlope = pointsArray[i].slopeTo(p[j]);
                if (slope == currentSlope) { 
                    match++; 
                    l.add(p[j]);
                }
                else {
                    if (match < 3) {
                        slope = currentSlope; 
                        // clear the list
                        l.clear();
                        match = 0;
                        l.add(pointsArray[i]);

                    }
                }
            }
            if (match >= 3) {
               StringBuffer s = new StringBuffer();
               Point nextPoint = null;
               Point currentPoint = l.get(0);
               s.append(currentPoint + " -> ");
               int count = 0;
               for (Point pt : l) {
                   count++;
                   nextPoint = pt;
                   if (currentPoint == nextPoint) continue;
                   s.append(nextPoint); 
                   if (count < l.size()) s.append(" -> "); 
                   currentPoint.drawTo(nextPoint);
                   currentPoint = nextPoint;
               }
               
               StdOut.println(s.toString());
           }
            if (points == 4) break;
        }
           
      }
        // display to screen all at once
        StdDraw.show(0);
    }
}
