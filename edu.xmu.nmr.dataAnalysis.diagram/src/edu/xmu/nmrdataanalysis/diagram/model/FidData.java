package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

public class FidData extends FElement {

	/**
	 * 原始数据，如fid数据，proc数据
	 */
	private ArrayList<Float> rawData;
	/**
	 * 原始数据中绝对值的最大者
	 */
	private float absMax;
	public static final String PRO_LS_FIDDATA = "pro_ls_fiddata";
	public static final String PRO_LS_STEPSIZE = "pro_ls_stepsize";

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

}
