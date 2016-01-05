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
	/**
	 * 当前坐标节点间像素间隔
	 */
	private float interval;
	/**
	 * 当前像素间隔下所代表的实际间隔值，即坐标节点对应数值的差值
	 */
	private float stepSize;
	/**
	 * 缩放比例
	 */
	private float[] zoomScales;
	public static final String PRO_RULER_STEPSIZE = "proRulerStepSize";
	public static final String PRO_RULER_INTERVAL = "proRulerInterval";
	/**
	 * 坐标轴坐标的占据像素长度
	 */
	public static final int AXISLL = 75;

	public Ruler() {
		interval = 40;
		zoomScales = new float[] { 0.125f, 0.25f, 0.5f, 1f, 1.25f, 1.5f, 1.75f,
				2f };
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
		this.stepSize = stepSize;
		getListeners().firePropertyChange(PRO_RULER_STEPSIZE, old,
				this.stepSize);
	}

	/**
	 * 获取当前像素间隔对应的真实间隔下所表示的比例
	 * 
	 * @return
	 */
	public float getScale() {
		return this.stepSize / (this.interval * 1.0f);
	}
}
