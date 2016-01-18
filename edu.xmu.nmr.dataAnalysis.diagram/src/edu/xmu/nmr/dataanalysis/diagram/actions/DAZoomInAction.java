package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.ui.actions.GEFActionConstants;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;

public class DAZoomInAction extends DAZoomAction {
    
    public DAZoomInAction(DAZoomManager zoomManager) {
        super(GEFMessages.ZoomIn_Label, InternalImages.DESC_ZOOM_IN,
                zoomManager);
        setToolTipText(GEFMessages.ZoomIn_Tooltip);
        setId(GEFActionConstants.ZOOM_IN);
        setActionDefinitionId(DAActionConstants.DA_ZOOM_IN);
    }
    
    @Override public void run() {
        zoomManager.zoomIn();
    }
    
    @Override public void zoomChanged(double zoom) {
        setEnabled(true);
    }
    
}
