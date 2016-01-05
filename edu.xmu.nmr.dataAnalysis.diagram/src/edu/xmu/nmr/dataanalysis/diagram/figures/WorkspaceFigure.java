package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;

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

}
