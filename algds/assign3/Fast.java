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
    private static Point[] ArrayOfPoints;
    
    
    /**
     * Method to read input file and initialize the points array
     */
    private static void readInput(String filename) {
        // read in the input
       
        In in = new In(filename);
        points = in.readInt();
        ArrayOfPoints = new Point[points];

        
        for (int i = 0; i < points; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            ArrayOfPoints[i] = p;
            p.draw();
        }
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
         Arrays.sort(ArrayOfPoints);
        for (Point p: ArrayOfPoints) 
            StdOut.println(p);
        // Faster algorithm that calculates slope
        // for each point against each other point, sorts them
        // any 3 or more equal slope makes it collinear for each point
         if ( points >= 4) {
             for (int i = 0; (points-1) - i >= 4; i++) {
            // Sort the array of points by its comparator
            Point[]  p = Arrays.copyOfRange(ArrayOfPoints,i,points);
            //StdOut.println("Size of p: " + p.length);
            StdOut.println("Before sorting");
            for (Point pti: p) 
                     StdOut.println(pti);
            
            Arrays.sort(p,ArrayOfPoints[i].SLOPE_ORDER);
            StdOut.println("After sorting");
            for (Point pti2: p) 
                     StdOut.println(pti2);
            
            
            // Check if there are 3 or more form same 
            // slope with the point designated by i
            double current_slope, slope ;
            int match = 0;
            List<Point> l = new ArrayList<Point>();
            
            slope = p[0].slopeTo(p[0]);
           
            // StdOut.println("slope: " + current_slope);
            
            l.add(p[0]);
            for ( int j = 1; j < p.length; j++) {
                
                current_slope = p[0].slopeTo(p[j]);
                if (slope == current_slope) { 
                    match++; 
                    l.add(p[j]);
                }
                else {
                  slope = current_slope; 
                    // clear the list
                  l.clear();
                  match = 0;
                  l.add(p[0]);
                 }
            }
            // We found a collinear set of points
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
            
         }
      }
        // display to screen all at once
        StdDraw.show(0);
    }
}
