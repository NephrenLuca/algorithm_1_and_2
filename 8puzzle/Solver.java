/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final int moves;
    private final boolean solvable;


    private class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final int manhattanHeuristic;
        final Node prev;

        Node(Board b, int m, Node p) {
            board = b;
            moves = m;
            prev = p;
            manhattanHeuristic = m + board.manhattan();
        }

        public int compareTo(Node that) {
            return Integer.compare(manhattanHeuristic, that.manhattanHeuristic);
        }
    }

    private final Node goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null initializer");
        MinPQ<Node> solveSeq = new MinPQ<>();
        MinPQ<Node> twinSolveSeq = new MinPQ<>();
        solveSeq.insert(new Node(initial, 0, null));
        twinSolveSeq.insert(new Node(initial.twin(), 0, null));
        MinPQ<Node> executor = solveSeq;
        Node node = null;
        while (!executor.isEmpty()) {
            node = executor.delMin();
            if (node.board.isGoal()) {
                break;
            }
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board neighbor : neighbors) {
                if (node.prev == null || !neighbor.equals(node.prev.board)) {
                    executor.insert(new Node(neighbor, node.moves + 1, node));
                }
            }
            executor = executor == solveSeq ? twinSolveSeq : solveSeq;
        }
        solvable = executor == solveSeq;
        assert node != null;
        moves = node.moves;
        goal = node;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solvable) {
            return -1;
        }
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }
        Stack<Board> p = new Stack<>();
        Node cur = goal;
        while (cur != null) {
            p.push(cur.board);
            cur = cur.prev;
        }
        return p;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
