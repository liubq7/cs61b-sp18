package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    // just for test

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        Random random = new Random(333);

        // initialize tiles
        TETile[][] world = new TETile[80][30];
        for (int x = 0; x < 80; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        ArrayList<Room> rl = Room.roomList(world, 25, random);
        ArrayList<Hallway> hl = Hallway.hallwayList(rl);
        Hallway.drawHListWall(world, hl);
        Room.drawRoomList(world, rl);
        Hallway.drawHListFloor(world, hl);
        Position door = Room.lockedDoor(world, rl, random);
        world[door.x][door.y] = Tileset.LOCKED_DOOR;

        ter.renderFrame(world);
    }
}
