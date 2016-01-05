package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

public class FidData extends FElement {

	private ArrayList<Float> rawData; // 原始数据，如fid数据，proc数据
	private float rawStepSize; // 主要用于采样时间
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

	public float getRawStepSize() {
		return rawStepSize;
	}

	public void setRawStepSize(float rawStepSize) {
		float old = this.rawStepSize;
		this.rawStepSize = rawStepSize;
		getListeners().firePropertyChange(PRO_LS_STEPSIZE, old,
				this.rawStepSize);
	}
}
