package edu.xmu.nmr.dataanalysis.diagram.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Geometry;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;
import edu.xmu.nmr.dataanalysis.diagram.layouts.CoordinateTf;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DataAnalysisPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DataAnalysisPrefPageUtil;

/**
 * <p>
 * 绘制Fid曲线的figure类，实现类似{@link AbstractPointListShape}
 * </p>
 * 
 * @author software
 * @see AbstractPointListShape
 */
public class LineFigure extends Figure {

	private ArrayList<Float> rawData; // 原始数据，如fid数据，proc数据
	private float absMax;
	private int tolerance = 2;
	private CoordinateTf ctf;
	private ArrayList<Integer> selectedIndex;
	PointList points = new PointList();

	public LineFigure() {
		setOpaque(false);
		getInitConfig();
		ctf = new CoordinateTf();
		addPreferenceListener();
	}

	public LineFigure(ArrayList<Float> rawData) {
		this();
		this.rawData = rawData;

	}

	/**
	 * 获取偏好页中的配置，设置绘图方式
	 */
	private void getInitConfig() {
		boolean isBorder = DataAnalysisPrefPageUtil.getValueOfFidBorderCheck();
		RGB foreColor = DataAnalysisPrefPageUtil.getValueOfFidForeColor();
		RGB backColor = DataAnalysisPrefPageUtil.getValueOfFidBackColor();
		if (isBorder) {
			setBorder(new LineBorder(ColorConstants.lightGray, 1));
		}
		setForegroundColor(new Color(null, foreColor));
		setBackgroundColor(new Color(null, backColor));
	}

	/**
	 * 每次在设置完数据后，都进行数据点的筛选
	 * 
	 * @param rawData
	 */
	public void setRawData(ArrayList<Float> rawData) {
		this.rawData = rawData;
	}

	public void setAbsMax(float max) {
		this.absMax = max;
	}

	/**
	 * 设置坐标转换标准
	 */
	private void setupCoordinateTf() {
		Rectangle rect = getBounds();
		if (rect == null) {
			return;
		}
		ctf.setxOffset(rect.x);
		ctf.setyOffset(rect.y);
		ctf.setxScale((float) (rect.width * 1 / (float) rawData.size()));
		ctf.setyScale(rect.height / (2 * absMax));
	}

	public CoordinateTf getCoordinateTf() {
		return ctf;
	}

	/**
	 * 在x值相同时，取出当前位置的原始数据的最大值，最小值，其他该位置上的值舍弃
	 */
	private void selectPoints() {

		if (selectedIndex != null && !selectedIndex.isEmpty()) {
			return;
		}
		if (rawData == null || rawData.size() == 0) {
			return;
		}
		selectedIndex = new ArrayList<Integer>();
		ArrayList<Integer> sameXIndex = new ArrayList<Integer>(); // 记录当下一组x坐标转换后相同的数据点的索引
		Comparator<Integer> c = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				float v1 = rawData.get(o1);
				float v2 = rawData.get(o2);
				if (v1 > v2)
					return 1;
				else if (v1 < v2)
					return -1;
				else
					return 0;
			}
		};
		int tempX = 0;
		for (int i = 0; i < rawData.size(); i++) {
			if (sameXIndex.size() == 0)
				tempX = ctf.transfromX(i);
			else if (Math.abs(tempX - ctf.transfromX(i)) > 0) { // 记录x相同的一系列点
				int maxValueIndex = Collections.max(sameXIndex, c); // 记录值最大的索引
				int minValueIndex = Collections.min(sameXIndex, c); // 记录数据值最小的索引
				if (ctf.transformY(absMax - rawData.get(maxValueIndex)) == ctf
						.transformY(absMax - rawData.get(minValueIndex))) // 如果转换后纵坐标相等，只需要添加一个
					selectedIndex.add(minValueIndex);
				else {
					if (maxValueIndex > minValueIndex) {
						selectedIndex.add(minValueIndex);
						selectedIndex.add(maxValueIndex);
					} else {
						selectedIndex.add(maxValueIndex);
						selectedIndex.add(minValueIndex);
					}
				}
				sameXIndex.clear();
				tempX = ctf.transfromX(i);
			}
			sameXIndex.add(i);
		}
	}

	/**
	 * 将选择的数据点转换为指定区域下的像素点
	 */
	private void transformPoints() {
		this.setupCoordinateTf(); // 主要用于设置相对位移
		selectPoints();
		PointList pl = new PointList();
		for (int i : selectedIndex) {
			Point p = ctf.transformXY(i, absMax - rawData.get(i)); // 将0值作为整个fidfigure的纵向中心，
			pl.addPoint(p);
		}
		this.points = pl;
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}

	public void setGridLayout() {
		getParent().setConstraint(this,
				new GridData(GridData.FILL, GridData.FILL, true, true, 4, 4));
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		transformPoints();
		graphics.pushState();
		boolean isAdvanced = graphics.getAdvanced();
		graphics.setAdvanced(true);
		graphics.setAntialias(SWT.ON);
		graphics.drawPolyline(points);
		graphics.setAdvanced(isAdvanced);
		graphics.popState();
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (!super.containsPoint(x, y)) {
			return false;
		}
		return shapeContainsPoint(x, y) || childrenContainsPoint(x, y);
	}

	protected boolean shapeContainsPoint(int x, int y) {
		Point location = getLocation();
		return Geometry.polylineContainsPoint(points, x - location.x, y
				- location.y, tolerance);
	}

	protected boolean childrenContainsPoint(int x, int y) {
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			IFigure nextChild = (IFigure) it.next();
			if (nextChild.containsPoint(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 为preference 加入监听器，当preference发生变化时，刷新页面
	 */
	private void addPreferenceListener() {
		IPreferenceStore ips = Activator.getDefault().getPreferenceStore();
		ips.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {

				switch (event.getProperty()) {
				case DataAnalysisPrefConstants.FID_PREF_FOREGROUND_COLOR:
					setForegroundColor(new Color(null, (RGB) event
							.getNewValue()));
					break;
				case DataAnalysisPrefConstants.FID_PREF_BACHGROUND_COLOR:
					setBackgroundColor(new Color(null, (RGB) event
							.getNewValue()));
					break;
				case DataAnalysisPrefConstants.FID_PREF_HAVE_BORDER:
					boolean isBorder = (boolean) event.getNewValue();
					if (isBorder) {
						setBorder(new LineBorder(ColorConstants.lightGray, 1));
					} else {
						setBorder(null);
					}
					break;
				}
				repaint();
			}
		});
	}
}
