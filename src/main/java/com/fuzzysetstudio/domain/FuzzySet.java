package com.fuzzysetstudio.domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;

public class FuzzySet {
    ArrayList<Point> fuzzySetPoints;

    //Initialize class
    public FuzzySet(){
        this.fuzzySetPoints = new ArrayList<>();
    }

    public FuzzySet(ArrayList<Point> fuzzSetPoints){
        this.fuzzySetPoints = fuzzSetPoints;
        this.fuzzySetPoints.sort(null); //initialize and sort in ascending order
    }

    public FuzzySet(Point @NotNull ... points){
        this.fuzzySetPoints = new ArrayList<>(points.length);
        for (Point point : points){
            if (!isNull(point)){
                this.fuzzySetPoints.add(point);
            }
        }
        this.fuzzySetPoints.sort(null); //initialize and sort in ascending order
    }

    //getter and setter
    public ArrayList<Point> getFuzzySetPoints() {
        if (this.fuzzySetPoints == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(this.fuzzySetPoints);
    }

    public void setFuzzySetPoints(ArrayList<Point> fuzzySetPoints) {
        if (fuzzySetPoints == null){
            this.fuzzySetPoints = new ArrayList<>();
            return;
        }
        this.fuzzySetPoints = new ArrayList<>(fuzzySetPoints);
        this.fuzzySetPoints.sort(null); //invoke a sort in ascending order
    }

    public void setFuzzSetPoints(Point @NotNull ... fuzzSetPoints) {
        this.fuzzySetPoints = new ArrayList<>(fuzzSetPoints.length);
        for (Point point : fuzzSetPoints){
            if (!isNull(point)){
                this.fuzzySetPoints.add(point);
            }
            else{
                throw new IllegalArgumentException("The point cannot be null");
            }
        }
        this.fuzzySetPoints.sort(null); //invoke a sort in ascending order
    }

    public void putPoint(Point point){
        if (!isNull(point)){
            this.fuzzySetPoints.add(point);
        }
        else{
            throw new IllegalArgumentException("The point cannot be null");
        }
        this.fuzzySetPoints.sort(null); //invoke a sort in ascending order
    }

    public void putPoints(Point @NotNull ... points){
        for (Point point : points){
            if (!isNull(point)){
                this.fuzzySetPoints.add(point);
            }
        }
        this.fuzzySetPoints.sort(null); //invoke a sort in ascending order
    }

    //standard override
    @Override
    public int hashCode(){
        return hash(this.fuzzySetPoints);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FuzzySet that = (FuzzySet) o;

        if (this.fuzzySetPoints.size() != that.fuzzySetPoints.size()){
            return false;
        };

        for (int i = 0; i < this.fuzzySetPoints.size(); ++i){
            if (!this.fuzzySetPoints.get(i).equals(that.fuzzySetPoints.get(i))){
                return false;
            }
        }

        return true;
    }

    //Functions
    public double membershipFunction(double x){
        if (this.fuzzySetPoints.isEmpty()){
            throw new IllegalStateException("The trapezoidal fuzzy set is defined incorrectly.");
        }

        //Left border
        if (x <= fuzzySetPoints.getFirst().getX()){
            return fuzzySetPoints.getFirst().getY();
        }

        //Right border
        if (x >= fuzzySetPoints.getLast().getX()){
            return fuzzySetPoints.getLast().getY();
        }

        int i;
        for (i = 0; i < fuzzySetPoints.size(); ++i){
            if (x <= fuzzySetPoints.get(i).getX()) break;
        }
        Point p1 = fuzzySetPoints.get(i-1);
        Point p2 = fuzzySetPoints.get(i);

        //Divide by 0 protection
        double dx = p2.getX() - p1.getX();
        if (dx == 0) {
            return Math.max(p1.getY(), p2.getY());
        }

        double y;
        y = p1.getY() + ((p2.getY() - p1.getY())/(p2.getX() - p1.getX())*(x - p1.getX()));

        return y;
    }
}
