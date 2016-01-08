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

	public static final Rectangle WORKSPACE_CONSTRAINS = new Rectangle(0, 0,
			3000, 2000);
	public static final int EIGHT = 8;
	public static final int TEN = 10;
	public static final float WHRATIO = 1.25f;
	private static Rectangle clientArea;
	private static Rectangle containerBounds;

	public LayoutUtils() {
	}

	/**
	 * 获取当前工作区的bounds
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
	 * 按照指定比例，获取当前client area使用的container约束
	 * 
	 * @return
	 */
	public static Rectangle getContainerBounds() {
		if (containerBounds == null) {
			Rectangle clientArea = getClientArea();
			int cHeight = clientArea.height - 20;
			int cWeight = clientArea.width - 12;
			float cWHRatio = WHRATIO;
			if (cHeight * cWHRatio > cWeight) {
				cHeight = (int) (cWeight / cWHRatio);
			} else {
				cWeight = (int) (cHeight * cWHRatio);
			}
			int hSpan = (clientArea.width - cWeight) / 2;
			int vSpan = (clientArea.height - cHeight) / 2;
			int cX = hSpan + clientArea.x;
			int cY = vSpan + clientArea.y;
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
