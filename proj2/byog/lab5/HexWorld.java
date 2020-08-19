package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     */
    public static int hexRowWidth(int s, int i) {
        if (i >= s) {
            return 5 * s - 2 * i - 2;
        } else {
            return s + 2 * i;
        }
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxx
     *  xxxxx
     * xxxxxxx
     * xxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxx
     *   xxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     */
    public static int hexRowOffset(int s, int i) {
        if (i >= s) {
            return 1 + i - 2 * s;
        } else {
            return  -i;
        }
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int X, int Y) {
            x = X;
            y = Y;
        }
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    public static void background(TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    /** compute the bottom-left Position of a current hexagon’s top right neighbor. */
    public static Position topRightNeighbor(Position curr, int s) {
        int y = curr.y + s;
        int x = curr.x + 2 * s - 1;
        Position trn = new Position(x, y);
        return trn;
    }

    /** compute the bottom-left Position of a current hexagon’s bottom right neighbor. */
    public static Position bottomRightNeighbor(Position curr, int s) {
        int y = curr.y - s;
        int x = curr.x + 2 * s - 1;
        Position brn = new Position(x, y);
        return brn;
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        background(world, WIDTH, HEIGHT);

        Position p = new Position(10, 20);
        int s = 5;
        TETile t = Tileset.FLOWER;
        addHexagon(world, p, s, t);

        Position ptrn = topRightNeighbor(p,s);
        Position pbrn = bottomRightNeighbor(p,s);
        TETile t1 = Tileset.GRASS;
        TETile t2 = Tileset.SAND;
        addHexagon(world, ptrn, s, t1);
        addHexagon(world, pbrn, s, t2);

        ter.renderFrame(world);
    }



    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }
}
