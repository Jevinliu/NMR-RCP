package edu.xmu.nmr.dataanalysis.diagram.layouts;

import org.eclipse.draw2d.geometry.Point;

/**
 * <p>
 * Coordinate transform; 坐标转换类，主要为了将诸如fid数据转换到相应的坐标系内
 * </p>
 * 
 * @author software
 *         
 */
public class CoordinateTf {
    
    /**
     * x轴比例
     */
    private double xScale;
    /**
     * y轴比例
     */
    private double yScale;
    /**
     * x轴偏移
     */
    private float xOffset;
    /**
     * y轴偏移
     */
    private float yOffset;
    
    public CoordinateTf(float xScale, float yScale, float xOffset,
            float yOffset) {
        this.xScale = xScale;
        this.yScale = yScale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public CoordinateTf() {
        this(1.0f, 1.0f, 0.0f, 0.0f);
    }
    
    /**
     * 将指定的数据点的x值转换到相应的坐标系下
     * 
     * @param sourX
     *            源数据的X值
     * @return int 指定坐标系下X
     */
    public int transfromX(float sourX) {
        return (int) (sourX * xScale + xOffset);
    }
    
    /**
     * 将指定的数据点的y值转换到相应的坐标系下
     * 
     * @param sourY
     *            源数据的Y值
     * @return int 指定坐标系下 Y
     */
    public int transformY(float sourY) {
        return (int) (sourY * yScale + yOffset);
    }
    
    public Point transformXY(float sourX, float sourY) {
        Point p = new Point();
        p.x = transfromX(sourX);
        p.y = transformY(sourY);
        return p;
    }
    
    public Point tranformPoint(Point sourPoint) {
        Point p = new Point();
        p.x = transfromX(sourPoint.x);
        p.y = transformY(sourPoint.y);
        return p;
    }
    
    public double getxScale() {
        return xScale;
    }
    
    public void setxScale(double xScale) {
        this.xScale = xScale;
    }
    
    public double getyScale() {
        return yScale;
    }
    
    public void setyScale(double yScale) {
        this.yScale = yScale;
    }
    
    public float getxOffset() {
        return xOffset;
    }
    
    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }
    
    public float getyOffset() {
        return yOffset;
    }
    
    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
