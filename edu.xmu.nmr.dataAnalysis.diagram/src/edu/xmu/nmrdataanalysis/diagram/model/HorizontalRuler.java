package edu.xmu.nmrdataanalysis.diagram.model;

public class HorizontalRuler extends Ruler {

	/**
	 * 绘制图形的原始数据点数
	 */
	private int dataSize;
	/**
	 * 原始数据点间的间隔，如采样时间
	 */
	private float rawStepSize;

	public HorizontalRuler() {
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public float getRawStepSize() {
		return rawStepSize;
	}

	public void setRawStepSize(float rawStepSize) {
		this.rawStepSize = rawStepSize;
	}

	public void setBasicInfo(int dataSize, float rawStepSize) {
		this.dataSize = dataSize;
		this.rawStepSize = rawStepSize;
		setStepSize(this.dataSize * this.rawStepSize * getInterval()
				/ getLayout().width);
	}
}
