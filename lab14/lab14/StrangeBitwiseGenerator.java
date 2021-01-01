package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
//        int weirdState = state & (state >>> 3) % period;
        int weirdState = state & (state >> 3) & (state >> 8) % period;
        return normalize(weirdState);
    }

    private double normalize(int x) {
        return (double) x * 2 / period - 1;
    }

    public static void main(String[] args) {
        Generator generator = new StrangeBitwiseGenerator(512);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
