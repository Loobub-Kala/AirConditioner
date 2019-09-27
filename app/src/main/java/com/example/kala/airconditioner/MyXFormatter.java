package com.example.kala.airconditioner;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MyXFormatter implements IAxisValueFormatter {
    ArrayList<String> strings;
    public MyXFormatter(ArrayList<String> times) {
        strings = times;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int i = (int) value % strings.size();
        return strings.get(i);
    }
}
