package edu.xmu.nmr.dataanalysis.diagram.math;

import java.text.DecimalFormat;

public class MathUtils {
    
    /**
     * 返回某个数为底的对数
     * 
     * 
     * @param value
     *            要求解的值
     * @param base
     *            基数
     * @return
     */
    public static double log(double value, double base) {
        if (value <= 0 || base <= 0) {
            throw new IllegalArgumentException(
                    "Value and base must be greater zero.");
        }
        return Math.log(value) / Math.log(base);
    }
    
    /**
     * 获取E指数
     * 
     * @param source
     * @return
     */
    public static String getENotation(double source) {
        DecimalFormat df = new DecimalFormat("0.00E00");
        return df.format(source);
    }
}
