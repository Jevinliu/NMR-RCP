package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

public class Utils {

	public static String getArrayListToStringTrim(ArrayList input) {
		if (input != null) {
			String vs = input.toString();
			return vs.substring(1, vs.length() - 1);
		}
		return null;
	}

	/**
	 * 
	 * @param input
	 *            String 从其他源读取的字符串，形式为2.0,3.0
	 */
	public static ArrayList<String> setStringTrimToArrayList(String input) {
		if (input != null) {
			String[] vs = input.split(",");
			if (vs.length > 0) {
				ArrayList<String> res = new ArrayList<String>();
				for (String s : vs) {
					res.add(s);
				}
				return res;
			}
		}
		return null;
	}

	public static ArrayList<Double> setStringTrimToArrayListDouble(String input) {
		if (input != null) {
			String[] vs = input.split(",");
			if (vs.length > 0) {
				ArrayList<Double> res = new ArrayList<Double>();
				for (String s : vs) {
					if (s == null || s.equals(""))
						return null;
					res.add(Double.parseDouble(s));
				}
				return res;
			}
		}
		return null;
	}
}
