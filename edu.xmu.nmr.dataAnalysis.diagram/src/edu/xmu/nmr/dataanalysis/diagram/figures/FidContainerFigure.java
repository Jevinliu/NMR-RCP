package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class FidContainerFigure extends Figure {

	public FidContainerFigure() {

		GridLayout layout = new GridLayout(5, false);
		layout.marginHeight = 8;
		layout.marginWidth = 8;
		layout.horizontalSpacing = 3;
		layout.verticalSpacing = 3;
		setLayoutManager(layout);
		setBorder(null);
		setOpaque(false);
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}

}
