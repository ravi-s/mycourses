import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * ***********************************************************************
 * Compilation:  javac Board.java
 * <p>
 * A puzzle board abstraction, typically 8-puzzle problem
 *
 * @author Ravi S
 * Date: 16-June-2020
 * Change control:
 * 07-March:2015: Added set to track neigbhours
 * and efficiency of search using PQ
 */

public class Solver {
    private boolean solvable;
    private int moves;
    // Keep a Min-oriented priority queue for board and its twin
    // for A* algorithm search

    private Queue<Board> queue = new Queue<>();

    // Inner class for search node
    private static class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private Node previous;

        Node(Board b, int m, Node prev) {
            board = b;
            moves = m;
            priority = b.manhattan() + moves;
            previous = prev;
        }

        Board getBoard() {
            return board;
        }

        int getPriority() {
            return priority;
        }

        int getMoves() {
            return moves;
        }

        Node getPrevious() {
            return previous;
        }

        public int compareTo(Node b) {
            int value = 1;
            int priorityDifference = getPriority() - b.getPriority();

            if (priorityDifference < 0) {
//            if (getBoard().manhattan() < b.getBoard().manhattan())
                value = -1;
            }

            if (priorityDifference == 0) {
                /* int manhattanDifference =
                        getBoard().manhattan() - b.getBoard().manhattan();
                if (manhattanDifference < 0)
                    value = -1;
                else if (manhattanDifference == 0) */
                value = 0;
            }

            return value;
        }

        @Override
        public String toString() {
            return getBoard().toString();
        }
    }

    /**
     * Constructior
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        Iterable<Board> iter;
        Iterable<Board> twiniter;
        MinPQ<Node> pq;
        MinPQ<Node> pqtwin;
        boolean found = false; // set to true if either board or twin
        // reaches its goal
        int move = 0, movetwin = 0;
        boolean firstBoard = true, firstBoardTwin = true;
        this.solvable = false;


        if (initial == null)
            throw new IllegalArgumentException("initial board is null");

        if (initial.isGoal()) {
            this.moves = 0;
            this.solvable = true;
            queue.enqueue(initial);
            return;
        }

        Board twin = initial.twin();
        // StdOut.println("Twin:\n"+twin);

        if (twin.isGoal()) {
//            StdOut.println("twin solved");
            this.moves = -1;
            this.solvable = false;
            return;
        }

        this.moves = 0;

        pq = new MinPQ<>();
        pqtwin = new MinPQ<>();

        Node  next;
        Node  nextTwin;
        Board nextBoard = null;
        Board nextTwinBoard = null;
        Node previousSearch = null, previousSearchTwin = null;

        // pq.insert(previous);
        // pqtwin.insert(previousTwin);

        // Initialize main board and remember state
        next = new Node(initial, move, previousSearch);
        pq.insert(next);

        // Initialize twin board and remember state
        nextTwin = new Node(twin, movetwin, previousSearchTwin);
        pqtwin.insert(nextTwin);

        // Run A* algorithm using priority q
        // that has priority set for node. Always dequeue min priority
        // either board or its twin is solvable in some time
        do {
            Node tmp, tmpTwin;
            if (!pq.isEmpty()) {
                next = pq.delMin();
                move = next.getMoves();
                nextBoard = next.getBoard();
                previousSearch = next.getPrevious();
            } else {
                this.moves = -1;
                this.solvable = false;
                found = true;
            }

//            StdOut.println("nextBoard");
//            StdOut.println(("--------"));
//            StdOut.print(nextBoard);
            queue.enqueue(nextBoard);

            if (!pqtwin.isEmpty()) {
                nextTwin = pqtwin.delMin();
                movetwin = nextTwin.getMoves();
                nextTwinBoard = nextTwin.getBoard();
                previousSearchTwin = nextTwin.getPrevious();
            } else {
                this.moves = -1;
                this.solvable = false;
                found = true;
            }

            if (nextBoard != null && nextBoard.isGoal()) {
                found = true;
                this.moves = next.getMoves();
                this.solvable = true;

            } else if (nextTwinBoard != null && nextTwinBoard.isGoal()) {
                // StdOut.println("moves:" +moves);
                // StdOut.println("twin solved");
                this.moves = -1;
                this.solvable = false;
                found = true;
            } else if (!found) {
                iter = nextBoard.neighbors();
                if (firstBoard) {
                    firstBoard = false;
                    for (Board b: iter) {
                        tmp = new Node(b, move + 1, next);
                        pq.insert(tmp);
                    }
                }
                else {
                    for (Board b: iter) {
                            Node prev = next.getPrevious();
                            if (!b.equals(prev.getBoard())) {
                                tmp = new Node(b, move + 1, next);
                                pq.insert(tmp);
                            }
                    }
                }

                // Repeat for twin board operations
                twiniter = nextTwinBoard.neighbors();
                if (firstBoardTwin) {
                    firstBoardTwin = false;
//                    previousSearchTwin = nextTwin;
                    for (Board b: twiniter) {
                        tmpTwin = new Node(b, move + 1, nextTwin);
                        pqtwin.insert(tmpTwin);
                    }
                }
                else {
                    for (Board b: twiniter) {
                            Node prev = nextTwin.getPrevious();
                            if (!b.equals(prev.getBoard())) {
                                tmpTwin = new Node(b, movetwin + 1, nextTwin);
                                pqtwin.insert(tmpTwin);
                            }
                    }
                }
            }
        } while (!found);
    }

    // is the initial board solvable?

    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution;
    // null if unsolvable
    public Iterable<Board> solution() {
        if (!queue.isEmpty() && this.isSolvable()) return queue;
        else return null;
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int dimension = in.readInt();
        int[][] blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
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
