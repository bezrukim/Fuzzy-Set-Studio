package com.fuzzysetstudio.operations.relation;

import com.fuzzysetstudio.domain.FuzzyRelation;

import java.util.List;


public interface IFuzzyRelationChainOperation {
    FuzzyRelation apply(List<FuzzyRelation> fuzzyRelations);
}
