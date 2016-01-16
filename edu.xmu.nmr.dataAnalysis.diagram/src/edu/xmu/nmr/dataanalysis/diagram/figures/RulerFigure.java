package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;

import edu.xmu.nmr.dataanalysis.diagram.layouts.FormatXY;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;

public class RulerFigure extends Figure {
    
    /**
     * 该坐标轴要表示的值域，然后根据width或height以及interval来计算出stepSize
     */
    private float totalSize;
    /**
     * 每个interval对应的实际的坐标间隔
     */
    private float stepSize;
    /**
     * 坐标节点短画线的长度
     */
    private int tall = 10;
    /**
     * 当前坐标系的坐标轴的坐落方向
     */
    private RulerOrient orient;
    /**
     * 坐标系的像素间隔
     */
    private int interval;
    
    /**
     * 坐标图总的缩放比例
     */
    private double scale;
    
    /**
     * y或x轴方向上的偏移
     */
    private int offset;
    
    public RulerFigure() {
        setForegroundColor(ColorConstants.black);
        setOpaque(false);
        setFont(new Font(null, "Arial", 10, SWT.NORMAL));
    }
    
    public RulerFigure(float stepSize, RulerOrient orient, int interval) {
        this();
        this.stepSize = stepSize;
        this.orient = orient;
        this.interval = interval;
    }
    
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    public void setTotalSize(float totalSize) {
        this.totalSize = totalSize;
    }
    
    public void setStepSize(float stepSize) {
        this.stepSize = stepSize;
    }
    
    public void setOrient(RulerOrient orient) {
        this.orient = orient;
    }
    
    public void setScale(double scale) {
        this.scale = scale;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public RulerOrient getOrient() {
        return orient;
    }
    
    public void setLayout(Rectangle rect) {
        getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
    }
    
    public void setGridLayout() {
        if (this.orient == null) {
            return;
        }
        switch (this.orient) {
        case LEFT:
            this.setSize(Ruler.AXISLL, -1);
            getParent().setConstraint(this, new GridData(GridData.FILL,
                    GridData.FILL, false, true, 1, 4));
            break;
        case BOTTOM:
            this.setSize(-1, Ruler.AXISLL);
            getParent().setConstraint(this, new GridData(GridData.FILL,
                    GridData.FILL, true, false, 4, 1));
        }
        
    }
    
    @Override protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        graphics.pushState();
        Rectangle bounds = getBounds();
        int rulerWidth = Ruler.AXISLL - 5;
        int endX = bounds.x + bounds.width - 1;
        int endY = bounds.y + bounds.height;
        int centerY = bounds.y + bounds.height() / 2 + offset;
        switch (orient) {
        case LEFT:
            stepSize = (float) (totalSize * interval / (bounds.height * scale));
            TextLayout layout = new TextLayout(null);
            layout.setText("0");
            layout.setAlignment(SWT.RIGHT);
            layout.setWidth(rulerWidth - tall - 1); // 设置文字的宽度，该宽度要去除坐标轴的宽度
            int aboveY = centerY;
            int belowY = centerY;
            for (int i = 0; aboveY >= bounds.y || belowY <= endY; i++) {
                aboveY = centerY - interval * i;
                belowY = centerY + interval * i;
                float y = i * stepSize;
                String sy = String.valueOf(y);
                if (sy.length() > 8) {
                    sy = FormatXY.getENotation(y);
                }
                if (aboveY >= bounds.y) {
                    graphics.drawLine(endX - tall, aboveY, endX, aboveY);
                    layout.setText(sy);
                    graphics.drawTextLayout(layout, endX - rulerWidth,
                            aboveY - 6);
                }
                sy = "-" + sy;
                if (i != 0 && belowY <= endY) {
                    graphics.drawLine(endX - tall, belowY, endX, belowY); // 绘制坐标短线
                    layout.setText(sy);
                    graphics.drawTextLayout(layout, endX - rulerWidth,
                            belowY - 6);
                }
            }
            graphics.drawLine(endX, bounds.y, endX, endY);
            break;
        case BOTTOM:
            stepSize = totalSize * interval / (float) (bounds.width * scale);
            for (int j = 1 + Math.abs(offset) / interval; j
                    * interval <= (bounds.width + Math.abs(offset)); j++) {
                int pX = bounds.x + interval * j + offset;
                graphics.drawLine(pX, bounds.y, pX, bounds.y + tall);
                float b = j * stepSize;
                String sb = String.valueOf(b);
                if (sb.length() > 7) {
                    sb = FormatXY.getENotation(b);
                }
                TextLayout bLayout = new TextLayout(null);
                bLayout.setText(sb);
                bLayout.setWidth(interval - 10);
                bLayout.setAlignment(SWT.CENTER);
                graphics.drawTextLayout(bLayout, pX - interval / 2 + 5,
                        bounds.y + tall + 1);
            }
            graphics.drawLine(bounds.x, bounds.y, endX, bounds.y);
            break;
        }
        graphics.popState();
    }
}
