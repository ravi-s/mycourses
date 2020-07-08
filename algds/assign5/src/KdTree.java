import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdOut;

/*************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *
 *
 *************************************************************************/

public class KdTree {
    private Node root;             // root of Kd Tree
    private int size;                // Stores the count of Nodes

    private static class Node {

        private final Point2D p;          // Associated 2D point
        private RectHV rect;       // The axis-aligned rectangle that contains p
        private Node left, right;  // left and right subtrees
        private int level;

        public Node(Point2D pt) {
            this.p = pt;
            this.left = null;
            this.right = null;
        }

    }

    public KdTree() {
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of points in the tree
    public int size() {
        return size;
    }

    /***********************************************************************
     *  Search the tree for given Point2D, and return associated point if found,
     *  return null if not found
     ***********************************************************************/
    // does the tree contain the point2D p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point2D point is null");
        }
        Point2D checkPoint = get(root, p);
        // boolean value;
        if (checkPoint == null) return false;
        return checkPoint.compareTo(p) == 0;
//        StdOut.println("In contains():" + checkPoint);
//        StdOut.println(value);

    }

//    // return value associated with the given key, or null if no such key exists
//    public Value get(Key key) {
//        return get(root, key);
//    }

    private Point2D get(Node node, Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        int cmp;
        if (node == null) return null;
//        Node n = new Node(point, null, null);
//        int cmp = n.compareTo(x);
        // At root, level 0, compare x-coordinate, level 2, 4 ...
        // At level 1, it is y-coordinate, level 3, 5 ...
        Point2D nodePoint = node.p;
        cmp = compareTo(point, nodePoint, node.level);

        if (cmp < 0) return get(node.left, point);
        else if (cmp > 0) return get(node.right, point);
        else return point;
        // else return point;
    }

    /***********************************************************************
     *  Insert Point2D into the 2dtree
     *  If key already exists, update with new value
     ***********************************************************************/
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("insert failed:point is null");
        if (!contains(p)) {
            root = insert(root, p, 0, null);
        }
    }

    /*
     * Returns -1 if p's x or y coordinate is lesser than this point2d
     * 1 if p's x or y coordinate is greater than this point2d
     * 0 otherwise.
     */
    private int compareTo(Point2D p1, Point2D p2, int level) {
        int cmp;
        if (level % 2 == 0) {
            // Even level, it is x
            cmp = Double.compare(p1.x(), p2.x());
            // Check y-coordinates for duplicate check
            int cmpY = Double.compare(p1.y(), p2.y());
            if (cmp == 0) {
                if (cmpY == 0) {
                    return 0; // Duplicate point
                } else if (cmpY == -1) {
                    cmp = -1;
                } else {
                    cmp = 1;
                }
            }
        } else {
            // Even level, it is x
            cmp = Double.compare(p1.y(), p2.y());
            // Check x-coordinates for duplicate check
            int cmpX = Double.compare(p1.x(), p2.x());
            if (cmp == 0) {
                if (cmpX == 0) {
                    return 0; // Duplicate point
                } else if (cmpX == -1) {
                    cmp = -1;
                } else {
                    cmp = 1;
                }
            }
        }
        return cmp;
    }

    /*
     * Insert p in x Node
     *
     */
    private Node insert(Node node, Point2D p, int level, RectHV r) {
        Node n;

        // Except for root,
        // each sub tree is associated with 2 rects
        RectHV rect, leftRect, rightRect;
        if (r == null)
            rect = new RectHV(0, 0, 1.0, 1.0);
        else
            rect = r;

//        StdOut.println("Insert point:" + p);

        // Special case empty tree, root is level 0
        if (node == null) {
            n = new Node(p);
//            StdOut.println("Inserted point:" + p);
            n.level = level;
            n.rect = rect;
            size++;
//            StdOut.println("level: " + level);
            return n;
        }

//        n = new Node(p, );
//        int cmp = n.compareTo(x);
        int cmp = compareTo(p, node.p, node.level);

//        StdOut.println("cmp: " + cmp);

        if (cmp < 0) {
            // Calculate left rect dimensions
            double xMin, yMin, xMax, yMax;
            if (level % 2 != 0) {
//                StdOut.println("cmp < 0 if branch");
                // xMin = rect.xmin();
                xMin = rect.xmin();
                yMin = rect.ymin();
                xMax = rect.xmax();
                yMax = node.p.y();
            } else {
//                StdOut.println("cmp < 0 else branch");
                xMin = rect.xmin();
                yMin = rect.ymin();
                xMax = node.p.x();
                yMax = rect.ymax();
            }
            leftRect = new RectHV(xMin, yMin, xMax, yMax);
            node.left = insert(node.left, p, node.level + 1, leftRect);
        } else if (cmp > 0) {
            // Calculate right rect dimensions
            double xMin, yMin, xMax, yMax;
            if (level % 2 != 0) {
//                StdOut.println("cmp > 0 if branch");
                // xmin = rect.xmin();
                // xmin = p.x();
//                yMin = rect.ymin();
                // xMin = node.p.x();
                xMin = node.rect.xmin();
                yMin = node.p.y();
                xMax = rect.xmax();
                yMax = rect.ymax();
            } else {
//                StdOut.println("cmp > 0 else branch");
//                 xMin = p.x();
//                yMin = node.p.y();
                xMin = node.p.x();
                yMin = rect.ymin();
                xMax = rect.xmax();
                yMax = rect.ymax();
            }
            rightRect = new RectHV(xMin, yMin, xMax, yMax);
            node.right = insert(node.right, p, node.level + 1, rightRect);
        }
        return node;
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null)
            throw new IllegalArgumentException("range query rect is null");

        Stack<Node> nodeStack = new Stack<>();

        Queue<Point2D> queue = new Queue<>();
        int cmp = Double.compare(rect.xmin(), 0.0);
        boolean isOrigin = (0 == cmp && 0 == Double.compare(rect.xmax(), 0.0));
        isOrigin = isOrigin && (0 == Double.compare(rect.ymin(), 0.0));
        isOrigin = isOrigin && (0 == Double.compare(rect.ymax(), 0.0));

        if (isOrigin) {
            return queue;
        }
        Point2D pt;
        // Start with Root
        Node n = this.root;
        if (n == null) return queue;

        pt = getIntersectionPoint(n, rect);
        if (pt != null) {
            queue.enqueue(pt);
        }

        if (n.right != null) nodeStack.push(n.right);
        if (n.left != null) nodeStack.push(n.left);

        while (!nodeStack.isEmpty()) {
//            StdOut.println("nodeStack size: " + nodeStack.size());
            // StdOut.println("pt: " + pt);
            n = nodeStack.pop();
            // Does query rect intersect the node rect?
            if (rect.intersects(n.rect)) {
                pt = getIntersectionPoint(n, rect);
                if (pt != null) {
                    queue.enqueue(pt);
                }
                if (n.right != null) nodeStack.push(n.right);
                if (n.left != null) nodeStack.push(n.left);
            }
        }
//        StdOut.println("queue size: " + queue.size());
        return queue;
    }

    /**
     * a nearest neighbor in the set to point p;
     * null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("parameter p is null for nearest()");

//        Queue<Node> queue = new Queue<>();
        Stack<Node> stack = new Stack<>();
        double d, min;
        Point2D pt;

        if (isEmpty())
            return null;

//        StdOut.println("query point in nearest(): " + p);
        // Start with Root
        Node temp = this.root;

//        if (isPointWithinNodeRect(temp, p)) {
        d = distanceSquaredTo(temp, p);
//        d = temp.rect.distanceSquaredTo(p);
        min = d;
        pt = temp.p;

//        StdOut.println("query point:" + p);
//        StdOut.println("-------------");
//        StdOut.println("Investigating root point:" + temp.p);
        if (temp.left == null && temp.right == null) {
            return pt;
        }

        if (temp.right != null)
            stack.push(temp.right);
        if (temp.left != null)
            stack.push(temp.left);

        int cmp;
        double rdist, pdist;

        while (!stack.isEmpty()) {
            temp = stack.pop();
//            StdOut.println("Investigating node point:" + temp.p);
            pdist = temp.rect.distanceSquaredTo(p);
            cmp = Double.compare(pdist, min);
            if (cmp < 0) {
                rdist = distanceSquaredTo(temp, p);
//            rdist = temp.rect.distanceSquaredTo(p);
                cmp = Double.compare(rdist, min);
                if (cmp < 0) {
//                StdOut.println("rdist: " + rdist);
//                StdOut.println("pt: " + temp.p);
                    min = rdist;
                    pt = temp.p;

                    if (temp.right != null)
                        stack.push(temp.right);
                    if (temp.left != null)
                        stack.push(temp.left);
                }

            }
        }
//        StdOut.println("nearest:" + pt);
        return pt;
    }

    /**
     * Private helper method for range() API
     */
    private Point2D getIntersectionPoint(Node n, RectHV rect) {

        Point2D pt = null;
        // Check if the rect intersects at the node rectangle
        // if yes, add it to the queue
        // else prune the node and sub-tree
//        StdOut.println("Candidate point under check:" + n.p);
//        StdOut.println("query rect:" + rect);

        if (n == null || rect == null) return pt;
//            StdOut.println("node rect:" + n.rect);
//            StdOut.println("query rect:" + rect);
        if (rect.contains(n.p)) {
//            StdOut.println("Found a point in the query rectangle");
//            StdOut.println(n.p);
            pt = n.p;
        }
        return pt;
    }

    /**
     * Private helper method for nearest() API
     */
    private double distanceSquaredTo(Node n, Point2D p) {
        double sqrdist;

        // Point2D pt = n.p;
        sqrdist = p.distanceSquaredTo(n.p);
        return sqrdist;
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
     * Draw method
     */
    private void draw(Node n) {

        if (n == null)
            throw new IllegalArgumentException("Draw called with Null node");

        RectHV r = n.rect;
        int level = n.level;

        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Point2D p = n.p;
        // Debug
//        StdOut.println("Point2D p:" + p);
//        StdOut.println("RectHV: " + r.toString());
//        StdOut.println("Tree Size:" + size());
        p.draw();
        // Horizontal line in the boundary of the point rect
        if (level % 2 != 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.004);
            StdDraw.line(r.xmin(), p.y(), r.xmax(), p.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.004);
            StdDraw.line(p.x(), r.ymin(), p.x(), r.ymax());
        }

        // Traverse left node to draw
        if (n.left != null)
            draw(n.left);

        if (n.right != null)
            // Traverse right node to draw
            draw(n.right);
    }

    /*****************************************************************************
     *  Test client
     *****************************************************************************/
//    public static void main(String[] args) {
//
//    }
}
