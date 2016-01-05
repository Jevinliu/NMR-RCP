package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.layouts.CoordinateTf;

public class FidData extends FElement {

	private ArrayList<Float> rawData; // 原始数据，如fid数据，proc数据
	private float absMax;
	/**
	 * 坐标变换器，用于在fid的figure中数据经过坐标变换后的值
	 */
	private CoordinateTf ctf;
	public static final String PRO_LS_FIDDATA = "pro_ls_fiddata";
	public static final String PRO_LS_STEPSIZE = "pro_ls_stepsize";

	public FidData() {
		ctf = new CoordinateTf();
	}

	public ArrayList<Float> getRawData() {
		return rawData;
	}

	public void setRawData(ArrayList<Float> rawData) {
		ArrayList<Float> old = this.rawData;
		this.rawData = rawData;
		setupCoordinateTf();
		getListeners().firePropertyChange(PRO_LS_FIDDATA, old, this.rawData);
	}

	public CoordinateTf getCoordinateTf() {
		return ctf;
	}

	/**
	 * 根据输入的值，装配坐标变换的标准
	 */
	private void setupCoordinateTf() {
		if (ctf == null) {
			return;
		}
		if (absMax == 0) {
			return;
		}
		Rectangle rect = getLayout();
		if (rect == null) {
			return;
		}
		ctf.setxOffset(0);
		ctf.setyOffset(0);
		ctf.setxScale((float) (rect.width * 1 / (float) rawData.size()));
		ctf.setyScale(rect.height / (2 * absMax));
	}

	public void setCoordinateTf(CoordinateTf coordinateTf) {
		this.ctf = coordinateTf;
	}

	public float getAbsMax() {
		return absMax;
	}

	public void setAbsMax(float absMax) {
		this.absMax = absMax;
	}

}
