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

        ArrayList<Room> rl = Room.roomList(world, 20, random);
        ArrayList<Hallway> hl = Hallway.hallwayList(rl);
        Hallway.drawHListWall(world, hl);
        Room.drawRoomList(world, rl);
        Hallway.drawHListFloor(world, hl);

        ter.renderFrame(world);
    }
}
