package edu.xmu.nmr.dataanalysis.diagram.handlers;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.swt.widgets.Event;

import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomManager;

public class DAMouseWheelZoomHandler implements MouseWheelHandler {

	public static final MouseWheelHandler SINGLETON = new DAMouseWheelZoomHandler();

	private DAMouseWheelZoomHandler() {
	}

	@Override
	public void handleMouseWheel(Event event, EditPartViewer viewer) {
		DAZoomManager zoomMgr = (DAZoomManager) viewer
				.getProperty(DAZoomManager.class.toString());
		if (zoomMgr != null) {
			if (event.count > 0)
				zoomMgr.zoomIn();
			else
				zoomMgr.zoomOut();
			event.doit = false;
		}
	}

}
