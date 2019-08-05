package com.jhhscm.platform.fragment.sale;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private List<String> mValues;

    public MyXAxisValueFormatter(List<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return (int)value+ "";
    }
}
