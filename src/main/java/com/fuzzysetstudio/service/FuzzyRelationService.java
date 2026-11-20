package main.java.com.fuzzysetstudio.service;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.operations.relation.ComposeOperation;
import main.java.com.fuzzysetstudio.operations.relation.FuzzyRelationOperationType;
import main.java.com.fuzzysetstudio.operations.relation.IFuzzyRelationBinaryOperation;
import main.java.com.fuzzysetstudio.operations.relation.IFuzzyRelationOperation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FuzzyRelationService {
    private final Map<FuzzyRelationOperationType, IFuzzyRelationOperation> operations;

    public FuzzyRelationService(List<IFuzzyRelationOperation> operations) {
        this.operations = operations.stream().collect(Collectors.toMap(
                IFuzzyRelationOperation::getType,
                Function.identity()
        ));
    }

    public FuzzySet calculateImage(
            FuzzyRelationOperationType type,
            FuzzyRelation relation,
            FuzzySet set
    ){
        IFuzzyRelationOperation operation = operations.get(type);
        System.out.println("Requested type: " + type);
        System.out.println("Found operation: " + operations.get(type));
        System.out.println("Available operations: " + operations.keySet());
        if (operation instanceof IFuzzyRelationBinaryOperation){
            return ((IFuzzyRelationBinaryOperation) operation).apply(relation, set);
            }
        throw new IllegalArgumentException("Unsupported operation");
    }

    public FuzzyRelation calculateCompose(
            List<FuzzyRelation> relations
    ){
        return ComposeOperation.apply(relations);
    }
}

