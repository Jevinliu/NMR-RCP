package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * 
 * DAFigureUtils
 * <p>
 * Figure的一般工作类
 * </p>
 * 
 * @see
 */
public class DAFigureUtils {
    
    /**
     * 绘制网格线，
     * 
     * @param graphics
     * @param figure
     *            被绘制的figure
     * @param vInterval
     *            竖直方向的网格间隔
     * @param hInterval
     *            水平方向的网格间隔
     * @param offsetY
     *            竖直方向的偏移
     * @param offsetX
     *            水平方向的偏移
     */
    public static void paintGrid(Graphics graphics, IFigure figure,
            int vInterval, int hInterval, int offsetY, int offsetX) {
        if (vInterval == 0 || hInterval == 0) {
            return;
        }
        Rectangle bounds = figure.getBounds();
        graphics.getLineStyle();
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
    }
}
