package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;

public class BackgroundFigure extends Figure {

	/**
	 * 背景容器中间矩形框距离四周的留白
	 */
	public static final int SPAN = LayoutUtils.TEN;

	public BackgroundFigure() {
		setBorder(new LineBorder(ColorConstants.black, 2));
		setForegroundColor(ColorConstants.lightGray);
		setBackgroundColor(ColorConstants.white);
		setOpaque(true);
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		// graphics.pushState();
		Rectangle bounds = getBounds();
		int span = SPAN;
		graphics.drawRectangle(bounds.x + span, bounds.y + span, bounds.width
				- 2 * span, bounds.height - 2 * span);
		// graphics.pushState();
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}
}
