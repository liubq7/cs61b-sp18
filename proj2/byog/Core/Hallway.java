package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hallway {
    /* start在左边，corner与start同y */
    private Position hStart;
    private Position hEnd;

    private Hallway(Position p1, Position p2) {
        if (p1.x <= p2.x) {
            hStart = p1;
            hEnd = p2;
        } else {
            hStart = p2;
            hEnd = p1;
        }
    }

    private static class L {
        private Position start;
        private Position end;
        private Position corner;

        /* L不再判断起始位置，判断完了再创建类 */
        private L(Position pStart, Position pEnd) {
            start = pStart;
            end = pEnd;

            Position pCorner = new Position(pEnd.x, pStart.y);
            corner = pCorner;
        }
    }


    /* 绘制L型 */
    private static void drawL(TETile[][] world, L l, TETile t) {
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

    /* 绘制end在上面的hallway（含围墙） */
    private static void drawHallwayUp(TETile[][] world, Hallway h) {
        L l1 = new L(h.hStart, h.hEnd);
        L l2 = new L(h.hStart.upPosition(), h.hEnd.leftPosition());
        L l3 = new L(h.hStart.downPosition(), h.hEnd.rightPosition());
        drawL(world, l1, Tileset.FLOOR);
        drawL(world, l2, Tileset.WALL);
        drawL(world, l3, Tileset.WALL);
    }
    /* 绘制end在下面的hallway（含围墙） */
    private static void drawHallwayDown(TETile[][] world, Hallway h) {
        L l1 = new L(h.hStart, h.hEnd);
        L l2 = new L(h.hStart.upPosition(), h.hEnd.rightPosition());
        L l3 = new L(h.hStart.downPosition(), h.hEnd.leftPosition());
        drawL(world, l1, Tileset.FLOOR);
        drawL(world, l2, Tileset.WALL);
        drawL(world, l3, Tileset.WALL);
    }

    /* 绘制hallway（含围墙） */
    public static void drawHallway(TETile[][] world, Hallway h) {
        if (h.hEnd.y > h.hStart.y) {
            drawHallwayUp(world, h);
        } else {
            drawHallwayDown(world, h);
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

        Position p1 = new Position(3, 10);
        Position p2 = new Position(15, 13);
        Position p3 = new Position(45, 10);
        Position p4 = new Position(20, 20);
        Hallway h1 = new Hallway(p1, p2);
        Hallway h2 = new Hallway(p3, p4);
        drawHallway(world, h1);
        drawHallway(world, h2);


        ter.renderFrame(world);
    }
}
