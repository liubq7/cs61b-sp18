package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class Room {
    /* 房间包括围墙部分 */
    private Position bottomLeft;
    private int width;
    private int height;

    private Room(Position p, int w, int h) {
        bottomLeft = p;
        width = w;
        height = h;
    }

    /* 房间四角坐标 */
    private Position[] cornerPosition() {
        Position[] cp = new Position[4];
        cp[0] = bottomLeft;
        cp[1] = new Position(bottomLeft.x, bottomLeft.y + height - 1);
        cp[2] = new Position(bottomLeft.x + width - 1, bottomLeft.y + height - 1);
        cp[3] = new Position(bottomLeft.x + width - 1, bottomLeft.y);
        return cp;
    }

    /**
     * 生成一个随机位置的随机大小的房间,矩形长宽的取值范围为[4,9],房间不能超出world
     * 如果超出就生成一个在bottomleft位置最小的矩形(4X4)
     * @param random 由SEED生成的随机数
     */
    public static Room randomRoom(TETile[][] world, Random random) {
        int ww = world.length;
        int wh = world[0].length;

        int px = random.nextInt(ww - 3);
        int py = random.nextInt(wh - 3);
        Position p = new Position(px, py);
        int w = RandomUtils.uniform(random, 4, 10);
        int h = RandomUtils.uniform(random, 4, 10);
        Room rr = new Room(p, w, h);

        Position[] cp = rr.cornerPosition();
        for (Position P : cp) {
            if (P.x > ww - 1 || P.y > wh - 1) {
                return new Room(p, 4,4);
            }
        }
        return rr;
    }


    /* 某点是否包含在该房间(不含围墙)中 */
    private boolean containPosition(Position p) {
        return (p.x > bottomLeft.x && p.x < bottomLeft.x + width - 1)
                && (p.y > bottomLeft.y && p.y < bottomLeft.y + height - 1);
    }
    /* 房间r(含围墙)是否与此房间(不含围墙)相交 */
    public boolean isOverlap(Room r) {
        Position[] cp = r.cornerPosition();
        for (Position p : cp) {
            if (containPosition(p)) {
                return true;
            }
        }
        return false;
    }

    /* 绘制房间+围墙部分 */
    public static void drawRoom(TETile[][] world, Position p, int w, int h) {
        for (int x = p.x; x < p.x + w; x += 1) {
            for (int y = p.y; y < p.y + h; y += 1) {
                if (x == p.x || x == p.x + w - 1 || y == p.y || y == p.y + h - 1) {
                    world[x][y] =Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }
    public static void drawRoom(TETile[][] world, Room r) {
        Position p = r.bottomLeft;
        int w = r.width;
        int h = r.height;
        for (int x = p.x; x < p.x + w; x += 1) {
            for (int y = p.y; y < p.y + h; y += 1) {
                if (x == p.x || x == p.x + w - 1 || y == p.y || y == p.y + h - 1) {
                    world[x][y] =Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);
        Random random = new Random(233);

        // initialize tiles
        TETile[][] world = new TETile[60][30];
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(25, 10);
        drawRoom(world, p, 7, 5);

        Room rr = randomRoom(world, random);
        drawRoom(world, rr);

        ter.renderFrame(world);
    }
}
