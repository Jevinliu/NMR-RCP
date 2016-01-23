package edu.xmu.nmr.dataanalysis.diagram.layouts;

import java.util.HashMap;
import java.util.Map;

public class AxisProcess {
    private final static float MinBigGap = 50;
    private final static float MaxBigCount = 15;
    
    /**
     * Get the Axis.
     *
     * @return The infomation is in the map. <br>
     *         "exp" The exponent of 10. If it is 0, then no need the exponent.
     *         <br>
     *         "factor" Need to be added from small to big. <br>
     *         "min" The min axis value. <br>
     *         "count" How many it needs to draw the big axis. <br>
     *         "minPosition" The position for the min value. <br>
     *         "gap" The gap between values. <br>
     */
    public static Map<String, Float> getAxis(float min, float max, float width)
            throws IllegalArgumentException {
        if (width == 0) {
            throw new IllegalArgumentException("Width can't be 0.");
        }
        
        if (max <= min) {
            throw new IllegalArgumentException(
                    "Big and Small aren't properly set.");
        }
        
        double exp = Math.floor(Math.log10(max - min));
        double unit = Math.pow(10.0f, exp);
        Map<String, Float> factor = getFactor(min, max, width, unit);
        
        float realFactor = factor.get("factor");
        float realExp = factor.get("exp") + (float) exp;
        float realUnit = (float) Math.pow(10.0f, realExp) * realFactor;
        
        int count = 1;
        float minAxis = (float) Math.ceil(min / realUnit) * realUnit;
        float thisAxis = minAxis + realUnit;
        
        while (thisAxis <= max) {
            thisAxis += realUnit;
            count++;
        }
        
        float minPosi = (minAxis - min) * width / (max - min);
        float gap = realUnit * width / (max - min);
        
        if ((realUnit < 100 && realUnit >= 1)
                || (realUnit > 0.01 && realUnit <= 1)) {
            realFactor = realFactor * (float) Math.pow(10.0f, realExp);
            realExp = 0;
        } else {
            minAxis = minAxis / realUnit;
        }
        
        Map<String, Float> result = new HashMap<String, Float>();
        result.put("exp", realExp);
        result.put("factor", realFactor);
        result.put("min", minAxis);
        result.put("count", (float) count);
        result.put("minPosition", minPosi);
        result.put("gap", gap);
        return result;
    }
    
    /**
     * Get the real factor.
     *
     * @return "factor" the scale part; "exp" the exp part.
     */
    private static Map<String, Float> getFactor(float min, float max,
            float width, double unit) {
        double factor;
        if (checkOK(min, max, width, unit)) {
            int count = 1;
            factor = 0.5;
            
            while (checkOK(min, max, width, unit * factor)) {
                count++;
                double scale = 1;
                if (count % 3 == 1) {
                    scale = 0.5;
                } else if (count % 3 == 2) {
                    scale = 0.2;
                }
                factor = Math.pow(10, -count / 3) * scale;
            }
            
            double scale = 1;
            count--;
            if (count % 3 == 1) {
                scale = 0.5;
            } else if (count % 3 == 2) {
                scale = 0.2;
            }
            
            float exp = (float) (-count / 3);
            if (scale < 1) {
                scale *= 10;
                exp = exp - 1;
            }
            Map<String, Float> result = new HashMap<String, Float>();
            result.put("factor", (float) scale);
            result.put("exp", exp);
            return result;
            
        } else {
            int count = 1;
            factor = 2.0;
            
            while (!checkOK(min, max, width, unit * factor)) {
                count++;
                double scale = 1;
                if (count % 3 == 1) {
                    scale = 2;
                } else if (count % 3 == 2) {
                    scale = 5;
                }
                factor = Math.pow(10, count / 3) * scale;
            }
            
            double scale = 1;
            count--;
            if (count % 3 == 1) {
                scale = 2;
            } else if (count % 3 == 2) {
                scale = 5;
            }
            Map<String, Float> result = new HashMap<String, Float>();
            result.put("factor", (float) scale);
            result.put("exp", (float) (count / 3));
            return result;
        }
    }
    
    /**
     * Check whether the unit is ok.
     *
     * @return true if unit is ok; false means the unit is small.
     */
    private static boolean checkOK(float min, float max, float width,
            double unit) {
        double thisAxis = Math.ceil(min / unit) * unit;
        
        int count = 1;
        while (thisAxis < max) {
            thisAxis += unit;
            count++;
        }
        
        if (count > MaxBigCount) {
            return false;
        }
        
        double gap = width / (max - min) * unit;
        return gap >= MinBigGap;
    }
}
