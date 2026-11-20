package main.java.com.fuzzysetstudio.operations.relation;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SuperPreimageOperation implements IFuzzyRelationOperation, IFuzzyRelationBinaryOperation{
    @Override
    public FuzzyRelationOperationType getType() {return FuzzyRelationOperationType.SUPER_PREIMAGE;}

    @Override
    public FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setB){
        ArrayList<Point> points = new ArrayList<>();
        double[] X = fuzzyRelation.getX();
        ArrayList<Point> fuzzySetPoints = setB.getFuzzySetPoints();
        double[][] relation = fuzzyRelation.getR();

        for (int i = 0; i < relation.length; ++i){
            double minVal = 1.0;
            for (int j = 0; j < fuzzySetPoints.size(); ++j){
                double imp = Math.min(1.0, 1.0 - fuzzySetPoints.get(j).getY() + relation[i][j]);
                minVal = Math.min(minVal, imp);
            }
            points.add(new Point(X[i], minVal));
        }

        return new FuzzySet(points);
    }
}
