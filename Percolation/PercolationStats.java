/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONST_K = 1.96;
    private double[] percolationThreshold;
    private int t;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        percolationThreshold = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int openX = StdRandom.uniformInt(1, n + 1);
                int openY = StdRandom.uniformInt(1, n + 1);
                while (p.isOpen(openX, openY)) {
                    openX = StdRandom.uniformInt(1, n + 1);
                    openY = StdRandom.uniformInt(1, n + 1);

                }
                p.open(openX, openY);
            }
            percolationThreshold[i] = p.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(percolationThreshold);
    }

    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }

    public double confidenceLo() {
        return mean() - (CONST_K * stddev() / Math.sqrt(t * 1.0));
    }

    public double confidenceHi() {
        return mean() + (CONST_K * stddev() / Math.sqrt(t * 1.0));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        double mean = stats.mean();
        double stddev = stats.stddev();
        double coLo = stats.confidenceLo();
        double coHi = stats.confidenceHi();
        StdOut.println("mean                    = " + String.valueOf(mean));
        StdOut.println("stddev                  = " + String.valueOf(stddev));
        StdOut.println(
                "95% confidence interval = [" + String.valueOf(coLo) + ", " + String.valueOf(coHi)
                        + "]");
    }
}
