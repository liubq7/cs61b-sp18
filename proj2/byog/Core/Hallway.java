package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {
    private Position start;
    private Position end;
    private Position corner;

    /* start在左边，corner与start同y */
    private Hallway(Position p1, Position p2) {
        if (p1.x <= p2.x) {
            start = p1;
            end = p2;
        } else {
            start = p2;
            end = p1;
        }

        int pcx = end.x;
        int pcy = start.y;
        Position pc = new Position(pcx, pcy);
        corner = pc;
    }

    /* 绘制过道不含围墙部分 */
    public static void drawHallway(TETile[][] world, Hallway h) {
        for (int x = h.start.x; x < h.corner.x; x += 1) {
            world[x][h.start.y] = Tileset.FLOOR;
        }

        int y1;
        int y2;
        if (h.corner.y <= h.end.y) {
            y1 = h.corner.y;
            y2 = h.end.y;
        } else {
            y1 = h.end.y;
            y2 = h.corner.y;
        }
        for (int y = y1; y <= y2; y += 1) {
            world[h.end.x][y] = Tileset.FLOOR;
        }
    }
}
