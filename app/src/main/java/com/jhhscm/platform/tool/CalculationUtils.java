package com.jhhscm.platform.tool;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculationUtils {

    public static String wan(String toal) {
        try {
            DecimalFormat df = new DecimalFormat("#.0000");
            toal = df.format(Double.parseDouble(toal) / 10000);
            //保留2位小数
            BigDecimal b = new BigDecimal(Double.parseDouble(toal));
            toal = b.setScale(4, BigDecimal.ROUND_HALF_UP).toString() + "万";
            return toal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String wan1(String toal) {
        if (Double.parseDouble(toal) == 0.0) {
            return "暂无报价";
        } else {
            DecimalFormat df = new DecimalFormat("#.0000");
            toal = df.format(Double.parseDouble(toal) / 10000);
            //保留2位小数
            BigDecimal b = new BigDecimal(Double.parseDouble(toal));
            toal = b.setScale(4, BigDecimal.ROUND_HALF_UP).toString() + "万";
            return toal;
        }
    }
}
