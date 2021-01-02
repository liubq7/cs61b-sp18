import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.ArrayList;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        Color left, right, up, down;
        left = x > 0 ? picture.get(x - 1, y) : picture.get(width - 1, y);
        right = x + 1 < width ? picture.get(x + 1, y) : picture.get(0, y);
        up = y > 0 ? picture.get(x, y - 1) : picture.get(x, height - 1);
        down = y + 1 < height ? picture.get(x, y + 1) : picture.get(x, 0);

        return gradient(left, right) + gradient(up, down);
    }

    private double gradient(Color c1, Color c2) {
        double dr = c1.getRed() - c2.getRed();
        double dg = c1.getGreen() - c2.getGreen();
        double db = c1.getBlue() - c2.getBlue();
        return dr * dr + dg * dg + db * db;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double cost[][] = new double[width][height];
        int path[][] = new int[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (j == 0) {
                    cost[i][j] = energy(i, j);
                    path[i][j] = -1;
                } else {
                    double minCost = cost[i][j - 1];
                    int parent = i;
                    for (int n : getParent(i)) {
                        if (cost[n][j - 1] < minCost) {
                            minCost = cost[n][j - 1];
                            parent = n;
                        }
                    }
                    path[i][j] = parent;
                    cost[i][j] = energy(i, j) + minCost;
                }
            }
        }

        double minTotalCost = cost[0][height - 1];
        int minIndex = 0;
        for (int i = 0; i < width; i++) {
            if (cost[i][height - 1] < minTotalCost) {
                minTotalCost = cost[i][height - 1];
                minIndex = i;
            }
        }

        int[] paths = new int[height];
        int row = height - 1;
        while (row >= 0) {
            paths[row] = minIndex;
            minIndex = path[minIndex][row];
            row -= 1;
        }

        return paths;
    }

    private ArrayList<Integer> getParent(int n) {
        ArrayList<Integer> parents = new ArrayList<>();
        if (n > 0) {
            parents.add(n - 1);
        }
        if (n + 1 < width) {
            parents.add(n + 1);
        }
        return parents;
    }


    // sequence of indices for horizontal seam
    // transposing the image, running findVerticalSeam()
    public int[] findHorizontalSeam() {
        int[] paths = transpose().findVerticalSeam();
        return paths;
    }

    private SeamCarver transpose() {
        Picture trans = new Picture(height, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                trans.set(j, i, picture.get(i, j));
            }
        }
        SeamCarver transpose = new SeamCarver(trans);
        return transpose;
    }


    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {

    }

    public static void main(String[] args) {
        Picture p = new Picture("images/6x5.png");
        SeamCarver sc = new SeamCarver(p);

        int[] seam = sc.findVerticalSeam();
        for (int i = 0; i < seam.length; i++) {
            System.out.println(seam[i]);
        }

    }
}
