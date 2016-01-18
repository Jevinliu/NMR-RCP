package edu.xmu.nmr.dataanalysis.diagram.layouts;

import java.text.DecimalFormat;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * 布局工具类
 * 
 * @author software
 *         
 */
public class LayoutUtils {
    
    /**
     * 整个画布区的最大范围
     */
    public static final Rectangle WORKSPACE_CONSTRAINS = new Rectangle(0, 0,
            3000, 2000);
    public static final int EIGHT = 8;
    public static final int TEN = 10;
    /**
     * 宽高比例
     */
    public static final float WHRATIO = 1.25f;
    private static Rectangle clientArea;
    private static Rectangle containerBounds;
    
    public LayoutUtils() {
    }
    
    /**
     * 获取当前工作区的bounds,主要用于提供宽和高，进而使用{@link LayoutUtils#getContainerBounds()}
     * 计算fid container的较佳显示比例
     * 
     * @return
     */
    public static Rectangle getClientArea() {
        if (clientArea == null) {
            org.eclipse.swt.graphics.Rectangle rect = Display.getDefault()
                    .getClientArea();
            clientArea = new Rectangle(rect.x, rect.y, rect.width, rect.height);
        }
        return clientArea.getCopy();
    }
    
    /**
     * 按照指定比例，获取当前client area使用的background container的size约束，然后根据指定的workspace
     * container的大小计算出x，y位置
     * 
     * @return backgound container的constraint
     */
    public static Rectangle getContainerBounds() {
        if (containerBounds == null) {
            Rectangle clientArea = getClientArea();
            int cHeight = clientArea.height - TEN * 2;
            int cWeight = clientArea.width - EIGHT * 2;
            if (cHeight * WHRATIO > cWeight) {
                cHeight = (int) (cWeight / WHRATIO);
            } else {
                cWeight = (int) (cHeight * WHRATIO);
            }
            int hSpan = (WORKSPACE_CONSTRAINS.width - cWeight) / 2;
            int vSpan = (WORKSPACE_CONSTRAINS.height - cHeight) / 2;
            containerBounds = new Rectangle(hSpan, vSpan, cWeight, cHeight);
        }
        return containerBounds.getCopy();
    }
    
    /**
     * 在坐标现实的时候，如果坐标值太长不能合理的显示，就将其转化为科学技术法显示
     * 
     * @param source
     * @return
     */
    public static String getENotation(double source) {
        DecimalFormat df = new DecimalFormat("0.00E00");
        return df.format(source);
    }
}
