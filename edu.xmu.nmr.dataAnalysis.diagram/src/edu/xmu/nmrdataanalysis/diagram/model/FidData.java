package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;

public class FidData extends FElement {
    
    /**
     * 原始数据，如fid数据，proc数据
     */
    private ArrayList<Float> rawData;
    /**
     * 原始数据中绝对值的最大者
     */
    private float absMax;
    /**
     * 水平方向网格间隔，和ruler保持一致
     */
    private int hInterval;
    
    /**
     * 当前竖直方向上维持的一个缩放总比例
     */
    private float vScale;
    
    /**
     * 绘图时，图像向上偏移量
     */
    private int offsetY;
    
    /**
     * 竖直方向网格间隔，和竖直ruler保持一致
     */
    private int vInterval;
    public static final String PRO_LS_FIDDATA = "pro_ls_fiddata";
    public static final String PRO_LS_STEPSIZE = "pro_ls_stepsize";
    public static final String PRO_LS_INTERVAL = "pro_ls_interval";
    public static final String PRO_LS_OFFSETY = "pro_ls_offsety";
    
    public FidData() {
        reset();
    }
    
    public ArrayList<Float> getRawData() {
        return rawData;
    }
    
    public void setRawData(ArrayList<Float> rawData) {
        ArrayList<Float> old = this.rawData;
        this.rawData = rawData;
        getListeners().firePropertyChange(PRO_LS_FIDDATA, old, this.rawData);
    }
    
    public float getAbsMax() {
        return absMax;
    }
    
    public void setAbsMax(float absMax) {
        this.absMax = absMax;
    }
    
    public int getHInterval() {
        return hInterval;
    }
    
    public void setHInterval(int hInterval) {
        this.hInterval = hInterval;
    }
    
    public int getVInterval() {
        return vInterval;
    }
    
    public void setVInterval(int vInterval) {
        this.vInterval = vInterval;
    }
    
    /**
     * 设置当前fid data的纵坐标间隔的比例，根据设置比例，生成最新的纵坐标间隔，主要在{@link DAZoomManager}中使用，
     * 
     * @param factor
     * @return
     */
    public void setVIntervalScale(double totalScale, double factor) {
        this.vScale = (float) totalScale;
        int old = this.vInterval;
        this.vInterval = (int) Math.floor(factor * vInterval);
        getListeners().firePropertyChange(PRO_LS_INTERVAL, old, this.vInterval);
    }
    
    public float getVScale() {
        return vScale;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        int old = this.offsetY;
        this.offsetY = offsetY;
        getListeners().firePropertyChange(PRO_LS_OFFSETY, old, this.offsetY);
    }
    
    /**
     * 在原来的offsetY的基础上追加改变量，组成新的offsetY
     * 
     * @param appendOffsetY
     *            要追加的offsetY的改变量
     */
    public void appendOffsetY(int appendOffsetY) {
        setOffsetY(this.offsetY + appendOffsetY);
    }
    
    /**
     * 
     * 恢复初始设置
     */
    public void reset() {
        this.vScale = 1.0f;
        this.offsetY = 0;
    }
}
