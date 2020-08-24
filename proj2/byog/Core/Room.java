package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room {
    /* 房间包括围墙部分 */
    private Position bottomLeft;
    private int width;  // TODO: 长宽的取值范围
    private int height;

    private Room(Position p, int w, int h) {
        bottomLeft = p;
        width = w;
        height = h;
    }

    /* 房间四角坐标 */
    private Position[] cornerPosition() {
        Position[] cp = new Position[4];
        cp[1] = bottomLeft;
        cp[2] = new Position(bottomLeft.x, bottomLeft.y + height - 1);
        cp[3] = new Position(bottomLeft.x + width - 1, bottomLeft.y + height - 1);
        cp[4] = new Position(bottomLeft.x + width - 1, bottomLeft.y);
        return cp;
    }

    /* 某点是否包含在该房间(不含围墙)中 */
    private boolean containPosition(Position p) {
        return (p.x > bottomLeft.x && p.x < bottomLeft.x + width - 1)
                && (p.y > bottomLeft.y && p.y < bottomLeft.y + height - 1);
    }
    /* 房间r(含围墙)是否与此房间(不含围墙)相交 */
    private boolean isOverlap(Room r) {
        Position[] cp = r.cornerPosition();
        for (Position p : cp) {
            if (containPosition(p)) {
                return true;
            }
        }
        return false;
    }

    /* 绘制房间+围墙部分 */
    public static void drawARoom(TETile[][] world, Position p, int w, int h) {
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

        // initialize tiles
        TETile[][] world = new TETile[60][30];
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(25, 10);
        byog.Core.Room.drawARoom(world, p, 7, 5);

        ter.renderFrame(world);
    }
}
