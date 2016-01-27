package edu.xmu.nmr.dataanalysis.diagram.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.xmu.nmr.dataanalysis.diagram.layouts.CoordinateTf;

/**
 * <p>
 * 绘制Fid曲线的figure类，实现类似{@link AbstractPointListShape}
 * </p>
 * 
 * @author software
 * @see AbstractPointListShape
 */
public class LineFigure extends Figure {
    
    private ArrayList<Float> rawData; // 原始数据，如fid数据，proc数据
    private float absMax;
    private CoordinateTf ctf;
    private Rectangle oldBounds;
    private ArrayList<Integer> selectedIndex;
    PointList points = new PointList();
    /**
     * 水平方向网格间隔，和ruler的interval保持一致
     */
    private int hInterval;
    
    /**
     * 竖直方向网格间隔，和ruler的interval保持一致
     */
    private int vInterval;
    
    /**
     * 竖直方向上维持的缩放总比例
     */
    private double vScale;
    
    private int offsetY;
    
    private double hScale;
    
    private int offsetX;
    
    /**
     * 设置是否有网格
     */
    private boolean hasGrid = true;
    
    /**
     * 
     */
    private int lineWidth = -1;
    
    public LineFigure() {
        setOpaque(true);
        ctf = new CoordinateTf();
    }
    
    public LineFigure(ArrayList<Float> rawData) {
        this();
        this.rawData = rawData;
    }
    
    /**
     * 每次在设置完数据后，都进行数据点的筛选
     * 
     * @param rawData
     */
    public void setRawData(ArrayList<Float> rawData) {
        this.rawData = rawData;
    }
    
    public void setAbsMax(float max) {
        this.absMax = max;
    }
    
    public void setHInterval(int hInterval) {
        this.hInterval = hInterval;
    }
    
    public void setVInterval(int vInterval) {
        this.vInterval = vInterval;
    }
    
    public void setVScale(double vScale) {
        this.vScale = vScale;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    public void setHScale(double hScale) {
        this.hScale = hScale;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public void setHasGrid(boolean hasGrid) {
        this.hasGrid = hasGrid;
    }
    
    /**
     * 设置线宽
     * 
     * @param lineWidth
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    /**
     * 设置坐标转换标准
     */
    private void setupCoordinateTf() {
        Rectangle rect = getBounds();
        if (rect == null) {
            return;
        }
        ctf.setxOffset(rect.x + offsetX);
        ctf.setyOffset((float) (offsetY + rect.y
                - (rect.height * this.vScale / 2 - rect.height / 2)));
        ctf.setxScale(
                (float) (rect.width * 1 / (float) rawData.size()) * hScale);
        ctf.setyScale(rect.height * this.vScale / (2 * absMax));
    }
    
    public CoordinateTf getCoordinateTf() {
        return ctf;
    }
    
    /**
     * 在x值相同时，取出当前位置的原始数据的最大值，最小值，其他该位置上的值舍弃
     */
    private void selectPoints() {
        if (selectedIndex != null && !selectedIndex.isEmpty()
                && oldBounds.equals(getBounds())) {
            return;
        }
        if (rawData == null || rawData.size() == 0) {
            return;
        }
        // 选择点所需要的坐标转换器
        oldBounds = getBounds().getCopy();
        CoordinateTf contf = new CoordinateTf();
        contf.setxOffset(oldBounds.x);
        contf.setyOffset(oldBounds.y);
        contf.setxScale(oldBounds.width / (float) rawData.size());
        contf.setyScale(oldBounds.height / (2 * absMax));
        selectedIndex = new ArrayList<Integer>();
        ArrayList<Integer> sameXIndex = new ArrayList<Integer>(); // 记录当下一组x坐标转换后相同的数据点的索引
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override public int compare(Integer o1, Integer o2) {
                float v1 = rawData.get(o1);
                float v2 = rawData.get(o2);
                if (v1 > v2)
                    return 1;
                else if (v1 < v2)
                    return -1;
                else
                    return 0;
            }
        };
        int tempX = 0;
        for (int i = 0; i < rawData.size(); i++) {
            if (sameXIndex.size() == 0)
                tempX = contf.transfromX(i);
            else if (Math.abs(tempX - contf.transfromX(i)) > 0) { // 记录x相同的一系列点
                int maxValueIndex = Collections.max(sameXIndex, c); // 记录值最大的索引
                int minValueIndex = Collections.min(sameXIndex, c); // 记录数据值最小的索引
                if (contf.transformY(
                        absMax - rawData.get(maxValueIndex)) == contf
                                .transformY(
                                        absMax - rawData.get(minValueIndex))) // 如果转换后纵坐标相等，只需要添加一个
                    selectedIndex.add(minValueIndex);
                else {
                    if (maxValueIndex > minValueIndex) {
                        selectedIndex.add(minValueIndex);
                        selectedIndex.add(maxValueIndex);
                    } else {
                        selectedIndex.add(maxValueIndex);
                        selectedIndex.add(minValueIndex);
                    }
                }
                sameXIndex.clear();
                tempX = contf.transfromX(i);
            }
            sameXIndex.add(i);
        }
    }
    
    /**
     * 将选择的数据点转换为指定区域下的像素点
     */
    private void transformPoints() {
        this.setupCoordinateTf(); // 主要用于设置相对位移
        this.selectPoints();
        PointList pl = new PointList();
        for (int i : selectedIndex) {
            Point p = ctf.transformXY(i, absMax - rawData.get(i)); // 将0值作为整个fidfigure的纵向中心，
            pl.addPoint(p);
        }
        this.points = pl;
    }
    
    private void drawPolyline(Graphics graphics) {
        for (int i = 1; i < points.size(); i++) {
            int old = graphics.getLineWidth();
            if (lineWidth > 0) {
                graphics.setLineWidth(lineWidth);
            }
            graphics.drawLine(points.getPoint(i - 1), points.getPoint(i));
            graphics.setLineWidth(old);
        }
    }
    
    public void setLayout(Rectangle rect) {
        getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
    }
    
    public void setGridLayout() {
        if (getParent() == null) {
            return;
        }
        getParent().setConstraint(this,
                new GridData(GridData.FILL, GridData.FILL, true, true, 4, 4));
    }
    
    @Override protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (rawData == null || rawData.size() == 0) {
            return;
        }
        transformPoints();
        graphics.pushState();
        boolean isAdvanced = graphics.getAdvanced();
        graphics.setAdvanced(true);
        graphics.setAntialias(SWT.ON);
        if (this.hasGrid) {
            this.drawGrid(graphics);
        }
        this.drawPolyline(graphics);
        graphics.setAdvanced(isAdvanced);
        graphics.popState();
    }
    
    /**
     * 绘制网格
     * 
     * @param graphics
     */
    private void drawGrid(Graphics graphics) {
        if (vInterval == 0) {
            return;
        }
        Rectangle bounds = getBounds();
        graphics.getLineStyle();
        graphics.setForegroundColor(ColorConstants.lightGray);
        graphics.setLineStyle(SWT.LINE_DOT);
        int centerY = bounds.y + bounds.height / 2 + offsetY; // 以中点为基准
        int endX = bounds.x + bounds.width;
        int endY = bounds.y + bounds.height;
        // 绘制水平网格线
        int aboveY = centerY;
        int belowY = centerY;
        for (int i = 0; aboveY >= bounds.y || belowY <= endY; i++) {
            aboveY = centerY - vInterval * i;
            belowY = centerY + vInterval * i;
            if (aboveY >= bounds.y) {
                graphics.drawLine(bounds.x, aboveY, endX, aboveY);
            }
            if (i != 0 && belowY <= endY) {
                graphics.drawLine(bounds.x, belowY, endX, belowY);
            }
        }
        // 绘制竖直网格线
        int ox = 0;
        if (offsetX >= 0) {
            ox = offsetX % hInterval;
        } else {
            ox = hInterval + offsetX % hInterval;
        }
        for (int j = 0; j * hInterval < bounds.width; j++) {
            int x = bounds.x + ox + j * hInterval;
            graphics.drawLine(x, bounds.y, x, endY);
        }
        graphics.setLineStyle(SWT.LINE_SOLID);
        graphics.setForegroundColor(getForegroundColor());
    }
    
}
