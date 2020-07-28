package com.example.quadratic;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;

public class QuadraticEquation {

    private double a, b, c;
    private double[] xAxisIntersections; //Length 0, 1, or 2M
    private double vertexX;
    private double vertexY;
    private LineGraphSeries<DataPoint> series;


    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        processQuadratic();
    }

    private void processQuadratic() {
        double discriminantValue = calculateDiscriminantValue(a, b, c);
        int numberOfRealSolutions = numberOfRealSolutions(discriminantValue);
        xAxisIntersections = new double[numberOfRealSolutions];

        if (numberOfRealSolutions == 1 || numberOfRealSolutions == 2) {
            xAxisIntersections[0] = ((-b) - Math.sqrt(discriminantValue)) / (2.0 * a);
            if (numberOfRealSolutions == 2) {
                xAxisIntersections[1] = ((-b) + Math.sqrt(discriminantValue)) / (2.0 * a);
            }
        }

        vertexX = findMinMaxX(a, b);
        vertexY = findYPointOnQuadratic(vertexX, a, b, c);

        series = drawQuadraticGraph(a, b, c);
    }

    private LineGraphSeries<DataPoint>  drawQuadraticGraph(double a, double b, double c) {
        DataPoint[] points = new DataPoint[5001];

        for (int i = 0; i < points.length; i++) {
            double x = (i - 2500) / 10.0;
            points[i] = new DataPoint(x, (a * x * x) + (b * x) + (c));
        }

        return new LineGraphSeries<>(points);
    }

    private double calculateDiscriminantValue(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    private int numberOfRealSolutions(double discriminantValue) {
        if (discriminantValue > 0) {
            return 2;
        } else if (discriminantValue < 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private double findMinMaxX(double a, double b) {
        return -b / (2 * a);
    }

    private double findYPointOnQuadratic(double x, double a, double b, double c) {
        return (a * x * x) + (b * x) + c;
    }

    @Override
    public String toString() {
        return "QuadraticEquation{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", xAxisIntersection=" + Arrays.toString(xAxisIntersections) +
                ", vertexX=" + vertexX +
                ", vertexY=" + vertexY +
                ", series=" + series +
                '}';
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setABC(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        processQuadratic();
    }

    public double[] getXAxisIntersections() {
        return xAxisIntersections;
    }

    public double getXAxisIntersection(int i) {
        if (i < xAxisIntersections.length) {
            return xAxisIntersections[i];
        } else {
            throw new ArrayIndexOutOfBoundsException("No data at index:" + i + "!");
        }

    }

    public double getVertexX() {
        return vertexX;
    }

    public double getVertexY() {
        return vertexY;
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return series;
    }
}
