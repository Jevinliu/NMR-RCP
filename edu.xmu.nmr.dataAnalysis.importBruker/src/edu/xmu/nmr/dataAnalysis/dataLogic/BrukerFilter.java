package edu.xmu.nmr.dataAnalysis.dataLogic;

public class BrukerFilter {

	public static boolean select(String fileName) {
		if (fileName.equals("fid") || fileName.startsWith("acqu")) {
			return true;
		}
		return false;
	}
}
