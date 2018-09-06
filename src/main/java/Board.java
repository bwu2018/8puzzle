import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class Board {

    private final int[][] board;
    private ArrayList<Board> list;
    private int manhattan;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.board[i][j] = blocks[i][j];
            }
        }
        
        // calculate manhattan 
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    manhattan += (Math.abs(getCorrectRow(board[i][j]) - i) + Math.abs(getCorrectCol(board[i][j]) - j));
                }
            }
        }
    }

    public int dimension() {
        // board dimension n
        return board.length;
    }

    public int hamming() {
        // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != (i * board.length + j + 1)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        return manhattan;
    }

    private int getCorrectRow(int value) {
        return (value - 1) / board.length;
    }

    private int getCorrectCol(int value) {
        return (value - 1) % board.length;
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (hamming() == 0) {
            return true;
        }
        return false;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int num = 0;
        int row = 0;
        int col = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && num == 0) {
                    row = i;
                    col = j;
                    num++;
                } else if (board[i][j] != 0 && num == 1) {
                    return exchange(board, row, col, i, j);
                }
            }
        }
        throw new IllegalStateException();
    }

    private Board exchange(int[][] copy, int x, int y, int r, int c) {
        int[][] copyArray = new int[copy.length][copy.length];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length; j++) {
                copyArray[i][j] = copy[i][j];
            }
        }
        int holder = copyArray[x][y];
        copyArray[x][y] = copyArray[r][c];
        copyArray[r][c] = holder;
        return new Board(copyArray);
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board that = (Board) y;

        // check if this and that are same array size
        if (this.dimension() != that.dimension()) {
            return false;
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        // clear list 
        list = new ArrayList<Board>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row != 0) {
            Board copy = this.exchange(board, row, col, row - 1, col);
            list.add(copy);
        }
        if (row < board.length - 1) {
            Board copy = this.exchange(board, row, col, row + 1, col);
            list.add(copy);
        }
        if (col != 0) {
            Board copy = this.exchange(board, row, col, row, col - 1);
            list.add(copy);
        }
        if (col < board.length - 1) {
            Board copy = exchange(board, row, col, row, col + 1);
            list.add(copy);
        }
        return list;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In("8puzzle-test-files/puzzle09.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println("Initial is " + initial.toString());
        System.out.println("Twin is " + initial.twin().toString());
        System.out.println("Manhattan is " + initial.manhattan());
        System.out.println("Hamming is " + initial.hamming());
        for (Board b : initial.neighbors()) {
            System.out.println("Neighbor is " + b.toString());
        }
        System.out.println("test 2____________________________");
        System.out.println("Initial is " + initial.toString());
        System.out.println("Twin is " + initial.twin().toString());
        System.out.println("Manhattan is " + initial.manhattan());
        System.out.println("Hamming is " + initial.hamming());
        for (Board b : initial.neighbors()) {
            System.out.println("Neighbor is " + b.toString());
        }
        

    }
}