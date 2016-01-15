package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.FidEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;
import edu.xmu.nmr.dataanalysis.diagram.tool.DAPartZoomTool;

public class DAPartZoomAction extends SelectionAction {
    
    private boolean checked = false;
    
    public DAPartZoomAction(IWorkbenchPart part) {
        super(part);
        setId(DAActionConstants.DA_PART_ZOOM);
        setText("PartZoom");
        setImageDescriptor(DASharedImages.PART_ZOOM_IN);
    }
    
    @Override protected boolean calculateEnabled() {
        IWorkbenchPart part = getWorkbenchPart();
        if (!(part instanceof FidEditorPage)) {
            return false;
        }
        return true;
    }
    
    @Override public boolean isChecked() {
        return checked;
    }
    
    @Override public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    @Override public void run() {
        FidEditorPage editorPage = (FidEditorPage) getWorkbenchPart();
        EditDomain editDomain = editorPage.getGraphicalViewer().getEditDomain();
        if (isChecked()) {
            DAPartZoomTool zoomTool = new DAPartZoomTool();
            editDomain.setActiveTool(zoomTool);
        } else {
            editDomain.setActiveTool(editDomain.getDefaultTool());
        }
    }
    
}
