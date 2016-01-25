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
        figure.setVScale(fidData.getVScale());
        figure.setOffsetY(fidData.getOffsetY());
        figure.setOffsetX(fidData.getOffsetX());
        figure.setHScale(fidData.getHScale());
        if (fidData.getYAxis() != null) {
            figure.setVInterval(
                    (int) Math.floor(fidData.getYAxis().get("gap")));
        }
        if (fidData.getXAxis() != null) {
            figure.setHInterval(
                    (int) Math.floor(fidData.getXAxis().get("gap")));
        }
        figure.repaint();
    }
    
    @Override protected void createEditPolicies() {
    
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        String[] eventsName = new String[] { FidData.PRO_FD_FIDDATA,
                FElement.PRO_FE_LAYOUT, FidData.PRO_FD_OFFSETY,
                FidData.PRO_FD_OFFSETX, FidData.PRO_FD_YAXIS,
                FidData.PRO_FD_XAXIS };
        for (String eventName : eventsName) {
            if (evt.getPropertyName().equals(eventName)) {
                refreshVisuals();
                return;
            }
        }
    }
}
