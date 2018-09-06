import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {

    private final MinPQ<BoardNode> solver;
    private Board initialBoard;

    /** 
     * Node class that contains boards 
     * @author shadowaxis9000
     *
     */
    private class BoardNode {
        Board board;
        int depth; // how many moves from start node
        BoardNode previous;
        boolean isTwin;

        /** 
         * construct a board node which contains more information than a board
         * @param board
         *          board that is associated with the node
         * @param depth
         *          how many moves from initial state
         * @param previous
         *          previous node
         * @param isTwin
         *          returns true if a twin board node
         */
        public BoardNode(Board board, int depth, BoardNode previous, Boolean isTwin) {
            this.board = board;
            this.depth = depth;
            this.previous = previous;
            this.isTwin = isTwin;
        }
        
    }

    /**
     * returns manhattan comparator
     * @return
     */
    private Comparator<BoardNode> manhattanOrder() {
        return new ManhattanOrder();
    }
    
    /**
     * Manhattan comparator that compares depth and manhattan value 
     * @author shadowaxis9000
     *
     */
    private class ManhattanOrder implements Comparator<BoardNode> {
        
        // store manhattan in board node 
        
        
        
        @Override
        public int compare(BoardNode o1, BoardNode o2) {
            int num1 = o1.depth + o1.board.manhattan();
            int num2 = o2.depth + o2.board.manhattan();
            return Integer.compare(num1, num2);
        }
    }

    /** 
     * create solver with initial board
     * @param initial
     */
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        solver = new MinPQ<BoardNode>(manhattanOrder());
        solver.insert(new BoardNode(initial, 0, null, false));
        solver.insert(new BoardNode(initial.twin(), 0, null, true));

        Board previousBoard = null;

        while (!solver.min().board.isGoal()) { // maybe this is taking too long to check???

            BoardNode holder = solver.delMin();
            
            if (holder.previous != null) {
                previousBoard = holder.previous.board;
            } else {
                previousBoard = null;
            }
            for (Board x : holder.board.neighbors()) {
                if (!x.equals(previousBoard)) {
                    solver.insert(new BoardNode(x, holder.depth + 1, holder, holder.isTwin));
                }
            }
        }
    }

    /**
     * 
     * @return
     */
    public boolean isSolvable() {
        // is the initial board solvable?
        if (solver.min().board.isGoal() && solver.min().isTwin) {
            return false;
        }
        return true;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (solver.min().board.isGoal() && solver.min().isTwin) {
            return -1;
        }
        return solver.min().depth;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!this.isSolvable()) {
            return null;
        }
        Stack<Board> solution = new Stack<Board>();
        BoardNode current = new BoardNode(solver.min().board, solver.min().depth, solver.min().previous, false);
        while (current.depth != 0) {
            solution.push(current.board);
            current = current.previous;
        }
        // remember to add initial
        solution.push(initialBoard);
        return solution;
    }

    public static void main(String[] args) {
        Stopwatch stopWatch = new Stopwatch();

        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In("8puzzle-test-files/puzzle30.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }

        System.out.println("Elapsed Time: " + stopWatch.elapsedTime());
    }
}