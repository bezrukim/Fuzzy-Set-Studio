package domain;

import org.junit.jupiter.api.Test;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.domain.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuzzSetTest {

    @Test
    void getFuzzSetPoints() {
        FuzzySet testFuzzSet = new FuzzySet(new Point(0, 1),
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

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzySetPoints());
    }

    @Test
    void setFuzzSetPoints() {
        FuzzySet testFuzzSet = new FuzzySet(new Point(0, 0), new Point(1, 1));
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

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzySetPoints());
    }

    @Test
    void putPoint() {
        FuzzySet testFuzzSet = new FuzzySet(
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

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzySetPoints());
    }

    @Test
    void putPoints() {
        FuzzySet testFuzzSet = new FuzzySet(new Point(-1, 0));

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

        assertEquals(exceptedFuzzSet, testFuzzSet.getFuzzySetPoints());
    }

    @Test
    void testEquals() {
        FuzzySet testFuzzSet1 = new FuzzySet(new Point(-1, 0), new Point(-1, 1), new Point(1, 1));
        FuzzySet testFuzzSet2 = new FuzzySet(new Point(-1, 1), new Point(-1, 0), new Point(1, 1));
        FuzzySet testFuzzSet3 = new FuzzySet(new Point(-1, 0), new Point(0, 0), new Point(1, 2));
        FuzzySet testFuzzSet4 = new FuzzySet(new Point(-1, 0), new Point(0, 0));

        assertEquals(testFuzzSet1, testFuzzSet2);
        assertNotEquals(testFuzzSet2, testFuzzSet3);
        assertNotEquals(testFuzzSet3, testFuzzSet4);
    }

    @Test
    void membershipFunction() {
        FuzzySet testFuzzSet = new FuzzySet(
                new Point(0, 1),
                new Point(-1, 0),
                new Point(2, 0),
                new Point(1, 1)
        );

        Double actual1 = testFuzzSet.membershipFunction(0);
        assertEquals(1, actual1);

        Double actual2 = testFuzzSet.membershipFunction(0.5);
        assertEquals(1, actual2);

        Double actual3 = testFuzzSet.membershipFunction(-0.5);
        assertEquals(0.5, actual3);

        Double actual4 = testFuzzSet.membershipFunction(-2);
        assertEquals(0, actual4);
    }
}