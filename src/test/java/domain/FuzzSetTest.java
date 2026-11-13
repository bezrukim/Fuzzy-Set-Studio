package domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuzzSetTest {

    @Test
    void getFuzzSetPoints() {
        FuzzSet testFuzzSet = new FuzzSet(new Point(0, 1),
                new Point(-1, 0),
                new Point(2, 0),
                new Point(1, 1)
        );

        List<Point> exceptedFuzzSet = List.of(
                new Point(-1, 0),
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 0)
        );

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzSetPoints());
    }

    @Test
    void setFuzzSetPoints() {
        FuzzSet testFuzzSet = new FuzzSet(new Point(0, 0), new Point(1, 1));
        testFuzzSet.setFuzzSetPoints(
                new Point(0, 1),
                new Point(-1, 0),
                new Point(2, 0),
                new Point(1, 1)
        );

        List<Point> exceptedFuzzSet = List.of(
                new Point(-1, 0),
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 0)
        );

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzSetPoints());
    }

    @Test
    void putPoint() {
        FuzzSet testFuzzSet = new FuzzSet(
                new Point(0, 1),
                new Point(2, 0),
                new Point(1, 1)
        );

        testFuzzSet.putPoint(new Point(-1, 0));

        List<Point> exceptedFuzzSet = List.of(
                new Point(-1, 0),
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 0)
        );

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzSetPoints());
    }

    @Test
    void putPoints() {
        FuzzSet testFuzzSet = new FuzzSet(new Point(-1, 0));

        testFuzzSet.putPoints(new Point(0, 1),
                new Point(2, 0),
                new Point(1, 1)
        );

        List<Point> exceptedFuzzSet = List.of(
                new Point(-1, 0),
                new Point(0, 1),
                new Point(1, 1),
                new Point(2, 0)
        );

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzSetPoints());
    }

    @Test
    void testEquals() {
        FuzzSet testFuzzSet1 = new FuzzSet(new Point(-1, 0), new Point(-1, 1), new Point(1, 1));
        FuzzSet testFuzzSet2 = new FuzzSet(new Point(-1, 1), new Point(-1, 0), new Point(1, 1));
        FuzzSet testFuzzSet3 = new FuzzSet(new Point(-1, 0), new Point(0, 0), new Point(1, 2));
        FuzzSet testFuzzSet4 = new FuzzSet(new Point(-1, 0), new Point(0, 0));

        assertEquals(testFuzzSet1, testFuzzSet2);
        assertNotEquals(testFuzzSet2, testFuzzSet3);
        assertNotEquals(testFuzzSet3, testFuzzSet4);
    }

    @Test
    void membershipFunction() {
        FuzzSet testFuzzSet = new FuzzSet(
                new Point(0, 1),
                new Point(-1, 0),
                new Point(2, 0),
                new Point(1, 1)
        );

        Point actual = testFuzzSet.membershipFunction(0);
        assertEquals(new Point(0, 1), actual);

    }
}