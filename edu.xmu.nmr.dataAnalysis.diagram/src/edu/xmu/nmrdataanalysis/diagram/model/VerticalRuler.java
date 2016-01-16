package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeEvent;

public class VerticalRuler extends Ruler {
    
    /**
     * 原始数据的值的绝对值的最大值
     */
    private float absMax;
    
    public VerticalRuler() {
    }
    
    public float getAbsMax() {
        return absMax;
    }
    
    public void setAbsMax(float absMax) {
        this.absMax = absMax;
        setTotalSize(this.absMax * 2);
    }
    
    /**
     * 在纵坐标的情况下，根据真实数据的绝对值的最大值的2倍计算出在当前像素间隔下的坐标值间隔，即步长，
     * 
     * @param height
     */
    public void setStepSizeByHeight() {
        if (getLayout() == null || getLayout().height == 0) {
            log.error("Ruler's layout is null or it's height is 0.");
            return;
        }
        setStepSize(this.absMax * 2 * getInterval() / getLayout().height);
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(FidData.PRO_FD_OFFSETY)) {
            setOffset((int) evt.getNewValue());
        }
    }
    
}
