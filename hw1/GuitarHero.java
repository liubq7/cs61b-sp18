import synthesizer.GuitarString;

public class GuitarHero {
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString concert[] = new GuitarString[37];
        for (int i = 0; i < concert.length; i++) {
            concert[i] = new GuitarString(440.0 * Math.pow(2, (i - 24.0) / 12.0));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != 1) {
                    concert[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < concert.length; i++) {
                sample += concert[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < concert.length; i++) {
                concert[i].tic();
            }
        }
    }
}
