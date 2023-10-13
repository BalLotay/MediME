package com.example.simplecalculatorlab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    private enum Operator {none, add, sub, mul, div, eq}

    private double data01=0, data02 = 0;

    private Operator opp = Operator.none;

    private boolean hasDot = false;

    private boolean requiresCleaning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView)findViewById(R.id.resultText);

    }

    // Sample implementation of the onClickNumericalButton (...).
    // Feel free to re-implement or modidy.
    public void onClickNumericalButton(View view) {


        //Getting ID of pressed Button
        int pressID = view.getId();

        //If we had an equal sign pressed last, standard operation is to clean
        if (opp == Operator.eq) {
            opp = Operator.none;
            resultText.setText("");
        }

        if (requiresCleaning) {
            requiresCleaning = false;
            hasDot = false;
            resultText.setText("");
        }

        //Figuring out which button was pressed and updating the represented text field object
        if (pressID == R.id.button0) {
            resultText.setText(resultText.getText() + "0");
        } else if (pressID == R.id.button1) {
            resultText.setText(resultText.getText() + "1");
        } else if (pressID == R.id.button2) {
            resultText.setText(resultText.getText() + "2");
        } else if (pressID == R.id.button3) {
            resultText.setText(resultText.getText() + "3");
        } else if (pressID == R.id.button4) {
            resultText.setText(resultText.getText() + "4");
        } else if (pressID == R.id.button5) {
            resultText.setText(resultText.getText() + "5");
        } else if (pressID == R.id.button6) {
            resultText.setText(resultText.getText() + "6");
        } else if (pressID == R.id.button7) {
            resultText.setText(resultText.getText() + "7");
        } else if (pressID == R.id.button8) {
            resultText.setText(resultText.getText() + "8");
        } else if (pressID == R.id.button9) {
            resultText.setText(resultText.getText() + "9");
        } else if (pressID == R.id.buttonDot) {
            if (!hasDot) {
                resultText.setText(resultText.getText() + ".");
                hasDot = true;
            }
        } else {
            resultText.setText("ERROR");
        }

    }

    public void onClickFunctionButton(View view) {

        double result = 0;

        //Getting ID of pressed Button
        int pressID = view.getId();

        //then try to put into data
        //Figuring out which button was pressed and updating the represented text field object
        if (pressID == R.id.buttonPlus) {
            data01 = Double.parseDouble((String) resultText.getText());
            opp = Operator.add;
            resultText.setText("");
        } else if (pressID == R.id.buttonMinus) {
            data01 = Double.parseDouble((String) resultText.getText());
            opp = Operator.sub;
            resultText.setText("");
        } else if (pressID == R.id.buttonMul) {
            data01 = Double.parseDouble((String) resultText.getText());
            opp = Operator.mul;
            resultText.setText("");
        } else if (pressID == R.id.buttonDiv) {
            data01 = Double.parseDouble((String) resultText.getText());
            opp = Operator.div;
            resultText.setText("");
        }else { //equal button
            data02 = Double.parseDouble((String) resultText.getText());

            if (opp == Operator.add) {
                result = data01 + data02;
                opp = Operator.eq;
            }else if (opp == Operator.sub) {
                result = data01 - data02;
                opp = Operator.eq;
            }else if (opp == Operator.mul){
                result = data01*data02;
                opp = Operator.eq;
            }else if (opp == Operator.div){ //divide
                result = data01/data02;
                opp = Operator.eq;
            }else {
                resultText.setText("ERROR");
            }
            resultText.setText(Double.toString(result));
            data01 = 0;
            data02 = 0;
        }


    }

}