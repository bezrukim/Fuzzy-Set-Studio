package com.fuzzysetstudio.operations.set;

import com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzySetBinaryOperation {
    FuzzySet apply(FuzzySet first, FuzzySet second, double step);
}
