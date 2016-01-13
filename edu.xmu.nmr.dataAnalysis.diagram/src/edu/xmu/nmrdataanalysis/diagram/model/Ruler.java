package edu.xmu.nmrdataanalysis.diagram.model;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;

/**
 * 坐标轴模型层
 * 
 * @author Jevin
 *
 */
public class Ruler extends FElement {

	public static final int DEFAULT_INTERVAL = 40;
	/**
	 * 坐标轴位于的方向，
	 */
	private RulerOrient orient;
	/**
	 * 当前坐标节点间像素间隔
	 */
	private int interval;
	/**
	 * 当前像素间隔下所代表的实际间隔值，即坐标节点对应数值的差值
	 */
	private float stepSize;
	/**
	 * 该坐标轴所代表的值域
	 */
	private float totalSize;

	/**
	 * 坐标数值的倍数，初始时为1，之后根据{@link DAZoomManager}的MaxZoomLevel计算相应的幂次
	 */
	private double totalScale = 1;

	public static final String PRO_RULER_STEPSIZE = "proRulerStepSize";
	public static final String PRO_RULER_INTERVAL = "proRulerInterval";
	public static final String PRO_RULER_TOTALSIZE = "proRulerTotalSize";
	/**
	 * 坐标轴Figure占据的长度
	 */
	public static final int AXISLL = 75;

	public Ruler() {
		interval = DEFAULT_INTERVAL;
	}

	public RulerOrient getOrient() {
		return orient;
	}

	public void setOrient(RulerOrient orient) {
		this.orient = orient;
	}

	public int getInterval() {
		return interval;
	}

	public double getTotalScale() {
		return totalScale;
	}

	public void setTotalScale(double totalScale) {
		this.totalScale = totalScale;
	}

	public void setIntervalScale(double totalScale, double factor) {
		setTotalScale(totalScale);
		setInterval((int) Math.floor(factor * this.interval));
	}

	public void setInterval(int interval) {
		int old = this.interval;
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

	// /**
	// * 获取当前像素间隔对应的真实间隔下所表示的比例
	// *
	// * @return
	// */
	// public float getScale() {
	// return this.stepSize / (this.interval * 1.0f);
	// }

	public float getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(float totalSize) {
		float old = this.totalSize;
		this.totalSize = totalSize;
		getListeners().firePropertyChange(PRO_RULER_TOTALSIZE, old,
				this.totalSize);
	}

}
