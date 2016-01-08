package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.editpolicys.EditLayoutPolicy;
import edu.xmu.nmr.dataanalysis.diagram.figures.WorkspaceFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;

public class WorkspaceEditPart extends NMRAbstractEditPart {

	public WorkspaceEditPart() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}

	@Override
	protected IFigure createFigure() {
		IFigure wsf = new WorkspaceFigure();
		return wsf;
	}

	@Override
	protected void refreshVisuals() {
		WorkspaceFigure figure = (WorkspaceFigure) getFigure();
		Container container = (Container) getModel();
		figure.setLayout(container.getLayout());
	}

	@Override
	protected List getModelChildren() {
		return ((Container) getModel()).getChildren();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EditLayoutPolicy());
	}
}
