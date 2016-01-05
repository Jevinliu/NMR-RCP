package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;

import edu.xmu.nmr.dataanalysis.diagram.layouts.MyXYLayout;

public class FidContainerFigure extends Figure {

	private MyXYLayout layout;

	public FidContainerFigure() {

		layout = new MyXYLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.gray, 2));
		setOpaque(true);
	}
}
