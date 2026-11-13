package domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;

public class FuzzSet implements IFuzzSet {
    List<Point> fuzzSetPoints;

    //Initialize class
    public FuzzSet(){
        this.fuzzSetPoints = new ArrayList<>();
    }

    public FuzzSet(ArrayList<Point> fuzzSetPoints){
        this.fuzzSetPoints = fuzzSetPoints;
        this.fuzzSetPoints.sort(null); //initialize and sort in ascending order
    }

    public FuzzSet(Point @NotNull ... points){
        this.fuzzSetPoints = new ArrayList<>(points.length);
        for (Point point : points){
            if (!isNull(point)){
                this.fuzzSetPoints.add(point);
            }
        }
        this.fuzzSetPoints.sort(null); //initialize and sort in ascending order
    }

    //getter and setter
    public List<Point> getFuzzSetPoints() {
        return fuzzSetPoints;
    }

    public void setFuzzSetPoints(ArrayList<Point> fuzzSetPoints) {
        this.fuzzSetPoints = fuzzSetPoints;
        this.fuzzSetPoints.sort(null); //invoke a sort in ascending order
    }

    public void setFuzzSetPoints(Point @NotNull ... fuzzSetPoints) {
        this.fuzzSetPoints = new ArrayList<>(fuzzSetPoints.length);
        for (Point point : fuzzSetPoints){
            if (!isNull(point)){
                this.fuzzSetPoints.add(point);
            }
        }
        this.fuzzSetPoints.sort(null); //invoke a sort in ascending order
    }

    public void putPoint(Point point){
        this.fuzzSetPoints.add(point);
        this.fuzzSetPoints.sort(null); //invoke a sort in ascending order
    }

    public void putPoints(Point @NotNull ... points){
        for (Point point : points){
            if (!isNull(point)){
                this.fuzzSetPoints.add(point);
            }
        }
        this.fuzzSetPoints.sort(null); //invoke a sort in ascending order
    }

    //standard override
    @Override
    public int hashCode(){
        return hash(this.fuzzSetPoints);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FuzzSet that = (FuzzSet) o;

        if (this.fuzzSetPoints.size() != that.fuzzSetPoints.size()){
            return false;
        };

        for (int  i = 0; i < this.fuzzSetPoints.size(); ++i){
            if (!this.fuzzSetPoints.get(i).equals(that.fuzzSetPoints.get(i))){
                return false;
            }
        }

        return true;
    }

    //Functions
    public Point membershipFunction(double x){
        if (this.fuzzSetPoints.isEmpty()){
            throw new IllegalStateException("The trapezoidal fuzzy set is defined incorrectly.");
        }

        //Left border
        if (x <= fuzzSetPoints.getFirst().getX()){
            return new Point(x, fuzzSetPoints.getFirst().getY());
        }

        //Right border
        if (x >= fuzzSetPoints.getLast().getX()){
            return new Point(x, fuzzSetPoints.getLast().getY());
        }

        int i;
        for (i = 0; i < fuzzSetPoints.size(); ++i){
            if (x <= fuzzSetPoints.get(i).getX()) break;
        }
        Point p1 = fuzzSetPoints.get(i-1);
        Point p2 = fuzzSetPoints.get(i);

        //Divide by 0 protection
        double dx = p2.getX() - p1.getX();
        if (dx == 0) {
            return new Point(x, Math.max(p1.getY(), p2.getY()));
        }

        double y;
        y = p1.getY() + ((p2.getY() - p1.getY())/(p2.getX() - p1.getX())*(x - p1.getX()));

        return new Point(x, y);
    }
}
