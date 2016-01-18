package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;

public class DAZoomInRetargetAction extends RetargetAction {
    
    public DAZoomInRetargetAction() {
        super(null, null);
        setText(GEFMessages.ZoomIn_Label);
        setId(DAActionConstants.DA_ZOOM_IN);
        setToolTipText(GEFMessages.ZoomIn_Tooltip);
        setImageDescriptor(InternalImages.DESC_ZOOM_IN);
        setActionDefinitionId(DAActionConstants.DA_ZOOM_IN);
    }
}
