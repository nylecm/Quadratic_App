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

import java.util.NoSuchElementException;

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

    Stack<QuadraticEquation> undoStack = new Stack<>();
    Stack<QuadraticEquation> redoStack = new Stack<>();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewXAxisIntersections =
                (TextView) findViewById(R.id.textViewXAxisIntersections);
        textViewMinimumMaximumPoints =
                (TextView) findViewById(R.id.textViewMinimumMaximumPoints);
        aVariable = (EditText) findViewById(R.id.aVariable);
        bVariable = (EditText) findViewById(R.id.bVariable);
        cVariable = (EditText) findViewById(R.id.cVariable);

        graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setScrollable(true); // Allows graph scrolling.
        graph.getViewport().setScrollableY(true);

        graph.getViewport().setXAxisBoundsManual(true); //Sets graph to show default range.
        graph.getViewport().setMinX(-zoomLevel);
        graph.getViewport().setMaxX(zoomLevel);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-zoomLevel);
        graph.getViewport().setMaxY(zoomLevel);


        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);

        View.OnClickListener calculateButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pushes current equation to undo stack if one was being
                // displayed prior.
                if (currentEquation != null) {
                    undoStack.push(currentEquation);
                }

                //Creation and initiation of displaying new equation:
                double a = Double.parseDouble(aVariable.getText().toString());
                double b = Double.parseDouble(bVariable.getText().toString());
                double c = Double.parseDouble(cVariable.getText().toString());

                currentEquation = new QuadraticEquation(a, b, c);
                displayQuadratic(currentEquation);
            }
        };
        btnCalculate.setOnClickListener(calculateButtonListener);

        Button btnZoomIn = (Button) findViewById(R.id.btnZoomIn);
        View.OnClickListener zoomInListener = (view) -> zoomIn();
        btnZoomIn.setOnClickListener(zoomInListener);

        Button btnZoomOut = (Button) findViewById(R.id.btnZoomOut);
        View.OnClickListener zoomOutListener = (view) -> zoomOut();
        btnZoomOut.setOnClickListener(zoomOutListener);

        Button btnUndo = (Button) findViewById(R.id.btnUndo);
        View.OnClickListener undoListener = (view) -> undo();
        btnUndo.setOnClickListener(undoListener);

        Button btnRedo = (Button) findViewById(R.id.btnRedo);
        View.OnClickListener redoListener = (view) -> redo();
        btnRedo.setOnClickListener(redoListener);

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
     * @param zoomFactor when negative it zooms out.
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

    private void undo() { //FIXME fix unlimited undo redo problem.
        QuadraticEquation next;

        try {
            next = undoStack.peek();
        } catch (NoSuchElementException ex) {
            return;
        }

        if (currentEquation != null && !next.equals(currentEquation)) { // There is an equation being displayed.
            redoStack.push(currentEquation);
            currentEquation = next;
            currentEquation = next;
            displayEquationAndSetEditTexts(currentEquation);
            undoStack.pop();
        }
    }

    private void redo() {
        QuadraticEquation next;

        try {
            next = redoStack.peek();
        } catch (NoSuchElementException ex) {
            return;
        }

        if (currentEquation != null) {
            undoStack.push(currentEquation);
            currentEquation = next;
            displayEquationAndSetEditTexts(currentEquation);
            redoStack.pop();
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayEquationAndSetEditTexts(QuadraticEquation equation) {
        aVariable.setText(Double.toString(equation.getA()));
        bVariable.setText(Double.toString(equation.getB()));
        cVariable.setText(Double.toString(equation.getC()));

        displayQuadratic(equation);
    }
}