package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final Font BIG = new Font("Monaco", Font.BOLD, 30);
    private static final Font SMALL = new Font("Monaco", Font.BOLD, 20);
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        // Generate random string of letters of length n
        String generated = "";
        while (n > 0) {
            char picked = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            generated += Character.toString(picked);
            n -= 1;
        }
        return generated;
    }

    public void drawFrame(String s) {
        // Take the string and display it in the center of the screen
        // If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);

        if (!gameOver) {
        	StdDraw.setFont(SMALL);
	        StdDraw.textLeft(1, height - 1, "Round: " + round);
	        StdDraw.text(width / 2, height - 1, playerTurn ? "Type!" : "Watch!");
	        StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
	        StdDraw.line(0, height - 2, width, height - 2);
        }

        StdDraw.setFont(BIG);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text((double) width / 2, (double) height / 2, s);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            char letter = letters.charAt(i);
            String s = Character.toString(letter);

            drawFrame(s);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
	    String typed = "";
	    while (n > 0) {
	    	if (StdDraw.hasNextKeyTyped()) {
	    		char next = StdDraw.nextKeyTyped();
	    		typed += Character.toString(next);
	    		drawFrame(typed);
			    n -= 1;
		    }
	    }
	    StdDraw.pause(1000);
        return typed;
    }

    public void startGame() {
        // Set any relevant variables before the game starts
	    round = 1;
	    gameOver = false;

        // Establish Game loop
	    while (!gameOver) {
		    playerTurn = false;
	    	String given = generateRandomString(round);
		    flashSequence(given);

		    playerTurn = true;
		    drawFrame("");
		    String input = solicitNCharsInput(round);
		    if (input.equals(given)) {
		    	round += 1;
		    } else {
		    	gameOver = true;
		    	drawFrame("Game Over");
		    }
	    }
    }

}
