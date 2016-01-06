package edu.xmu.nmr.dataanalysis.diagram.layouts;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.figures.BackgroundFigure;
import edu.xmu.nmr.dataanalysis.diagram.figures.FidContainerFigure;

public class MyXYLayout extends XYLayout {

	public MyXYLayout() {
	}

	@Override
	public void layout(IFigure parent) {
		super.layout(parent);
		Iterator children = parent.getChildren().iterator();
		IFigure f;
		while (children.hasNext()) {
			f = (IFigure) children.next();
			if (f instanceof FidContainerFigure
					|| f instanceof BackgroundFigure) {
				Rectangle bounds = f.getBounds();
				Rectangle pBounds = parent.getBounds();
				int hSpan = (pBounds.width - bounds.width) / 2;
				int vSpan = (pBounds.height - bounds.height) / 2;
				f.setBounds(new Rectangle(hSpan + pBounds.x, vSpan + pBounds.y,
						bounds.width, bounds.height));
			}
		}
	}
}
