package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.swt.widgets.Event;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;
import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmr.dataanalysis.diagram.requests.ZoomRequst;

public class ZoomTool extends TargetingTool {
    
    private DAZoomManager zoomMgr;
    
    @Override protected String getCommandName() {
        return DARequestConstants.DA_REQ_ZOOM_IN;
    }
    
    @Override public void mouseWheelScrolled(Event event,
            EditPartViewer viewer) {
        setViewer(viewer);
        performMouseWheel(event, viewer);
        
    }
    
    @Override protected Request createTargetRequest() {
        Request request = new ZoomRequst();
        return request;
    }
    
    @Override protected void updateTargetRequest() {
        ZoomRequst request = (ZoomRequst) getTargetRequest();
        request.setTotalScale(zoomMgr.getTotalScale());
        request.setFactor(zoomMgr.getFactor());
        request.setMultiFactor(zoomMgr.isMultiFactor());
    }
    
    protected boolean handleMove() {
        return true;
    }
    
    private void performMouseWheel(Event event, EditPartViewer viewer) {
        updateTargetUnderMouse();
        EditPart tarEditPart = getTargetEditPart();
        if (!(tarEditPart instanceof FidContainerEditPart)) {
            return;
        }
        zoomMgr = (DAZoomManager) viewer
                .getProperty(DAZoomManager.class.toString());
        if (zoomMgr != null) {
            if (event.count > 0) {
                zoomMgr.zoomIn();
            } else {
                zoomMgr.zoomOut();
            }
            updateTargetRequest();
            unlockTargetEditPart();
            setCurrentCommand(getCommand());
            executeCurrentCommand();
            event.doit = false;
        }
        handleFinished();
    }
    
    @Override protected boolean handleEnteredEditPart() {
        return false;
    }
    
}
