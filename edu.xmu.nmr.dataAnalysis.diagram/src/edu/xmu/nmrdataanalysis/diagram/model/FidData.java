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
    private double vScale;
    
    /**
     * 绘图时，图像向上偏移量
     */
    private int offsetY;
    
    /**
     * 竖直方向网格间隔，和竖直ruler保持一致
     */
    private int vInterval;
    
    /**
     * 在当前水平方向的缩放比例下，当前x轴方向偏移量，
     */
    private int offsetX;
    
    /**
     * 水平方向总的缩放比例
     */
    private double hScale;
    
    public static final String PRO_FD_FIDDATA = "pro_fd_fiddata";
    public static final String PRO_FD_STEPSIZE = "pro_fd_stepsize";
    public static final String PRO_FD_VINTERVAL = "pro_fd_interval";
    public static final String PRO_FD_OFFSETY = "pro_fd_offsety";
    public static final String PRO_FD_PART_ZOOM = "pro_fd_part_zoom";
    public static final String PRO_FD_OFFSETX = "pro_fd_offsetx";
    public static final String PRO_FD_VSCALE = "pro_fd_vscal";
    
    public FidData() {
        reset();
    }
    
    public ArrayList<Float> getRawData() {
        return rawData;
    }
    
    public void setRawData(ArrayList<Float> rawData) {
        ArrayList<Float> old = this.rawData;
        this.rawData = rawData;
        getListeners().firePropertyChange(PRO_FD_FIDDATA, old, this.rawData);
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
        int old = this.vInterval;
        this.vInterval = vInterval;
        getListeners().firePropertyChange(PRO_FD_VINTERVAL, old, this.vInterval);
    }
    
    /**
     * 设置当前fid data的纵坐标间隔的比例，根据设置比例，生成最新的纵坐标间隔，主要在{@link DAZoomManager}中使用，
     * 
     * @param factor
     * @return
     */
    public void setVIntervalScale(double totalScale, double factor) {
        setVScale(totalScale);
        setVInterval((int) Math.floor(factor * vInterval));
    }
    
    public double getVScale() {
        return vScale;
    }
    
    public void setVScale(double vScale) {
        double old = this.vScale;
        this.vScale = vScale;
        getListeners().firePropertyChange(PRO_FD_VSCALE, old, this.vScale);
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        int old = this.offsetY;
        this.offsetY = offsetY;
        getListeners().firePropertyChange(PRO_FD_OFFSETY, old, this.offsetY);
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
        this.offsetX = 0;
        this.hScale = 1;
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        int old = this.offsetX;
        this.offsetX = offsetX;
        getListeners().firePropertyChange(PRO_FD_OFFSETX, old, this.offsetX);
    }
    
    /**
     * 追加x轴偏移
     * 
     * @param appendOffsetX
     */
    public void appendOffsetX(int appendOffsetX) {
        setOffsetX(this.offsetX + appendOffsetX);
    }
    
    public double getHScale() {
        return hScale;
    }
    
    public void setHScale(double hScale) {
        double old = this.hScale;
        this.hScale = hScale;
        getListeners().firePropertyChange(PRO_FD_PART_ZOOM, old, this.hScale);
    }
    
    public void appendHScale(double appendHScale) {
        setHScale(this.hScale * appendHScale);
    }
    
    public void setHScaleAndOffset(int appendOffsetX, double appendHScale) {
        this.setOffsetX((int) ((this.offsetX + appendOffsetX) * appendHScale));
        this.appendHScale(appendHScale);
    }
}
