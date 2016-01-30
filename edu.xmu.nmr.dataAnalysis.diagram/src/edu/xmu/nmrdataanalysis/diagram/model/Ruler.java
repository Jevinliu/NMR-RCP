package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;

import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;

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
     * 记录当前坐标轴偏移量，主要用于移动图像的局部显示
     */
    private int offset;
    
    /**
     * 转换后的坐标比例，间隔，等信息
     */
    private Map<String, Float> axis;
    
    public static final String PRO_RULER_OFFSET = "pro_ruler_offset";
    public static final String PRO_RULER_AXIS = "pro_ruler_axis";
    /**
     * 坐标轴Figure占据的长度
     */
    public static final int AXISLL = 40;
    
    /////// 可视化属性
    private boolean isVisiable = true;
    
    /**
     * 刻度
     */
    private String scale;
    
    public Ruler() {
        reset();
        setForegroundColor(ColorConstants.black);
    }
    
    public RulerOrient getOrient() {
        return orient;
    }
    
    public void setOrient(RulerOrient orient) {
        this.orient = orient;
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
    
    public Map<String, Float> getAxis() {
        return axis;
    }
    
    public void setAxis(Map<String, Float> axis) {
        Map<String, Float> old = this.axis;
        this.axis = axis;
        getListeners().firePropertyChange(PRO_RULER_AXIS, old, this.axis);
    }
    
    public void reset() {
        this.offset = 0;
    }
    
    ////// 可视化属性
    
    public boolean isVisiable() {
        return isVisiable;
    }
    
    public void setVisiable(boolean isVisiable) {
        boolean old = this.isVisiable;
        this.isVisiable = isVisiable;
        getListeners().firePropertyChange(
                DAPrefConstants.L_RULER_PREF_IS_VISIABLE, old, this.isVisiable);
    }
    
    public String getScale() {
        return scale;
    }
    
    public void setScale(String scale) {
        String old = this.scale;
        this.scale = scale;
        getListeners().firePropertyChange(DAPrefConstants.L_RULER_PREF_SCALE,
                old, this.scale);
    }
}
