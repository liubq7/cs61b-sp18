package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;

public class WorldGenerator {
    private int width;
    private int height;

    private void background(int width, int height) {
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* num也是随机来的 */
    public void drawRoomList(int num) {
        ArrayList<Room> roomList = new ArrayList<>();
        for (int i = 0; i < num; i += 1) {

        }
    }
}
