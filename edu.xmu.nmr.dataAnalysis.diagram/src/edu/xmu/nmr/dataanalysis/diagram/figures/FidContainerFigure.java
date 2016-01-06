package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class FidContainerFigure extends Figure {

	private XYLayout layout;

	public FidContainerFigure() {

		layout = new XYLayout();
		setLayoutManager(layout);
		setBorder(null);
		setOpaque(false);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}

}
