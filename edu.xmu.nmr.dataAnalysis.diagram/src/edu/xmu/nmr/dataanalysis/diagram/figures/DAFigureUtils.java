package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextLayout;

import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

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
    
    /**
     * 绘制底部的Axis，
     * 
     * @param graphics
     * @param bounds
     *            要绘制的Figure的bounds，
     * @param interval
     *            坐标轴标尺的像素间隔
     * @param offset
     *            偏移量
     * @param factor
     *            坐标值的比例因子
     * @param tall
     *            坐标短线长度
     */
    public static void paintBottomAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall) {
        paintHorizontalAxis(graphics, bounds, interval, offset, factor, tall,
                bounds.y, bounds.y + tall, bounds.y + tall + 1);
        graphics.drawLine(bounds.x, bounds.y, bounds.getBottomRight().x,
                bounds.y);
    }
    
    /**
     * 绘制顶部坐标轴
     * 
     * @param graphics
     * @param bounds
     * @param interval
     * @param offset
     * @param factor
     * @param tall
     */
    public static void paintTopAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall) {
        int bottomLeftY = bounds.getBottomLeft().y;
        paintHorizontalAxis(graphics, bounds, interval, offset, factor, tall,
                bottomLeftY - tall, bottomLeftY, bounds.y + 15);
        graphics.drawLine(bounds.x, bottomLeftY - 1, bounds.getBottomRight().x,
                bottomLeftY - 1);
    }
    
    /**
     * 绘制水平坐标轴的通用方法
     * 
     * @param graphics
     * @param bounds
     * @param interval
     * @param offset
     * @param factor
     * @param tall
     * @param startY
     *            绘制坐标短画线的起始y
     * @param endY
     *            绘制坐标短画线的终止y
     * @param textStartY
     *            绘制坐标值时的起始y
     */
    private static void paintHorizontalAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall, int startY,
            int endY, int textStartY) {
        for (int j = -offset / interval; j
                * interval <= (bounds.width + Math.abs(offset)); j++) {
            int pX = bounds.x + interval * j + offset;
            graphics.drawLine(pX, startY, pX, endY);
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
            } else if (textRightX > bounds.getBottomRight().x) {
                textLeftX = pX - interval + 10;
                bLayout.setAlignment(SWT.RIGHT);
            } else {
                bLayout.setAlignment(SWT.CENTER);
            }
            graphics.drawTextLayout(bLayout, textLeftX, textStartY);
        }
    }
    
    /**
     * 绘制左边坐标轴
     * 
     * @param graphics
     * @param bounds
     * @param interval
     * @param offset
     * @param factor
     * @param tall
     */
    public static void paintLeftAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall) {
            
        int rulerWidth = Ruler.AXISLL - 5;
        int topRightX = bounds.getTopRight().x - 1;
        paintVerticalAxis(graphics, bounds, interval, offset, factor, tall,
                topRightX - tall, topRightX, topRightX - rulerWidth, SWT.RIGHT);
        // 绘制坐标轴的轴线
        graphics.drawLine(topRightX, bounds.y, topRightX,
                bounds.getBottomLeft().y);
    }
    
    public static void paintRightAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall) {
        paintVerticalAxis(graphics, bounds, interval, offset, factor, tall,
                bounds.x, bounds.x + tall, bounds.x + tall + 1, SWT.LEFT);
        graphics.drawLine(bounds.x, bounds.y, bounds.x,
                bounds.getBottomLeft().y);
    }
    
    /**
     * 绘制竖直方向的坐标轴
     * 
     * @param graphics
     * @param bounds
     * @param interval
     * @param offset
     * @param factor
     * @param tall
     * @param startX
     *            坐标短画线的起始点X
     * @param endX
     *            坐标短画线的终点X
     * @param textStartX
     *            坐标值文本的起点X
     * @param alignment
     *            文本的对齐方式
     */
    private static void paintVerticalAxis(Graphics graphics, Rectangle bounds,
            int interval, int offset, float factor, int tall, int startX,
            int endX, int textStartX, int alignment) {
        int rulerWidth = Ruler.AXISLL - 5;
        int centerY = bounds.y + bounds.height() / 2 + offset;
        int bottomLeftY = bounds.getBottomLeft().y;
        TextLayout layout = new TextLayout(null);
        layout.setText("0");
        layout.setAlignment(alignment);
        layout.setWidth(rulerWidth - tall - 1); // 设置文字的宽度，该宽度要去除坐标轴的宽度
        int aboveY = centerY;
        int belowY = centerY;
        for (int i = 0; aboveY >= bounds.y || belowY <= bottomLeftY; i++) {
            aboveY = centerY - interval * i;
            belowY = centerY + interval * i;
            String sy = String.valueOf(i * factor); // 绘制坐标值
            if (aboveY >= bounds.y) {
                graphics.drawLine(startX, aboveY, endX, aboveY);
                layout.setText(sy);
                int textY = aboveY - 8;
                if (textY < bounds.y) {
                    textY = aboveY;
                }
                graphics.drawTextLayout(layout, textStartX, textY);
            }
            sy = "-" + sy;
            if (i != 0 && belowY <= bottomLeftY) {
                graphics.drawLine(startX, belowY, endX, belowY); // 绘制坐标短线
                layout.setText(sy);
                int textY = belowY - 8;
                if (belowY + 8 > bottomLeftY) { // 判断是否坐标值绘制点超出边界
                    textY = belowY - 16;
                }
                graphics.drawTextLayout(layout, textStartX, textY);
            }
        }
    }
    
}
