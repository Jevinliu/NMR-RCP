package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Tool;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;

import edu.xmu.nmr.dataanalysis.diagram.tool.ZoomTool;

public class DADefaultEditDomain extends DefaultEditDomain {
    
    public DADefaultEditDomain(IEditorPart editorPart) {
        super(editorPart);
    }
    
    @Override public void mouseWheelScrolled(Event event,
            EditPartViewer viewer) {
        Tool tool = getDefaultTool();
        Tool currentTool = getActiveTool();
        if (tool instanceof ZoomTool) {
            tool.mouseWheelScrolled(event, viewer);
            setActiveTool(currentTool);
            return;
        }
        tool = getActiveTool();
        if (tool != null)
            tool.mouseWheelScrolled(event, viewer);
    }
}
