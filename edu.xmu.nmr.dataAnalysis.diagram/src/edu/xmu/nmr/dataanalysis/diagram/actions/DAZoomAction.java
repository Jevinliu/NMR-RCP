package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.Disposable;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public abstract class DAZoomAction extends Action implements ZoomListener,
		Disposable {

	protected DAZoomManager zoomManager;

	public DAZoomAction(String text, ImageDescriptor image,
			DAZoomManager zoomManager) {
		super(text, image);
		this.zoomManager = zoomManager;
		zoomManager.addZoomListener(this);
	}

	public void dispose() {
		zoomManager.removeZoomListener(this);
	}

}
