package edu.xmu.nmr.dataanalysis.diagram.others;

public class MathUtils {

	/**
	 * 获取某个数据以已知数为底时，不大于原数据的最大次幂.
	 * 
	 * @param baseNumber
	 *            底数，如果底数为baseNumber的倒数，自行纠正，其他情况错误
	 * @param sourceNumber
	 *            原数据
	 */
	public static int getPowerInteger(double baseNumber, double sourceNumber) {
		int count = 0;
		double start = 1;
		if (sourceNumber >= 1) {
			if (baseNumber < 1) {
				baseNumber = 1 / baseNumber;
			}
			while (start <= sourceNumber) {
				start *= baseNumber;
				count++;
			}
			count--;
		} else {
			if (baseNumber > 1) {
				baseNumber = 1 / baseNumber;
			}
			while (start >= sourceNumber) {
				start *= baseNumber;
				count++;
			}
			count--;
		}
		return count;
	}
}
