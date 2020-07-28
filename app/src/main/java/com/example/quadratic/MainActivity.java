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

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-20);
        graph.getViewport().setMaxX(20);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-20);
        graph.getViewport().setMaxY(20);


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
                        + currentEquation.getXAxisIntersection(0) + ",0)");
                break;
            case 2:
                textViewXAxisIntersections.setText("Two X axis intersections at: ("
                        + currentEquation.getXAxisIntersection(0) + ",0) & ("
                        + currentEquation.getXAxisIntersection(1) + ",0)");
                break;
        }
        textViewMinimumMaximumPoints.setText("Min/Max Point ("
                + currentEquation.getVertexX() + ","
                + currentEquation.getVertexY() + ")");
        graph.addSeries(currentEquation.getSeries());

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