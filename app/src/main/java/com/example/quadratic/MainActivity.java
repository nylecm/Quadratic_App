package com.example.quadratic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    private TextView textViewXAxisIntersections;
    private TextView textViewMinimumMaximumPoints;
    private EditText aVariable;
    private EditText bVariable;
    private EditText cVariable;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewXAxisIntersections = (TextView) findViewById(R.id.textViewXAxisIntersections);
        textViewMinimumMaximumPoints = (TextView) findViewById(R.id.textViewMinimumMaximumPoints);
        aVariable = (EditText) findViewById(R.id.aVariable);
        bVariable = (EditText) findViewById(R.id.bVariable);
        cVariable = (EditText) findViewById(R.id.cVariable);

        graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setScrollable(true);

        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);

        View.OnClickListener calculateButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double a = Double.parseDouble(aVariable.getText().toString());
                double b = Double.parseDouble(bVariable.getText().toString());
                double c = Double.parseDouble(cVariable.getText().toString());

                solveQuadratic(a, b, c);
            }
        };

        Button btnZoomIn = (Button) findViewById(R.id.btnZoomIn);

        View.OnClickListener zoomInListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomIn();
            }
        };

        btnZoomIn.setOnClickListener(zoomInListener);

        Button btnZoomOut = (Button) findViewById(R.id.btnZoomOut);

        View.OnClickListener zoomOutListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomOut();
            }
        };

        btnZoomOut.setOnClickListener(zoomOutListener);

        Button btnUndo = (Button) findViewById(R.id.btnUndo);

        Button btnRedo = (Button) findViewById(R.id.btnRedo);

        btnCalculate.setOnClickListener(calculateButtonListener);
    }

    @SuppressLint("SetTextI18n")
    private void solveQuadratic(double a, double b, double c) {
        double discriminantValue = calculateDiscriminantValue(a, b, c);
        int numberOfRealSolutions = numberOfRealSolutions(discriminantValue);

        switch (numberOfRealSolutions) {
            case 0:
                textViewXAxisIntersections.setText("No real solution!");
                textViewMinimumMaximumPoints.setText("No minimum/maximum!");
                drawQuadraticGraph(a, b, c);
                break;
            case 1:
                double x = roundNumber((-b) - Math.sqrt((b * b) - (4 * a * c)) / (2 * a), 4);

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
        }
    }

    private double roundNumber(double n, int decimalPlaces) {
        if (decimalPlaces != 0) { //TODO Handle negative dp.
            return Math.round(n*(10 * decimalPlaces)) / (10.0 * decimalPlaces);
        }
        return Math.round(n);
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

    private int numberOfRealSolutions(double a, double b, double c) {
        double discriminantValue = b * b - 4 * a * c;
        if (discriminantValue > 0) {
            return 2;
        } else if (discriminantValue < 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private void drawQuadraticGraph(double a, double b, double c) {
        DataPoint[] points = new DataPoint[5001];

        for (int i = 0; i < points.length; i++) {
            double x = (i - 2500) / 10.0;
            points[i] = new DataPoint(x, (a*x*x) + (b*x) + (c));
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-20);
        graph.getViewport().setMaxX(20);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-20);
        graph.getViewport().setMaxY(20);

        graph.addSeries(series);
    }

    private double findMinMaxX(double a, double b) {
        return -b / (2 * a);
    }

    private double findYPointOnQuadratic(double x, double a, double b, double c) {
        return (a * x * x) + (b * x) + c;
    }

    private void zoomOut() {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graph.getViewport().getMinX(false) - 10);
        graph.getViewport().setMaxX(graph.getViewport().getMaxX(false) + 10);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(graph.getViewport().getMinY(false) - 10);
        graph.getViewport().setMaxY(graph.getViewport().getMaxY(false) + 10);
    }

    private void zoomIn() {
        //Add code to prevent over-zooming.
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graph.getViewport().getMinX(false) + 10);
        graph.getViewport().setMaxX(graph.getViewport().getMaxX(false) - 10);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(graph.getViewport().getMinY(false) + 10);
        graph.getViewport().setMaxY(graph.getViewport().getMaxY(false) - 10);
    }
}