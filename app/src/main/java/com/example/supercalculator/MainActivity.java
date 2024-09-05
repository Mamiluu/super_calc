package com.example.supercalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private String currentInput = "";
    private String previousInput = "";
    private String currentOperator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.resultTextView);

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonEquals, R.id.buttonClear, R.id.buttonBackspace, R.id.buttonDot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> handleButtonClick((Button) v));
        }
    }

    private void handleButtonClick(Button button) {
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                clearDisplay();
                break;
            case "←":
                removeLastDigit();
                break;
            case "=":
                performCalculation();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                setOperator(buttonText);
                break;
            case ".":
                addDecimal();
                break;
            default:
                appendDigit(buttonText);
                break;
        }
    }

    private void clearDisplay() {
        currentInput = "";
        previousInput = "";
        currentOperator = null;
        display.setText("0");
    }

    private void removeLastDigit() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            display.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
    }

    private void performCalculation() {
        if (currentOperator != null && !currentInput.isEmpty()) {
            double result = 0;
            double prev = Double.parseDouble(previousInput);
            double current = Double.parseDouble(currentInput);

            switch (currentOperator) {
                case "+":
                    result = prev + current;
                    break;
                case "-":
                    result = prev - current;
                    break;
                case "*":
                    result = prev * current;
                    break;
                case "/":
                    result = prev / current;
                    break;
            }

            display.setText(String.valueOf(result));
            previousInput = String.valueOf(result);
            currentInput = "";
            currentOperator = null;
        }
    }

    private void setOperator(String operator) {
        if (!currentInput.isEmpty()) {
            if (currentOperator != null) {
                performCalculation();
            }
            currentOperator = operator;
            previousInput = currentInput;
            currentInput = "";
        }
    }

    private void addDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            display.setText(currentInput);
        }
    }

    private void appendDigit(String digit) {
        currentInput += digit;
        display.setText(currentInput);
    }
}
