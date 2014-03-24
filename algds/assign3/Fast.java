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
        // Faster algorithm that calculates slope
        // for each point against each other point, sorts them
        // any 3 or more equal slope makes it collinear for each point
        
        for (int i = 0; i < points; i++) {
            // Sort the array of points by its comparator
            Point[]  p = Arrays.copyOfRange(ArrayOfPoints,i+1,points);
            Arrays.sort(p,ArrayOfPoints[i].SLOPE_ORDER);
            
        }
       
        // display to screen all at once
        StdDraw.show(0);
    }
}
