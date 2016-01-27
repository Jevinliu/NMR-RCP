package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Tool;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;

import edu.xmu.nmr.dataanalysis.diagram.tool.ZoomTool;

public class DADefaultEditDomain extends DefaultEditDomain {
    
    private ZoomTool zoomTool;
    
    public DADefaultEditDomain(IEditorPart editorPart) {
        super(editorPart);
    }
    
    @Override public void mouseWheelScrolled(Event event,
            EditPartViewer viewer) {
        if (zoomTool == null) {
            zoomTool = new ZoomTool();
        }
        Tool currentTool = getActiveTool();
        setActiveTool(zoomTool);
        zoomTool.mouseWheelScrolled(event, viewer);
        setActiveTool(currentTool);
        if (currentTool != null)
            currentTool.mouseWheelScrolled(event, viewer);
    }
}
