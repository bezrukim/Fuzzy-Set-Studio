package com.fuzzysetstudio.operations.set;

import org.springframework.stereotype.Component;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.domain.Point;

import java.util.ArrayList;

@Component
public class AlgebraicSubstraction implements IFuzzySetOperation, IFuzzySetBinaryOperation {
    @Override
    public FuzzySetOperationType getType(){
        return FuzzySetOperationType.ALGEBRAIC_SUBSTRACTION;
    }

    @Override
    public FuzzySet apply (FuzzySet first, FuzzySet second, double step){
        ArrayList<Point> points = new ArrayList<>();
        double start = Math.min(first.getFuzzySetPoints().getFirst().getX(), second.getFuzzySetPoints().getFirst().getX());
        double end = Math.max(first.getFuzzySetPoints().getLast().getX(), second.getFuzzySetPoints().getLast().getX());

        for (double x = start; x <= end + step; x += step){
            double y;
            y = Math.max(0, first.membershipFunction(x) - second.membershipFunction(x));
            points.add(new Point(x, y));
        }

        return new FuzzySet(points);
    }
}
