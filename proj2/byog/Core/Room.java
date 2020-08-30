package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Room {
    /* 房间包括围墙部分 */
    private Position bottomLeft;
    private int width;
    private int height;
    private Position randomPos;

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

    /* 生成房间内的一个随机点，不包含围墙 */
    private void randomPosition(Random random) {
        int rx = RandomUtils.uniform(random, bottomLeft.x + 1, bottomLeft.x + width - 1);
        int ry = RandomUtils.uniform(random, bottomLeft.y + 1, bottomLeft.y + height - 1);
        randomPos = new Position(rx, ry);
    }

    /* 外界获取randomPos */
    public Position getRandomPos() {
        return randomPos;
    }

    /**
     * 生成一个随机位置的随机大小的房间,矩形长宽的取值范围为[4,9],房间不能超出world
     * 如果超出就生成一个在bottomleft位置最小的矩形(4X4)
     * 同时生成该房间内的一个随机点
     * @param random 由SEED生成的随机数
     */
    private static Room randomRoom(TETile[][] world, Random random) {
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
                rr = new Room(p, 4,4);
                break;
            }
        }
        rr.randomPosition(random);
        return rr;
    }


    /* 某点是否包含在该房间(含围墙)中 */
    private boolean containPosition(Position p) {
        return (p.x >= bottomLeft.x && p.x < bottomLeft.x + width)
                && (p.y >= bottomLeft.y && p.y < bottomLeft.y + height);
    }
    /* 房间r(含围墙)是否与此房间(含围墙)相交 */
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
    private static void drawRoom(TETile[][] world, Position p, int w, int h) {
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
    private static void drawRoom(TETile[][] world, Room r) {
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

    /* 绘制roomlist */
    public static void drawRoomList(TETile[][] world, ArrayList<Room> roomList) {
        for (Room r : roomList) {
            drawRoom(world, r);
        }
    }


    /**
     * 生成一系列不相交的随机位置随机大小的房间（含围墙），并组成arraylist
     * @param num 预计要产生的房间数（实际扣除重叠可能会小于这个数）设为固定值25
     * @param random 由seed产生的随机数，一旦seed确定则生成房间list确定（除数量）
     */
    private static ArrayList<Room> generateRoomList(TETile[][] world, int num, Random random) {
        ArrayList<Room> rl = new ArrayList<>();
        Room rr1 = randomRoom(world, random);
        rl.add(rr1);
        for (int i = 0; i < num - 1; i += 1) {
            Room rr = randomRoom(world, random);
            boolean available = true;
            for (Room r : rl) {
                if (r.isOverlap(rr) || rr.isOverlap(r)) {
                    available = false;
                    break;
                }
            }
            if (available == true) {
                rl.add(rr);
            }
        }
        return rl;
    }

    private static class roomComparator implements Comparator<Room> {
        /* 比较r1与r2 randompos的x+y，r1比r2小则返回负数 */
        @Override
        public int compare(Room r1, Room r2) {
            int rpSum1 = r1.randomPos.x + r1.randomPos.y;
            int rpSum2 = r2.randomPos.x + r2.randomPos.y;
            return rpSum1 - rpSum2;
        }
    }

    /**
     * 生成一系列不相交的随机位置随机大小的房间（含围墙），并组成arraylist
     * 按照randompos的位置大小对roomlist进行排序，randompos的x+y越小则越靠前
     * @param num 预计要产生的房间数（实际扣除重叠可能会小于这个数）设为固定值25
     * @param random 由seed产生的随机数，一旦seed确定则生成房间list确定（除数量）
     */
    public static ArrayList<Room> roomList(TETile[][] world, int num, Random random) {
        ArrayList<Room> rl = generateRoomList(world, num, random);
        roomComparator cp = new roomComparator();
        rl.sort(cp);
        return rl;
    }



    /* 生成locked door的position */
    public static Position lockedDoor(TETile[][] world, ArrayList<Room> rl, Random random) {
        int doorIndex = random.nextInt(rl.size());
        Room doorRoom = rl.get(doorIndex);
        ArrayList<Position> door = new ArrayList<>();
        for (int i = doorRoom.bottomLeft.x + 1; i < doorRoom.bottomLeft.x + doorRoom.width - 1; i++) {
            if (doorRoom.bottomLeft.y > 0) {
                if (world[i][doorRoom.bottomLeft.y].equals(Tileset.WALL)
                        && world[i][doorRoom.bottomLeft.y - 1].equals(Tileset.NOTHING)) {
                    door.add(new Position(i, doorRoom.bottomLeft.y));
                }
            }
            if (doorRoom.bottomLeft.y + doorRoom.height < world[0].length) {
                if (world[i][doorRoom.bottomLeft.y + doorRoom.height - 1].equals(Tileset.WALL)
                        && world[i][doorRoom.bottomLeft.y + doorRoom.height].equals(Tileset.NOTHING)) {
                    door.add(new Position(i, doorRoom.bottomLeft.y + doorRoom.height - 1));
                }
            }
        }
        for (int j = doorRoom.bottomLeft.y + 1; j < doorRoom.bottomLeft.y + doorRoom.height - 1; j++) {
            if (doorRoom.bottomLeft.x > 0) {
                if (world[doorRoom.bottomLeft.x][j].equals(Tileset.WALL)
                        && world[doorRoom.bottomLeft.x - 1][j].equals(Tileset.NOTHING)) {
                    door.add(new Position(doorRoom.bottomLeft.x, j));
                }
            }
            if (doorRoom.bottomLeft.x + doorRoom.width < world.length) {
                if (world[doorRoom.bottomLeft.x + doorRoom.width - 1][j].equals(Tileset.WALL)
                        && world[doorRoom.bottomLeft.x + doorRoom.width][j].equals(Tileset.NOTHING)) {
                    door.add(new Position(doorRoom.bottomLeft.x + doorRoom.width - 1, j));
                }
            }
        }

        if (door.size() > 0) {
            return door.get(random.nextInt(door.size()));
        } else {
            return lockedDoor(world, rl, random);
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


        ArrayList<Room> rl = roomList(world, 20, random);
        for (int i = 0; i < rl.size(); i++) {
            System.out.print(rl.get(i).randomPos.x + " ");
        }
        System.out.println();
        drawRoomList(world, rl);


        ter.renderFrame(world);
    }
}
