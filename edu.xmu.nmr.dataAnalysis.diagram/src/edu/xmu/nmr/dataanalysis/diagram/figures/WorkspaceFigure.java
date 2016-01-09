package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;

public class WorkspaceFigure extends Figure {

	public WorkspaceFigure() {
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.gray, 2));
		setBackgroundColor(ColorConstants.lightGray);
		setOpaque(true);
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle bounds = getBounds();
		Rectangle clientArea = LayoutUtils.getContainerBounds();
		int span = LayoutUtils.TEN;
		int width = clientArea.width - 2 * span;
		int height = clientArea.height - 2 * span;
		int x = bounds.x + bounds.width / 2 - width / 2;
		int y = bounds.y + bounds.height / 2 - height / 2;
		graphics.setBackgroundColor(ColorConstants.white);
		int fillX = bounds.x + bounds.width / 2 - clientArea.width / 2;
		int fillY = bounds.y + bounds.height / 2 - clientArea.height / 2;
		graphics.fillRectangle(fillX, fillY, clientArea.width,
				clientArea.height);
		// 最后绘制中间的矩形框，防止被背景覆盖
		graphics.setForegroundColor(ColorConstants.lightGray);
		graphics.drawRectangle(x, y, width, height);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}
}
