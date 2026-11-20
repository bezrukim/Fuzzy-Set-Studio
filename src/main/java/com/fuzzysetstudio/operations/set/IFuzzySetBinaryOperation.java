package main.java.com.fuzzysetstudio.operations.set;

import main.java.com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzySetBinaryOperation {
    FuzzySet apply(FuzzySet first, FuzzySet second, double step);
}
