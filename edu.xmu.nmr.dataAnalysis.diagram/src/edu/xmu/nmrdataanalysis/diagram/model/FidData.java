package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;
import edu.xmu.nmr.dataanalysis.diagram.figures.PointsTools;
import edu.xmu.nmr.dataanalysis.diagram.layouts.AxisProcess;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefPageUtil;

public class FidData extends FElement implements IPropertyChangeListener {
    
    /**
     * 原始数据，如fid数据，proc数据
     */
    private ArrayList<Float> rawData;
    /**
     * 原始数据中绝对值的最大者
     */
    private float absMax;
    
    /**
     * 当前竖直方向上维持的一个缩放总比例
     */
    private double vScale;
    
    /**
     * 绘图时，图像向上偏移量
     */
    private int offsetY;
    
    /**
     * 在当前水平方向的缩放比例下，当前x轴方向偏移量，
     */
    private int offsetX;
    
    /**
     * 水平方向总的缩放比例
     */
    private double hScale;
    
    /**
     * 数据长度
     */
    private int dataSize;
    
    /**
     * 数据点间的步长
     */
    private float stride;
    
    private Dimension size;
    
    private Map<String, Float> yAxis;
    
    private Map<String, Float> xAxis;
    
    public static final String PRO_FD_FIDDATA = "pro_fd_fiddata";
    public static final String PRO_FD_OFFSETY = "pro_fd_offsety";
    public static final String PRO_FD_OFFSETX = "pro_fd_offsetx";
    public static final String PRO_FD_YAXIS = "pro_fd_yaxis";
    public static final String PRO_FD_XAXIS = "pro_fd_xaxis";
    
    /// 非业务模型区
    private Color foregroundColor = ColorConstants.blue;
    private Color backgroundColor = ColorConstants.white;
    private boolean hasGird = true;
    private int lineWidth = 1;
    private boolean hasBorder;
    
    public FidData() {
        this.size = new Dimension(0, 0);
        reset();
        getInitConfig();
        IPreferenceStore ips = Activator.getDefault().getPreferenceStore();
        ips.addPropertyChangeListener(this);
    }
    
    /**
     * 获取偏好页中的配置，设置绘图方式
     */
    private void getInitConfig() {
        hasBorder = DAPrefPageUtil.getValueOfFidBorderCheck();
        foregroundColor = new Color(null,
                DAPrefPageUtil.getValueOfFidForeColor());
        backgroundColor = new Color(null,
                DAPrefPageUtil.getValueOfFidBackColor());
    }
    
    public ArrayList<Float> getRawData() {
        return rawData;
    }
    
    public void setRawData(ArrayList<Float> rawData) {
        ArrayList<Float> old = this.rawData;
        this.rawData = rawData;
        setAbsMax(PointsTools.getAbsMax(this.rawData));
        setDataSize(this.rawData.size());
        getListeners().firePropertyChange(PRO_FD_FIDDATA, old, this.rawData);
    }
    
    public int getDataSize() {
        return dataSize;
    }
    
    public Dimension getSize() {
        return size;
    }
    
    public void setSize(Dimension size) {
        Dimension old = this.size;
        this.size = size;
        if (old == null || this.size == null || !old.equals(this.size)) {
            setYAxis();
            setXAxis();
        }
    }
    
    public void setDataSize(int dataSize) {
        int old = this.dataSize;
        this.dataSize = dataSize;
        if (old != this.dataSize) {
            setXAxis();
        }
    }
    
    public float getStride() {
        return stride;
    }
    
    public void setStride(float stride) {
        float old = this.stride;
        this.stride = stride;
        if (old != this.stride) {
            setXAxis();
        }
    }
    
    public float getAbsMax() {
        return absMax;
    }
    
    public void setAbsMax(float absMax) {
        float old = this.absMax;
        this.absMax = absMax;
        if (old != this.absMax) {
            setYAxis();
        }
    }
    
    public Map<String, Float> getYAxis() {
        return yAxis;
    }
    
    public void setYAxis() {
        if (absMax <= 0 || vScale == 0 || this.size == null
                || this.size.height <= 0) {
            return;
        }
        Map<String, Float> old = this.yAxis;
        this.yAxis = AxisProcess.getAxis(0, (float) (2 * absMax / vScale),
                this.size.height);
        getListeners().firePropertyChange(PRO_FD_YAXIS, old, this.yAxis);
    }
    
    public Map<String, Float> getXAxis() {
        return xAxis;
    }
    
    /**
     * 计算x坐标比例
     */
    public void setXAxis() {
        if (this.rawData == null || this.rawData.size() == 0 || this.hScale == 0
                || this.size == null || this.size.width <= 0
                || this.stride == 0) {
            return;
        }
        Map<String, Float> old = this.xAxis;
        this.xAxis = AxisProcess.getAxis(0,
                (float) (dataSize * stride / hScale), this.size.width);
        getListeners().firePropertyChange(PRO_FD_XAXIS, old, this.xAxis);
    }
    
    public double getVScale() {
        return vScale;
    }
    
    public void setVScale(double vScale) {
        double old = this.vScale;
        this.vScale = vScale;
        if (old != this.vScale) {
            setYAxis();
        }
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
        setOffsetX(0);
        setOffsetY(0);
        setVScale(1);
        setHScale(1);
    }
    
    /**
     * 检查是否需要重新设置，
     * 
     * @return
     */
    public boolean checkNeedReset() {
        if (this.offsetX == 0 && this.offsetY == 0 && this.vScale == 1
                && this.hScale == 1)
            return false;
        return true;
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
        if (old != this.hScale) {
            setXAxis();
        }
    }
    
    public void appendHScale(double appendHScale) {
        setHScale(this.hScale * appendHScale);
    }
    
    public void setHScaleAndOffset(int appendOffsetX, double appendHScale) {
        this.setOffsetX((int) ((this.offsetX + appendOffsetX) * appendHScale));
        this.appendHScale(appendHScale);
    }
    
    /**
     * *****************************************************************
     * ******非业务模型区*******
     * *****************************************************************
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }
    
    public void setForegroundColor(Color foregroundColor) {
        Color old = this.foregroundColor;
        this.foregroundColor = foregroundColor;
        getListeners().firePropertyChange(DAPrefConstants.FID_PREF_FORE_COLOR,
                old, this.foregroundColor);
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public void setBackgroundColor(Color backgroundColor) {
        Color old = this.backgroundColor;
        this.backgroundColor = backgroundColor;
        getListeners().firePropertyChange(DAPrefConstants.FID_PREF_BACH_COLOR,
                old, this.backgroundColor);
    }
    
    public boolean isHasGird() {
        return hasGird;
    }
    
    public void setHasGird(boolean hasGird) {
        boolean old = this.hasGird;
        this.hasGird = hasGird;
        getListeners().firePropertyChange(DAPrefConstants.FID_PREF_HAS_GRID,
                old, this.hasGird);
    }
    
    public int getLineWidth() {
        return lineWidth;
    }
    
    public void setLineWidth(int lineWidth) {
        int old = this.lineWidth;
        this.lineWidth = lineWidth;
        getListeners().firePropertyChange(DAPrefConstants.FID_PREF_LINE_WIDTH,
                old, this.lineWidth);
    }
    
    public boolean isHasBorder() {
        return hasBorder;
    }
    
    public void setHasBorder(boolean hasBorder) {
        boolean old = this.hasBorder;
        this.hasBorder = hasBorder;
        getListeners().firePropertyChange(DAPrefConstants.FID_PREF_HAS_BORDER,
                old, this.hasBorder);
    }
    
    @Override public void propertyChange(PropertyChangeEvent event) {
        switch (event.getProperty()) {
        case DAPrefConstants.FID_PREF_FORE_COLOR:
            setForegroundColor(new Color(null, (RGB) event.getNewValue()));
            break;
        case DAPrefConstants.FID_PREF_BACH_COLOR:
            setBackgroundColor(new Color(null, (RGB) event.getNewValue()));
            break;
        case DAPrefConstants.FID_PREF_HAS_BORDER:
            setHasBorder((boolean) event.getNewValue());
            break;
        case DAPrefConstants.FID_PREF_HAS_GRID:
            setHasGird((boolean) event.getNewValue());
            break;
        case DAPrefConstants.FID_PREF_LINE_WIDTH:
            setLineWidth((int) event.getNewValue());
            break;
        }
    }
}
