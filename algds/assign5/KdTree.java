/*************************************************************************
  *  Compilation:  javac KdTree.java
  *  Execution:    java KdTree
  *  Dependencies: StdIn.java StdOut.java Queue.java
  *  
  *
  *************************************************************************/


public class KdTree {
    private Node root;             // root of Kd Tree
    private int N;                 // Stores the count of elements
    
    private static class Node implements Comparable<Node> {
        
        private Point2D p;          // Associated point
        private RectHV rect;       // The axis-aligned rectangle
        private Node left, right;  // left and right subtrees
        private int level;
        
        public Node(Point2D pt) { 
            makeNode(pt, null);
        }
        
        public Node(Point2D pt, RectHV r) {
            makeNode(pt, r);
        }
        
        
        private void makeNode(Point2D pt, RectHV r) {
            this.p = pt;
            this.left = null;
            this.right = null;
            this.rect = r;
        }
        void setLevel(int l) { this.level = l; }
        
        int getLevel() { return this.level; }
        
        Point2D getPoint() { return p; }
        
        RectHV getRect() { return rect; }
        
        // Implement Comparable interface
        public int compareTo(Node that) {
            /* Comparison of nodes is based on two aspects:
             * level of node and point within node
             * Levels 1,3,5, ... are compared using x-coords
             * Levels 2,4,6 are compared using y-coords
             */
            int value = p.compareTo(that.p);
            if (0 == value) return value;
            
            int l = that.getLevel();
            
            
            // Even level, compare y-coords
            if (l % 2 == 0)  {
                if (p.y() < that.p.y()) value =  -1;
                else value = 1;
            }
            // Odd level, compare x-coords
            else {
                if (p.x() < that.p.x()) value =  -1;
                else value =  1;
            }
            return value;
        }
    }
    
    public KdTree() { 
        root = null;
        N = 0;
    }
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // return number of points in the tree
    public int size() {
        return N;
    }
    
//    // return number of key-value pairs in BST rooted at x
//    private int size(Node x) {
//        if (x == null) return 0;
//        else return x.N;
//    }
//    
    /***********************************************************************
      *  Search the tree for given Point2D, and return associated point if found,
      *  return null if not found
      ***********************************************************************/
    // does the tree contain the point2D p?
    public boolean contains(Point2D p) {
        return get(root, p) != null;
    }
    
//    // return value associated with the given key, or null if no such key exists
//    public Value get(Key key) {
//        return get(root, key);
//    }
    
    private Point2D get(Node x, Point2D point) {
        if (x == null) return null;
        Node n = new Node(point);
        int cmp = n.compareTo(x);
        if      (cmp < 0) return get(x.left, point);
        else if (cmp > 0) return get(x.right, point);
        else              return point;
    }
    
    /***********************************************************************
      *  Insert Point2D into the 2dtree
      *  If key already exists, update with new value
      ***********************************************************************/
    public void insert(Point2D p) {
        if (p == null) 
            throw new NullPointerException("insert failed:point is null");
        root = insert(root, p, 1, null);
    }
    
    private Node insert(Node x, Point2D p, int l, RectHV r) {
        Node n;
        int level;
        // Except for root, 
        // each sub tree is associated with 2 rects
        RectHV rect, leftRect, rightRect;
        if (r == null) 
            rect = new RectHV(0, 0, 1.0, 1.0);
        else
            rect = r;
        
        if (x == null) { 
            n =  new Node(p, rect); // root is level 1
            n.setLevel(l);
            N++;
            return n;
        }
        
        n = new Node(p);
        int cmp = n.compareTo(x);
        level = x.getLevel();
        
        if (cmp < 0)  {
            // Calculate left rect dimensions
            double xmin, ymin, xmax, ymax;
            if (l % 2 == 0) {
                xmin = rect.xmin();
                ymin = rect.ymin();
                xmax  = rect.xmax();
                ymax = x.getPoint().y();
            }
            else {
                xmin = rect.xmin();
                ymin = rect.ymin();
                xmax  = x.getPoint().x();
                ymax = rect.ymax();
            }  
            
            leftRect = new RectHV(xmin, ymin, xmax, ymax);
            x.left  = insert(x.left, p, level + 1, leftRect);
        }
        else if (cmp > 0) {
            // Calculate right rect dimensions
            double xmin, ymin, xmax, ymax;
            if (l % 2 == 0) {
                xmin = rect.xmin();
                ymin = x.getPoint().y();
                xmax  = rect.xmax();
                ymax = rect.ymax();
            }
            else {
                xmin = x.getPoint().x();
                ymin = rect.ymin();
                xmax  = rect.xmax();
                ymax = rect.ymax();
            }
            
            rightRect = new RectHV(xmin, ymin, xmax, ymax);
            x.right = insert(x.right, p, level + 1, rightRect);
            
        }
        return x;
    }
    
    /**
     * all points that are inside the rectangle 
     */
    public Iterable<Point2D> range(RectHV rect) {
        
        if (rect == null) 
            throw new NullPointerException("range query rect is null");
        
        Queue<Point2D> q = new Queue<>();
        
        Stack<Node> stack = new Stack<>();
        Point2D pt;
        
        // Start with Root
        Node n = this.root;
        pt = getIntersectionPoint(n, rect);
        
        if (null == pt) return q;
        else {
            if (rect.contains(pt))
                q.enqueue(pt);
            if (n.right != null) 
                stack.push(n.right);
            if (n.left != null)
                stack.push(n.left);
            while (!stack.isEmpty()) {
                /*
                 * Dequeue node and check 
                 */
                n = stack.pop();
                pt = getIntersectionPoint(n, rect);
                if (pt == null) continue;
                else {
                    
                    if (rect.contains(pt))
                        q.enqueue(pt);
                    if (n.right != null) 
                        stack.push(n.right);
                    if (n.left != null)
                        stack.push(n.left);
                }
            }
        }
        
        return q;
    }
    
    /**
     * a nearest neighbor in the set to point p; 
     * null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("parameter p is null for nearest()");
        
        Stack<Node> stack = new Stack<>(); 
        double d = Double.MAX_VALUE, 
            min = Double.MAX_VALUE;
        
        Point2D pt = null;
        
        if (isEmpty()) 
            return null; 
        // Start with Root
        Node temp = this.root;
        
//        if (isPointWithinNodeRect(temp, p)) {  
        d = getSquaredistances(temp, p);
        min = d; 
        pt = temp.getPoint();
        if (temp.right != null) 
            stack.push(temp.right);
        if (temp.left != null)
            stack.push(temp.left);
        if (temp.left == null && temp.right == null) {
            
            return pt;
        }
//        }
        
        while (!stack.isEmpty()) {
            temp = stack.pop();
//            if (isPointWithinNodeRect(temp, p)) {
            
            RectHV r = temp.getRect();
            double rdist = r.distanceSquaredTo(p);
            if (rdist < min) {
                d = getSquaredistances(temp, p);
                if (d < min) {
                    min = d;
                    pt = temp.getPoint();
                }
                if (temp.right != null) 
                    stack.push(temp.right);
                if (temp.left != null)
                    stack.push(temp.left);
            }
            
//            }
            
        }
        
        return pt;
    }
    
    /**
     * Draw Method
     */
    
    public void draw() {
        /*
         * Traverse the tree from root, left tree recursively, right 
         * tree. For each parent node, draw the boundary line for the
         * rect and also the points
         */
        draw(root);
        
    }
    
    /**
     * Private helper method for range() API
     */
    private Point2D getIntersectionPoint(Node n, RectHV rect) {
        
        Point2D pt = null;
        // Check if the rect intersects at the node rectangle
        // if yes, add it to the queue
        // else prune the node and sub-tree
        RectHV r = n.getRect();
        if (r.intersects(rect)) {
            pt = n.getPoint();
        }
        return pt;
    }
    
    /**
     * Check if the point is within a
     * node's rectangle
     * 
     */
    private boolean isPointWithinNodeRect(Node n, Point2D p) {
        if (n == null)
            throw new NullPointerException("Node null in isPointWithinNodeRect()");
        
        RectHV rect = n.getRect();
        return rect.contains(p); 
    }
    
    /**
     * Private helper method for nearest() API
     */
    private double getSquaredistances(Node n, Point2D p) {
        
        double sqrdist;
        
        Point2D pt = n.getPoint();
        sqrdist = p.distanceSquaredTo(pt);
        return sqrdist;
    }
    
    /**
     * Draw method
     */
    private void draw(Node n) {
        
        if (n == null) 
            throw new NullPointerException("Draw called with Null node");
        
        
        RectHV r = n.getRect();
        int level = n.getLevel();
        
        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        Point2D p = n.getPoint();
        p.draw();
        // Horizontal line in the boundary of the point rect
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(.004);
            StdDraw.line(r.xmin(), p.y(), r.xmax(), p.y());
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(.004);
            StdDraw.line(p.x(), r.ymin(), p.x(), r.ymax());
        }
        
        //Traverse left node to draw
        if (n.left != null) 
            draw(n.left);
        
        if (n.right != null) 
            // Traverse right node to draw
            draw(n.right);
        
    }
    
//    
    /*****************************************************************************
      *  Test client
      *****************************************************************************/
    public static void main(String[] args) { 
        
        Point2D p;
        KdTree tree;
        
        //Test cases for insert, size, isEmpty()
        
        //Simple test case 1
        tree = new KdTree();
//        p = new Point2D(0.7, 0.2);
//        tree.insert(p);
        
//        if (tree.isEmpty()) StdOut.println("tree is empty");
//        tree.insert(p);
//        StdOut.println("Number of elements in the tree: " + tree.size());
//        if (tree.contains(p)) 
//            StdOut.println("Tree contains the point:" + p);
        
        // Test case 2
//        p = new Point2D(0.5, 0.4);
//        tree.insert(p);
//        p = new Point2D(0.2, 0.3);
//        tree.insert(p);
//        p = new Point2D(0.4, 0.7);
//        tree.insert(p);    
//        p = new Point2D(0.9, 0.6);
//        tree.insert(p);
//        // Check duplicate insert
//        tree.insert(p);
        
        p = new Point2D(0.821653, 0.300758);
        tree.insert(p);
        p = new Point2D(0.0, 0.0);
        tree.insert(p);
        p = new Point2D(0.069324, 0.275381);
        tree.insert(p);
        p = new Point2D(1.0, 1.0);
        tree.insert(p);    
        
//        
//        StdOut.println("Number of elements in the tree: " + tree.size());
//        p = new Point2D(0.4, 0.7);
//        if (tree.contains(p)) 
//            StdOut.println("Tree contains the point:" + p);
//        
////         Test case 3 for Range search
////        RectHV r = new RectHV(0.0, 0.0, 0.2, 0.4);
//          RectHV r = new RectHV(0.2, 0.3, 0.4, 0.7);
        
//        String filename = args[0];
//        In in = new In(filename);
//        tree = new KdTree();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            p = new Point2D(x, y);
//            tree.insert(p);
//        }
//        
//        
//        RectHV r = new RectHV(0.009765625, 0.091796875, 0.255859375, 0.41796875);
//        
//        StdOut.println("Points in query Rectangle: " + r);
//        
//        for (Point2D pt: tree.range(r))
//            StdOut.println(pt);
        
        // Test case 4 : Check nearest point to the given point
//        Point2D queryPoint = new Point2D(0.1, 0.2);
//        
//        Point2D nearest = tree.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        queryPoint = new Point2D(0.2, 0.2);
//        nearest = tree.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        queryPoint = new Point2D(0.8, 0.4);
//        nearest = tree.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        
//        queryPoint = new Point2D(0.15, 0.6);
//        nearest = tree.nearest(queryPoint);
//        if (null != nearest) {
//            StdOut.println("querypoint: " + queryPoint);
//            StdOut.println("Nearest Point: " + nearest);   
//        }
//        
        Point2D queryPoint = new Point2D(0.412109375, 0.564453125);
        Point2D nearest = tree.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);   
        }
        
        queryPoint = new Point2D(0.3359375, 0.62890625);
        nearest = tree.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);   
        }
        
        queryPoint = new Point2D(0.50390625, 0.841796875);
        nearest = tree.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);   
        }
        queryPoint = new Point2D(0.681640625, 0.802734375);
        nearest = tree.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);   
        }
        
        queryPoint = new Point2D(0.357421875, 0.359375);
        nearest = tree.nearest(queryPoint);
        if (null != nearest) {
            StdOut.println("querypoint: " + queryPoint);
            StdOut.println("Nearest Point: " + nearest);   
        }
        // Test draw
        StdDraw.show(0);
        tree.draw();
        StdDraw.show(0);
        
    }
}
