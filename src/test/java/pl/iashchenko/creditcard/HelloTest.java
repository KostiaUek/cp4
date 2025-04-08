package pl.iashchenko.creditcard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HelloTest {
    @Test
    void itWorks() {
        int a = 2;
        int b = 3;

        int result = a + b;

        assert result == 5;
    }


    @Test
    void myFailingTest() {
        int a = 2;
        int b = 3;

        int result = a + b;

        assert result == 3;
    }

    @Test
    void assertTest() {
        assertTrue(true);
    }
}
