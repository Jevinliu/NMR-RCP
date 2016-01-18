package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.editpolicys.EditLayoutPolicy;
import edu.xmu.nmr.dataanalysis.diagram.figures.WorkspaceFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;

public class WorkspaceEditPart extends DAAbstractEditPart {
    
    public WorkspaceEditPart() {
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(FElement.PRO_FE_LAYOUT)) {
            refreshVisuals();
        }
    }
    
    @Override protected IFigure createFigure() {
        IFigure wsf = new WorkspaceFigure();
        wsf.setBackgroundColor(ColorConstants.red);
        return wsf;
    }
    
    @Override protected void refreshVisuals() {
        WorkspaceFigure figure = (WorkspaceFigure) getFigure();
        Container container = (Container) getModel();
        figure.setLayout(container.getLayout());
    }
    
    @Override protected List getModelChildren() {
        return ((Container) getModel()).getChildren();
    }
    
    @Override protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new EditLayoutPolicy());
    }
}
