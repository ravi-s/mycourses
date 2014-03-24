/*************************************************************************
 *  Compilation:  javac Brute.java
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

public class Brute {
    private static int points; // Stores the number of points
    private static Point[] arrayOfPoints;
    
    private static void readInput(String filename) {
        // read in the input
       
        In in = new In(filename);
        points = in.readInt();
        arrayOfPoints = new Point[points];
        
        for (int i = 0; i < points; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            arrayOfPoints[i] = p;
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

        long start = System.currentTimeMillis();
        
        readInput(args[0]);
        
        Arrays.sort(arrayOfPoints);
        
        /* Brute Force algorithm to check if 4 points at any time are collinear */
        for (int i = 0; i < points; i++) {
          boolean b = false; // state of slope match for 4 points
          for (int j = i + 1; j < points; j++)
            for (int k = j + 1; k < points; k++)
              for (int l = k + 1; l < points; l++) {
                double slope = arrayOfPoints[i].slopeTo(arrayOfPoints[j]);
               b = 
                 slope == arrayOfPoints[i].slopeTo(arrayOfPoints[k]);
               b = b && (slope == arrayOfPoints[i].slopeTo(arrayOfPoints[l]));
              // if match                                  
              if (b) {
                   String s = 
                       arrayOfPoints[i] + " -> " 
                       + arrayOfPoints[j] + " -> " 
                       + arrayOfPoints[k] + " -> " 
                       + arrayOfPoints[l];
                   StdOut.println(s);
                   arrayOfPoints[i].drawTo(arrayOfPoints[j]);
                   arrayOfPoints[j].drawTo(arrayOfPoints[k]);
                   arrayOfPoints[k].drawTo(arrayOfPoints[l]);
               }
            
            }
        }   
        long end = System.currentTimeMillis();
        
        StdOut.println("Total Time(s): " + (end - start)/1000);
        // display to screen all at once
        StdDraw.show(0);
    }
}
