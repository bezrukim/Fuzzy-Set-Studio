package main.java.com.fuzzysetstudio.operations.relation;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;

public interface IFuzzyRelationBinaryOperation {
    FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setA);
}
