package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private PercolationFactory pf;
    private double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) throws IllegalArgumentException {
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N should be greater than 0 but given N = " + N + "."
            );
        }
        if (T <= 0) {
            throw new IllegalArgumentException(
                    "T should be greater than 0 but given T = " + T + "."
            );
        }

        this.N = N;
        this.T = T;
        this.pf = pf;
        thresholds = new double[T];
    }

    private void simulate() {
        for (int t = 0; t < T; t++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            double threshold = percolation.numberOfOpenSites() / (N * N);
            thresholds[t] = threshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

}
