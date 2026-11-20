package com.fuzzysetstudio.operations.set;

import com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzySetUnaryOperation {
    FuzzySet apply (FuzzySet fuzzySet, double step);
}
