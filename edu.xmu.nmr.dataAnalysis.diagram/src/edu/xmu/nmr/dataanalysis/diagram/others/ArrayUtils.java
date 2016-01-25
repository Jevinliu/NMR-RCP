package edu.xmu.nmr.dataanalysis.diagram.others;

import java.util.ArrayList;

public class ArrayUtils {
    
    /**
     * 将原数组在中间位置进行前后交换；注意该数组的长度必须为偶数，否则报参数异常
     * 
     * @param sour
     *            原数组
     * @return
     */
    public static float[] swapArrayInMid(float[] sour) {
        if (sour.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Float array's length is not even number.");
        }
        float[] dest = new float[sour.length];
        System.arraycopy(sour, sour.length / 2, dest, 0, sour.length / 2);
        System.arraycopy(sour, 0, dest, sour.length / 2, sour.length / 2);
        return dest;
    }
    
    /**
     * 
     * 将原浮点型数组转换为ArrayList
     * 
     * @param 原数组
     * @return
     */
    public static ArrayList<Float> floatArrayToList(float[] sour) {
        ArrayList<Float> res = new ArrayList<Float>();
        for (float f : sour) {
            res.add(f);
        }
        return res;
    }
}
