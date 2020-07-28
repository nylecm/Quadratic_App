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
import com.jjoe64.graphview.series.Series;

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
    private QuadraticEquation currentEquation;
    private int zoomLevel = 22;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stack<QuadraticEquation> undoStack = new Stack<>();
        Stack<QuadraticEquation> redoStack = new Stack<>();

        textViewXAxisIntersections =
                (TextView) findViewById(R.id.textViewXAxisIntersections);
        textViewMinimumMaximumPoints =
                (TextView) findViewById(R.id.textViewMinimumMaximumPoints);
        aVariable = (EditText) findViewById(R.id.aVariable);
        bVariable = (EditText) findViewById(R.id.bVariable);
        cVariable = (EditText) findViewById(R.id.cVariable);

        graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-zoomLevel);
        graph.getViewport().setMaxX(zoomLevel);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-zoomLevel);
        graph.getViewport().setMaxY(zoomLevel);


        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);

        View.OnClickListener calculateButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double a = Double.parseDouble(aVariable.getText().toString());
                double b = Double.parseDouble(bVariable.getText().toString());
                double c = Double.parseDouble(cVariable.getText().toString());

                currentEquation = new QuadraticEquation(a, b, c);
                displayQuadratic(currentEquation);
                //TODO implement stack.
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

    private double roundNumber(double n, int decimalPlaces) {
        if (decimalPlaces != 0) { //TODO Handle negative dp.
            return Math.round(n * (10 * decimalPlaces)) / (10.0 * decimalPlaces);
        }
        return Math.round(n);
    }

    @SuppressLint("SetTextI18n")
    private void displayQuadratic(QuadraticEquation newQuadratic) {
        switch (newQuadratic.getXAxisIntersections().length) {
            case 0:
                textViewXAxisIntersections.setText("No real solution!");
                break;
            case 1:
                textViewXAxisIntersections.setText("One X axis intersection at: ("
                        + roundNumber(currentEquation.getXAxisIntersection(0), 4) + ",0)");
                break;
            case 2:
                textViewXAxisIntersections.setText("Two X axis intersections at: ("
                        + roundNumber(currentEquation.getXAxisIntersection(0), 4) + ",0) & ("
                        + roundNumber(currentEquation.getXAxisIntersection(1), 4) + ",0)");
                break;
        }
        textViewMinimumMaximumPoints.setText("Min/Max Point ("
                + roundNumber(currentEquation.getVertexX(), 4) + ","
                + roundNumber(currentEquation.getVertexY(), 4) + ")");
        graph.addSeries(currentEquation.getSeries());

    }

    private void zoomOut() {
        final int zoomFactor = -10;
        zoom(zoomFactor);
    }

    private void zoomIn() {
        final int zoomFactor = 10;

        if (zoomLevel - zoomFactor > 0) {
            zoom(zoomFactor);
        }
    }

    /**
     *
     * @param zoomFactor when negative it zooms out. TODO
     */
    private void zoom(int zoomFactor) {
        zoomLevel -= zoomFactor;

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(graph.getViewport().getMinX(false) + zoomFactor);
        graph.getViewport().setMaxX(graph.getViewport().getMaxX(false) - zoomFactor);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(graph.getViewport().getMinY(false) + zoomFactor);
        graph.getViewport().setMaxY(graph.getViewport().getMaxY(false) - zoomFactor);
    }

    private void undo() {
        //TODO
    }


    private void redo() {
        //TODO
    }
}