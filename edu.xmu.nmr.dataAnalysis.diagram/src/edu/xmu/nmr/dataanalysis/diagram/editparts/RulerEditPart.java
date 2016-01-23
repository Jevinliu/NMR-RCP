package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.RulerFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class RulerEditPart extends DAAbstractEditPart {
    
    public RulerEditPart() {
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        String[] eventsName = new String[] { Ruler.PRO_RULER_OFFSET,
                Ruler.PRO_RULER_AXIS };
        for (String eventName : eventsName) {
            if (evt.getPropertyName().equals(eventName)) {
                refreshVisuals();
                return;
            }
        }
    }
    
    @Override protected IFigure createFigure() {
        IFigure rulerFigure = new RulerFigure();
        return rulerFigure;
    }
    
    @Override protected void refreshVisuals() {
        RulerFigure figure = (RulerFigure) getFigure();
        Ruler ruler = (Ruler) getModel();
        figure.setOrient(ruler.getOrient());
        figure.setGridLayout();
        figure.setOffset(ruler.getOffset());
        if (ruler.getAxis() != null) {
            figure.setFactor(ruler.getAxis().get("factor"));
            figure.setInterval((int) Math.floor(ruler.getAxis().get("gap")));
            figure.setUnitLabelText(ruler.getAxis().get("exp"));
        }
    }
    
    @Override protected void createEditPolicies() {
    
    }
    
}
