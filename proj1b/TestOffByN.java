import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator offBy5 = new OffByN(5);
    CharacterComparator offBy7 = new OffByN(7);

    @Test
    public void testEqualChars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('h', 'f'));

        assertTrue(offBy7.equalChars('a', 'h'));
        assertTrue(offBy7.equalChars('h', 'a'));
        assertFalse(offBy7.equalChars('h', 'f'));
    }
}
