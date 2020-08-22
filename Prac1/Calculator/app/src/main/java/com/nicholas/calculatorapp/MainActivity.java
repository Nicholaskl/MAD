package com.nicholas.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private Button plus, minus, multiply, divide;
    private EditText input1, input2;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plus = findViewById(R.id.plus);
        divide = findViewById(R.id.divide);
        multiply = findViewById(R.id.multiply);
        minus = findViewById(R.id.minus);

        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        result = findViewById(R.id.result);

        plus.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
                double num1 = Double.parseDouble(input1.getText().toString());
                double num2 = Double.parseDouble(input2.getText().toString());

                double res = num1 + num2;

                result.setText(Double.toString(res));
            }
        });

        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double num1 = Double.parseDouble(input1.getText().toString());
                double num2 = Double.parseDouble(input2.getText().toString());

                double res = num1 - num2;

                result.setText(Double.toString(res));
            }
        });

        multiply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double num1 = Double.parseDouble(input1.getText().toString());
                double num2 = Double.parseDouble(input2.getText().toString());

                double res = num1 * num2;

                result.setText(Double.toString(res));
            }
        });

        divide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double num1 = Double.parseDouble(input1.getText().toString());
                double num2 = Double.parseDouble(input2.getText().toString());

                if((num1 != 0) && (num2 != 0))
                {
                    double res = num1 / num2;
                    result.setText(Double.toString(res));
                }
                else
                {
                    result.setText("Error! Can't divide by 0!");
                }
            }
        });
    }
}