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

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextView textViewXAxisIntersections;
    private TextView textViewMinimumMaximumPoints;
    private EditText aVariable;
    private EditText bVariable;
    private EditText cVariable;
    private GraphView graph;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stack<QuadraticEquation> undoStack = new Stack<>();
        Stack<QuadraticEquation> redoStack = new Stack<>();

        textViewXAxisIntersections = (TextView) findViewById(R.id.textViewXAxisIntersections);
        textViewMinimumMaximumPoints = (TextView) findViewById(R.id.textViewMinimumMaximumPoints);
        aVariable = (EditText) findViewById(R.id.aVariable);
        bVariable = (EditText) findViewById(R.id.bVariable);
        cVariable = (EditText) findViewById(R.id.cVariable);

        graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);


        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);

        View.OnClickListener calculateButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double a = Double.parseDouble(aVariable.getText().toString());
                double b = Double.parseDouble(bVariable.getText().toString());
                double c = Double.parseDouble(cVariable.getText().toString());

                QuadraticEquation newQuadratic = new QuadraticEquation(a, b, c);
                displayQuadratic(newQuadratic);

                undoStack.push(newQuadratic);
                undoStack.peek();
                //TODO take from stack.
            }
        };

        Button btnZoomIn = (Button) findViewById(R.id.btnZoomIn);
        View.OnClickListener zoomInListener = (view) -> zoomIn();
        btnZoomIn.setOnClickListener(zoomInListener);

        Button btnZoomOut = (Button) findViewById(R.id.btnZoomOut);
        View.OnClickListener zoomOutListener = (view) -> zoomOut();
        btnZoomOut.setOnClickListener(zoomOutListener);

        Button btnUndo = (Button) findViewById(R.id.btnUndo);
        View.OnClickListener undoListener = (view) -> undo();

        Button btnRedo = (Button) findViewById(R.id.btnRedo);
        View.OnClickListener redoListener = (view) -> redo();

        btnCalculate.setOnClickListener(calculateButtonListener);
    }

    private void displayQuadratic(QuadraticEquation newQuadratic) {

    }

    private double roundNumber(double n, int decimalPlaces) {
        if (decimalPlaces != 0) { //TODO Handle negative dp.
            return Math.round(n * (10 * decimalPlaces)) / (10.0 * decimalPlaces);
        }
        return Math.round(n);
    }

    private void drawQuadraticGraph(double a, double b, double c) {
        DataPoint[] points = new DataPoint[5001];

        for (int i = 0; i < points.length; i++) {
            double x = (i - 2500) / 10.0;
            points[i] = new DataPoint(x, (a * x * x) + (b * x) + (c));
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

    private void zoomOut() {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graph.getViewport().getMinX(false) - 10);
        graph.getViewport().setMaxX(graph.getViewport().getMaxX(false) + 10);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(graph.getViewport().getMinY(false) - 10);
        graph.getViewport().setMaxY(graph.getViewport().getMaxY(false) + 10);
    }

    private void zoomIn() {
        //TODO Add code to prevent over-zooming.

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graph.getViewport().getMinX(false) + 10);
        graph.getViewport().setMaxX(graph.getViewport().getMaxX(false) - 10);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(graph.getViewport().getMinY(false) + 10);
        graph.getViewport().setMaxY(graph.getViewport().getMaxY(false) - 10);
    }

    private void undo() {
        //TODO
    }


    private void redo() {
        //TODO
    }
}