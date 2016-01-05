package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.FidContainerFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;

public class FidContainerEditPart extends NMRAbstractEditPart {

	public FidContainerEditPart() {
	}

	@Override
	protected IFigure createFigure() {
		IFigure figure = new FidContainerFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {

	}

	@Override
	protected void refreshVisuals() {
		FidContainerFigure figure = (FidContainerFigure) getFigure();
		Container container = (Container) getModel();
		figure.setLayout(container.getLayout());
	}

	@Override
	protected List getModelChildren() {
		return ((Container) getModel()).getChildren();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

}
