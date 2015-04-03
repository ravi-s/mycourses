/**
 * ***********************************************************************
 *  Compilation:  javac Board.java
 *  
 *  PointSet
 *  - point set abstraction that represents a 
 * 
 *  @author Ravi S
 *  Created: 18-March-2015
 *
 *
 */
public class PointSET {
    
    private SET<Point2D> set;
//    int N; // Store the number of points in the set
    
    /**
     *  construct an empty set of points
     */
    public PointSET() { 
        set = new SET<>();
    }  
    
    /**
     *  is the set empty?
     */
    public boolean isEmpty() { return set.isEmpty(); }
    
    /**
     * number of points in the set    
     */
    public int size()  { return set.size(); }
    
    /**
     * Add the point to the set 
     * (if it is not already in the set)
     */
    public void insert(Point2D p) { 
        if (null == p) throw 
            new NullPointerException("point is null");
        set.add(p); 
//        N++;
    }
    
    /**
     * Does the set contain point p? 
     */
    public boolean contains(Point2D p) { 
        if (null == p) throw 
            new NullPointerException("point is null\n");
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
        for (Point2D p: set) p.draw();
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
        if (null == rect) throw new NullPointerException("rect is null\n");
        
        SET<Point2D> rectset = new SET<>();
        
        // Time proportional to number of elements in the set
        for (Point2D p: this.set) {
            if (rect.contains(p))
                rectset.add(p);
        }
        return rectset;
    }     
    
    /**
     * @return a nearest neighbor in the set to point p; 
     * null if the set is empty 
     */
    public Point2D nearest(Point2D p) {
        if (null == p) 
            throw new NullPointerException("Point p is null\n");
        
        /*
         * For each point in set, calculate distance of p with 
         * each point, return the point with least distance
         */
        double minimum = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D point: this.set) {
            double dist = point.distanceTo(p);
            if (dist < minimum) {
                minimum = dist;
                nearest = point;
            }
        }
        return nearest;
        
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) { 
//        // create initial board from file
//        In in = new In(args[0]);
//        int N = in.readInt();
//        
//        Point2D point;
//        PointSET pointset = new PointSET();
//        
//        for (int i = 0; i < N; i++) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            point = new Point2D(x, y);
//            pointset.insert(point);
//        }
        
        /*
         * Test case for Range method 
         */
        // Test case 1
//        RectHV r = new RectHV(0.1, 0.2, 0.25, 0.5);
//        StdOut.println("Points within Rectangle: " + r);
//        // Should return (0.233013, .417143)
//        for (Point2D p: pointset.range(r))
//            StdOut.println(p);
//        
//        // Test case 2 
//        r = new RectHV(0.2, 0.3, 0.25, 0.5);
//        StdOut.println("Points within Rectangle: " + r);
//        // Should return (0.233013, .417143)
//        for (Point2D p: pointset.range(r))
//            StdOut.println(p);
//        
//        // Test case 3, points on the corners of the rectangle
//        // Should return (.271502, .847208) and (.668687, .566795)
//        r = new RectHV(0.271502, 0.4, 0.668687, 0.846208);
//        StdOut.println("Points within Rectangle: " + r);
//        for (Point2D p: pointset.range(r))
//            StdOut.println(p);
        
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
        
        /*
         * Test case for nearest method
         * 
         */
//        Point2D queryPoint = new Point2D(0.1, 0.2);
//        
//        Point2D nearest = pointset.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        queryPoint = new Point2D(0.2, 0.2);
//        nearest = pointset.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        queryPoint = new Point2D(0.8, 0.4);
//        nearest = pointset.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        
//        queryPoint = new Point2D(0.15, 0.6);
//        nearest = pointset.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
        
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