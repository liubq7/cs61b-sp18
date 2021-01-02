import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        Color left, right, up, down;
        int[] neighborsX = getNeighbor(x, false);
        int[] neighborsY = getNeighbor(y, true);
        left = picture.get(neighborsX[0], y);
        right = picture.get(neighborsX[1], y);
        up = picture.get(x, neighborsY[0]);
        down = picture.get(x, neighborsY[1]);

        return gradient(left, right) + gradient(up, down);
    }

    private int[] getNeighbor(int n, boolean isVertical) {
        int[] neighbors = new int[2];
        if (isVertical) {
            neighbors[0] = n > 0 ? n - 1 : height() - 1;
            neighbors[1] = n + 1 < height() ? n + 1 : 0;
        } else {
            neighbors[0] = n > 0 ? n - 1 : width() - 1;
            neighbors[1] = n + 1 < width() ? n + 1 : 0;
        }
        return neighbors;
    }

    private double gradient(Color c1, Color c2) {
        double dr = c1.getRed() - c2.getRed();
        double dg = c1.getGreen() - c2.getGreen();
        double db = c1.getBlue() - c2.getBlue();
        return dr * dr + dg * dg + db * db;
    }




//    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
//
//    // sequence of indices for vertical seam
//    public int[] findVerticalSeam()
//    public void removeHorizontalSeam(int[] seam)   // remove horizontal seam from picture
//    public void removeVerticalSeam(int[] seam)     // remove vertical seam from picture
}
