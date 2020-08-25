package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    // TODO: 修改是否需要
    private int width;
    private int height;

    // TODO: 修改是否需要
    public WorldGenerator(int w, int h) {
        width = w;
        height = h;
    }

    // TODO: 修改是否需要
    private void background(int width, int height) {
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    /**
     * 生成一系列不相交的随机位置随机大小的房间（含围墙）
     * @param num 预计要产生的房间数（实际扣除重叠可能会小于这个数）介于15-20比较合适?（是否需要随机生成？随机应由seed确定一个不变的数）
     * @param random 由seed产生的随机数，一旦seed确定则生成房间list确定（除数量）
     */
    public static ArrayList<Room> generateRoomList(TETile[][] world, int num, Random random) {
        ArrayList<Room> roomList = new ArrayList<>();
        Room rr1 = Room.randomRoom(world, random);
        roomList.add(rr1);
        for (int i = 0; i < num - 1; i += 1) {
            Room rr = Room.randomRoom(world, random);
            boolean available = true;
            for (Room r : roomList) {
                if (r.isOverlap(rr)) {
                    available = false;
                    break;
                }
            }
            if (available == true) {
                roomList.add(rr);
            }
        }
        return roomList;
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

        ArrayList<Room> rl = generateRoomList(world, 15, random);
        Room.drawRoomList(world, rl);

        ter.renderFrame(world);
    }
}
