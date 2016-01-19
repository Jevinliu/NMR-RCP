package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeListener;

/**
 * 坐标轴模型层
 * 
 * @author Jevin
 *         
 */
public abstract class Ruler extends FElement implements PropertyChangeListener {
    
    /**
     * 坐标轴位于的方向，
     */
    private RulerOrient orient;
    /**
     * 当前坐标节点间像素间隔
     */
    private int interval;
    /**
     * 当前像素间隔下所代表的实际间隔值，即坐标节点对应数值的差值
     */
    private float stepSize;
    /**
     * 该坐标轴所代表的值域
     */
    private float totalSize;
    
    /**
     * 当前图像总的缩放比例
     */
    private double totalScale;
    
    /**
     * 记录当前坐标轴偏移量，主要用于移动图像的局部显示
     */
    private int offset;
    
    public static final String PRO_RULER_STEPSIZE = "pro_ruler_stepSize";
    public static final String PRO_RULER_INTERVAL = "pro_ruler_interval";
    public static final String PRO_RULER_TOTALSIZE = "pro_ruler_totalSize";
    public static final String PRO_RULER_OFFSET = "pro_ruler_offset";
    public static final String PRO_RULER_TOTALSCALE = "pro_ruler_totalscale";
    /**
     * 坐标轴Figure占据的长度
     */
    public static final int AXISLL = 65;
    public static final int DEFAULT_INTERVAL = 40;
    
    public Ruler() {
        reset();
    }
    
    public RulerOrient getOrient() {
        return orient;
    }
    
    public void setOrient(RulerOrient orient) {
        this.orient = orient;
    }
    
    public int getInterval() {
        return interval;
    }
    
    public double getTotalScale() {
        return totalScale;
    }
    
    public void setTotalScale(double totalScale) {
        double old = this.totalScale;
        this.totalScale = totalScale;
        getListeners().firePropertyChange(PRO_RULER_TOTALSCALE, old,
                this.totalScale);
    }
    
    /**
     * @param totalScale
     *            当前图像总的缩放比例，
     * @param factor
     *            当前坐标缩放比例因子，结合当前的interval生成坐标轴间隔
     */
    public void setIntervalScale(double totalScale, double factor) {
        setTotalScale(totalScale);
        setInterval((int) Math.floor(factor * this.interval));
    }
    
    public void setInterval(int interval) {
        int old = this.interval;
        this.interval = interval;
        getListeners().firePropertyChange(PRO_RULER_INTERVAL, old,
                this.interval);
    }
    
    public float getStepSize() {
        return stepSize;
    }
    
    public void setStepSize(float stepSize) {
        float old = this.stepSize;
        this.stepSize = stepSize;
        getListeners().firePropertyChange(PRO_RULER_STEPSIZE, old,
                this.stepSize);
    }
    
    public float getTotalSize() {
        return totalSize;
    }
    
    public void setTotalSize(float totalSize) {
        float old = this.totalSize;
        this.totalSize = totalSize;
        getListeners().firePropertyChange(PRO_RULER_TOTALSIZE, old,
                this.totalSize);
    }
    
    public int getOffset() {
        return offset;
    }
    
    public void setOffset(int offset) {
        int old = this.offset;
        this.offset = offset;
        getListeners().firePropertyChange(PRO_RULER_OFFSET, old, this.offset);
    }
    
    public void appendOffset(int appendOffset) {
        setOffset(this.offset + appendOffset);
    }
    
    public void reset() {
        this.offset = 0;
        this.interval = DEFAULT_INTERVAL;
        this.totalScale = 1;
    }
}
