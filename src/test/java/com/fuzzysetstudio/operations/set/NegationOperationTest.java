package com.fuzzysetstudio.operations.set;

import main.java.com.fuzzysetstudio.operations.set.IFuzzySetUnaryOperation;
import main.java.com.fuzzysetstudio.operations.set.NegationOperation;
import org.junit.jupiter.api.Test;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;

import static org.junit.jupiter.api.Assertions.*;

class NegationOperationTest {
    IFuzzySetUnaryOperation negationOperation = new NegationOperation();

    @Test
    public void negationOperationTest() {
        FuzzySet A = new FuzzySet(new Point(0, 0), new Point(0.5, 1.0), new Point(1.0, 1.0), new Point(1.5, 0.0));
        FuzzySet negA = negationOperation.apply(A, 0.1);
        assertNull(negA, "Размер: " + negA.getFuzzySetPoints().size());
    }
}