package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.editpolicys.DAPartZoomInPolicy;
import edu.xmu.nmr.dataanalysis.diagram.editpolicys.DAYMovePolicy;
import edu.xmu.nmr.dataanalysis.diagram.editpolicys.ShowFullPolicy;
import edu.xmu.nmr.dataanalysis.diagram.editpolicys.ZoomPolicy;
import edu.xmu.nmr.dataanalysis.diagram.figures.FidContainerFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;

public class FidContainerEditPart extends DAAbstractEditPart {
    
    public FidContainerEditPart() {
    }
    
    @Override protected IFigure createFigure() {
        IFigure figure = new FidContainerFigure();
        return figure;
    }
    
    @Override protected void createEditPolicies() {
        installEditPolicy(DAYMovePolicy.ROLE, new DAYMovePolicy());
        installEditPolicy(DAPartZoomInPolicy.ROLE, new DAPartZoomInPolicy());
        installEditPolicy(ZoomPolicy.ROLE, new ZoomPolicy());
        installEditPolicy(ShowFullPolicy.ROLE, new ShowFullPolicy());
    }
    
    @Override protected void refreshVisuals() {
        FidContainerFigure figure = (FidContainerFigure) getFigure();
        Container container = (Container) getModel();
        figure.setLayout(container.getLayout());
    }
    
    @Override protected List getModelChildren() {
        return ((Container) getModel()).getChildren();
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(FElement.PRO_FE_LAYOUT)) {
            refreshVisuals();
        }
    }
    
}
