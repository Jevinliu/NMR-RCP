package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmr.dataanalysis.diagram.layouts.MyXYLayout;

public class WorkspaceFigure extends Figure {

	private MyXYLayout layout;

	public WorkspaceFigure() {
		layout = new MyXYLayout();
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
		// graphics.setForegroundColor(ColorConstants.lightGray);
		// graphics.setBackgroundColor(ColorConstants.white);
		// graphics.drawRectangle(x, y, width, height);
		// graphics.fillRectangle(x, y, width, height);
		int fillX = bounds.x + bounds.width / 2 - clientArea.width / 2;
		int fillY = bounds.y + bounds.height / 2 - clientArea.height / 2;
		// graphics.fillRectangle(fillX, fillY, clientArea.width,
		// clientArea.height);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}
}
