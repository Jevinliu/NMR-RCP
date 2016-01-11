package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.ui.actions.GEFActionConstants;

public class DAZoomOutAction extends DAZoomAction {

	public DAZoomOutAction(DAZoomManager zoomManager) {
		super(GEFMessages.ZoomOut_Label, InternalImages.DESC_ZOOM_OUT,
				zoomManager);
		setId(GEFActionConstants.ZOOM_OUT);
		setToolTipText(GEFMessages.ZoomOut_Tooltip);
		setActionDefinitionId(GEFActionConstants.ZOOM_OUT);
	}

	public void run() {
		zoomManager.zoomOut();
	}

	@Override
	public void zoomChanged(double zoom) {
		setEnabled(true);
	}

}