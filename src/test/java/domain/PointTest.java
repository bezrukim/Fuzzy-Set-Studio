package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void testEquals() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(0, 1);
        Point p5 = new Point(1, 1);

        assertEquals(p1, p2);
        assertNotEquals(p2, p3);
        assertEquals(p3, p4);
        assertNotEquals(p4, p5);
    }

    @Test
    void compareTo() {
        Point p1 = new Point(0, 1);
        Point p2 = new Point(0, 0);

        assertEquals(1, p1.compareTo(p2));
    }
}