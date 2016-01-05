package edu.xmu.nmrdataanalysis.diagram.model;

public class VerticalRuler extends Ruler {

	/**
	 * 原始数据的值的绝对值的最大值
	 */
	private float absMax;

	public VerticalRuler() {
	}

	public float getAbsMax() {
		return absMax;
	}

	public void setAbsMax(float absMax) {
		this.absMax = absMax;
		setStepSizeByHeight();
	}

	/**
	 * 在纵坐标的情况下，根据真实数据的绝对值的最大值的2倍计算出在当前像素间隔下的坐标值间隔，即步长，
	 * 
	 * @param height
	 */
	private void setStepSizeByHeight() {
		setStepSize(this.absMax * 2 * getInterval() / getLayout().height);
	}

}
