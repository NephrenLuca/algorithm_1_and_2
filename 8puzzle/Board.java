/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int[][] DIR = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    private final int n;
    private final int vacancyX;
    private final int vacancyY;
    private final int[][] tiles;
    private final int hamming;
    private final int manhattan;

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("null tiles");
        }
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], tiles.length);
        }
        int px = 0, py = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    px = i;
                    py = j;
                    break;
                }
            }
        }
        vacancyX = px;
        vacancyY = py;
        int hammingDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1) {
                    hammingDistance++;
                }
            }
        }
        hamming = hammingDistance;
        int manhattanDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int correctj = tiles[i][j] % n == 0 ? n - 1 : tiles[i][j] % n - 1;
                manhattanDistance += abs(tiles[i][j] / n - i) + abs(correctj - j);
            }
        }
        manhattan = manhattanDistance;
    }

    private int abs(int x) {
        return x > 0 ? x : -x;
    }

    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringRepresentation.append(String.format("%2d ", tiles[i][j]));
            }
            stringRepresentation.append("\n");
        }
        return stringRepresentation.toString();
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Board that = (Board) obj;
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    private int[][] copyBoard() {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    private boolean isInRange(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> p = new ArrayList<>();
        int moveX = vacancyX;
        int moveY = vacancyY;
        for (int[] dir : DIR) {
            moveX += dir[0];
            moveY += dir[1];
            if (isInRange(moveX, moveY)) {
                int[][] newBoard = copyBoard();
                newBoard[vacancyX][vacancyY] = newBoard[moveX][moveY];
                newBoard[moveX][moveY] = 0;
                p.add(new Board(newBoard));
            }
        }
        return p;
    }

    public Board twin() {
        int[][] newTiles = copyBoard();
        int[] indices = new int[4];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != vacancyX || j != vacancyY) {
                    indices[k++] = i;
                    indices[k++] = j;
                    if (k == indices.length) break;
                }
            }
            if (k == indices.length) break;
        }
        int t = newTiles[indices[0]][indices[1]];
        newTiles[indices[0]][indices[1]] = newTiles[indices[2]][indices[3]];
        newTiles[indices[2]][indices[3]] = t;
        return new Board(newTiles);
    }

    public static void main(String[] args) {
    }
}
