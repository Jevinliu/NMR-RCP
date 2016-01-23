package edu.xmu.nmr.dataanalysis.diagram.math;

import java.util.ArrayList;

import org.jtransforms.fft.FloatFFT_1D;

public class FFTMathUtils {
    
    /**
     * 复数一维的傅里叶变换
     * 
     * @param source
     *            原数据数组
     * @return 返回复数数据
     */
    public static float[] getComplexFloatFFT_1D(float[] source) {
        FloatFFT_1D floatFFT = new FloatFFT_1D(source.length);
        float[] result = new float[source.length * 2];
        System.arraycopy(source, 0, result, 0, source.length);
        floatFFT.complexForward(result);
        return result;
    }
    
    /**
     * 
     * @param source
     * @return 返回实数数据
     */
    public static ArrayList<Float> getComplexFloatFFT_1D(
            ArrayList<Float> source) {
        FloatFFT_1D floatFFT = new FloatFFT_1D(source.size());
        float[] result = new float[source.size() * 2];
        for (int i = 0; i < source.size(); i++) {
            result[i] = source.get(i);
        }
        floatFFT.complexForward(result);
        return extractReOrImData(result, 0);
    }
    
    /**
     * 实数数据的一维傅里叶变换，
     * 
     * @param source
     *            源实数数据
     * @return 返回实数数据
     */
    public static ArrayList<Float> getRealFloatFFT_1D(ArrayList<Float> source) {
        FloatFFT_1D floatFFT = new FloatFFT_1D(source.size());
        float[] result = new float[source.size() * 2];
        for (int i = 0; i < source.size(); i++) {
            result[i] = source.get(i);
        }
        floatFFT.realForward(result);
        return extractReOrImData(result, 0);
    }
    
    public static float[] getRealFloatFFT_1D(float[] source) {
        FloatFFT_1D floatFFT = new FloatFFT_1D(source.length);
        float[] result = new float[source.length * 2];
        System.arraycopy(source, 0, result, 0, source.length);
        floatFFT.realForward(result);
        return result;
    }
    
    /**
     * 从实部与虚部交替的复数点中抽取实部或虚部点
     * 
     * @param complexData
     *            原复数点
     * @param flag
     *            "0" 抽取实部<br>
     *            "1" 抽取虚部<br>
     * @return 返回抽取后的实部点
     */
    private static ArrayList<Float> extractReOrImData(float[] complexData,
            int flag) {
        if (flag != 0 && flag != 1) {
            throw new IllegalArgumentException("Flag only can be 0 or 1.");
        }
        ArrayList<Float> realRes = new ArrayList<Float>();
        for (int i = 0 + flag; i < complexData.length; i++) {
            realRes.add(complexData[i]);
            i++;
        }
        return realRes;
    }
    
}
