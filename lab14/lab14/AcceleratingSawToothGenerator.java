package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        state = (state + 1);
        if (state == period) {
            period = (int) (period * factor);
            state = 0;
        }

        return normalize(state % period);
    }

    private double normalize(int x) {
        return (double) x * 2 / period - 1;
    }

    public static void main(String[] args) {
        Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
