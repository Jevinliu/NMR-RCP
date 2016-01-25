package edu.xmu.nmr.dataanalysis.diagram.math;

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
        return Math.log(value) / Math.log(base);
    }
}
