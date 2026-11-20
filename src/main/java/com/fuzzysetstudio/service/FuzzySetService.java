package main.java.com.fuzzysetstudio.service;

import org.springframework.stereotype.Service;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.operations.set.IFuzzySetBinaryOperation;
import main.java.com.fuzzysetstudio.operations.set.IFuzzySetOperation;
import main.java.com.fuzzysetstudio.operations.set.FuzzySetOperationType;
import main.java.com.fuzzysetstudio.operations.set.IFuzzySetUnaryOperation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FuzzySetService {
    private final Map<FuzzySetOperationType, IFuzzySetOperation> operations;

    public FuzzySetService(List<IFuzzySetOperation> operationList){
        this.operations = operationList.stream()
                .collect(Collectors.toMap(
                        IFuzzySetOperation::getType,
                        Function.identity()
                ));
    }

    public FuzzySet calculate(
            FuzzySetOperationType operationType,
            FuzzySet a,
            FuzzySet b,
            double step
    ){
        checkStep(step);

        IFuzzySetOperation operation = operations.get(operationType);
        switch (operation) {
            case null -> {
                throw new IllegalArgumentException("Operation cannot be null");
            }
            case IFuzzySetBinaryOperation binaryOperation -> {
                return binaryOperation.apply(a, b, step);
            }
            case IFuzzySetUnaryOperation unaryOperation -> {
                return unaryOperation.apply(a, step);
            }
            default -> throw new IllegalArgumentException("Unsupported operation");
        }
    }
    private void checkStep(double step){
        if (!Double.isFinite(step) || step <= 0){
            throw new IllegalArgumentException("Invalid step: must be positive and finite.");
        }
    }
}
