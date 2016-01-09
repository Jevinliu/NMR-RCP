package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;

import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class PlaceHolderEditPart extends DAAbstractEditPart {

	public PlaceHolderEditPart() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

	@Override
	protected IFigure createFigure() {
		RectangleFigure rf = new RectangleFigure();
		rf.setSize(Ruler.AXISLL, Ruler.AXISLL);
		rf.setAlpha(0);
		return rf;
	}

	@Override
	protected void createEditPolicies() {

	}

	@Override
	protected void refreshVisuals() {
		RectangleFigure rf = (RectangleFigure) getFigure();
		rf.getParent().setConstraint(rf,
				new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));
	}

}
