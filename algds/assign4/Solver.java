/**
 * ***********************************************************************
 *  Compilation:  javac Board.java
 *  
 * A puzzle board abstraction, typically 8-puzzle problem
 *  @author Ravi S
 *  Date: 27-February-2015
 *  Change control:
 *   07-March:2015: Added set to track neigbhours 
 *   and efficiency of search using PQ
 *
 */


public class Solver {
    private boolean solvable;
    private int moves;
    private Queue<Board> queue = new Queue<Board>();
    
    // Inner class for search node
    private static class Node implements Comparable<Node> {
        private Board board;
        //int moves;
        private int priority;
        
        Node(Board b, int m) { 
            board = b;
            //moves = m;
            priority = b.manhattan() + m;
        }
        Board getBoard() { return board; }
        int getPriority() { return priority; }
        
        public int compareTo(Node b) {
            if (board.equals(b.board)) return 0;
            else {
                if (priority < b.priority) return -1;
                else return 1;
            }
        }
        
    };
    
    /**
     * Constructior
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        
        // Keep a Min-oriented priority queue for board and its twin
        // for A* algorithm search
        MinPQ<Node> pq;
        MinPQ<Node> pqtwin;
        Iterable<Board> iter;
        Iterable<Board> twiniter;
        
        boolean found = false; // set to true if either board or twin 
        this.solvable = false;
        // reaches its goal
        
        if (initial == null) 
            throw new NullPointerException("initial board is null");
        
        if (initial.isGoal()) {
            this.moves = 0;
            this.solvable = true;
            queue.enqueue(initial);
            return;
        }
        
        Board twin = initial.twin();
        //StdOut.println("Twin:\n"+twin);
        
        if (twin.isGoal()) {
//            StdOut.println("twin solved");
            this.moves = -1;
            this.solvable = false;
            return;
        }
        
        this.moves = 0;
        
        pq = new MinPQ<Node>();
        pqtwin = new MinPQ<Node>();
        
        Node previous = null, next = null;
        Node previousTwin = null, nextTwin = null;
        Board nextBoard = null;
        Board nextTwinBoard = null;
        
        //pq.insert(previous);
        //pqtwin.insert(previousTwin);
        
        // Initialize main board and remember state
        next = new Node(initial, this.moves);
        pq.insert(next);
        Board previousBoard = initial;
        
        // Initialize twin board and remember state
        nextTwin = new Node(twin, moves);
        pqtwin.insert(nextTwin); 
        Board previousTwinBoard = twin;
        
        // Run A* algorithm using priority q
        // that has priority set for node. Always dequeue min priority
        // either board or its twin is solvable in some time
        do {
            
            Node tmp, tmpTwin;
            iter = null;
            twiniter = null;
            
            if (!pq.isEmpty()) {
                next = pq.delMin();
                nextBoard = next.getBoard();
            }
            else { 
                this.moves = -1;
                this.solvable = false;
                found = true;
            }
            
//            StdOut.println("Dequeued Board:\n" + nextBoard);
//            StdOut.println("with Priority:" + next.getPriority());
            queue.enqueue(nextBoard);
            
            if (!pqtwin.isEmpty()) {
                nextTwin = pqtwin.delMin();
                nextTwinBoard = nextTwin.getBoard();
            }
            else { 
                this.moves = -1;
                this.solvable = false;
                found = true;
            }
            
            // StdOut.println("Twin Board:\n" + nextTwinBoard);
            
            if (nextBoard.isGoal()) {
                found = true;
                this.solvable = true;
                
            }
            else if (nextTwinBoard.isGoal()) {
                //StdOut.println("moves:" +moves);
                //StdOut.println("twin solved");
                this.moves = -1;
                this.solvable = false;
                found = true;
            }
            else if (!found) {
                moves++;
                // Main board operations
//                StdOut.println("nextBoard:\n"+nextBoard);
                
                iter = nextBoard.neighbors();
                for (Board b1: iter) {
                   
                    if (!b1.equals(previousBoard)) {
                        tmp = new Node(b1, moves);
                        pq.insert(tmp);
                    }
                }
                previous = next;
                previousBoard = previous.getBoard();
                twiniter = nextTwinBoard.neighbors();
                // Twin board operations
                for (Board b2:twiniter) {
                    if (!b2.equals(previousTwinBoard)) {
                        tmpTwin = new Node(b2, moves);
                        pqtwin.insert(tmpTwin);
                    }
                }
                previousTwin = nextTwin;
                previousTwinBoard = previousTwin.getBoard();
                
            }
            
        } while (!found);
    }           
    
    // is the initial board solvable?
    
    public boolean isSolvable() { return this.solvable; }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()   { return this.moves; }  
    // sequence of boards in a shortest solution; 
    // null if unsolvable
    public Iterable<Board> solution() {
        if (!queue.isEmpty() && this.isSolvable()) return queue;
        else return null;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args)  {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}
