package com.example.iseenero;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class AxisDateFormatter extends ValueFormatter {
    String[] values;

    public AxisDateFormatter(String[] values) {
        this.values = values;
    }

    public String getFormattedValue(float value) {
        if(value >= 0){
            if(values.length > value){
                return values[(int)value];
            }else return "";
        }else return "";
    }
}
