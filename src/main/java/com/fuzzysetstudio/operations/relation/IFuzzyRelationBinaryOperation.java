package com.fuzzysetstudio.operations.relation;

import com.fuzzysetstudio.domain.FuzzyRelation;
import com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzyRelationBinaryOperation {
    FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setA);
}
