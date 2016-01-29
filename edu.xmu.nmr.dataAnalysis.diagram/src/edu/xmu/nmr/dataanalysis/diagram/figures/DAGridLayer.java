package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * 
 * DAGridLayer
 * <p>
 * 重写网格层，当没有网格的时候会充当背景层
 * </p>
 * 
 * @see
 */

public class DAGridLayer extends FreeformLayer {
    
    private int vInterval;
    
    private int hInterval;
    
    private int offsetY;
    
    private int offsetX;
    
    /**
     * 是否绘制网格
     */
    private boolean hasGrid;
    
    public DAGridLayer() {
        super();
        setOpaque(true);
        setForegroundColor(ColorConstants.lightGray);
    }
    
    public void setVInterval(int vInterval) {
        this.vInterval = vInterval;
    }
    
    public void setHInterval(int hInterval) {
        this.hInterval = hInterval;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public void setHasGrid(boolean hasGrid) {
        this.hasGrid = hasGrid;
    }
    
    /**
     * Overridden to indicate no preferred size. The grid layer should not
     * affect the size of the layered pane in which it is placed.
     * 
     * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
     */
    public Dimension getPreferredSize(int wHint, int hHint) {
        return new Dimension();
    }
    
    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (hasGrid) {
            paintGrid(graphics);
        }
    }
    
    protected void paintGrid(Graphics g) {
        DAFigureUtils.paintGrid(g, this, vInterval, hInterval, offsetY,
                offsetX);
    }
}
