package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hallway {
    private Position hStart;
    private Position hEnd;

    private static class L {
        private Position start;
        private Position end;
        private Position corner;

        /* start在左边，corner与start同y */
        private L(Position p1, Position p2) {
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
    }


    /* 绘制L型 */
    public static void drawL(TETile[][] world, L l, TETile t) {
        for (int x = l.start.x; x < l.corner.x; x += 1) {
            world[x][l.start.y] = t;
        }

        int y1;
        int y2;
        if (l.corner.y <= l.end.y) {
            y1 = l.corner.y;
            y2 = l.end.y;
        } else {
            y1 = l.end.y;
            y2 = l.corner.y;
        }
        for (int y = y1; y <= y2; y += 1) {
            world[l.end.x][y] = t;
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);

        // initialize tiles
        TETile[][] world = new TETile[60][30];
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p1 = new Position(25, 10);
        Position p2 = new Position(30, 20);
        L l = new Hallway.L(p1, p2);
        drawL(world, l, Tileset.FLOWER);


        ter.renderFrame(world);
    }
}
