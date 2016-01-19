package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import org.eclipse.jface.action.IAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

public class DAPartZoomInRetargetAction extends DAAbstractRetargetAction {
    
    public DAPartZoomInRetargetAction() {
        super(null, null, IAction.AS_CHECK_BOX);
        setId(DAActionConstants.DA_PART_ZOOM_IN);
        setActionDefinitionId(DAActionConstants.DA_PART_ZOOM_IN);
        setText("PartZoom");
        setToolTipText("Part Zoom");
        setImageDescriptor(DASharedImages.PART_ZOOM_IN);
        
    }
}
