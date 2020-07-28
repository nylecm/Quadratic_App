package com.example.quadratic;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;

public class QuadraticEquation {

    private double a, b, c;
    private double[] xAxisIntersection; //Length 0, 1, or 2M
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
        xAxisIntersection = new double[numberOfRealSolutions];

        if (numberOfRealSolutions == 1 || numberOfRealSolutions == 2) {
            xAxisIntersection[0] = ((-b) - Math.sqrt(discriminantValue)) / (2.0 * a);
            if (numberOfRealSolutions == 2) {
                xAxisIntersection[1] = ((-b) + Math.sqrt(discriminantValue)) / (2.0 * a);
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
                ", xAxisIntersection=" + Arrays.toString(xAxisIntersection) +
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

    public double[] getXAxisIntersection() {
        return xAxisIntersection;
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

/*
switch (numberOfRealSolutions) {
                case 0:
                    textViewXAxisIntersections.setText("No real solution!");
                    textViewMinimumMaximumPoints.setText("No minimum/maximum!");
                    drawQuadraticGraph(a, b, c);
                    break;


                case 1:
                    double x = roundNumber(, 4);

                    double minMaxX = findMinMaxX(a, b);
                    double minMaxY = findYPointOnQuadratic(minMaxX, a, b, c);
                    minMaxX = roundNumber(minMaxX, 4);
                    minMaxY = roundNumber(minMaxY, 4);

                    textViewXAxisIntersections.setText("X axis intersections: x1 = (" + x + ",0)");
                    textViewMinimumMaximumPoints.setText("Min/Max Point (" + minMaxX + "," + minMaxY + ")");

                    drawQuadraticGraph(a, b, c);

                    break;
                case 2:
                    double x1 = roundNumber(((-b) - Math.sqrt(discriminantValue)) / (2.0 * a), 4);
                    double x2 = roundNumber(((-b) + Math.sqrt(discriminantValue)) / (2.0 * a), 4);

                    double minMaxX2 = findMinMaxX(a, b);
                    double minMaxY2 = findYPointOnQuadratic(minMaxX2, a, b, c);
                    minMaxX2 = roundNumber(minMaxX2, 4);
                    minMaxY2 = roundNumber(minMaxY2, 4);

                    textViewXAxisIntersections.setText("X axis intersections: x1 = (" + x1 + ",0) x2 = (" + x2 + ",0)");
                    textViewMinimumMaximumPoints.setText("Min/Max Point (" + minMaxX2 + "," + minMaxY2 + ")");

                    drawQuadraticGraph(a, b, c);
                    break;
 */