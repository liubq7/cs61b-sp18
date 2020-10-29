package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int numOfOpen = 0;
    private boolean[] openGridIn1D;
    private WeightedQuickUnionUF topBottom;
    private WeightedQuickUnionUF top;
    private int virtualTop;
    private int virtualBottom;

    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) throws IllegalArgumentException {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0 but given N = " + N + ".");
        }
        this.N = N;
        openGridIn1D = new boolean[N * N];
        topBottom = new WeightedQuickUnionUF(N * N + 2);
        top = new WeightedQuickUnionUF(N * N + 1);

        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    private boolean isInvalidIndex(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return true;
        } else {
            return false;
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) throws IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arguments given: row = " + row + " col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        openGridIn1D[indexIn1D] = true;
        numOfOpen += 1;

        updateConnection(row, col, row - 1, col);
        updateConnection(row, col, row + 1, col);
        updateConnection(row, col, row, col - 1);
        updateConnection(row, col, row, col + 1);

        if (row == 0) {
            topBottom.connected(indexIn1D, virtualTop);
            top.connected(indexIn1D, virtualTop);
        }
        if (row == N - 1) {
            topBottom.connected(indexIn1D, virtualBottom);
        }
    }

    private void updateConnection(int row, int col, int neighborRow, int neighborCol) {
        int index = xyTo1D(row, col);
        if (!isInvalidIndex(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
            int neighborIndex = xyTo1D(neighborRow, neighborCol);
            topBottom.union(index, neighborIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arguments given: row = " + row + " col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        return openGridIn1D[indexIn1D];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arguments given: row = " + row + " col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        return top.connected(indexIn1D, virtualTop);
    }

    // number of open sites, must take constant time
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return topBottom.connected(virtualTop, virtualBottom);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
