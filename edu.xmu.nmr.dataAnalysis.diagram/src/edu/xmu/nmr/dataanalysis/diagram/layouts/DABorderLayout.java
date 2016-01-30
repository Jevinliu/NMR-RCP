package edu.xmu.nmr.dataanalysis.diagram.layouts;

import org.eclipse.draw2d.AbstractHintLayout;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 * 参考{@link BorderLayout}书写的上下左右中的布局，应用在坐标轴与Fid、谱图的显示布局
 * 
 * @author Jevin
 * @see BorderLayout
 */
public class DABorderLayout extends AbstractHintLayout {
    
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer CENTER = new Integer(PositionConstants.CENTER);
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer TOP = new Integer(PositionConstants.TOP);
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer BOTTOM = new Integer(PositionConstants.BOTTOM);
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer LEFT = new Integer(PositionConstants.LEFT);
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer RIGHT = new Integer(PositionConstants.RIGHT);
    
    private IFigure center, left, top, bottom, right;
    
    /**
     * 周围留白
     */
    private int vMargin = 3, hMargin = 3;
    /**
     * 坐标轴宽度
     */
    private int rulerGap = 40;
    
    /**
     * 中间间隙
     */
    private int vSpacing = 2, hSpacing = 2;
    
    @Override public void layout(IFigure container) {
        Rectangle area = container.getClientArea();
        Rectangle rect = new Rectangle();
        
        int hTotalGap = rulerGap + hSpacing;
        int vTotalGap = rulerGap + vSpacing;
        
        /// 以中心的figure为参考点，之后换算出上下左右的figure.bounds
        Dimension centerDs = area.getSize().getCopy().expand(-2 * hMargin,
                -2 * vMargin);
        Point centerTopLeft = area.getTopLeft().getTranslated(hMargin, vMargin);
        Point centerBottomRight = area.getBottomRight().getTranslated(-hMargin,
                -vMargin);
        if (left != null && left.isVisible()) {
            centerDs.width -= hTotalGap;
            centerTopLeft.x += hTotalGap;
        }
        if (right != null && right.isVisible()) {
            centerDs.width -= hTotalGap;
            centerBottomRight.x -= hTotalGap;
        }
        if (top != null && top.isVisible()) {
            centerDs.height -= vTotalGap;
            centerTopLeft.y += vTotalGap;
        }
        if (bottom != null && bottom.isVisible()) {
            centerDs.height -= vTotalGap;
            centerBottomRight.y -= vTotalGap;
        }
        
        if (top != null && top.isVisible()) {
            rect.setSize(centerDs.width, rulerGap);
            rect.setLocation(centerTopLeft.x, area.y + vMargin);
            top.setBounds(rect);
        }
        if (bottom != null && bottom.isVisible()) {
            rect.setSize(centerDs.width, rulerGap);
            rect.setLocation(centerTopLeft.x, centerBottomRight.y + vSpacing);
            bottom.setBounds(rect);
        }
        if (left != null && left.isVisible()) {
            rect.setSize(rulerGap, centerDs.height);
            rect.setLocation(area.x + hMargin, centerTopLeft.y);
            left.setBounds(rect);
        }
        if (right != null && right.isVisible()) {
            rect.setSize(rulerGap, centerDs.height);
            rect.setLocation(centerBottomRight.x + hSpacing, centerTopLeft.y);
            right.setBounds(rect);
        }
        if (center != null && center.isVisible()) {
            rect.setSize(centerDs.width, centerDs.height);
            rect.setLocation(centerTopLeft.x, centerTopLeft.y);
            center.setBounds(rect);
        }
    }
    
    public void remove(IFigure child) {
        if (center == child) {
            center = null;
        } else if (top == child) {
            top = null;
        } else if (bottom == child) {
            bottom = null;
        } else if (right == child) {
            right = null;
        } else if (left == child) {
            left = null;
        }
    }
    
    public void setConstraint(IFigure child, Object constraint) {
        remove(child);
        super.setConstraint(child, constraint);
        if (constraint == null) {
            return;
        }
        
        switch (((Integer) constraint).intValue()) {
        case PositionConstants.CENTER:
            center = child;
            break;
        case PositionConstants.TOP:
            top = child;
            break;
        case PositionConstants.BOTTOM:
            bottom = child;
            break;
        case PositionConstants.RIGHT:
            right = child;
            break;
        case PositionConstants.LEFT:
            left = child;
            break;
        default:
            break;
        }
    }
    
    @Override protected Dimension calculatePreferredSize(IFigure container,
            int wHint, int hHint) {
        return new Dimension(-1, -1);
    }
    
    public void setVMargin(int vMargin) {
        this.vMargin = vMargin;
    }
    
    public void setHMargin(int hMargin) {
        this.hMargin = hMargin;
    }
    
    public void setVSpacing(int vSpacing) {
        this.vSpacing = vSpacing;
    }
    
    public void setHSpacing(int hSpacing) {
        this.hSpacing = hSpacing;
    }
    
    public void setRulerGap(int rulerGap) {
        this.rulerGap = rulerGap;
    }
}
