package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import edu.xmu.nmr.dataanalysis.diagram.layouts.DABorderLayout;
import edu.xmu.nmr.dataanalysis.diagram.layouts.DAHintLayout;
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
        setOpaque(true);
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
        if (this.orient == null || !this.orient.equals(orient)) {
            this.orient = orient;
            setUnitLabel();
        }
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setUnitLabelText(float exp) {
        StringBuilder sb = new StringBuilder("e");
        switch (orient) {
        case LEFT:
            this.vUnitLabel.setText(sb.append(exp).toString());
            break;
        case BOTTOM:
            this.unitLabel.setText(sb.append(exp).toString());
            break;
        default:
            break;
        }
    }
    
    public void setFactor(float factor) {
        this.factor = factor;
    }
    
    public void setLayout() {
        if (this.orient == null) {
            return;
        }
        switch (orient) {
        case LEFT:
            getParent().setConstraint(this, DABorderLayout.LEFT);
            break;
        case BOTTOM:
            getParent().setConstraint(this, DABorderLayout.BOTTOM);
            break;
        case RIGHT:
            getParent().setConstraint(this, DABorderLayout.RIGHT);
            break;
        case TOP:
            getParent().setConstraint(this, DABorderLayout.TOP);
            break;
        }
    }
    
    @Override protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (interval <= 0) {
            return;
        }
        graphics.pushState();
        switch (orient) {
        case LEFT:
            DAFigureUtils.paintLeftAxis(graphics, getBounds(), interval, offset,
                    factor, tall);
            break;
        case BOTTOM:
            DAFigureUtils.paintBottomAxis(graphics, getBounds(), interval,
                    offset, factor, tall);
            break;
        case RIGHT:
            DAFigureUtils.paintRightAxis(graphics, getBounds(), interval,
                    offset, factor, tall);
            break;
        case TOP:
            DAFigureUtils.paintTopAxis(graphics, bounds, interval, offset,
                    factor, tall);
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
