package com.example.quadratic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewSolutions;
    private EditText aVariable;
    private EditText bVariable;
    private EditText cVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSolutions = (TextView) findViewById(R.id.textViewSolutions);
        aVariable = (EditText) findViewById(R.id.aVariable);
        bVariable = (EditText) findViewById(R.id.bVariable);
        cVariable = (EditText) findViewById(R.id.cVariable);

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

        btnCalculate.setOnClickListener(calculateButtonListener);
    }

    private void solveQuadratic(double a, double b, double c) {
        double discriminantValue = calculateDiscriminantValue(a, b, c);
        int numberOfRealSolutions = numberOfRealSolutions(discriminantValue);

        switch (numberOfRealSolutions) {
            case 0:
                textViewSolutions.setText("No real solution!");
                break;
            case 1:
                double x = (-b + Math.sqrt(discriminantValue)) / (2 * a);

                textViewSolutions.setText("x = " + x);
                break;
            case 2:
                double x1 = (-b - Math.sqrt(discriminantValue)) / (2 * a);
                double x2 = (-b + Math.sqrt(discriminantValue)) / (2 * a);

                textViewSolutions.setText("x1 = " + x1 + " x2 = " + x2);
                break;
        }
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
        double discriminantValue = (b * b) - (4 * a * c);
        if (discriminantValue > 0) {
            return 2;
        } else if (discriminantValue < 0) {
            return 0;
        } else {
            return 1;
        }
    }
}