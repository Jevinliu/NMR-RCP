package edu.xmu.nmrdataanalysis.diagram.model;


/**
 * 坐标轴模型层
 * 
 * @author Jevin
 *
 */
public class Ruler extends FElement {

	/**
	 * 坐标轴位于的方向，
	 */
	private RulerOrient orient;
	private float interval; // 代表缺省的和当前的像素间隔
	private float stepSize; // 代表在缺省的像素间隔下或当前的像素间隔下所代表的实际的间隔
	private float[] zoomScales; // 缩放比例
	private float rawScale; // 每次坐标轴上显示的坐标的真实缩放比例,缺省0.5
	private float absMax;
	private int dataSize;
	private float rawStepSize;
	public static final String PRO_RULER_STEPSIZE = "proRulerStepSize";
	public static final String PRO_RULER_ABSMAX = "proRulerAbsMax";
	public static final String PRO_RULER_INTERVAL = "proRulerInterval";

	public Ruler() {
		interval = 40;
		zoomScales = new float[] { 0.125f, 0.25f, 0.5f, 1f, 1.25f, 1.5f, 1.75f,
				2f };
		rawScale = 0.5f;
	}

	public RulerOrient getOrient() {
		return orient;
	}

	public void setOrient(RulerOrient orient) {
		this.orient = orient;
	}

	/**
	 * 获取最小的像素间隔
	 * 
	 * @return
	 */
	public float getMinInterval() {
		return zoomScales[0] * interval;
	}

	/**
	 * 获取最大的像素间隔
	 * 
	 * @return
	 */
	public float getMaxInterval() {
		return zoomScales[7] * interval;
	}

	public float getInterval() {
		return interval;
	}

	public void setInterval(float interval) {
		float old = this.interval;
		this.interval = interval;
		getListeners().firePropertyChange(PRO_RULER_INTERVAL, old,
				this.interval);
	}

	public float getStepSize() {
		return stepSize;
	}

	public void setStepSize(float stepSize) {
		float old = this.stepSize;
		this.stepSize = stepSize * interval;
		getListeners().firePropertyChange(PRO_RULER_STEPSIZE, old,
				this.stepSize);
	}

	/**
	 * 获取当前像素间隔对应的真实间隔下所表示的比例
	 * 
	 * @return
	 */
	public float getScale() {
		return this.stepSize / (this.interval * 0.1f);
	}

	public float getRawScale() {
		return rawScale;
	}

	public void setRawScale(float rawScale) {
		this.rawScale = rawScale;
	}

	public float getAbsMax() {
		return absMax;
	}

	public void setAbsMax(float absMax) {
		float old = this.absMax;
		this.absMax = absMax;
		getListeners().firePropertyChange(PRO_RULER_ABSMAX, old, this.absMax);
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

}
