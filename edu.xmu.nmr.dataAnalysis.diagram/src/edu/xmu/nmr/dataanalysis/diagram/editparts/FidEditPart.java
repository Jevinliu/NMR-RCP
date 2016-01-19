package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.LineFigure;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class FidEditPart extends DAAbstractEditPart {
    
    public FidEditPart() {
    }
    
    @Override protected IFigure createFigure() {
        IFigure fidFigure = new LineFigure();
        return fidFigure;
    }
    
    @Override public void refreshVisuals() {
        LineFigure figure = (LineFigure) getFigure();
        FidData fidData = (FidData) getModel();
        figure.setGridLayout();
        figure.setRawData(fidData.getRawData()); // 模型层与view层结合，装填数据
        figure.setAbsMax(fidData.getAbsMax());
        figure.setVInterval(fidData.getVInterval());
        figure.setHInterval(fidData.getHInterval());
        figure.setVScale(fidData.getVScale());
        figure.setOffsetY(fidData.getOffsetY());
        figure.setOffsetX(fidData.getOffsetX());
        figure.setHScale(fidData.getHScale());
        figure.repaint();
    }
    
    @Override protected void createEditPolicies() {
    
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        String[] eventsName = new String[] { FidData.PRO_FD_FIDDATA,
                FidData.PRO_FD_STEPSIZE, FElement.PRO_FE_LAYOUT,
                FidData.PRO_FD_VINTERVAL, FidData.PRO_FD_OFFSETY,
                FidData.PRO_FD_PART_ZOOM };
        for (String eventName : eventsName) {
            if (evt.getPropertyName().equals(eventName)) {
                refreshVisuals();
                return;
            }
        }
    }
}
