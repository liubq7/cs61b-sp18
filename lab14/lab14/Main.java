package lab14;

import lab14lib.*;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
		Generator generator = new SineWaveGenerator(100);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
//		GeneratorAudioAnimator gaa = new GeneratorAudioAnimator(generator);
//		gaa.drawAndPlay(4096, 1000000);
	}
} 