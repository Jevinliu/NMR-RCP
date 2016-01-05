package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class FidContainerFigure extends Figure {

	private XYLayout layout;

	public FidContainerFigure() {

		layout = new XYLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.gray, 2));
		setOpaque(true);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}
}
