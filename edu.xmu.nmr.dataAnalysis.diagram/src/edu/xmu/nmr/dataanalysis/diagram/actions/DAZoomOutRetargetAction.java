package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;

public class DAZoomOutRetargetAction extends RetargetAction {
    
    public DAZoomOutRetargetAction() {
        super(null, null);
        setText(GEFMessages.ZoomOut_Label);
        setId(DAActionConstants.ZOOM_OUT);
        setToolTipText(GEFMessages.ZoomOut_Tooltip);
        setImageDescriptor(InternalImages.DESC_ZOOM_OUT);
        setActionDefinitionId(DAActionConstants.ZOOM_OUT);
    }
}
