package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGenerator {
    private int width;
    private int height;

    private static class Position {
        private int x;
        private int y;

        private Position(int X, int Y) {
            x = X;
            y = Y;
        }
    }

    private void initialize() {
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    // TODO: check能组成矩形.
    private void makeRoom(TETile[][] world, Position leftBottom, Position rightUpper) {
        for (int x = leftBottom.x; x <= rightUpper.x; x += 1) {
            for (int y = leftBottom.y; y <= rightUpper.y; y += 1) {
                if (x == leftBottom.x || x == rightUpper.x || y == leftBottom.y || y == rightUpper.y) {
                    world[x][y] =Tileset.FLOOR;
                } else {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

}
