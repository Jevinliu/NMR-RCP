package edu.xmu.nmr.dataanalysis.diagram.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.editparts.ZoomListener;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

/**
 * 缩放管理器，通过操作模型interval的缩放比例，来影响figure的变化
 * 
 * @author software
 *
 */
public class DAZoomManager {

	/**
	 * 记录当前缩放比例，
	 */
	private double zoom = 1.0;
	private double[] zoomLevels = { 0.5, 0.75, 1.0, 1.5, 2.0, 4.0 };
	private FidData fidData;
	private List<ZoomListener> listeners = new ArrayList<ZoomListener>();

	public DAZoomManager(FidData fidData) {
		this.fidData = fidData;
	}

	public void addZoomListener(ZoomListener listener) {
		listeners.add(listener);
	}

	public void removeZoomListener(ZoomListener listener) {
		listeners.remove(listener);
	}

	public double getMaxZoom() {
		return getZoomLevels()[getZoomLevels().length - 1];
	}

	public double getMinZoom() {
		return getZoomLevels()[0];
	}

	public double[] getZoomLevels() {
		return zoomLevels;
	}

	/**
	 * 找出下一个缩放比例，当没有比当前缩放比例大的zoom时，选取原始缩放比例
	 * 
	 * @return
	 */
	public double getNextZoomLevel() {
		for (int i = 0; i < zoomLevels.length; i++) {
			if (zoomLevels[i] > zoom)
				return zoomLevels[i];
		}
		return getMinZoom();
	}

	public double getPreviousZoomLevel() {
		for (int i = 0; i < zoomLevels.length; i++)
			if (zoomLevels[i] >= zoom) {
				if (i == 0) {
					break;
				}
				return zoomLevels[i - 1];
			}
		return getMaxZoom();
	}

	public double getZoom() {
		return zoom;
	}

	/**
	 * 设置zoom level给指定的value，
	 * 
	 * @param zoom
	 */
	protected void primSetZoom(double zoom) {
		double prevZoom = this.zoom;
		this.zoom = zoom;
		fireZoomChanged();
		fidData.setVIntervalScale(zoom / prevZoom);
	}

	protected void fireZoomChanged() {
		Iterator<ZoomListener> iter = listeners.iterator();
		while (iter.hasNext())
			((ZoomListener) iter.next()).zoomChanged(zoom);
	}

	public FidData getFidData() {
		return fidData;
	}

	/**
	 * 设置zoom level到指定值，如果zoom 出了范围，它会被忽略
	 * 
	 * @param zoom
	 *            新的zoom level
	 */
	public void setZoom(double zoom) {
		zoom = Math.min(getMaxZoom(), zoom);
		zoom = Math.max(getMinZoom(), zoom);
		if (this.zoom != zoom)
			primSetZoom(zoom);
	}

	public void setZoomLevels(double[] zoomLevels) {
		this.zoomLevels = zoomLevels;
	}

	/**
	 * 放大
	 */
	public void zoomIn() {
		System.out.println("zoom in");
		setZoom(getNextZoomLevel());
	}

	/**
	 * 缩小
	 */
	public void zoomOut() {
		System.out.println("zoom out");
		setZoom(getPreviousZoomLevel());
	}
}
