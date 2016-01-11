package edu.xmu.nmr.dataanalysis.diagram.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.editparts.ZoomListener;

import edu.xmu.nmr.dataanalysis.diagram.others.MathUtils;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

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
	private double[] zoomLevels = { 1.0, 1.25, 1.5, 1.75, 2.0, 3.0, 4.0 };
	private FidData fidData;
	private Ruler ruler;
	private List<ZoomListener> listeners = new ArrayList<ZoomListener>();

	/**
	 * 总的缩放比例
	 */
	private double totalScale = 1.0;

	public DAZoomManager(FidData fidData, Ruler ruler) {
		this.fidData = fidData;
		this.ruler = ruler;
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
		// 根据zoom值自行判断是zoom in还是zoom out，方便以后使用setZoom()
		// zoom in时计算总的totalScale，用于fid的显示,此处边界值极为重要
		double factor = zoom / prevZoom;
		if ((zoom == getMinZoom() && prevZoom == getMaxZoom())
				|| (zoom != getMaxZoom() && zoom > prevZoom)
				|| (zoom == getMaxZoom() && prevZoom != getMinZoom())) {
			if (zoom != getMinZoom()) {
				totalScale = totalScale * factor;
			}
		} else {// zoom out 时计算totalScale，用于fid显示
			double preTemp = prevZoom / getMaxZoom();
			double temp = zoom / getMaxZoom();
			if (temp != 1) {
				totalScale = totalScale * temp / preTemp;
			}
		}
		System.out.println("prevZoom: " + prevZoom + ", zoom: " + zoom);
		System.out.println("totalScale: " + totalScale);
		fidData.setVIntervalScale(totalScale, factor);
		if (ruler != null) {
			int power = MathUtils.getPowerInteger(getMaxZoom(), totalScale);
			System.out.println("power: " + power);
			double scaleByPower;
			if (totalScale > 1) {
				scaleByPower = Math.pow(getMaxZoom(), power);
			} else {
				scaleByPower = Math.pow(1 / getMaxZoom(), power);
			}
			System.out.println("scaleByPower: " + scaleByPower);
			ruler.setIntervalScale(scaleByPower, factor);
		}
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
		setZoom(getNextZoomLevel());
	}

	/**
	 * 缩小
	 */
	public void zoomOut() {
		setZoom(getPreviousZoomLevel());
	}
}
