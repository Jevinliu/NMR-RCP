package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;

import edu.xmu.nmr.dataanalysis.diagram.layouts.DAHintLayout;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;

public class RulerFigure extends Figure {
    
    /**
     * 坐标节点短画线的长度
     */
    private int tall = 8;
    /**
     * 当前坐标系的坐标轴的坐落方向
     */
    private RulerOrient orient;
    /**
     * 坐标系的像素间隔
     */
    private int interval;
    
    /**
     * y或x轴方向上的偏移
     */
    private int offset;
    
    /**
     * 坐标轴大格代表的数据值间隔相对e指数的比例
     */
    private float factor;
    
    /**
     * 单位和坐标间隔标签
     */
    private VerticalLabel vUnitLabel;
    
    private Label unitLabel;
    
    public RulerFigure() {
        setForegroundColor(ColorConstants.black);
        setOpaque(false);
        setFont(new Font(null, "Arial", 8, SWT.NORMAL));
        setLayoutManager(new DAHintLayout());
        vUnitLabel = new VerticalLabel();
        unitLabel = new Label();
        unitLabel.setOpaque(true);
    }
    
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    public void setOrient(RulerOrient orient) {
        RulerOrient old = this.orient;
        this.orient = orient;
        if (!this.orient.equals(old)) {
            setUnitLabel();
        }
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setUnitLabelText(float exp) {
        StringBuilder sb = new StringBuilder("e");
        if (this.orient.equals(RulerOrient.LEFT))
            this.vUnitLabel.setText(sb.append(exp).toString());
        else
            this.unitLabel.setText(sb.append(exp).toString());
    }
    
    public void setFactor(float factor) {
        this.factor = factor;
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
        if (interval <= 0) {
            return;
        }
        graphics.pushState();
        Rectangle bounds = getBounds();
        int rulerWidth = Ruler.AXISLL - 5;
        int endX = bounds.getTopRight().x - 1;
        int endY = bounds.getBottomLeft().y;
        int centerY = bounds.y + bounds.height() / 2 + offset;
        switch (orient) {
        case LEFT:
            TextLayout layout = new TextLayout(null);
            layout.setText("0");
            layout.setAlignment(SWT.RIGHT);
            layout.setWidth(rulerWidth - tall - 1); // 设置文字的宽度，该宽度要去除坐标轴的宽度
            int aboveY = centerY;
            int belowY = centerY;
            for (int i = 0; aboveY >= bounds.y || belowY <= endY; i++) {
                aboveY = centerY - interval * i;
                belowY = centerY + interval * i;
                String sy = String.valueOf(i * factor); // 绘制坐标值
                if (aboveY >= bounds.y) {
                    graphics.drawLine(endX - tall, aboveY, endX, aboveY);
                    layout.setText(sy);
                    int textY = aboveY - 8;
                    if (textY < bounds.y) {
                        textY = aboveY;
                    }
                    graphics.drawTextLayout(layout, endX - rulerWidth, textY);
                }
                sy = "-" + sy;
                if (i != 0 && belowY <= endY) {
                    graphics.drawLine(endX - tall, belowY, endX, belowY); // 绘制坐标短线
                    layout.setText(sy);
                    int textY = belowY - 8;
                    if (belowY + 8 > endY) { // 判断是否坐标值绘制点超出边界
                        textY = belowY - 16;
                    }
                    graphics.drawTextLayout(layout, endX - rulerWidth, textY);
                }
            }
            graphics.drawLine(endX, bounds.y, endX, endY);
            break;
        case BOTTOM:
            for (int j = -offset / interval; j
                    * interval <= (bounds.width + Math.abs(offset)); j++) {
                int pX = bounds.x + interval * j + offset;
                graphics.drawLine(pX, bounds.y, pX, bounds.y + tall);
                String sb = String.valueOf(j * factor);
                TextLayout bLayout = new TextLayout(null);
                bLayout.setText(sb);
                bLayout.setWidth(interval - 10);
                bLayout.setAlignment(SWT.CENTER);
                int textLeftX = pX - interval / 2 + 5;
                int textRightX = pX + interval / 2 - 5;
                if (textLeftX < bounds.x) {
                    textLeftX = pX;
                    bLayout.setAlignment(SWT.LEFT);
                } else if (textRightX > endX) {
                    textLeftX = pX - interval + 10;
                    bLayout.setAlignment(SWT.RIGHT);
                } else {
                    bLayout.setAlignment(SWT.CENTER);
                }
                graphics.drawTextLayout(bLayout, textLeftX,
                        bounds.y + tall + 1);
            }
            graphics.drawLine(bounds.x, bounds.y, endX, bounds.y);
            break;
        }
        graphics.popState();
    }
    
    /**
     * 添加表示坐标单位与指数因子的标签
     */
    private void setUnitLabel() {
        if (this.orient.equals(RulerOrient.LEFT)) {
            add(vUnitLabel, DAHintLayout.TOPLEFT);
            
        } else {
            add(unitLabel, DAHintLayout.BOTTOMRIGHT);
        }
    }
}
