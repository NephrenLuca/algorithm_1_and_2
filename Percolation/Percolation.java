/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionSet, unionFull;
    private int size, openedSites;
    private boolean[] opened;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        // using 0 and n * n + 1 as super sources
        unionSet = new WeightedQuickUnionUF(n * n + 2);
        unionFull = new WeightedQuickUnionUF(n * n + 1);
        size = n;
        opened = new boolean[n * n + 2];
        opened[0] = true;
        openedSites = 0;
    }

    private int getPos(int row, int col) {
        return size * (row - 1) + col;
    }

    private void unionFillIn(int row1, int col1, int row2, int col2) {
        // unites two adjacent open sites
        int pos1 = getPos(row1, col1);
        int pos2 = getPos(row2, col2);
        unionSet.union(pos1, pos2);
        unionFull.union(pos1, pos2);
    }

    public void open(int row, int col) {
        if (!(1 <= row && row <= size && 1 <= col && col <= size)) {
            throw new IllegalArgumentException("row and col out of range");
        }
        int pos = getPos(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openedSites++;
        opened[pos] = true;
        if (row == 1) {
            unionSet.union(0, pos);
            unionFull.union(0, pos);
        }
        if (row == size) {
            unionSet.union(size * size + 1, pos);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            unionFillIn(row, col, row - 1, col);
        }
        if (row < size && isOpen(row + 1, col)) {
            unionFillIn(row, col, row + 1, col);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            unionFillIn(row, col, row, col - 1);
        }
        if (col < size && isOpen(row, col + 1)) {
            unionFillIn(row, col, row, col + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!(1 <= row && row <= size && 1 <= col && col <= size)) {
            throw new IllegalArgumentException("row and col out of range");
        }
        int pos = getPos(row, col);
        return opened[pos];
    }

    public boolean isFull(int row, int col) {
        if (!(1 <= row && row <= size && 1 <= col && col <= size)) {
            throw new IllegalArgumentException("row and col out of range");
        }
        int pos = getPos(row, col);
        return unionFull.find(pos) == unionFull.find(0);
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        return unionSet.find(size * size + 1) == unionSet.find(0);
    }

    public static void main(String[] args) {
        int n, k;
        n = StdIn.readInt();
        Percolation p = new Percolation(n);
        k = StdIn.readInt();
        for (int i = 0; i < k; i++) {
            int x, y;
            x = StdIn.readInt();
            y = StdIn.readInt();
            p.open(x, y);
        }
        StdOut.println(p.percolates());
        for (int j = 1; j <= n; j++) {
            for (int kk = 1; kk <= n; kk++) {
                StdOut.print(p.isOpen(j, kk));
            }
            StdOut.println();
        }
    }
}
