package com.fuzzysetstudio.domain;

import java.util.ArrayList;

public class FuzzyRelation {
    private final double[] X;
    private final double[] Y;
    private final double[][] R;

    public FuzzyRelation(FuzzySet setA, FuzzySet setB) {
        ArrayList<Point> setAPoints = setA.getFuzzySetPoints();
        this.X = setAPoints.stream().mapToDouble(Point::getX).toArray();
        ArrayList<Point> setBPoints = setB.getFuzzySetPoints();
        this.Y = setBPoints.stream().mapToDouble(Point::getX).toArray();
        double[][] fuzzyRelation = new double[setAPoints.size()][setBPoints.size()];

        for (int i = 0; i < setAPoints.size(); ++i){
            for (int j = 0; j < setBPoints.size(); ++j){
                fuzzyRelation[i][j] = Math.min(setA.membershipFunction(this.X[i]), setB.membershipFunction(Y[j]));
            }
        }

        this.R = fuzzyRelation;
    }

    public FuzzyRelation(double[][] R, double[] X, double[] Y){
        this.R = R;
        this.X = X;
        this.Y = Y;
    }

    public static double membershipFunction(FuzzySet setA, double x, FuzzySet setB, double y){
        return Math.min(setA.membershipFunction(x), setB.membershipFunction(y));
    }

    public double[][] getR() {
        return R;
    }

    public double[] getX() {
        return X;
    }

    public double[] getY() {
        return Y;
    }
}