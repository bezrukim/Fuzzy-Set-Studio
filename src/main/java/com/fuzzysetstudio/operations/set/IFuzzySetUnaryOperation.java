package main.java.com.fuzzysetstudio.operations.set;

import main.java.com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzySetUnaryOperation {
    FuzzySet apply (FuzzySet fuzzySet, double step);
}
