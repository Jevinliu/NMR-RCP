package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomManager;

public class FidData extends FElement {

	/**
	 * 原始数据，如fid数据，proc数据
	 */
	private ArrayList<Float> rawData;
	/**
	 * 原始数据中绝对值的最大者
	 */
	private float absMax;
	/**
	 * 水平方向网格间隔，和ruler保持一致
	 */
	private int hInterval;

	/**
	 * 竖直方向网格间隔，和竖直ruler保持一致
	 */
	private int vInterval;
	public static final String PRO_LS_FIDDATA = "pro_ls_fiddata";
	public static final String PRO_LS_STEPSIZE = "pro_ls_stepsize";
	public static final String PRO_LS_INTERVAL = "pro_ls_interval";

	public FidData() {
	}

	public ArrayList<Float> getRawData() {
		return rawData;
	}

	public void setRawData(ArrayList<Float> rawData) {
		ArrayList<Float> old = this.rawData;
		this.rawData = rawData;
		getListeners().firePropertyChange(PRO_LS_FIDDATA, old, this.rawData);
	}

	public float getAbsMax() {
		return absMax;
	}

	public void setAbsMax(float absMax) {
		this.absMax = absMax;
	}

	public int getHInterval() {
		return hInterval;
	}

	public void setHInterval(int hInterval) {
		this.hInterval = hInterval;
	}

	public int getVInterval() {
		return vInterval;
	}

	public void setVInterval(int vInterval) {
		this.vInterval = vInterval;
	}

	/**
	 * 设置当前fid data的纵坐标间隔的比例，根据设置比例，生成最新的纵坐标间隔，主要在{@link DAZoomManager}中使用，
	 * 
	 * @param factor
	 * @return
	 */
	public void setVIntervalScale(double factor) {
		int old = this.vInterval;
		this.vInterval = (int) Math.floor(factor * vInterval);
		System.out.println("old: " + old + ", new :" + this.vInterval);
		getListeners().firePropertyChange(PRO_LS_INTERVAL, old, this.vInterval);
	}
}
