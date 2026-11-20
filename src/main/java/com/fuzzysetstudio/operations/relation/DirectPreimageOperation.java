package main.java.com.fuzzysetstudio.operations.relation;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DirectPreimageOperation implements IFuzzyRelationOperation, IFuzzyRelationBinaryOperation{
    @Override
    public FuzzyRelationOperationType getType() {return FuzzyRelationOperationType.DIRECT_PREIMAGE;}

    @Override
    public FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setB){
        ArrayList<Point> points = new ArrayList<>();
        double[] X = fuzzyRelation.getX();
        ArrayList<Point> fuzzySetPoints = setB.getFuzzySetPoints();
        double[][] relation = fuzzyRelation.getR();

        for (int i = 0; i < relation.length; ++i){
            double maxVal = 0.0;
            for (int j = 0; j < fuzzySetPoints.size(); ++j){
                maxVal = Math.max(maxVal, Math.min(relation[i][j], fuzzySetPoints.get(j).getY()));
            }
            points.add(new Point(X[i], maxVal));
        }

        return new FuzzySet(points);
    }
}
