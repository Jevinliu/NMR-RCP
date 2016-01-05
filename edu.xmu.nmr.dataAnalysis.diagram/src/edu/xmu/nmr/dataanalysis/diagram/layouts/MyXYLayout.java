package edu.xmu.nmr.dataanalysis.diagram.layouts;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.figures.LineFigure;
import edu.xmu.nmr.dataanalysis.diagram.figures.RulerFigure;

public class MyXYLayout extends XYLayout {

	public MyXYLayout() {
	}

	@Override
	public void layout(IFigure parent) {
		Iterator children = parent.getChildren().iterator();
		Point offset = getOrigin(parent);
		IFigure f;
		while (children.hasNext()) {
			f = (IFigure) children.next();
			Rectangle bounds = (Rectangle) getConstraint(f);
			if (bounds == null)
				continue;
			if (bounds.width == -1 || bounds.height == -1) {
				Dimension preferredSize = f.getPreferredSize(bounds.width,
						bounds.height);
				bounds = bounds.getCopy();
				if (bounds.width == -1)
					bounds.width = preferredSize.width;
				if (bounds.height == -1)
					bounds.height = preferredSize.height;
			}
			bounds = bounds.getTranslated(offset);

			// 自定义布局
			Rectangle rect = f.getParent().getBounds();
			if (f instanceof LineFigure) { // 画fid时，设置bounds
				bounds = new Rectangle(rect.x + 70, rect.y + 10,
						rect.width - 80, rect.height - 80);
			}
			if (f instanceof RulerFigure) {
				switch (((RulerFigure) f).getOrient()) {
				case LEFT:
					bounds = new Rectangle(rect.x + 10, rect.y + 10, 59,
							rect.height - 80);
					break;
				case BOTTOM:
					bounds = new Rectangle(rect.x + 70, rect.y + rect.height
							- 69, rect.width - 80, 59);
				}
			}
			f.setBounds(bounds);
		}
	}
}
