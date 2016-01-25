package edu.xmu.nmr.dataanalysis.diagram.math;

import java.util.ArrayList;

import org.jtransforms.fft.FloatFFT_1D;

import edu.xmu.nmr.dataanalysis.diagram.others.ArrayUtils;

public class FFTMathUtils {
    
    /**
     * 复数一维的傅里叶变换
     * 
     * @param source
     *            原数据数组
     * @return 返回复数数据
     */
    public static float[] getComplexFloatFFT_1D(float[] source) {
        int length = getClosestZeroizeSize(source.length);
        FloatFFT_1D floatFFT = new FloatFFT_1D(length);
        float[] result = new float[length * 2];
        System.arraycopy(source, 0, result, 0, source.length);
        floatFFT.complexForward(result);
        return ArrayUtils.swapArrayInMid(extractReOrImDataArray(result, 0));
    }
    
    /**
     * 复数一维的傅里叶变换
     * 
     * @param source
     * @return 返回实数数据
     */
    public static ArrayList<Float> getComplexFloatFFT_1D(
            ArrayList<Float> source) {
        int length = getClosestZeroizeSize(source.size());
        FloatFFT_1D floatFFT = new FloatFFT_1D(length);
        float[] result = new float[length * 2];
        for (int i = 0; i < length; i++) {
            if (i < source.size()) {
                result[i] = source.get(i);
            } else {
                result[i] = 0;
            }
        }
        floatFFT.complexForward(result);
        float[] res = ArrayUtils
                .swapArrayInMid(extractReOrImDataArray(result, 0));
        return ArrayUtils.floatArrayToList(res);
    }
    
    /**
     * 获取比 原数据长度大的最小2的次幂点数
     * 
     * @param sourSize
     *            原数据长度
     * @return
     */
    private static int getClosestZeroizeSize(int sourSize) {
        int exponent = (int) (Math.round(MathUtils.log(sourSize, 2)) + 1);
        return (int) Math.pow(2, exponent);
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
    
    private static float[] extractReOrImDataArray(float[] complexData,
            int flag) {
        if (flag != 0 && flag != 1) {
            throw new IllegalArgumentException("Flag only can be 0 or 1.");
        }
        float[] res = new float[complexData.length / 2];
        for (int i = 0 + flag; i < complexData.length; i++) {
            res[i / 2] = complexData[i];
            i++;
        }
        return res;
    }
}
