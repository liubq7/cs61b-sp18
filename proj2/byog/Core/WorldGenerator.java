package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    private int width;
    private int height;

    public WorldGenerator(int w, int h) {
        width = w;
        height = h;
    }

    private void background(int width, int height) {
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* num也是随机来的,并且要规定范围(吗?) */
    public void drawRoomList(TETile[][] world, int num, Random random) {
        ArrayList<Room> roomList = new ArrayList<>();
        Room rr1 = Room.randomRoom(width, height, random);
        roomList.add(rr1);
        Room.drawARoom(world, rr1);
        for (int i = 0; i < num - 1; i += 1) {
            Room rr = Room.randomRoom(width, height, random);
            for (Room r : roomList) {
                if (r.isOverlap(rr)) {
                    break;// todo: 推迟大的for循环,how?
                }
            }
            roomList.add(rr);
            Room.drawARoom(world, rr);
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

        WorldGenerator wg = new WorldGenerator(60, 30);
        wg.drawRoomList(world, 5, random);

        ter.renderFrame(world);
    }
}
