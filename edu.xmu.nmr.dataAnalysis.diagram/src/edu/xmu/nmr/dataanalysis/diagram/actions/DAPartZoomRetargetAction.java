package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

public class DAPartZoomRetargetAction extends RetargetAction {
    
    public DAPartZoomRetargetAction() {
        super(null, null, IAction.AS_CHECK_BOX);
        setId(DAActionConstants.DA_PART_ZOOM);
        setActionDefinitionId(DAActionConstants.DA_PART_ZOOM);
        setText("PartZoom");
        setToolTipText("Part Zoom");
        setImageDescriptor(DASharedImages.PART_ZOOM_IN);
    }
}
