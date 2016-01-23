package edu.xmu.nmr.dataanalysis.diagram.figures;

import java.util.ArrayList;

public class PointsTools {
    
    /**
     * 针对fid数据（包含正数和实数），获取当前float数组的最大值和最小值中绝对值较大的
     * 
     * @param values
     * @return
     */
    public static float getAbsMax(ArrayList<Float> values) {
        if (values == null || values.size() == 0)
            return 0f;
        float abs = 0;
        for (float v : values) {
            float temp = Math.abs(v);
            if (temp > abs)
                abs = temp;
        }
        return abs;
    }
    
}