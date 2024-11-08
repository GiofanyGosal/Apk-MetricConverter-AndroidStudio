package com.example.metricconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spOriginal;
    private Spinner spConvert;
    private TextView resultText;

    private void calculateResult(double input, int index, int index2, int index3) {
        int index4;
        double result = input;
        if (index == 1 || index == 2 || index == 3 || index == 4) {
            index4 = index2 - index3;
            if (index4 > 0) {
                for (int x = 1; x <= index4; x++)
                    result *= 10;
                if (result == (long) result)
                    resultText.setText(String.format(Locale.getDefault(), "%d %s", (long) result, getUnit(index, index3)));
                else
                    resultText.setText(String.format(Locale.getDefault(), "%s %s", result, getUnit(index, index3)));
            } else if (index4 < 0) {
                index4 = Math.abs(index4);
                for (int x = 1; x <= index4; x++)
                    result /= 10;
                if (result == (long) result)
                    resultText.setText(String.format(Locale.getDefault(), "%d %s", (long) result, getUnit(index, index3)));
                else
                    resultText.setText(String.format(Locale.getDefault(), "%s %s", result, getUnit(index, index3)));
            } else {
                if (result == (long) result)
                    resultText.setText(String.format(Locale.getDefault(), "%d %s", (long) result, getUnit(index, index3)));
                else
                    resultText.setText(String.format(Locale.getDefault(), "%s %s", result, getUnit(index, index3)));
            }
        } else if (index == 5) {
            if (!((index2 == 0 && index3 == 0) || (index2 == 1 && index3 == 1) || (index2 == 2 && index3 == 2))) {
                if (index2 == 0 && index3 == 1)
                    result = (input * 9 / 5) + 32;
                else if (index2 == 0 && index3 == 2)
                    result = input + 273.15;
                else if (index2 == 1 && index3 == 0)
                    result = (input - 32) * 5 / 9;
                else if (index2 == 1 && index3 == 2)
                    result = ((input - 32) * 5 / 9) + 273.15;
                else if (index2 == 2 && index3 == 0)
                    result = input - 273.15;
                else if (index2 == 2 && index3 == 1)
                    result = ((input - 273.15) * 9 / 5) + 32;
            }
            if (result == (long) result)
                resultText.setText(String.format(Locale.getDefault(), "%d %s", (long) result, getUnit(index, index3)));
            else
                resultText.setText(String.format(Locale.getDefault(), "%s %s", result, getUnit(index, index3)));
        }
    }

    private String getUnit(int indexMetric, int indexConvert) {
        String[] lengths = {"Milimeter", "Centimeter", "Desimeter", "Meter", "Dekameter", "Hektometer", "Kilometer"};
        String[] mass = {"Miligram", "Centigram", "Desigram", "Gram", "Dekagram", "Hektogram", "Kilogram"};
        String[] time = {"Milisekon", "Centisekon", "Desisekon", "Sekon", "Dekasekon", "Hektosekon", "Kilosekon"};
        String[] electric_current = {"Miliampere", "Centiampere", "Desiampere", "Ampere", "Dekaampere", "Hektoampere", "Kiloampere"};
        String[] temperature = {"Celcius", "Fahrenheit", "Kelvin"};
        switch (indexMetric) {
            case 1:
                return lengths[indexConvert];
            case 2:
                return mass[indexConvert];
            case 3:
                return time[indexConvert];
            case 4:
                return electric_current[indexConvert];
            case 5:
                return temperature[indexConvert];
            default:
                return "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputValue = findViewById(R.id.getInputValue);
        resultText = findViewById(R.id.resultText);

        String[] lengths = {"Milimeter", "Centimeter", "Desimeter", "Meter", "Dekameter", "Hektometer", "Kilometer"};
        String[] mass = {"Miligram", "Centigram", "Desigram", "Gram", "Dekagram", "Hektogram", "Kilogram"};
        String[] time = {"Milisekon", "Centisekon", "Desisekon", "Sekon", "Dekasekon", "Hektosekon", "Kilosekon"};
        String[] electric_current = {"Miliampere", "Centiampere", "Desiampere", "Ampere", "Dekaampere", "Hektoampere", "Kiloampere"};
        String[] temperature = {"Celcius", "Fahrenheit", "Kelvin"};

        ArrayAdapter<String> adapter_lengths = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lengths);
        ArrayAdapter<String> adapter_mass = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mass);
        ArrayAdapter<String> adapter_time = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, time);
        ArrayAdapter<String> adapter_electric_current = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, electric_current);
        ArrayAdapter<String> adapter_temperature = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, temperature);

        final int[] indexMetric = {0};
        final int[] indexMetric2 = {0};
        final int[] indexMetric3 = {0};
        final double[] getInputNum = {0.0};

        spOriginal = findViewById(R.id.spOriginal);
        spOriginal.setEnabled(false);

        spConvert = findViewById(R.id.spConvert);
        spConvert.setEnabled(false);

        Spinner spMetrics = findViewById(R.id.spMetrics);
        ArrayAdapter<CharSequence> metricsAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.metrics,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );
        spMetrics.setAdapter(metricsAdapter);
        spMetrics.setSelection(0);

        spMetrics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    spOriginal.setEnabled(false);
                    spConvert.setEnabled(false);
                    return;
                }
                spOriginal.setEnabled(true);
                spConvert.setEnabled(true);
                switch(position){
                    case 1:
                        spOriginal.setAdapter(adapter_lengths);
                        spConvert.setAdapter(adapter_lengths);
                        indexMetric[0] = position;
                        break;
                    case 2:
                        spOriginal.setAdapter(adapter_mass);
                        spConvert.setAdapter(adapter_mass);
                        indexMetric[0] = position;
                        break;
                    case 3:
                        spOriginal.setAdapter(adapter_time);
                        spConvert.setAdapter(adapter_time);
                        indexMetric[0] = position;
                        break;
                    case 4:
                        spOriginal.setAdapter(adapter_electric_current);
                        spConvert.setAdapter(adapter_electric_current);
                        indexMetric[0] = position;
                        break;
                    case 5:
                        spOriginal.setAdapter(adapter_temperature);
                        spConvert.setAdapter(adapter_temperature);
                        indexMetric[0] = position;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spOriginal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexMetric2[0] = position;
                if(getInputNum[0] != 0.0 && indexMetric3[0] != 0)
                    calculateResult(getInputNum[0], indexMetric[0], indexMetric2[0], indexMetric3[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spConvert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexMetric3[0] = position;
                if(getInputNum[0] != 0.0 && indexMetric2[0] != 0)
                    calculateResult(getInputNum[0], indexMetric[0], indexMetric2[0], indexMetric3[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double num = Double.parseDouble(s.toString());
                    getInputNum[0] = num;
                    calculateResult(num, indexMetric[0], indexMetric2[0], indexMetric3[0]);
                } catch (NumberFormatException e) {
                    resultText.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
