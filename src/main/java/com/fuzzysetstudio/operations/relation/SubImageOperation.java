package com.fuzzysetstudio.operations.relation;

import com.fuzzysetstudio.domain.FuzzyRelation;
import com.fuzzysetstudio.domain.FuzzySet;
import com.fuzzysetstudio.domain.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SubImageOperation implements IFuzzyRelationOperation, IFuzzyRelationBinaryOperation{
    @Override
    public FuzzyRelationOperationType getType() {return FuzzyRelationOperationType.SUB_PREIMAGE;}

    @Override
    public FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setB){
        ArrayList<Point> points = new ArrayList<>();
        double[] Y = fuzzyRelation.getY();
        ArrayList<Point> fuzzySetPoints = setB.getFuzzySetPoints();
        double[][] relation = fuzzyRelation.getR();

        for (int i = 0; i < Y.length; ++i){
            double minVal = 1.0;
            for (int j = 0; j < fuzzySetPoints.size(); ++j){
                double imp = Math.min(1.0, 1.0 - relation[j][i] + fuzzySetPoints.get(j).getY());
                minVal = Math.min(minVal, imp);
            }
            points.add(new Point(Y[i], minVal));
        }

        return new FuzzySet(points);
    }
}
