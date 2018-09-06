import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoardTest {
  
  Board board;
  @Before
  public void setUp() throws Exception {
    board = generateBoard("puzzle07.txt");
  }
  
  private Board generateBoard(String filename) {
 // create initial board from file
    In in = new In("8puzzle-test-files/" + filename);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
//    if (!solver.isSolvable())
//        StdOut.println("No solution possible");
//    else {
//        StdOut.println("Minimum number of moves = " + solver.moves());
//        for (Board board : solver.solution())
//            StdOut.println(board);
//    }
    return initial;
  }

  @Test
  public void testDimension() {
    //fail("Not yet implemented");
  }

  @Test
  public void testSegments() {
    //fail("Not yet implemented");
  }

}
