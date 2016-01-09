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

	/**
	 * 设置水平坐标轴的最基本信息，如传入数据的点数，以及数据点间表达的作用间隔，如采样时间； 根据基本信息算出坐标轴的值域
	 * 
	 * @param dataSize
	 *            数据点数，
	 * @param rawStepSize
	 *            原始间隔
	 */
	public void setBasicInfo(int dataSize, float rawStepSize) {
		this.dataSize = dataSize;
		this.rawStepSize = rawStepSize;
		setTotalSize(this.dataSize * this.rawStepSize);
	}

	/**
	 * 由预知的基本信息，计算经过转换后的坐标间的步长
	 */
	public void setStepSizeByBasicInfo() {
		if (getLayout() == null || getLayout().width == 0) {
			log.error("Ruler's layout is null or it's width is 0.");
			return;
		}
		setStepSize(this.dataSize * this.rawStepSize * getInterval()
				/ getLayout().width);
	}
}
