import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    @Test
    public void testFlik() {
        int a = 1;
        int b = 1;
        assertTrue(Flik.isSameNumber(a, b));

        a = 127;
        b = 127;
        assertTrue(Flik.isSameNumber(a, b));

        a = 128;
        b = 128;
        assertTrue(Flik.isSameNumber(a, b));

        a = 500;
        b = 500;
        assertTrue(Flik.isSameNumber(a, b));

        a = 128;
        b = 500;
        assertFalse(Flik.isSameNumber(a, b));
    }
}

/** 测试得超过127方法失效，
 *  原因是Integer是对象类型，"=="符号比较的是两者的地址
 *  在给Integer赋值时，实际上是自动装箱的过程，也就是调用了Integer.valueOf(int)方法
 *  当这个值大于等于-128小于等于127时使用了常量池，所以两个地址是相等的，而超出此范围则不使用常量池，故不相等
 */
