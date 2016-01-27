package edu.xmu.nmr.dataanalysis.diagram.requests;

import org.eclipse.gef.Request;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;

public class ShowFullRequest extends Request {
    
    private DAZoomManager zoomManager;
    
    public ShowFullRequest() {
        setType(DARequestConstants.DA_REQ_SHOW_FULL);
    }
    
    public DAZoomManager getZoomManager() {
        return zoomManager;
    }
    
    public void setZoomManager(DAZoomManager zoomManager) {
        this.zoomManager = zoomManager;
    }
}
