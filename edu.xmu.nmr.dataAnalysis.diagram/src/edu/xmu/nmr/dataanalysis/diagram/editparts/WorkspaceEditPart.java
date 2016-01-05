package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.WorkspaceFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;

public class WorkspaceEditPart extends NMRAbstractEditPart {

	public WorkspaceEditPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IFigure createFigure() {
		IFigure wsf = new WorkspaceFigure();
		return wsf;
	}

	@Override
	protected List getModelChildren() {
		return ((Container) getModel()).getChildren();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
