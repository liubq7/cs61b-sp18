package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Liu Beiqian
 */

public class TestArrayRingBuffer {

    @Test
    public void isEmptyTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        assertFalse(arb.isEmpty());
    }

    @Test
    public void isFullTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        assertFalse(arb.isFull());
        for (int i = 0; i < arb.capacity; i++) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
    }

    @Test
    public void queueTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        for (int i = 0; i < arb.capacity; i++) {
            arb.enqueue(i);
        }

        Integer expected1 = 0;
        assertEquals(expected1, arb.dequeue());

        arb.enqueue(10);

        Integer expected2 = 1;
        assertEquals(expected2, arb.peek());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
