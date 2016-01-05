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

	public static final int EIGHT = 8;
	public static final int TEN = 10;

	public LayoutUtils() {
	}

	/**
	 * 获取当前工作区的bounds
	 * 
	 * @return
	 */
	public static Rectangle getClientArea() {
		org.eclipse.swt.graphics.Rectangle rect = Display.getDefault()
				.getClientArea();
		return new Rectangle(rect.x, rect.y, rect.width, rect.height);
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
