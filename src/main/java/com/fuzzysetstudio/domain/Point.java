package com.fuzzysetstudio.domain;

import org.jetbrains.annotations.NotNull;

import static java.util.Objects.hash;

public class Point implements Comparable<Point>{
    private double x;
    private double y;

    //initialize
    public Point(double x, double y){
        if (!Double.isFinite(x) || !    Double.isFinite(y)){
            throw new IllegalArgumentException("Некорректные координаты точки");
        }

        this.x = x;
        this.y = y;
    }

    //getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        if (Double.isFinite(x)){
            throw new IllegalArgumentException("Некорректные координаты точки");
        }
        this.x = x;
    }

    public void setY(double y) {
        if (Double.isFinite(y)){
            throw new IllegalArgumentException("Некорректные координаты точки");
        }
        this.y = y;
    }

    //override
    @Override
    public int hashCode(){
        return hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Point other = (Point) o;

        return this.x == other.x && this.y == other.y;
    }

    //Compare?
    @Override
    public int compareTo (@NotNull Point other){
        int xCompare = Double.compare(this.x, other.x);
        if (xCompare != 0){
            return xCompare;
        }

        return Double.compare(this.y, other.y);
    }
}
