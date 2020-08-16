import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private static String message = "";

    private void randomAdd(double random, Integer i, ArrayDequeSolution<Integer> ads, StudentArrayDeque<Integer> sad) {
        if (random < 0.5) {
            ads.addFirst(i);
            sad.addFirst(i);
            message += "\naddFirst(" + i + ")";
        } else {
            ads.addLast(i);
            sad.addLast(i);
            message += "\naddLast(" + i + ")";
        }
    }

    private void randomRemove(double random, Integer i, ArrayDequeSolution<Integer> ads, StudentArrayDeque<Integer> sad) {
        Integer expected;
        Integer actual;
        if (random < 0.5) {
            expected = ads.removeFirst();
            actual = sad.removeFirst();
            message += "\nremoveFirst()";
        } else {
            expected = ads.removeLast();
            actual = sad.removeLast();
            message += "\nremoveLast()";
        }
        assertEquals(message, expected,actual);
    }

    @Test
    public void testRandomized() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            if (sad.isEmpty()) {
                double random = StdRandom.uniform();
                randomAdd(random, i, ads, sad);
            } else {
                double random1 = StdRandom.uniform();
                double random2 = StdRandom.uniform();
                if (random1 < 0.5) {
                    randomAdd(random2, i, ads, sad);
                } else {
                    randomRemove(random2, i ,ads, sad);
                }
            }
        }
    }
}
