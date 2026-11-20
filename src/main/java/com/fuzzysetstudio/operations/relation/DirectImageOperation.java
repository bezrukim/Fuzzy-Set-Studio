package main.java.com.fuzzysetstudio.operations.relation;

import main.java.com.fuzzysetstudio.domain.FuzzyRelation;
import main.java.com.fuzzysetstudio.domain.FuzzySet;
import main.java.com.fuzzysetstudio.domain.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DirectImageOperation implements IFuzzyRelationOperation, IFuzzyRelationBinaryOperation{
    @Override
    public FuzzyRelationOperationType getType() {return FuzzyRelationOperationType.DIRECT_IMAGE;}

    @Override
    public FuzzySet apply(FuzzyRelation fuzzyRelation, FuzzySet setA){
        ArrayList<Point> points = new ArrayList<>();
        double[] Y = fuzzyRelation.getY();
        ArrayList<Point> fuzzySetPoints = setA.getFuzzySetPoints();
        double[][] relation = fuzzyRelation.getR();

        for (int i = 0; i < Y.length; ++i){
            double maxVal = 0.0;
            for (int j = 0; j < fuzzySetPoints.size(); ++j){
                maxVal = Math.max(maxVal, Math.min(relation[j][i], fuzzySetPoints.get(j).getY()));
            }
            points.add(new Point(Y[i], maxVal));
        }

        return new FuzzySet(points);
    }

    public static FuzzyRelation ImageFuzzyRelation(FuzzyRelation fuzzyRelationA, FuzzyRelation fuzzyRelationB){
        double[][] R1 = fuzzyRelationA.getR();
        double[][] R2 = fuzzyRelationB.getR();
        double[] X = fuzzyRelationA.getX();
        double[] Y = fuzzyRelationB.getY();

        double[][] resR = new double[R1.length][R2.length];

        for (int i = 0; i < R1.length; ++i){
            double maxVal = 0.0;
            for (int j = 0; j < R2[0].length; ++j) {
                for (int s = 0; s < R2.length; ++s) {
                    maxVal = Math.max(maxVal, Math.min(R1[i][s], R2[s][j]));
                }
                resR[i][j] = maxVal;
            }
        }

        return new FuzzyRelation(resR, X, Y);
    }
}
