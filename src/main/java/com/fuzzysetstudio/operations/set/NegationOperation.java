package com.fuzzysetstudio.operations.set;

import org.springframework.stereotype.Component;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.domain.Point;

import java.util.ArrayList;

@Component
public class NegationOperation implements IFuzzySetOperation, IFuzzySetUnaryOperation {
    @Override
    public FuzzySetOperationType getType(){
        return FuzzySetOperationType.NEGATION;
    }
    @Override
    public FuzzySet apply (FuzzySet fuzzySet, double step){
        ArrayList<Point> points = new ArrayList<>();
        double start = fuzzySet.getFuzzySetPoints().getFirst().getX();
        double end = fuzzySet.getFuzzySetPoints().getLast().getX();

        for (double x = start; x <= end + step; x += step){
            double y;
            y = 1 - fuzzySet.membershipFunction(x);
            points.add(new Point(x, y));
        }

        return new FuzzySet(points);
    }
}
