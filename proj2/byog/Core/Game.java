package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
	public static final int NUM = 25;
	private String seedString = "";

	private TETile[][] worldGenerate(long seed) {
		Random random = new Random(seed);

		// initialize tiles
		TETile[][] world = new TETile[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x += 1) {
			for (int y = 0; y < HEIGHT; y += 1) {
				world[x][y] = Tileset.NOTHING;
			}
		}

		ArrayList<Room> rl = Room.roomList(world, NUM, random);
		ArrayList<Hallway> hl = Hallway.hallwayList(rl);
		Hallway.drawHListWall(world, hl);
		Room.drawRoomList(world, rl);
		Hallway.drawHListFloor(world, hl);
		Position door = Room.lockedDoor(world, rl, random);
		world[door.x][door.y] = Tileset.LOCKED_DOOR;

		return world;
	}


	private void processInput(String input) {
		if (input == null) {
			throw new RuntimeException("No input given.");
		}

		String first = Character.toString(input.charAt(0));
		first = first.toLowerCase(); // normalize an input to lower case
		processString(first);

		if (input.length() > 1) {
			String rest = input.substring(1);
			processInput(rest); // recursive call until input ends
		}

	}
	private void processString(String first) {
		if (first.equals("n")) { // start and initializes new game

		} else if (first.equals("s")) { // generate a randomized world

//        } else if (first == "l") {
//            load();
//        } else if (first == ":") {
//            setQuitFlag();
//        } else if (first == "q") {
//            quit();
		} else {                // append next seed integer to seedString
			try {
				Long.parseLong(first);
				seedString += first;
			} catch (NumberFormatException e) { // throw error if invalid input given
				throw new RuntimeException("Invalid input given: " + first);
			}
		}

	}

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

	    processInput(input);
	    long seed = Long.parseLong(seedString);
        TETile[][] finalWorldFrame = worldGenerate(seed);
        return finalWorldFrame;
    }
}
