package main.java.com.fuzzysetstudio.operations.relation;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;

import java.util.List;


public interface IFuzzyRelationChainOperation {
    FuzzyRelation apply(List<FuzzyRelation> fuzzyRelations);
}
