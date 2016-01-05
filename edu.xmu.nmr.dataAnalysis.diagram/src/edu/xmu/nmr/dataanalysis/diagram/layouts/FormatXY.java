package edu.xmu.nmr.dataanalysis.diagram.layouts;

import java.text.DecimalFormat;

/**
 * 将显示的坐标值进行格式化
 * 
 * @author software
 *
 */
public class FormatXY {

	public static String getENotation(double source) {
		DecimalFormat df = new DecimalFormat("0.00E00");
		return df.format(source);
	}
}
